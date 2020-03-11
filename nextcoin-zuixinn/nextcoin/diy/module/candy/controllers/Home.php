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
require FCPATH.'module/exc/controllers/Controller.php';
class Home extends Controller {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
    }

    /**
     * 首页
     */
    public function index() {
		//parent::_index();
		$data = $this->db->where('status',9)->get('1_candy')->result_array();
		foreach($data as $key=>$val) {
			if(FID) {
				if($val['lqfs'] == 1) {//每日领取
					$start = strtotime(date('Y-m-d 00:00:00'));
					$end = strtotime(date('Y-m-d 23:59:59'));
					$is_get = $this->db->where('fid',FID)->where('inputtime >=',$start)->where('inputtime <=',$end)->where('candyid',$val['id'])->get('1_coin_record')->row_array();
				}else{//只领取一次
					$is_get = $this->db->where('fid',FID)->where('candyid',$val['id'])->get('1_coin_record')->row_array();
				}
				if($is_get) {
					$data[$key]['is_get'] = 1;
				}else{
					$data[$key]['is_get'] = 0;
				}
			}else{
				$data[$key]['is_get'] = 0;
			}
			
		}
		$this->template->assign(array(
    			'data' => $data,
        ));
		$this->template->display('candy.html');
    }
	public function receive() {
		$fid = FID;
		$id = $_GET['id'];
		//查看是否存在
		$is_exist = $this->db->where('id',$id)->get('1_candy')->row_array();
		if(!$is_exist) {
			echo json_encode(array('code'=>0,'msg'=>'该糖果不存在'));
			exit;
		}
		if(!$fid) {
			echo json_encode(array('code'=>401,'msg'=>'请登录'));
			exit;
		}
		//是否领取完
		if($is_exist['received'] >= $is_exist['circulation']) {
			echo json_encode(array('code'=>0,'msg'=>'该糖果已领取完'));
			exit;
		}
		//查看是否领取
		if($is_exist['lqfs'] == 1) {//每日领取
			$start = strtotime(date('Y-m-d 00:00:00'));
			$end = strtotime(date('Y-m-d 23:59:59'));
			$is_get = $this->db->where('fid',$fid)->where('inputtime >=',$start)->where('inputtime <=',$end)->where('candyid',$is_exist['id'])->get('1_coin_record')->row_array();
		}else{//只领取一次
			$is_get = $this->db->where('fid',$fid)->where('candyid',$is_exist['id'])->get('1_coin_record')->row_array();
		}
		if($is_get) {
			echo json_encode(array('code'=>0,'msg'=>'已经领取，无需重复操作'));
			exit;
		}
		if($is_exist['dailyreceipt'] > 0 && $is_exist['coin_id']) {
			$this->db->trans_start();//开启事务
			$result = $this->db->query('UPDATE `user_coin_wallet` SET `total` = `total`+'.$is_exist['dailyreceipt'].' WHERE `uid` = '.$fid.' AND `coin_id` = '.$is_exist['coin_id']);
			$this->db->insert('1_coin_record',array(
				'fid' => $fid,
				'coin_id' => $is_exist['coin_id'],
				'value' => $is_exist['dailyreceipt'],
				'note' => '领取糖果',
				'inputtime' => time(),
				'candyid' => $is_exist['id']
			));
			//增加已领取数量
			$this->db->where('id',$id)->set('received', 'received+'.$is_exist['dailyreceipt'], FALSE)->update('1_candy');
			if ($this->db->trans_status() === FALSE) {
			    $this->db->trans_rollback();
			    echo json_encode(array('code'=>0,'msg'=>'领取失败，请重试'));
				exit;
			}else {
			    $this->db->trans_commit();
			}		
		}else{
			echo json_encode(array('code'=>0,'msg'=>'数据有误，请联系客服'));
			exit;
		}
		echo json_encode(array('code'=>1,'msg'=>'领取成功'));
		exit;
	}
}