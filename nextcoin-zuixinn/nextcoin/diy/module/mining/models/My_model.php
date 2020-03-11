<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/* v3.1.0  */

class My_model extends CI_Model {

    /*
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
    }
	
	/**
	 * 销售额挖矿
	 */
	public function sales_mining($uid,$username,$num){
		$user = $this->db->where('username',$username)->get('member')->row_array();
		if(empty($user)){
			//用户不存在
			return -1;
		}
		
		//返还币种 和比例
		$log = $this->db->get(SITE_ID.'_mining_return_coin')->row_array();
		$num *= $log['proportion'] / 100;//返还数量
		$member = dr_member_info($uid);
		
		if($log['return_coin'] == -1){
			//商家币
			$merchant_currency = $this->db->where('uid',$uid)->get(SITE_ID.'_merchant_currency')->row_array();
			if(empty($merchant_currency)){
				//尚未拥有商家币
				return -2;
			}
			
			if($merchant_currency['surplus'] < $num){
				//余额不足
				return -3;
			}
			//转账给用户
			if($this->db->where(array('uid' => $user['uid'],'mid' => $merchant_currency['id']))->get('member_merchant_currency')->row_array()){
				//存在记录
				$this->db->where(array('uid' => $user['uid'],'mid' => $merchant_currency['id']))->set('num','num+'.$num,FALSE)->update('member_merchant_currency');
			}else{
				$this->db->insert('member_merchant_currency',array(
									'uid' => $user['uid'],
									'mid' => $merchant_currency['id'],
									'num' => $num,
									'inputtime' => time()
				));
			}
			
			//扣除商家币
			$this->db->where('uid',$uid)->set('surplus','surplus-'.$num,FALSE)->update(SITE_ID.'_merchant_currency');
			
			//增加记录
			$this->db->insert('member_scorelog',array(
									'uid' => $uid,
									'type' => -1,
									'value' => -1*$num,
									'mark' => 'sales_mining',
									'note' => '返还用户'.$username.'商家币',
									'inputtime' => time()
			));
			
			$this->db->insert('member_scorelog',array(
									'uid' => $user['uid'],
									'type' => -1,
									'value' => $num,
									'mark' => 'sales_mining',
									'note' => '得到商家'.$member['username'].'返还商家币',
									'inputtime' => time()
			));
			
		}
//		else if($log['return_coin'] == 36){
//			//其他币
//			$member_ = $this->db->query('select fshowid from f_user where floginname='.$member['username'])->row_array();
//			$member_coin = $this->db->query('select total from user_coin_wallet where uid='.$member_['fshowid'].' and coin_id='.$log['return_coin'])->row_array();
//			
//			//返还平台币
////			if($member['score'] < $num){
////				//余额不足
////				return -4;
////			}
//			
//			//转账给用户
//			$user_ = $this->db->query('select fshowid from f_user where floginname='.$user['username'])->row_array();
//			if(!$this->db->query('select total from user_coin_wallet where uid='.$user_['fshowid'].' and coin_id='.$log['return_coin'])->row_array()){
//				$this->db->query('insert into user_coin_wallet(`uid`,`coin_id`,`total`,`gmt_create`,`gmt_modified`) values('.$user_['fshowid'].','.$log['return_coin'].','.$num.',"'.date('Y-m-d H:i:s',time()).'","'.date('Y-m-d H:i:s',time()).'")');
//			}else{
//				$this->db->query('update user_coin_wallet set total=total+'.$num.' where uid='.$user_['fshowid'].' and coin_id='.$log['return_coin']);
//			}
//			
//			//增加记录
//			$this->db->insert(SITE_ID.'_coin_record',array(
//												'uid' => $uid,
//												'fid' => $member_['fshowid'],
//												'coin_id' => $log['return_coin'],
//												'value' => -1*$num,
//												'note' => '返还用户'.$username.'',
//												'inputtime' => time()
//			));
//			$this->db->insert(SITE_ID.'_coin_record',array(
//												'uid' => $user['uid'],
//												'fid' => $user_['fshowid'],
//												'coin_id' => $log['return_coin'],
//												'value' => $num,
//												'note' => '得到商家'.$member['username'].'返还',
//												'inputtime' => time(),
//			));
//			
//		}
		else{
			//其他币
			$member_ = $this->db->query('select fshowid from f_user where floginname='.$member['username'])->row_array();
			$member_coin = $this->db->query('select total from user_coin_wallet where uid='.$member_['fshowid'].' and coin_id='.$log['return_coin'])->row_array();
			if(empty($member_coin['total']) || $member_coin['total'] < $num){
				//余额不足
				return -5;
			}
			
			//转账给用户
			$user_ = $this->db->query('select fshowid from f_user where floginname='.$user['username'])->row_array();
			if(!$this->db->query('select total from user_coin_wallet where uid='.$user_['fshowid'].' and coin_id='.$log['return_coin'])->row_array()){
				$this->db->query('insert into user_coin_wallet(`uid`,`coin_id`,`total`,`gmt_create`) values('.$user_['fshowid'].','.$log['return_coin'].','.$num.',"'.date('Y-m-d H:i:s',time()).'")');
			}else{
				$this->db->query('update user_coin_wallet set total=total+'.$num.' where uid='.$user_['fshowid'].' and coin_id='.$log['return_coin']);
			}
			
			//扣除商家相关币
			$this->db->query('update user_coin_wallet set total=total-'.$num.' where uid='.$member_['fshowid'].' and coin_id='.$log['return_coin']);
			
			//增加记录
			$this->db->insert(SITE_ID.'_coin_record',array(
												'uid' => $uid,
												'fid' => $member_['fshowid'],
												'coin_id' => $log['return_coin'],
												'value' => -1*$num,
												'note' => '返还用户'.$username.'',
												'inputtime' => time()
			));
			$this->db->insert(SITE_ID.'_coin_record',array(
												'uid' => $user['uid'],
												'fid' => $user_['fshowid'],
												'coin_id' => $log['return_coin'],
												'value' => $num,
												'note' => '得到商家'.$member['username'].'返还',
												'inputtime' => time()
			));
		}
	}

