<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');
//require dirname(__FILE__).'/Controller.php';
/**
 * Dayrui Website Management System
 *
 * @since		version 2.0.2
 * @author		Dayrui <dayrui@gmail.com>
 * @license     http://www.dayrui.com/license
 * @copyright   Copyright (c) 2011 - 9999, Dayrui.Com, Inc.
 */
require FCPATH.'module/exc/controllers/Controller.php';
class Test extends Controller {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
    }

	public function bbb(){
		$this->upgrade(10175, 5, 90);
	}
	
	/**
	 * 会员升级
	 */
	public function upgrade($uid,$now_group,$num){
		$member = $this->get_member($uid);
		$user = $this->user_info($uid);
//		if($member['groupid'] == $now_group){
//			return -1;
//		}
//		// 判断用户余额是否足够
//		$user_coin = $this->db->query('select total from user_coin_wallet where coin_id=37 and uid='.$uid)->row_array();
//		
//		if($user_coin['total'] < $num){
//			//余额不足
//			return -2;
//		}
//				
//		//升级
//		$this->db->where('uid',$member['uid'])->set('groupid',$now_group)->update('member');
//		//增加记录
//		$this->db->insert('member_upgrade_log',array(
//										'uid' => $member['uid'],
//										'before_group' => $member['groupid'],
//										'now_group' => $now_group,
//										'num' => $num,
//										'inputtime' => time()
//		));
//		
//		$insert_id = $this->db->insert_id();
//		
//		//扣除用户余额
//		$this->db->query('update user_coin_wallet set total=total-'.$num.' where uid='.$uid.' and coin_id=37');
//		//增加记录
//		$this->db->query('insert into f_coin_record(uid,coin_id,value,note,inputtime) values('.$uid.',37,'. -1*$num.',"购买会员",'.time().')');
//		
//		
//		//减少平台币总量
//		$this->db->query('update system_coin_info set total=total-'.$num.' where coin_id=37');
		
		if($user['fintrouid'] && $now_group == 5){
			//有上级,并且购买体验会员，给上级返还30CC
			$this->db->query('update user_coin_wallet set total=total+30 where coin_id=37 and uid='.$user['fintrouid']);
			$this->db->query('insert into f_coin_record(uid,coin_id,value,note,inputtime) values('.$user['fintrouid'].',37,30,"推荐体验矿工",'.time().')');
			$log = $this->db->query('select id from f_coin_record where uid='.$user['fintrouid'].' and note="推荐体验矿工"')->result_array();
			if(count($log) == 3){
				//当推荐三个体验旷工,获得一次性奖励 90CC
				$this->db->query('update user_coin_wallet set total=total+90 where coin_id=37 and uid='.$user['fintrouid']);
			$this->db->query('insert into f_coin_record(uid,coin_id,value,note,inputtime) values('.$user['fintrouid'].',37,90,"推荐体验矿工达到3名",'.time().')');
			}
		}
		
		return $insert_id;
	}
	
    public function a(){
		$list = $this->db->query('select ftradeid,ftype,fuid,ffees,flastupdattime from f_entrust_history where fid=3057')->row_array();
		$this->trade_mining($list['fuid'], $list['ffees'], $list['ftype'], $list['ftradeid']);
	}
	
	public function httpGet($url,$params=null,$header_out=false) {


        $header[] =  'X-Forwarded-For: ' . $this->getClietnIp();
        $header[] =  'cookie: ' . $this->getCookie();

        $httpInfo = array();
        $ch = curl_init();

        curl_setopt($ch, CURLOPT_HTTP_VERSION, CURL_HTTP_VERSION_1_1);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);
        curl_setopt($ch, CURLOPT_TIMEOUT, 20);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        curl_setopt($ch, CURLINFO_HEADER_OUT, TRUE);//获取请求头信息

