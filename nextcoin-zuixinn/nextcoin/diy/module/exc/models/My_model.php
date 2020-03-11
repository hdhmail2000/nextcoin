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
	 * 转账
	 */
	public function transfer($uid,$phone,$coin,$num){
		$user = $this->db->where('username',$phone)->get('member')->row_array();
		if(empty($user)){
			//用户不存在
			return -1;
		}		
		
//		$member_coin = $this->db->where(array())
	}
}

