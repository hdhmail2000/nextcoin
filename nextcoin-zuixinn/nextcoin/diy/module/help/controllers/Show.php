<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');
require FCPATH.'module/exc/controllers/Controller.php';
/**
 * Dayrui Website Management System
 *
 * @since		version 2.0.2
 * @author		Dayrui <dayrui@gmail.com>
 * @license     http://www.dayrui.com/license
 * @copyright   Copyright (c) 2011 - 9999, Dayrui.Com, Inc.
 */
class Show extends Controller {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
        $member = $this->isLogin();
        $this->template->assign(array('USERNAME' => $member['floginname']));
    }
    public function isLogin() {
		$url = $this->host . '/real/userWallet_json.html';
        $response = $this->httpPost($url,array());
		$response = json_decode($response,TRUE);
		if($response['code'] == 401) {
			return array();
		}else{
			$member = $this->member;
			$member['floginname'] = $response['data']['userinfo']['floginname'];
			return $member;
		}
	}
	
	/**
     * 内容
     */
    public function index($id = 0, $page = 0, $return = FALSE) {
		$id = $id ? $id : (int)$this->input->get('id');
		$page = $page ? $page : max(1, (int)$this->input->get('page'));
        return $this->_show($id, $page, $return);
    }
	
	/**
     * 创建html
     */
    public function create_html() {
        $this->_create_show_file((int)$this->input->get('id'), FALSE);
    }
	
	/**
     * 生成html
     */
    public function html() {
        $this->_show_html();
    }
}