	/**
	 * 交易挖矿
	 */
	public function trading_mining(){
		//查询今日是否已挖矿
		$today = date('Y-m-d',time());
		$log = $this->db->where('date',$today)->get(SITE_ID.'_trading_mining_log')->row_array();
		if($log){
			//今日已挖矿
			return -1;
		}
		//查询当日交易记录
		$list = $this->db->query('select fuid,sum(fsuccessamount) as amount,sum(ffees) as fees from f_entrust_history where fstatus in(3,5) and flastupdattime between "'.date('Y-m-d 00:00:00',time()).'" and "'.date('Y-m-d 23:59:59',time()).'" group by fuid')->result_array();
		$amount = $fees = 0;
		foreach($list as $k => $v){
			//计算总量
			$amount += $v['amount'];
			$fees += $v['fees'];
		}
		$ratio = dr_block('usdt_ratio');//USDT兑换f1数量
		foreach($list as $k => $v){
			//计算用户交易额占总量比重
			$proportion = number_format($v['amount']/$amount,4);
			//按比例给用户增加平台币
			$user_ = $this->db->query('select * from f_user where fshowid='.$v['fuid'])->row_array();
			$user = $this->db->where('username',$user_['floginname'])->get('member')->row_array();
			
			$num = $fees * $proportion * $ratio;
			//转账给用户
			if(!$this->db->query('select total from user_coin_wallet where uid='.$user_['fshowid'].' and coin_id=36')->row_array()){
				$this->db->query('insert into user_coin_wallet(`uid`,`coin_id`,`total`,`gmt_create`) values('.$user_['fshowid'].',36,'.$num.',"'.date('Y-m-d H:i:s',time()).'")');
			}else{
				$this->db->query('update user_coin_wallet set total=total+'.$num.' where uid='.$user_['fshowid'].' and coin_id=36');
			}
			
			//增加记录
			$this->db->insert(SITE_ID.'_coin_record',array(
												'uid' => $user['uid'],
												'fid' => $user_['fshowid'],
												'coin_id' => 36,
												'value' => $num,
												'note' => $today.'交易额挖矿',
												'inputtime' => time()
			));
		}

		//挖矿完毕 添加记录
		$this->db->insert(SITE_ID.'_trading_mining_log',array(
												'date' => $today,
												'inputtime' => date('Y-m-d H:i:s',time())
		));

		return 1;
	}
	
}