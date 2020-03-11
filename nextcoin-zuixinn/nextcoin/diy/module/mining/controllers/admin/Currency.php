<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/**
 * Dayrui Website Management System
 *
 * @since		version 2.0.0
 * @author		Dayrui <dayrui@gmail.com>
 * @license     http://www.dayrui.com/license
 * @copyright   Copyright (c) 2011 - 9999, Dayrui.Com, Inc.
 * @filesource	svn://www.dayrui.net/v2/dayrui/controllers/admin/page.php
 */

require FCPATH.'branch/fqb/D_Admin_Home.php';

class Currency extends D_Admin_Home {
	
    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
	}
	
	public function return_coin(){
		$log = $this->db->get(SITE_ID.'_mining_status')->row_array();
		if($log['endtime'] < time() && $log['status'] == 2){
			$this->db->where('id',1)->set('status',3)->update(SITE_ID.'_mining_status');
		}
		$log = $this->db->get(SITE_ID.'_mining_status')->row_array();
		if (IS_POST) {
        	$data = $_POST['data'];
			if($log['status'] == 2){
				$this->admin_msg(
	                fc_lang('创世挖矿中,不能修改').
	                ($create ? "<script src='".$create."'></script>".dr_module_create_list_file($catid) : ''),
	                $this->_get_back_url(APP_DIR.'/currency/return_coin'),
	                1,
	                1
	            );
			}
			
			$this->db->where('id',1)->set('status',$data['status'])->update(SITE_ID.'_mining_status');
			if($data['status'] == 2){
				$this->db->where('id',1)->update(SITE_ID.'_mining_status',array(
																	'time' => time(),
																	'endtime' => time() + 86400 * 7
				));
			}
			
			$this->admin_msg(
	            fc_lang('操作成功，正在刷新...').
	            ($create ? "<script src='".$create."'></script>".dr_module_create_list_file($catid) : ''),
	            $this->_get_back_url(APP_DIR.'/currency/return_coin'),
	            1,
	            1
	        );
			exit;
        }
		
		$this->template->assign(array(
			'data' => $log,
        ));
	 $this->template->display('return_coin.html');
	}
}