//        curl_setopt($ch, CURLOPT_COOKIEJAR, $cookieFile);//用来存放登录成功的cookie
//        curl_setopt($ch, CURLOPT_COOKIEFILE, $cookieFile); //使用cookie

        if(!empty($header_out)){
            curl_setopt($ch, CURLOPT_HEADER, true);
        }

        if ($params) {
            $params = http_build_query($params);
            curl_setopt($ch, CURLOPT_URL, $url . '?' . $params);
        } else {
            curl_setopt($ch, CURLOPT_URL, $url);
        }
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        $httpInfo = curl_getinfo($ch);
//        print_r($httpInfo);


        return $response;
    }

	public function getCookie(){

        $cookie['token']  = isset($_COOKIE['token']) ? $_COOKIE['token'] : '';
        $cookie['CHECKCODE']  = isset($_COOKIE['CHECKCODE']) ? $_COOKIE['CHECKCODE'] : '';
        $cookie['oex_lan']  = isset($_COOKIE['oex_lan']) ? $_COOKIE['oex_lan'] : '';
        $cookie['phone_reset']  = isset($_COOKIE['phone_reset']) ? $_COOKIE['phone_reset'] : '';
        $cookie['phone_reset_Second']  = isset($_COOKIE['phone_reset_Second']) ? $_COOKIE['phone_reset_Second'] : '';
        $cookieStr = '';
        foreach ($cookie as $key=>$value){
            if(empty($value)){
                unset($cookie[$key]);
            }else{
                $cookieStr .= $key . '=' . $value .'; ';
            }

        }
        return $cookieStr;
    }
	
	 public function getClietnIp(){

        if(isset($_SERVER['HTTP_X_FORWARDED_FOR'])){
            return  $_SERVER['HTTP_X_FORWARDED_FOR'];
        }
      return $_SERVER['REMOTE_ADDR'];
    }
	
	public function b(){
		$this->change(52);
	}
	
	/**
	 * 汇率转换
	 */	 
	public function change($num,$tradeid,$type){
		$trade = $this->db->query('select * from system_trade_type where id='.$tradeid)->row_array();
		if($tradeid == 55){
			//USDT 汇率
			$url = '172.21.126.30:8080/real/markets.html?symbol=55';
			$response = $this->httpGet($url);
			$usdt = json_decode($response,true)['data'];
			$usdt_huilv = $usdt[0]['p_new'];
			if($type == 1){
				$num = $num / $usdt_huilv;
			}
		}else{
			if($trade['type'] == 1){
				$url = '172.21.126.30:8080/real/markets.html?symbol='.$tradeid;
				$response = $this->httpGet($url);
				$hv = json_decode($response,true)['data'];
				$hv = $hv[0]['p_new'] ?: 1;
				
				//USDT 汇率
				$url = '172.21.126.30:8080/real/markets.html?symbol=55';
				$response = $this->httpGet($url);
				$usdt = json_decode($response,true)['data'];
				$usdt_huilv = $usdt[0]['p_new'];
				if($type == 0){
					$num = $num * $hv;
				}
				$num = $num / $usdt_huilv;
			}else if($trade['type'] == 2){
				$temp = $this->db->query('select * from system_trade_type where buy_coin_id=33 and sell_coin_id='.$trade['sell_coin_id'])->row_array();
				
				$url = '172.21.126.30:8080/real/markets.html?symbol='.$tradeid;
				$response = $this->httpGet($url);
				$hv = json_decode($response,true)['data'];
				$hv = $hv[0]['p_new'];
				$url = '172.21.126.30:8080/real/markets.html?symbol='.$temp['id'];
				$response = $this->httpGet($url);
				$hv_ = json_decode($response,true)['data'];
				$hv_ = $hv_[0]['p_new'];
				print_r($hv_);
				
				//USDT 汇率
				$url = '172.21.126.30:8080/real/markets.html?symbol=55';
				$response = $this->httpGet($url);
				$usdt = json_decode($response,true)['data'];
				$usdt_huilv = $usdt[0]['p_new'];
				print_r($usdt);
				if($type == 0){
					$num = $num * $hv ;
				}
				$num = $num * $hv_ / $usdt_huilv;
			}
		}

		return $num;
	}

	/**
	 * 交易挖矿
	 */
	public function trade_mining($fid,$num,$type,$tradeid){
		
		$num = $this->change($num,$tradeid,$type);
		$start = date('Y-m-d H:00:00',time());
		$end = date('Y-m-d H:59:59',time());
		$time_log = $this->db->query('select sum(num) as total from f_rebate_log where inputtime>="'.$start.'" and inputtime <="'.$end.'"')->row_array();
		if($time_log['total'] > 50000){
			//没小时挖矿达到5W停止挖矿
			return -1;
		}
		
		//查询用户今日挖矿总量
		$log = $this->db->query('select sum(num) as total from f_rebate_log where type=1 and uid='.$fid.' and inputtime >="'.date('Y-m-d 00:00:00',time()).'" and inputtime <="'.date('Y-m-d 23:59:59',time()).'"')->row_array();
		$today_mining = $log['total'];
		$user = $this->user_info($fid);
		if(empty($user['frealname']) || $today_mining >= 20000){
			//账户未实名认证 不参与返佣 或 今日 挖矿超出20000
			return -1;;
		}
		
		$member = $this->get_member($fid);
		$return_num = $up_return_num = 0;
		//判断会员等级
		if($member['groupid'] == 6){
			//白银会员 返还80%
			$return_num = $num * 80 / 100;
		}else if($member['groupid'] == 7){
			//黄金会员 返还90%
			$return_num = $num * 90 / 100;
		}else if($member['groupid'] == 8){
			//钻石会员 返还100%
			$return_num = $num;
		}else if($member['groupid'] == 5){
			//体验会员 返还60%
			$user_dt = $this->db->query('select total from user_coin_wallet where coin_id=37 and uid='.$fid)->row_array();
			$return_num = $num * 60 / 100;
		}
		
		//达到每小时5W限制
		if(($return_num + $time_log['total']) > 50000){
			$return_num = 50000 - $time_log['total'];
		}
		$total = $today_mining + $return_num;
		if($total > 20000){
			$return_num = 20000 - $today_mining;
		}
		//加入结算记录
//		if($return_num > 0){
//			$this->db->query('insert into f_rebate_log(uid,type,num,status,inputtime) values('.$fid.',1,'.$return_num.',2,"'.date('Y-m-d H:i:s',time()).'")');
//			//返回给用户
//			$this->db->query('update user_coin_wallet set total=total+'.$return_num.' where coin_id=37 and uid='.$fid);
//			$this->db->query('insert into f_coin_record(uid,coin_id,value,note,inputtime) values('.$fid.',37,'.$return_num.',"'.date('Y-m-d H:i:s',time()).'交易额返佣",'.time().')');
//		}
		if($user['fintrouid']){
			$intro_num = $this->db->query('select fid from f_user where frealname is not null and fintrouid='.$user['fintrouid'])->num_rows();
			//返佣比例
			if($intro_num >= 2 && $intro_num < 10 && $intro_num){
				$prop = 0.15;
			}else if($intro_num >= 10 && $intro_num <= 99){
				$prop = 0.2;
			}else if($intro_num >= 100){
				$prop = 0.3;
			}else{
				//邀请人数不达标
				return -1;
			}
			
			//查询用户今日返佣总量
			$user_ = $this->user_info($user['fintrouid']);
			$up_log = $this->db->query('select sum(num) as total from f_rebate_log where type=2 and uid='.$user_['fshowid'].' and inputtime >="'.date('Y-m-d 00:00:00',time()).'" and inputtime <="'.date('Y-m-d 23:59:59',time()).'"')->row_array();
			$up_today_mining = $up_log['total'];
			
			if($up_today_mining >= 6000){
				//超过今日返佣总额
				return -1;
			}
			//返佣数量
			$up_return_num = $num * $prop;
			
			$time_log = $this->db->query('select sum(num) as total from f_rebate_log where inputtime>="'.$start.'" and inputtime <="'.$end.'"')->row_array();
			if($time_log['total'] > 50000){
				//没小时挖矿达到5W停止挖矿
				return -1;
			}
			//超过每小时5W
			if(($up_return_num + $time_log['total']) > 50000){
				$up_return_num = 50000 - $time_log['total'];
			}
						
			$up_total = $up_return_num + $up_today_mining;
			if($up_total > 6000){
				$up_return_num = 6000 - $up_today_mining;
			} 
			//加入结算记录
			echo $up_return_num;
			print_r($user_);exit;
			if($up_return_num > 0){
				$this->db->query('insert into f_rebate_log(uid,type,cuid,num,status,inputtime) values('.$user_['fshowid'].',2,'.$fid.','.$up_return_num.',2,"'.date('Y-m-d H:i:s',time()).'")');
				$this->db->query('update user_coin_wallet set total=total+'.$up_return_num.' where coin_id=37 and uid='.$user_['fshowid']);
			
			//增加返佣记录
				$this->db->query('insert into f_coin_record(uid,coin_id,value,note,inputtime) values('.$user_['fshowid'].',37,'.$up_return_num.',"下级用户'.$user['floginname'].'在'.date('Y-m-d H:i:s',time()-86400).'交易额返佣",'.time().')');
			}	
		}
	}

