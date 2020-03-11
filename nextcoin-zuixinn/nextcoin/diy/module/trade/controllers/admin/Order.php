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
 
class Order extends D_Admin_Home {
	
    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
	}
	
	public function index() {
    		$page = $_GET['page'];
		$list_total = $this->db->where('order_status',4)->order_by('order_time','desc')->get('c2c_order')->num_rows();
		$list = $this->db->where('order_status',4)->order_by('order_time','desc')->get('c2c_order')->result_array();
		
//		foreach($list as $k => $v){
//			$user = dr_member_info($v['uid']);
//			$list[$k]['name'] = $user['username'];
//		}
		
		$param['total'] = $list_total;
        // 存储当前页URL
        $this->_set_back_url(APP_DIR.'/order/index', $param);
        $this->template->assign(array(
            'app' => $app,
            'form' => $form,
            'list' => $list,
            'flag' => isset($param['flag']) ? $param['flag'] : '',
            'flags' => $flag,
            'param' => $param,
            'meta_name' => $meta_name,
            'field' => $this->_admin_search_field($this->field),
            'pages' => $this->get_pagination(dr_url(APP_DIR.'/order/index', $param), $param['total']),
            'extend' => $this->get_cache('module-'.SITE_ID.'-'.APP_DIR, 'extend'),
            'select' => $this->select_category($this->get_cache('module-'.SITE_ID.'-'.APP_DIR, 'category'), 0, 'id=\'move_id\' name=\'catid\'', ' --- ', 1, 1),
            'select2' => $this->select_category($this->get_cache('module-'.SITE_ID.'-'.APP_DIR, 'category'), $catid, ' name=\'data[catid]\'', ' --- ', 0, 1),
            'html_url' => 'index.php?s='.APP_DIR.'&',
            'post_url' => $this->duri->uri2url(APP_DIR.'/admin/order/add'),
            'list_url' =>  $this->duri->uri2url($catid ? APP_DIR.'/admin/order/index/catid/'.$catid : APP_DIR.'/admin/home/index'),
            'list_data_tpl' => is_file(FCPATH.'module/'.APP_DIR.'/templates/admin/content_data.html') ? FCPATH.'module/'.APP_DIR.'/templates/admin/content_data.html' : FCPATH.'dayrui/templates/admin/content_data.html',
        ));
        $this->template->display('currency.html');
    }
    
    public function jsoncallback($error=NULL){
		if($error){
			echo json_encode($error, JSON_UNESCAPED_UNICODE);
			exit();
		} else {
			echo json_encode(array('code'=>404,'msg'=>'json错误'), JSON_UNESCAPED_UNICODE);
			exit();
		}
	}
    
    //标记为完成 我已收款
	public function mark_receved(){
		$id = $_REQUEST['id'];
		$order = $this->db->where('id',$id)->get('c2c_order')->row_array();
		
//		if(time()-$order_info['order_time'] > timeStamp15){
//			$this->db->where('id',$id)->update('c2c_order',array('order_status'=>9));
//			$success = array('msg' => fc_lang('操作失败，订单已失效'), 'code' => 0);
//			$this->jsoncallback($success);
//		}
		
		$trade = $this->db->where('id',$order['mid_id'])->get('1_trade')->row_array();
		//订单完成，加给购买者
		$this->db->query('update user_coin_wallet set total=total+'. $order['order_volume'] .' where uid='.$order['buy_uid'].' and coin_id='.$trade['symbol']);
		
		//交易成功后 给卖家的冻结额减少
		$this->db->query('update user_coin_wallet set frozen=frozen-'. $order['order_volume'] .' where uid='.$order['sell_uid'].' and coin_id='.$trade['symbol']);
		
		//成功交易后 更新这笔交易成功数
		$this->db->where('id',$order['mid_id'])->set('success_total','success_total+'.$order['order_volume'],FALSE)->update('1_trade');
		
		$this->db->where('id',$id)->set('order_status',3,FALSE)->update('c2c_order');
		
		$trade = $this->db->where('id',$order['mid_id'])->get('1_trade')->row_array();
		//如果发布的商品为余量为0 就自动标记为已完成
		if($trade['order_volume'] == 0 && $trade['success_total'] == $trade['trade_total']){
			$this->db->where('id',$order['mid_id'])->update('1_trade',array('status'=>0));
		}
		
		$success = array('msg' => fc_lang('操作成功'), 'code' => 1);
		$this->jsoncallback($success);
	}

}