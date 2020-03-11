<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/**
 * Dayrui Website Management System
 *
 * @since		version 2.0.2
 * @author		Dayrui <dayrui@gmail.com>
 * @license     http://www.dayrui.com/license
 * @copyright   Copyright (c) 2011 - 9999, Dayrui.Com, Inc.
 */
define(SIGN_CODE, 'futureex_2018');

class Mining_interface extends M_Controller {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
		$this->load->model('my_model');
    }
	
	
	/**
	 * 销售额挖矿
	 */
	public function sales_mining(){
		$uid = $_GET['uid'];//商家id
		$username = $_GET['username'];//用户账号
		$num = $_GET['num'];//销售额
		$sign = $_GET['sign'];
		$vaildate = md5(date('Y-m-d H:i:s',time).SIGN_CODE);
//		if($sign != $vaildate){
//			echo json_encode(array(
//							'code' => 0,
//							'msg' => '验签失败'
//			));
//			exit;
//		}
		$return =$this->my_model->sales_mining($uid,$username,$num);
		$code = 1;
		$msg = 'ok';
		if($return == -1){
			$code = 0;
			$msg = '账号不存在';
		}else if($return == -2){
			$code = 0;
			$msg = '尚未拥有商家币';
		}else if($return == -3){
			$code = 0;
			$msg = '商家币余额不足';
		}else if($return == -4){
			$code = 0;
			$msg = '平台币余额不足';
		}else if($return == -5){
			$code = 0;
			$msg = '余额不足';
		}
		echo json_encode(array('code' => $code,'msg' => $msg),JSON_UNESCAPED_UNICODE);
		exit;
	}
	
	/**
	 * 交易挖矿
	 */
	public function trading_mining(){
		
		$return = $this->my_model->trading_mining();
		if($return == -1){
			echo json_encode(array('code' => 0,'msg' => '今日已挖矿'),JSON_UNESCAPED_UNICODE);
			exit;
		}else{
			echo json_encode(array('code' => 1,'msg' => 'ok'),JSON_UNESCAPED_UNICODE);
			exit;
		}
	}

}