/**
	 * f_user uid 查询用户在e_member的信息
	 */
	public function get_member($uid){
		$user = $this->user_info($uid);

		$member = $this->db->where('username',$user['floginname'])->get('member')->row_array();
		return $member;
	}
	
	/**
	 * 通过username查用户e_member的信息
	 */
	public function member_info_by_uesrname($username){
		$member = $this->db->where('username',$username)->get('member')->row_array();
		return $member;
	}
	
	/**
	 * 通过username查用户f_user的信息
	 */
	public function user_info_by_uesrname($username){
		$member = $this->db->query('select * from f_user where floginname="'.$username.'"')->row_array();
		return $member;
	}
	
	/**
	 * e_memebr uid查询用户在f_user的信息
	 */
	public function get_user($uid){
		$member = dr_member_info($uid);
		
		$user = $this->db->query('select * from f_user where floginname='.$member['username'])->row_array();
		return $user;
	}
	
	/**
	 * 用户f_user的信息
	 */
	public function user_info($uid){
		$user = $this->db->query('select * from f_user where fshowid='.$uid)->row_array();
		return $user;
	}
	
	public function aa(){
		$c = $this->db->query('select * from f_user where floginname="chengf0660@163.com"')->row_array();
		print_r($c);
		$b = $this->db->query('select * from f_user where fid=10152')->result_array();
		print_r($b);
		$a = $this->db->query('select * from f_user where fintrouid=10152')->result_array();
		print_r($a);
	}
	
	
}