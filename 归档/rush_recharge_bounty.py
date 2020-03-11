#!/usr/bin/python
#-*-coding:utf-8 -*-
import json
import time
import logging
from redis_client import RedisClient
from database_mysql import MySqlDbClient
from logger import Logger

#client = MySqlDbClient('47.92.222.25', 3306, 'futures', 'Mysqlab66', 'root')

class RushBounty(object):
    def __init__(self, db_host, db_port, db_server, db_password, db_user, redis_password):
        self._client = MySqlDbClient(db_host, db_port, db_server, db_password, db_user)
        self._client.conn()
        self._activity_level = self.getActivityLevel()
        self.redis_client = RedisClient(db_host, 6379, 0, redis_password)
        self.logger = Logger("./log/"+ str(time.strftime("%Y_%m_%d_log.txt", time.localtime())))
    def getActivityLevel(self):
        command = "select recharge_money, level, reward, demand from tbl_activity_level ORDER BY `level`"
        result = self._client.execute_select(command)
        activity_level = []
        for item in json.loads(result):
            item_json = {}
            item_json['recharge_money'] = item[0]
            item_json['level'] = item[1]
            item_json['reward'] = item[2]
            item_json['demand'] = item[3]
            activity_level.append(item_json)
        return  activity_level
    def getUsers(self):
        command = "SELECT user_id, first_recharge_amount, max_amount, unlock_amount, state, rule_version from tbl_recharge_bounty where state = -1"
        result = self._client.execute_select(command)
        ret = []
        for item in json.loads(result):
            item_json = {}
            item_json['user_id'] = item[0]
            item_json['first_recharge_amount'] = item[1]
            item_json['max_amount'] = item[2]
            item_json['unlock_amount'] = item[3]
            item_json['state'] = item[4]
            item_json['rule_version'] = item[5]
            ret.append(item_json)
        return  ret
    def getRechargeBountyByUserId(self, user_id):
        command = "SELECT max_amount, unlock_amount, state, rule_version from tbl_recharge_bounty where user_id = " + str(user_id)
        result = self._client.execute_select(command)
        array_ret = json.loads(result)
        if len(array_ret) > 0:
            return array_ret[0]
        else:
            return None
    def getNeedRushTransactionUsers(self):
        command = "SELECT user_id, first_recharge_amount, max_amount, unlock_amount, state, rule_version from tbl_recharge_bounty where state != -1 and finished = 0"
        result = self._client.execute_select(command)
        ret = []
        for item in json.loads(result):
            item_json = {}
            item_json['user_id'] = item[0]
            item_json['first_recharge_amount'] = item[1]
            item_json['max_amount'] = item[2]
            item_json['unlock_amount'] = item[3]
            item_json['state'] = item[4]
            item_json['rule_version'] = item[5]
            ret.append(item_json)
        return  ret
    def getUserFirstRecharge(self, user_id):
        command = "select amount from tbl_account_order where type = 1 and status = 1 and user_id = " + str(user_id) + " ORDER BY create_time LIMIT 1"
        result = self._client.execute_select(command)
        return json.loads(result)
    def setUserActivityLevel(self, user_id, first_recharge_amount, max_amount,unlock_amount, rule_version):
         command ='UPDATE tbl_recharge_bounty  set state = 0, first_recharge_amount = ' + str(first_recharge_amount) + ', max_amount = ' + str(max_amount) + ', unlock_amount = ' + str(unlock_amount) + ', rule_version = "' + str(rule_version) + '" where user_id = ' + str(user_id)
         result = self._client.execute(command)
    def updateUserActivityLevel(self, user_id, unlock_amount, state):
         command = None
         if unlock_amount == 0:
             command ='UPDATE tbl_recharge_bounty  set finished = 1, unlock_amount = ' + str(unlock_amount) + ', state = ' + str(state)  + ' where user_id = ' + str(user_id)
         else:
             command ='UPDATE tbl_recharge_bounty  set  unlock_amount = ' + str(unlock_amount) + ', state = ' + str(state)  + ' where user_id = ' + str(user_id)
         result = self._client.execute(command)
    def sumUserTransactionAmount(self, user_id):
        command = 'select CASE WHEN ISNULL(sum(amount)) THEN 0 ELSE sum(amount) END as sum from tbl_transaction where account_type = 1 and user_id =' + str(user_id)
        result = self._client.execute_select_fun(command)
        #print(str(result[0][0]))
        return long(str(result[0][0]))
    def getUserBountyByUserId(self, user_id):
        command = "SELECT total_amount, amount, version from tbl_user_bounty where user_id = " + str(user_id)
        result = self._client.execute_select(command)
        array_ret = json.loads(result)
        if len(array_ret) > 0:
            return array_ret[0]
        else:
            return None
    def updateUserBounty(self, user_id, amount, total_transaction_amount,version):
        time_version = time.time()
        command ='UPDATE tbl_user_bounty set total_amount = total_amount +  ' + str(amount) +', amount =  amount + ' + str(amount) + ', total_transaction_amount = '+ str(total_transaction_amount) +', version = ' + str(time_version) +' where user_id = ' + str(user_id) + ' and version = "' + version + '"'
        result = self._client.execute(command)
    def updateUserBountyTotalTransaction(self, user_id, total_transaction_amount,version):
        time_version = time.time()
        command ='UPDATE tbl_user_bounty set  total_transaction_amount = '+ str(total_transaction_amount) +', version = ' + str(time_version) +' where user_id = ' + str(user_id) + ' and version = "' + version + '"'
        result = self._client.execute(command)
    def calculateNextAmount(self,rules, amount, first_recharge_amount):
        max_demand_amount = 0
        for rule in rules:
            if long(rule['recharge_money']) <= long(first_recharge_amount):
                max_demand_amount = long(rule['demand'])
        if amount >= max_demand_amount:
            return 0
        for rule in rules:
            if long(rule["demand"]) > long(amount):
                return long(rule["demand"]) - amount
        return  0
    def calculateMaxAmountByDemand(self,rules, demand, first_recharge_amount):
        reward = 0
        ret_demand = 0
        level = 0
        for rule in rules:
            if long(rule["demand"]) <= long(demand) and long(first_recharge_amount) >= long(rule["recharge_money"]):
                reward =  long(rule["reward"])
                ret_demand = long(rule["demand"])
                level = long(rule["level"])
            else:
                break
        return  reward, ret_demand, level
    def calculateMaxAmountByMony(self,rules, amount):
        reward = 0
        demand = 0
        level = 0
        for rule in rules:
            if long(rule["recharge_money"]) <= amount:
                reward =  long(rule["reward"])
                demand = long(rule["demand"])
                level = long(rule["level"])
            else:
                break
        return  reward, demand, level
    def getRuleByLevel(self,rules, level):
        reward = 0
        demand = 0
        ret_level = 0
        for rule in rules:
            if long(rule["level"]) == long(level):
                reward =  long(rule["reward"])
                demand = long(rule["demand"])
                ret_level = long(rule["level"])
                break
        return  reward, demand, ret_level
    def loopFirstRechangeUser(self):
        activityLevels = self.getActivityLevel()
        users = self.getUsers()
        for user in users:
            recharge_info = self.getUserFirstRecharge(user['user_id'])
            if len(recharge_info) > 0:
                first_recharge_amount = long(recharge_info[0][0])
                max_amount,demand, level  = self.calculateMaxAmountByMony(activityLevels, first_recharge_amount)
                demand = long(activityLevels[0]["demand"])
                self.setUserActivityLevel(user['user_id'],long(first_recharge_amount), max_amount, demand, str(activityLevels))
    def loopTransactionForRechargeBounty(self):
        try:
            users = self.getNeedRushTransactionUsers()
            for user in users:
                sum_amount = self.sumUserTransactionAmount(user['user_id'])
                rule_version = user['rule_version'].replace('\'','\"')
                rules = json.loads(rule_version)
                old_reward, old_demand, old_level = self.getRuleByLevel(rules, str(user['state']))
                new_reward, new_demand, new_level = self.calculateMaxAmountByDemand(rules, sum_amount, user['first_recharge_amount'])
                user_bounty = self.getUserBountyByUserId(user['user_id'])
                #user_id, total_amount, amount, total_transaction_amount,version
                amount = new_reward - old_reward
                if amount > 0:
                    self.updateUserBounty(user['user_id'],amount, sum_amount, user_bounty[2])
                else:
                    self.updateUserBountyTotalTransaction(user['user_id'], sum_amount, user_bounty[2])
                #更新tbl_recharge_bounty表
                unlock_amount = self.calculateNextAmount(rules, sum_amount, user['first_recharge_amount'])
                self.updateUserActivityLevel(user['user_id'], unlock_amount, new_level)
                #info = self.getRechargeBountyByUserId(user['user_id'])
                #print(new_reward)
        except Exception, e:
            logging.error(e.message)
    def getInvitationRelation(self):
        command = "select user_id, father_ower, grand_father_ower, status, version from tbl_invitation_relation where status = 0"
        result = self._client.execute_select(command)
        ret = []
        for item in json.loads(result):
            item_json = {}
            item_json['user_id'] = item[0]
            item_json['father_ower'] = item[1]
            item_json['grand_father_ower'] = item[2]
            item_json['status'] = item[3]
            item_json['version'] = item[4]
            ret.append(item_json)
        return  ret
    def setInvitationRelation(self, user_id):
        command ='UPDATE tbl_invitation_relation  set status = 1 where user_id = ' + str(user_id)
        result = self._client.execute(command)
        return
    def loopInvitationRelation(self):
        try:
            relations = self.getInvitationRelation()
            for relation in relations:
                if self.sumUserTransactionAmount(relation['user_id']) >= 30000:
                    self.setInvitationRelation(relation['user_id'])
                    bounty = self.getUserBountyByUserId(relation['father_ower'])
                    self.updateUserBounty(relation['father_ower'], 600, bounty[0], bounty[2])
                    if relation['grand_father_ower'] != None:
                        bounty = self.getUserBountyByUserId(relation['grand_father_ower'])
                        self.updateUserBounty(relation['grand_father_ower'], 300, bounty[0], bounty[2])
        except Exception, e:
            self.logger.logger.info(e.message)
        return
    def getInvitationRelationAll(self):
        command = "select user_id, father_ower, grand_father_ower, status, version from tbl_invitation_relation"
        result = self._client.execute_select(command)
        ret = []
        for item in json.loads(result):
            item_json = {}
            item_json['user_id'] = item[0]
            item_json['father_ower'] = item[1]
            item_json['grand_father_ower'] = item[2]
            item_json['status'] = item[3]
            item_json['version'] = item[4]
            ret.append(item_json)
        return  ret
    def getTransactionSumAmount(self, time_last):
        command = "select sum(amount), user_id from tbl_transaction where account_type = 1 and status = 1 and create_time > "+ str(time_last) + " GROUP BY user_id"
        result = self._client.execute_select_no_json(command)
        activity_level = []
        for item in result:
            item_json = {}
            item_json['sum'] = long(item[0])
            item_json['user_id'] = item[1]
            activity_level.append(item_json)
        return  activity_level
    def getInvitationRelationByUserId(self,user_id):
        command = "select father_ower, grand_father_ower from tbl_invitation_relation where  user_id = "+ str(user_id)
        result = self._client.execute_select(command)
        ret = []
        for item in json.loads(result):
            item_json = {}
            item_json['father_ower'] = item[0]
            item_json['grand_father_ower'] = item[1]
            ret.append(item_json)
        return  ret
    def loopNewTransactions(self):
        try:
            last_time = 0
            last_time_str = self.redis_client.get("transaction_last_time")
            if last_time_str != None:
                last_time = long(last_time_str)
            self.redis_client.set("transaction_last_time", str(long(time.time())), 0)

            transactions = self.getTransactionSumAmount(last_time)
            for transaction in transactions:
                relations = self.getInvitationRelationByUserId(transaction["user_id"])
                if len(relations) > 0:
                    relation = relations[0]
                    bounty = self.getUserBountyByUserId(relation['father_ower'])
                    self.updateUserBounty(relation['father_ower'], transaction['sum']*2/100, bounty[0], bounty[2])
                    if relation['grand_father_ower'] != None:
                        bounty = self.getUserBountyByUserId(relation['grand_father_ower'])
                        self.updateUserBounty(relation['grand_father_ower'], transaction['sum']/100, bounty[0], bounty[2])

        except Exception, e:
            self.logger.logger.error(e.message)
        return
    def run(self):
        try:
            self.logger.logger.info("Begin Excute Procedure")
            self.loopFirstRechangeUser()
            self.loopTransactionForRechargeBounty()
            self.loopInvitationRelation()
            self.loopNewTransactions()
            self._client.close()
            self.logger.logger.info("End Excute Procedure")
        except Exception, e:
            self._client.close()
            self.logger.logger.error(e.message)
        return
RushBounty('127.0.0.1', 3306, 'futures', 'Mysqlab66', 'root', 'relang123456').run()

