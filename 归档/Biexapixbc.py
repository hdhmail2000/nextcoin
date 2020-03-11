# !/usr/bin/env python
# coding:utf-8

import hashlib
import requests
import urllib
import time
import hmac
import json
import base64
import os
from pydash import collections
import random
import datetime


class biex():
    def __init__(self,api_key,api_secret):
        self.url = "www.biex.io"
        self.api_key = api_key
        self.api_secret = api_secret

    def doRequest(self,path,method='post',params={}):
    	paramsPrefix = {"host":self.url,'method':method.upper(),'uri':path}
        params['AccessKeyId'] = self.api_key
        params['SignatureVersion'] = 2
        params['SignatureMethod'] = 'HmacSHA256'
        params['Timestamp'] = int(time.time())
        params['Signature'] = self.paramsSign(params,paramsPrefix,self.api_secret)
        # print params
        url = 'https://'+self.url+path
        # print url
        return self.doPostRequest(url,params)

    def doPostRequest(self,url,params):
        try:
            response = requests.post(url,data=params,headers={"oex_lan":"zh_TW"},timeout=5)
            if response.status_code == 200:
                res = response.json()
                res["status"] = "ok"
                return res
            else:
                print response.text
                return {"status":"error","msg":response.text}
        except BaseException as e:
            print e
            return {"msg":e,"status":"error"}

    def doGetRequest(self,url,params):
        return False

    def paramsSign(self,params,paramsPrefix,secret):
    	"""
		concat params
        """
        host = paramsPrefix['host'].lower()
        method = paramsPrefix['method'].upper()
        uri = paramsPrefix['uri']
        tempParams = urllib.urlencode(sorted(params.items(),key=lambda d:d[0],reverse=False))
        payload = '\n'.join([method,host,uri,tempParams]).encode(encoding='UTF-8')
        return self._sign(payload,secret)

    def _sign(self,message,secret):
        secret = secret.encode(encoding='UTF-8')
        message = message.encode(encoding='UTF-8')
        return base64.b64encode(hmac.new(secret,message,digestmod=hashlib.sha256).digest())

    def _signMessage(self,message,secret):
        """
            sign data
        """
        secret = secret.encode(encoding='UTF-8')
        message = message.encode(encoding='UTF-8')
        return base64.b64encode(hmac.new(secret,message,digestmod=hashlib.sha256).digest())

    def signBefore(self):
        pass

    def signAfter(self):
        pass

    def tickers(self,symbol):
    	"""
			depth
		"""
        response = self.doRequest("/v1/depth","post",{"symbol":symbol})
        try:
            if response.get("status") == "ok":
                sell = response["data"]["depth"]["asks"]
                buy = response["data"]["depth"]["bids"]
                return {"buyOne": buy[0][0] if buy else 0,"sellOne":sell[0][0] if sell else 0}
        except BaseException as e:
            print e
            return response

    def accountInfo(self, symbol):
        response = self.doRequest("/v1/balance",'post')
        try:
            if response.get("status") == "ok":
                wallet = response["data"]["wallet"]
                res = {}
                for coin in symbol.split("_"):
                    for info in wallet:
                        if info["shortName"].lower() == coin:
                            res[info["shortName"].lower()] = {"free":round(float(info["total"]),4),"locked":round(float(info["frozen"]),4)}
                return res
        except BaseException as e:
            print e
            return response

    def buy(self,symbol,price,amount):
        response = self.doRequest("/v1/order/place",'post',{"symbol":symbol,"tradeAmount":amount,"tradePrice":price,"type":"buy"})
        try:
            if response.get("status") == "ok":
                response["symbol"] = symbol
                response["price"] = price
                response["amount"] = amount
                return response
            return response
        except BaseException as e:
            print e
            return response

    def sell(self,symbol,price,amount):
        response = self.doRequest("/v1/order/place",'post',{"symbol":symbol,"tradeAmount":amount,"tradePrice":price,"type":"sell"})
        try:
            if response.get("status") == "ok":
                response["symbol"] = symbol
                response["price"] = price
                response["amount"] = amount
                return response
            return response
        except BaseException as e:
            print e
            return response

if __name__ == '__main__':
    b = biex("7d76b9663a9d42328f446e714e91bc50","7F90481ACE82E30246F7FA0B0F554E99")
    # accountInfo = b.accountInfo("sc_cnyt")
    # print accountInfo
    tickers = b.tickers("xbc_cnyt")
    print tickers

    price = round(random.uniform(tickers.get('buyOne'), tickers.get('sellOne')), 4)

    nowTime = datetime.datetime.now().hour
    if nowTime in range(1, 4):
        amount = round(random.uniform(1, 10), 1)
    elif nowTime in range(6, 8):
        amount = round(random.uniform(25, 40), 1)
    elif nowTime in range(13, 16):
        amount = round(random.uniform(20, 30), 1)
    elif nowTime in range(18, 22):
        amount = round(random.uniform(10, 150), 1)
    else:
        amount = round(random.uniform(10, 30), 1)
    round_type = round(random.uniform(1, 2), 0)
    if round_type == 2.0:
        buyOne = b.buy("xbc_cnyt", price, amount)
        if buyOne.get('status') == 'ok':
            sellOne = b.sell("xbc_cnyt", price, amount)
            while sellOne.get('status') != 'ok':
                sellOne = b.sell("xbc_cnyt", price, amount)
            print buyOne
            print sellOne
    elif round_type == 1.0:
        sellOne = b.sell("xbc_cnyt", price, amount)
        if sellOne.get('status') == 'ok':
            buyOne = b.buy("xbc_cnyt", price, amount)
            while buyOne.get('status') != 'ok':
                buyOne = b.buy("xbc_cnyt", price, amount)
            print sellOne
            print buyOne