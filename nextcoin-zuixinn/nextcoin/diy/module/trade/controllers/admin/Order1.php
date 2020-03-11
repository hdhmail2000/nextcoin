<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/**
 * Dayrui Website Management System
 *
 * @since		version 2.0.0
 * @author		Dayrui <dayrui@gmail.com>
 * @license     http://www.dayrui.com/license
 * @copyright   Copyright (c) 2011 - 9999, Dayrui.Com, Inc.
 * @filesource	svn://www.dayrui.net/v2/news/controllers/home.php
 */

require FCPATH.'branch/fqb/D_Admin_Home.php';
 
class Order1 extends D_Admin_Home {
	
    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
	}
	
	public function index() {
    		$page = $_GET['page'];
		$list_total = $this->db->where('order_status',1)->or_where('order_status',2)->or_where('order_status',3)->or_where('order_status',9)->order_by('order_time','desc')->get('c2c_order')->num_rows();
		$list = $this->db->where('order_status',1)->or_where('order_status',2)->or_where('order_status',3)->or_where('order_status',9)->order_by('order_time','desc')->get('c2c_order')->result_array();
		
//		foreach($list as $k => $v){
//			$user = dr_member_info($v['uid']);
//			$list[$k]['name'] = $user['username'];
//		}
		
		$param['total'] = $list_total;
        // 存储当前页URL
        $this->_set_back_url(APP_DIR.'/order1/index', $param);
        $this->template->assign(array(
            'app' => $app,
            'form' => $form,
            'list' => $list,
            'flag' => isset($param['flag']) ? $param['flag'] : '',
            'flags' => $flag,
            'param' => $param,
            'meta_name' => $meta_name,
            'field' => $this->_admin_search_field($this->field),
            'pages' => $this->get_pagination(dr_url(APP_DIR.'/order1/index', $param), $param['total']),
            'extend' => $this->get_cache('module-'.SITE_ID.'-'.APP_DIR, 'extend'),
            'select' => $this->select_category($this->get_cache('module-'.SITE_ID.'-'.APP_DIR, 'category'), 0, 'id=\'move_id\' name=\'catid\'', ' --- ', 1, 1),
            'select2' => $this->select_category($this->get_cache('module-'.SITE_ID.'-'.APP_DIR, 'category'), $catid, ' name=\'data[catid]\'', ' --- ', 0, 1),
            'html_url' => 'index.php?s='.APP_DIR.'&',
            'post_url' => $this->duri->uri2url(APP_DIR.'/admin/order1/add'),
            'list_url' =>  $this->duri->uri2url($catid ? APP_DIR.'/admin/order1/index/catid/'.$catid : APP_DIR.'/admin/home/index'),
            'list_data_tpl' => is_file(FCPATH.'module/'.APP_DIR.'/templates/admin/content_data.html') ? FCPATH.'module/'.APP_DIR.'/templates/admin/content_data.html' : FCPATH.'dayrui/templates/admin/content_data.html',
        ));
        $this->template->display('currency1.html');
    }

}