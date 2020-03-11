<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

//设置 订单有效时长 15分钟
define("timeStamp15", 15*60);

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
    public function __construct(){
		parent::__construct();
        $wallet = '';
        $coin_list = '';
		$code =  $this->isLogin();
		if($code == 401){
			header("Location: ".SITE_URL."/index.php?s=exc&c=userController&m=login");
        		exit;
		}
        $username = USERNAME;
        if(empty($username)){
        		header("Location: ".SITE_URL."/index.php?s=exc&c=userController&m=login");
        		exit;
        }
        try {
	        $member_d = $this->db->query("select * from f_user where `floginname`='".$username."'")->row_array();
	    } catch(Exception $e) {
	        //...
	    }
		
		if($member_d){
			$m_uid = $member_d['fid'];
	        if($m_uid){
	        		$wallet = $this->db->query('select * from user_coin_wallet where uid='.$m_uid)->result_array();
	        		foreach($wallet as $k=>$v){
	        			$coin = $this->db->query('select * from system_coin_type where id='.$v['coin_id'])->row_array();
	        			$wallet[$k]['short_name'] = $coin['short_name'];
	        		}
	        }
	        
	        $coin_list = $this->db->query('select * from system_coin_type where `status`=1')->result_array();
		}

		$this->template->assign(array(
            //'member' => $member_d,
            'wallet'=>$wallet,
            'coin_list'=>$coin_list,
            'USERNAME' => USERNAME,
        ));
		
	}

    /**
     * 首页
     */
    public function index() {
		$this->c2cSell();
    }
    
    public function isLogin() {
	  $url = $this->host . '/real/userWallet_json.html';
	  $response = $this->httpPost($url,array());
	  $response = json_decode($response,TRUE);
//	  if($response['code'] == 401) {
	   return $response['code'];
//	  }else{
//	   $member = $this->member;
//	   $member['floginname'] = $response['data']['userinfo']['floginname'];
//	   return $member;
//	  }
	 }
    
    public function relogin(){
    		header("Location: ".SITE_URL."index.php?s=exc&c=userController&m=login");
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
	
	/**
	 * 退出登录
	 */
	public function logout(){
		header("Location: ".SITE_URL."/index.php?s=exc&c=userController&m=logout");
		exit;
	}
	
	
	public function getUidByUsername(){
		$username = USERNAME;
		$member = $this->db->query('select * from f_user where `floginname`='."'".$username."'")->row_array();
		return $member['fid'];
	}
    
    /**
     * 交易大厅 购买
     */
    public function c2cBuy(){
    		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
    		$m = __FUNCTION__;
    		$id = $_REQUEST['id'];
    		$trade1 = $this->db->where('deal_type',1)->where('symbol',$id)->where('status',9)->where('order_volume >',0)->order_by('inputtime','desc')->get('1_trade')->result_array();
    		
    		//分页
	    	$pagesize = 4;
	    	$page = empty($_GET['page']) ? 0 : intval($_GET['page']);
	    	$page<1 && $page = 1;
	    	$start = ($page-1)*$pagesize;
   	 	$trade = $this->db->where('deal_type',1)->where('symbol',$id)->where('status',9)->where('order_volume >',0)->limit($pagesize,$start)->order_by('order_price','asd')->get('1_trade')->result_array();
   	 	// 记算总共有多少页
		if( count($trade1) ){
		  	if( count($trade1) < $pagesize ){
		  		$pagecount = 1; 
		  	}        //如果总数据量小于$PageSize，那么只有一页
			if( count($trade1) % $pagesize ){//取总数据量除以每页数的余数
			    $pagecount = intval(count($trade1) / ($pagesize)) + 1;      //如果有余数，则页数等于总数据量除以每页数的结果取整再加一
			  }else{
			    $pagecount = count($trade1) / $pagesize;           //如果没有余数，则页数等于总数据量除以每页数的结果
			  }
		}else{
		  $pagecount = 0;
		}
    		
    		$this->template->assign(array('m'=>$m,'id'=>$id,'type'=>1,'data'=>$trade,'pagecount'=>$pagecount,'page'=>$page));
    		$this->template->display('c2c_buy.html');
    }
    
    /**
     * 交易大厅 出售
     */
    public function c2cSell(){
    		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
    		$m = __FUNCTION__;
    		$id = $_REQUEST['id'];
    		if(empty($id)){
    			
    			$coin_list = $this->db->query('select * from system_coin_type')->result_array();
    			
    			$id = $coin_list[0]['id'];
    		}
    		
    		$trade1 = $this->db->where('deal_type',2)->where('symbol',$id)->where('status',9)->where('order_volume >',0)->order_by('inputtime','desc')->get('1_trade')->result_array();
		
    		//分页
	    	$pagesize = 4;
	    	$page = empty($_GET['page']) ? 0 : intval($_GET['page']);
	    	$page<1 && $page = 1;
	    	$start = ($page-1)*$pagesize;
   	 	$trade = $this->db->where('deal_type',2)->where('symbol',$id)->where('status',9)->where('order_volume >',0)->limit($pagesize,$start)->order_by('order_price','desc')->get('1_trade')->result_array();
   	 	
   	 	// 记算总共有多少页
		if( count($trade1) ){
		  	if( count($trade1) < $pagesize ){
		  		$pagecount = 1; 
		  	}        //如果总数据量小于$PageSize，那么只有一页
			if( count($trade1) % $pagesize ){//取总数据量除以每页数的余数
			    $pagecount = intval(count($trade1) / ($pagesize)) + 1;      //如果有余数，则页数等于总数据量除以每页数的结果取整再加一
			  }else{
			    $pagecount = count($trade1) / $pagesize;           //如果没有余数，则页数等于总数据量除以每页数的结果
			  }
		}else{
		  $pagecount = 0;
		}
		
    		$this->template->assign(array('m'=>$m,'id'=>$id,'type'=>2,'data'=>$trade,'pagecount'=>$pagecount,'page'=>$page));
			
    		$this->template->display('c2c_sell.html');
    }
    
    /**
     * 发布交易
     */
    public function c2cTrade(){
    		$m = __FUNCTION__;
    		$this->template->assign(array('m'=>$m));
    		$this->template->display('c2c_form.html');
    }
    
    /**
     * 我的发布
     */
    public function c2cReleaseList(){
    		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
    		$m = __FUNCTION__;
    		$tradeList1 = $this->db->where('uid',$uid)->order_by('inputtime','desc')->get('1_trade')->result_array();
    		//分页
	    	$pagesize = 4;
	    	$page = empty($_GET['page']) ? 0 : intval($_GET['page']);
	    	$page<1 && $page = 1;
	    	$start = ($page-1)*$pagesize;
	    	$tradeList = $this->db->where('uid',$uid)->order_by('inputtime','desc')->limit($pagesize,$start)->get('1_trade')->result_array();
    	
    	// 记算总共有多少页
		if( count($tradeList1) ){
		  	if( count($tradeList1) < $pagesize ){
		  		$pagecount = 1; 
		  	}        //如果总数据量小于$PageSize，那么只有一页
			if( count($tradeList1) % $pagesize ){//取总数据量除以每页数的余数
			    $pagecount = intval(count($tradeList1) / ($pagesize)) + 1;      //如果有余数，则页数等于总数据量除以每页数的结果取整再加一
			  }else{
			    $pagecount = count($tradeList1) / $pagesize;           //如果没有余数，则页数等于总数据量除以每页数的结果
			  }
		}else{
		  $pagecount = 0;
		}
    		
    		$this->template->assign(array('m'=>$m,'data'=>$tradeList,'pagecount'=>$pagecount,'page'=>$page));
    		$this->template->display('c2c_release_list.html');
    }
    
    /**
     * 我的订单
     */
	public function c2cMyorder(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$m = __FUNCTION__;
		
		$type = $_REQUEST['type'] ? $_REQUEST['type'] : 1;
		
		//买入订单
		if($type == 1){
			$mr_order1 = $this->db->where('buy_uid',$uid)->order_by('order_time','desc')->get('c2c_order')->result_array();
			foreach($mr_order1 as $k=>$v){
				if($v['order_status'] == 1){
					if(time()-$v['order_time'] > timeStamp15){
						//取消订单，返还给发布者
						$this->db->where('id',$v['mid_id'])->set('order_volume','order_volume+'.$v['order_volume'],FALSE)->update('1_trade');
						$this->db->where('id',$v['id'])->update('c2c_order',array('order_status'=>9));
					}
				}
			}
//			$bank = $alipay = $weixin = '';
//			foreach($mr_order1 as $k=>$v){
//				$pay_types = explode(',',$v['pay_type']);
//				foreach($pay_types as $k1=>$v1){
//					if($v1 == '银行卡'){
//						$bank = $this->db->where('uid',$v['sell_uid'])->get('member_bank')->row_array();
//					} else if($v1 == '支付宝'){
//						$alipay = $this->db->where('uid',$v['sell_uid'])->get('member_alipay')->row_array();
//					} else if($v1 == '微信'){
//						$weixin = $this->db->where('uid',$v['sell_uid'])->get('member_weixin')->row_array();
//					}
//				}
//				$mr_order[$k]['bank'] = $bank;
//				$mr_order[$k]['alipay'] = $alipay;
//				$mr_order[$k]['weixin'] = $weixin;
//				
//			}
			
			//分页
		    	$pagesize = 4;
		    	$page = empty($_GET['page']) ? 0 : intval($_GET['page']);
		    	$page<1 && $page = 1;
		    	$start = ($page-1)*$pagesize;
		    	$mr_order = $this->db->where('buy_uid',$uid)->order_by('order_time','desc')->limit($pagesize,$start)->get('c2c_order')->result_array();
		    	$bank1 = $alipay1 = $weixin1 = '';
			foreach($mr_order as $k=>$v){
				$pay_types = explode(',',$v['pay_type']);
				foreach($pay_types as $k1=>$v1){
					if($v1 == '银行卡'){
						$bank1 = $this->db->where('uid',$v['sell_uid'])->get('member_bank')->row_array();
					} else if($v1 == '支付宝'){
						$alipay1 = $this->db->where('uid',$v['sell_uid'])->get('member_alipay')->row_array();
					} else if($v1 == '微信'){
						$weixin1 = $this->db->where('uid',$v['sell_uid'])->get('member_weixin')->row_array();
					}
				}
				$mr_order[$k]['bank'] = $bank1;
				$mr_order[$k]['alipay'] = $alipay1;
				$mr_order[$k]['weixin'] = $weixin1;
				
				$fabu = $this->db->where('id',$v['mid_id'])->get(SITE_ID.'_trade')->row_array();
				$mr_order[$k]['symbolName'] = $fabu['symbol_name'];
			}
			
	   	 	// 记算总共有多少页
			if( count($mr_order1) ){
			  	if( count($mr_order1) < $pagesize ){
			  		$pagecount = 1; 
			  	}        //如果总数据量小于$PageSize，那么只有一页
				if( count($mr_order1) % $pagesize ){//取总数据量除以每页数的余数
				    $pagecount = intval(count($mr_order1) / ($pagesize)) + 1;      //如果有余数，则页数等于总数据量除以每页数的结果取整再加一
				}else{
				    $pagecount = count($mr_order1) / $pagesize;           //如果没有余数，则页数等于总数据量除以每页数的结果
				}
			}else{
			  $pagecount = 0;
			}
			
			$this->template->assign(array('m'=>$m,'mr'=>$mr_order,'type'=>$type,'page'=>$page,'pagecount'=>$pagecount));
    			$this->template->display('c2c_order_list.html');
		}
		
		//卖出订单
		if($type == 2){
			$mc_order1 = $this->db->where('sell_uid',$uid)->order_by('order_time','desc')->get('c2c_order')->result_array();
			foreach($mc_order1 as $k=>$v){
				if($v['order_status'] == 1){
					if(time()-$v['order_time'] > timeStamp15){
						//取消订单，返还给发布者
						$this->db->where('id',$v['mid_id'])->set('order_volume','order_volume+'.$v['order_volume'],FALSE)->update('1_trade');
						$this->db->where('id',$v['id'])->update('c2c_order',array('order_status'=>9));
					}
				}
			}
//			$bank1 = $alipay1 = $weixin1 = '';
//			foreach($mc_order as $k=>$v){
//				$pay_types = explode(',',$v['pay_type']);
//				foreach($pay_types as $k1=>$v1){
//					if($v1 == '银行卡'){
//						$bank1 = $this->db->where('uid',$v['sell_uid'])->get('member_bank')->row_array();
//					} else if($v1 == '支付宝'){
//						$alipay1 = $this->db->where('uid',$v['sell_uid'])->get('member_alipay')->row_array();
//					} else if($v1 == '微信'){
//						$weixin1 = $this->db->where('uid',$v['sell_uid'])->get('member_weixin')->row_array();
//					}
//				}
//				$mc_order[$k]['bank'] = $bank1;
//				$mc_order[$k]['alipay'] = $alipay1;
//				$mc_order[$k]['weixin'] = $weixin1;
//				
//			}
			//分页
		    	$pagesize = 4;
		    	$page = empty($_GET['page']) ? 0 : intval($_GET['page']);
		    	$page<1 && $page = 1;
		    	$start = ($page-1)*$pagesize;
		    	$mc_order = $this->db->where('sell_uid',$uid)->order_by('order_time','desc')->limit($pagesize,$start)->get('c2c_order')->result_array();
		    	$bank1 = $alipay1 = $weixin1 = '';
			foreach($mc_order as $k=>$v){
				$pay_types = explode(',',$v['pay_type']);
				foreach($pay_types as $k1=>$v1){
					if($v1 == '银行卡'){
						$bank1 = $this->db->where('uid',$v['sell_uid'])->get('member_bank')->row_array();
					} else if($v1 == '支付宝'){
						$alipay1 = $this->db->where('uid',$v['sell_uid'])->get('member_alipay')->row_array();
					} else if($v1 == '微信'){
						$weixin1 = $this->db->where('uid',$v['sell_uid'])->get('member_weixin')->row_array();
					}
				}
				$mc_order[$k]['bank'] = $bank1;
				$mc_order[$k]['alipay'] = $alipay1;
				$mc_order[$k]['weixin'] = $weixin1;
				
				$fabu = $this->db->where('id',$v['mid_id'])->get(SITE_ID.'_trade')->row_array();
				$mc_order[$k]['symbolName'] = $fabu['symbol_name'];
				
			}
		    	
	   	 	// 记算总共有多少页
			if( count($mc_order1) ){
			  	if( count($mc_order1) < $pagesize ){
			  		$pagecount = 1; 
			  	}        //如果总数据量小于$PageSize，那么只有一页
				if( count($mc_order1) % $pagesize ){//取总数据量除以每页数的余数
				    $pagecount = intval(count($mc_order1) / ($pagesize)) + 1;      //如果有余数，则页数等于总数据量除以每页数的结果取整再加一
				}else{
				    $pagecount = count($mc_order1) / $pagesize;           //如果没有余数，则页数等于总数据量除以每页数的结果
				}
			}else{
			  $pagecount = 0;
			}
			
			
			
			$this->template->assign(array('m'=>$m,'mc'=>$mc_order,'type'=>$type,'page'=>$page,'pagecount'=>$pagecount));
    			$this->template->display('c2c_order_list.html');
		}
		
	}
	
	/**
	 * 购买 下单
	 */
	public function buyOrder(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$m = __FUNCTION__;
		$orderId = $_REQUEST['orderId'];
		$order = $this->db->where('id',$orderId)->get('c2c_order')->row_array();
		$trade = $this->db->where('id',$order['mid_id'])->get('1_trade')->row_array();
		$order['trade'] = $trade;
		$bank = $alipay = $weixin = '';
		
		$pays = explode(',',$trade['pay_type']);
        foreach($pays as $k=>$v){
            if($v == '银行卡'){
                $bank = $this->db->where('uid',$order['sell_uid'])->get('member_bank')->row_array();
            } else if ($v == '支付宝') {
                $alipay = $this->db->where('uid',$order['sell_uid'])->get('member_alipay')->row_array();
            } else if ($v == '微信'){
                $weixin = $this->db->where('uid',$order['sell_uid'])->get('member_weixin')->row_array();
            }
        }
		
//		if($trade['pay_type'] == '银行卡'){
//			$bank = $this->db->where('uid',$order['sell_uid'])->get('member_bank')->row_array();
//		} else if ($trade['pay_type'] == '支付宝') {
//			$alipay = $this->db->where('uid',$order['sell_uid'])->get('member_alipay')->row_array();
//		} else if ($trade['pay_type'] == '微信'){
//			$weixin = $this->db->where('uid',$order['sell_uid'])->get('member_weixin')->row_array();
//		}
		$order['bank'] = $bank;
		$order['weixin'] = $weixin;
		$order['alipay'] = $alipay;
    		$this->template->assign(array('m'=>$m,'data'=>$order));
    		$this->template->display('c2c_order_data_buy.html');
	}
	
	/**
	 * 出售 下单
	 */
	public function sellOrder(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$m = __FUNCTION__;
		$orderId = $_REQUEST['orderId'];
		$order = $this->db->where('id',$orderId)->get('c2c_order')->row_array();
		$trade = $this->db->where('id',$order['mid_id'])->get('1_trade')->row_array();
		$order['trade'] = $trade;
		$this->template->assign(array('m'=>$m,'data'=>$order));
    		$this->template->display('c2c_order_data_sell.html');
	}
	
	/**
	 * 添加付款方式
	 */
	public function addPayType(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$m = __FUNCTION__;
		$type = $_REQUEST['type']; //1-银行卡 2-支付宝 3-微信
		$bank = $alipay = $weixin = array();
		
			$bank = $this->db->where('uid',$uid)->get('member_bank')->row_array();
		
			$alipay = $this->db->where('uid',$uid)->get('member_alipay')->row_array();
			
			$weixin = $this->db->where('uid',$uid)->get('member_weixin')->row_array();
		$this->template->assign(array('m'=>$m,'bank'=>$bank,'alipay'=>$alipay,'weixin'=>$weixin));
    		$this->template->display('receipt.html');
	}
	
	/**
	 * 银行信息提交 
	 */
	public function bank_submit(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$data = $_REQUEST['data'];
		$bank_data = $this->db->where('uid',$uid)->get('member_bank')->row_array();
		if($bank_data){
			//修改
			$this->db->where('uid',$uid)->update('member_bank',$data);
			$success = array('msg' => fc_lang('修改成功'), 'code' => 1);
			$this->jsoncallback($success);
		} else {
			$data['inputip'] = $this->input->ip_address();
			$member = $this->db->query('select * from f_user where fid='.$uid)->row_array();
			$data['uid'] = $uid;
			$data['author'] = $member['frealname'] ? $member['frealname'] : $member['floginname'];
			$data['inputtime'] = time();
			$id = $this->db->insert('member_bank',$data);
			if($id > 0){
				$success = array('msg' => fc_lang('提交成功'), 'code' => 1);
				$this->jsoncallback($success);
			} else {
				$success = array('msg' => fc_lang('提交失败'), 'code' => 0);
				$this->jsoncallback($success);
			}
		}
	}
	
	/**
	 * 支付宝信息提交
	 */
	public function alipay_submit(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$data = $_REQUEST['data'];
		/** -------------二维码-------------- **/
		$base64_str1 = $data['qrcode'];
		$base64_str_arr1 = explode(",",$base64_str1);
		$img1 = base64_decode($base64_str_arr1[1]);  //记得要将base64解码，还有去除base64码前缀
		$filename1 = 'uid_'.$uid.'_qrcode_'.time().'_'.rand(10,99).'.png';
		$path1 = $_SERVER['DOCUMENT_ROOT'].'/uploadfile/alipay/'.$filename1;
		$a1 = file_put_contents($path1, $img1);
		if($a1 > 0){
			$fm_path = '/uploadfile/alipay/'.$filename1;
			$data['qrcode'] = $fm_path;
		}
		$alipay_data = $this->db->where('uid',$uid)->get('member_alipay')->row_array();
		if($alipay_data){
			//修改
			$this->db->where('uid',$uid)->update('member_alipay',$data);
			$success = array('msg' => fc_lang('修改成功'), 'code' => 1);
			$this->jsoncallback($success);
		} else {
			//添加
			$data['inputip'] = $this->input->ip_address();
			$member = $this->db->query('select * from f_user where fid='.$uid)->row_array();
			$data['uid'] = $uid;
			$data['author'] = $member['frealname'] ? $member['frealname'] : $member['floginname'];
			$data['inputtime'] = time();
			$id = $this->db->insert('member_alipay',$data);
			if($id > 0){
				$success = array('msg' => fc_lang('提交成功'), 'code' => 1);
				$this->jsoncallback($success);
			} else {
				$success = array('msg' => fc_lang('提交失败'), 'code' => 0);
				$this->jsoncallback($success);
			}
		}
	}
	
	/**
	 * 微信信息提交
	 */
	public function weixin_submit(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$data = $_REQUEST['data'];
		/** -------------二维码-------------- **/
		$base64_str1 = $data['qrcode'];
		$base64_str_arr1 = explode(",",$base64_str1);
		$img1 = base64_decode($base64_str_arr1[1]);  //记得要将base64解码，还有去除base64码前缀
		$filename1 = 'uid_'.$uid.'_qrcode_'.time().'_'.rand(10,99).'.png';
		$path1 = $_SERVER['DOCUMENT_ROOT'].'/uploadfile/weixin/'.$filename1;
		$a1 = file_put_contents($path1, $img1);
		if($a1 > 0){
			$fm_path = '/uploadfile/weixin/'.$filename1;
			$data['qrcode'] = $fm_path;
		}
		$weixin_data = $this->db->where('uid',$uid)->get('member_weixin')->row_array();
		if($weixin_data){
			//修改
			$this->db->where('uid',$uid)->update('member_weixin',$data);
			$success = array('msg' => fc_lang('修改成功'), 'code' => 1);
			$this->jsoncallback($success);
		} else {
			//添加
			$data['inputip'] = $this->input->ip_address();
			$member = $this->db->query('select * from f_user where fid='.$uid)->row_array();
			$data['uid'] = $uid;
			$data['author'] = $member['frealname'] ? $member['frealname'] : $member['floginname'];
			$data['inputtime'] = time();
			$id = $this->db->insert('member_weixin',$data);
			if($id > 0){
				$success = array('msg' => fc_lang('提交成功'), 'code' => 1);
				$this->jsoncallback($success);
			} else {
				$success = array('msg' => fc_lang('提交失败'), 'code' => 0);
				$this->jsoncallback($success);
			}
		}
	}
	
	/**
	 * 发布交易
	 */
	public function fabu(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$member = $this->db->query('select * from f_user where fid='.$uid)->row_array();
		
		if(empty($member['trade_status'])){
			$success = array('msg' => fc_lang('您无权发布交易'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		if(empty($member['frealname'])){
			$success = array('msg' => fc_lang('未实名认证，请先实名认证'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		if(empty($member['ftelephone'])){
			$success = array('msg' => fc_lang('未绑定手机，请先绑定手机'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		//发布类型 1-卖出 2-买入
		$type = $_REQUEST['type'];
		$data = $_REQUEST['data'];
		if(empty($member['ftradepassword'])){
			$success = array('msg' => fc_lang('交易密码未设置'), 'code' => 0);
			$this->jsoncallback($success);
		}
		if($type == 1){
			$psw = $data['deal_psw'];
			if($psw){
    				if(base64_encode(md5($psw)) == $member['ftradepassword']){
	    			 	$country = $data['country'];
					$symbol = $data['symbol_sell'];
					$symbol_data = $this->db->query('select * from system_coin_type where id='.$symbol)->row_array();
					$symbol_name = $symbol_data['short_name'];
					$order_price = $data['order_price_sell'];
					$order_volume = $data['order_volume_sell'];
					if($order_volume == 0){
						$success = array('msg' => fc_lang('不能为0'), 'code' => 0);
						$this->jsoncallback($success);
					}
					
					$wallet = $this->db->query('select * from user_coin_wallet where uid='.$uid.' and coin_id='.$symbol)->row_array();
					if($wallet['total'] < $order_volume){
						$success = array('msg' => fc_lang('余额不足'), 'code' => 0);
						$this->jsoncallback($success);
					}
					$min_value = $data['min_value'];
					$max_value = $data['max_value'];
					if($max_value > $order_volume){
						$success = array('msg' => fc_lang('最大值超出余额'), 'code' => 0);
						$this->jsoncallback($success);
					}
					if($min_value >= $max_value){
						$success = array('msg' => fc_lang('最小值最大值有误'), 'code' => 0);
						$this->jsoncallback($success);
					}
					$pay_type = $data['pay_type'];
					if(empty($pay_type)){
						$success = array('msg' => fc_lang('付款方式不能为空'), 'code' => 0);
						$this->jsoncallback($success);
					}
					$str = '';
		    			for($i=0;$i<count($pay_type);$i++){
		    				if($i>0){
		    					$str .= ','.$pay_type[$i]; 
		    				} else {
		    					$str .= $pay_type[$i];
		    				}
		    			}
		    			//判断是否绑定该种付款方式
		    			$pay_types = explode(',',$str);
		    			foreach($pay_types as $k=>$v){
		    				if($v == '支付宝'){
		    					$zfb = $this->db->where('uid',$uid)->get('member_alipay')->row_array();
		    					if(empty($zfb)){
		    						$fail = array('msg' => fc_lang('支付宝账户未绑定'), 'code' => 0);
								$this->jsoncallback($fail);
		    					}
		    				}
		    				if($v == '微信'){
		    					$wx = $this->db->where('uid',$uid)->get('member_weixin')->row_array();
		    					if(empty($wx)){
		    						$fail = array('msg' => fc_lang('微信账户未绑定'), 'code' => 0);
								$this->jsoncallback($fail);
		    					}
		    				}
		    				if($v == '银行卡'){
		    					$yhk = $this->db->where('uid',$uid)->get('member_bank')->row_array();
		    					if(empty($yhk)){
		    						$fail = array('msg' => fc_lang('银行卡账户未绑定'), 'code' => 0);
								$this->jsoncallback($fail);
		    					}
		    				}
		    			}
					
					$name = $member['frealname'] ? $member['frealname'] : $member['floginname'];
					$ip = $this->input->ip_address();
					$insert_id = $this->db->insert('1_trade',array(
														'title'=>$name,
														'uid'=>$uid,
														'author'=>$name,
														'status'=>9,
														'inputip'=>$ip,
														'inputtime'=>time(),
														'updatetime'=>time(),
														'order_price'=>$order_price,
														'order_volume'=>$order_volume,
														'deal_type'=>$type,
														'min_value'=>$min_value,
														'max_value'=>$max_value,
														'symbol'=>$symbol,
														'pay_type'=>$str,
														'trade_total'=>$order_volume,
														'symbol_name'=>$symbol_name,
														));
					if($insert_id > 0){
						$this->db->query('update user_coin_wallet set frozen=frozen+'.$order_volume.',total=total-'.$order_volume.' where uid='.$uid.' and coin_id='.$symbol);
						$success = array('msg' => fc_lang('发布成功'), 'code' => 1);
						$this->jsoncallback($success);
					} else {
						$success = array('msg' => fc_lang('发布失败'), 'code' => 0);
						$this->jsoncallback($success);
					}
					
	    			} else {
	    				$fail = array('msg' => fc_lang('交易密码错误'), 'code' => 0);
					$this->jsoncallback($fail);
	    			}
    			}
		} else if($type == 2){
			$psw = $data['deal_psw'];
			if($psw){
    				if(base64_encode(md5($psw)) == $member['ftradepassword']){
	    			 	$country = $data['country'];
					$symbol = $data['symbol_buy'];
					$symbol_data = $this->db->query('select * from system_coin_type where id='.$symbol)->row_array();
					$symbol_name = $symbol_data['short_name'];
					$order_price = $data['order_price_buy'];
					$order_volume = $data['order_volume_buy'];
					if($order_volume == 0){
						$success = array('msg' => fc_lang('不能为0'), 'code' => 0);
						$this->jsoncallback($success);
					}
					$min_value = $data['min_value'];
					$max_value = $data['max_value'];
					$pay_type = $data['pay_type'];
					if(empty($pay_type)){
						$success = array('msg' => fc_lang('付款方式不能为空'), 'code' => 0);
						$this->jsoncallback($success);
					}
					
					$str = '';
		    			for($i=0;$i<count($pay_type);$i++){
		    				if($i>0){
		    					$str .= ','.$pay_type[$i]; 
		    				} else {
		    					$str .= $pay_type[$i];
		    				}
		    			}
		    			//判断是否绑定该种付款方式
		    			$pay_types = explode(',',$str);
//		    			print_r($pay_types); exit;
		    			foreach($pay_types as $k=>$v){
		    				if($v == '支付宝'){
		    					$zfb = $this->db->where('uid',$uid)->get('member_alipay')->row_array();
		    					if(empty($zfb)){
		    						$fail = array('msg' => fc_lang('支付宝账户未绑定'), 'code' => 0);
								$this->jsoncallback($fail);
		    					}
		    				}
		    				if($v == '微信'){
		    					$wx = $this->db->where('uid',$uid)->get('member_weixin')->row_array();
		    					if(empty($wx)){
		    						$fail = array('msg' => fc_lang('微信账户未绑定'), 'code' => 0);
								$this->jsoncallback($fail);
		    					}
		    				}
		    				if($v == '银行卡'){
		    					$yhk = $this->db->where('uid',$uid)->get('member_bank')->row_array();
		    					if(empty($yhk)){
		    						$fail = array('msg' => fc_lang('银行卡账户未绑定'), 'code' => 0);
								$this->jsoncallback($fail);
		    					}
		    				}
		    			}
					
					$name = $member['frealname'] ? $member['frealname'] : $member['floginname'];
					$ip = $this->input->ip_address();
					$insert_id = $this->db->insert('1_trade',array(
														'title'=>$name,
														'uid'=>$uid,
														'author'=>$name,
														'status'=>9,
														'inputip'=>$ip,
														'inputtime'=>time(),
														'updatetime'=>time(),
														'order_price'=>$order_price,
														'order_volume'=>$order_volume,
														'deal_type'=>$type,
														'min_value'=>$min_value,
														'max_value'=>$max_value,
														'symbol'=>$symbol,
														'pay_type'=>$str,
														'trade_total'=>$order_volume,
														'symbol_name'=>$symbol_name,
														));
					if($insert_id > 0){
						$success = array('msg' => fc_lang('发布成功'), 'code' => 1);
						$this->jsoncallback($success);
					} else {
						$success = array('msg' => fc_lang('发布失败'), 'code' => 0);
						$this->jsoncallback($success);
					}	
					
	    			} else {
	    				$fail = array('msg' => fc_lang('交易密码错误'), 'code' => 0);
					$this->jsoncallback($fail);
	    			}
    			}
		}
	}
	
	/**
	 * 根据币种id返回对应剩余数量
	 */
	public function total_of_symbol(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$symbol = $_REQUEST['symbol'];
		$wallet = $this->db->query('select * from user_coin_wallet where uid='.$uid.' and coin_id='.$symbol)->row_array();
		$success = array('msg' => fc_lang('发布成功'), 'code' => 1, 'resultData'=>$wallet['total']);
		$this->jsoncallback($success);
	}
	
	/*
	 * 我的交易详情
	 */
	public function releaseBuy(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$id = $_REQUEST['id'];
		$trade = $this->db->where('id',$id)->get('1_trade')->row_array();
		
		$mr_order1 = $this->db->where('mid_id',$trade['id'])->where('buy_uid',$uid)->order_by('order_time','desc')->get('c2c_order')->result_array();
		
		//分页
	    	$pagesize = 4;
	    	$page = empty($_GET['page']) ? 0 : intval($_GET['page']);
	    	$page<1 && $page = 1;
	    	$start = ($page-1)*$pagesize;
	    	$mr_order = $this->db->where('mid_id',$trade['id'])->where('buy_uid',$uid)->order_by('order_time','desc')->limit($pagesize,$start)->get('c2c_order')->result_array();
   	 	// 记算总共有多少页
		if( count($mr_order1) ){
		  	if( count($mr_order1) < $pagesize ){
		  		$pagecount = 1; 
		  	}        //如果总数据量小于$PageSize，那么只有一页
			if( count($mr_order1) % $pagesize ){//取总数据量除以每页数的余数
			    $pagecount = intval(count($mr_order1) / ($pagesize)) + 1;      //如果有余数，则页数等于总数据量除以每页数的结果取整再加一
			  }else{
			    $pagecount = count($mr_order1) / $pagesize;           //如果没有余数，则页数等于总数据量除以每页数的结果
			  }
		}else{
		  $pagecount = 0;
		}
		
		$bank = $alipay = $weixin = '';
		foreach($mr_order as $k=>$v){
			$pay_types = explode(',',$v['pay_type']);
			foreach($pay_types as $k1=>$v1){
				if($v1 == '银行卡'){
					$bank = $this->db->where('uid',$v['sell_uid'])->get('member_bank')->row_array();
				} else if($v1 == '支付宝'){
					$alipay = $this->db->where('uid',$v['sell_uid'])->get('member_alipay')->row_array();
				} else if($v1 == '微信'){
					$weixin = $this->db->where('uid',$v['sell_uid'])->get('member_weixin')->row_array();
				}
			}
			$mr_order[$k]['bank'] = $bank;
			$mr_order[$k]['alipay'] = $alipay;
			$mr_order[$k]['weixin'] = $weixin;
			
		}
		
		$this->template->assign(array('trade'=>$trade,'mr'=>$mr_order,'pagecount'=>$pagecount,'page'=>$page));
		$this->template->display('c2c_release_buy.html');
	}
	
	/*
	 * 我的交易详情
	 */
	public function releaseSell(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$id = $_REQUEST['id'];
		$trade = $this->db->where('id',$id)->get('1_trade')->row_array();
		$mc_order1 = $this->db->where('mid_id',$trade['id'])->where('sell_uid',$uid)->get('c2c_order')->result_array();
		
		//分页
	    	$pagesize = 4;
	    	$page = empty($_GET['page']) ? 0 : intval($_GET['page']);
	    	$page<1 && $page = 1;
	    	$start = ($page-1)*$pagesize;
	    	$mc_order = $this->db->where('mid_id',$trade['id'])->where('sell_uid',$uid)->limit($pagesize,$start)->get('c2c_order')->result_array();
   	 	// 记算总共有多少页
		if( count($mc_order1) ){
		  	if( count($mc_order1) < $pagesize ){
		  		$pagecount = 1; 
		  	}        //如果总数据量小于$PageSize，那么只有一页
			if( count($mc_order1) % $pagesize ){//取总数据量除以每页数的余数
			    $pagecount = intval(count($mc_order1) / ($pagesize)) + 1;      //如果有余数，则页数等于总数据量除以每页数的结果取整再加一
			  }else{
			    $pagecount = count($mc_order1) / $pagesize;           //如果没有余数，则页数等于总数据量除以每页数的结果
			  }
		}else{
		  $pagecount = 0;
		}
		
		$bank = $alipay = $weixin = '';
		foreach($mc_order as $k=>$v){
			$pay_types = explode(',',$v['pay_type']);
			foreach($pay_types as $k1=>$v1){
				if($v1 == '银行卡'){
					$bank = $this->db->where('uid',$v['sell_uid'])->get('member_bank')->row_array();
				} else if($v1 == '支付宝'){
					$alipay = $this->db->where('uid',$v['sell_uid'])->get('member_alipay')->row_array();
				} else if($v1 == '微信'){
					$weixin = $this->db->where('uid',$v['sell_uid'])->get('member_weixin')->row_array();
				}
			}
			$mc_order[$k]['bank'] = $bank;
			$mc_order[$k]['alipay'] = $alipay;
			$mc_order[$k]['weixin'] = $weixin;
			
		}
		$this->template->assign(array('trade'=>$trade,'mc'=>$mc_order,'pagecount'=>$pagecount,'page'=>$page));
		$this->template->display('c2c_release_sell.html');
	}
	
	/**
	 * 下线
	 */
	public function underline(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		
		$id = $_REQUEST['id'];
		$this->db->where('id',$id)->update('1_trade',array('status'=>10));
		
		$trade = $this->db->where('id',$id)->get('1_trade')->row_array();
		if($trade['status'] == 10){
			$this->db->query('update user_coin_wallet set frozen=frozen-'.$trade['order_volume'].',total=total+'.$trade['order_volume'].' where uid='.$uid.' and coin_id='.$trade['symbol']);
		}
		$success = array('msg' => fc_lang('操作成功'), 'code' => 1);
		$this->jsoncallback($success);
	}
	
	
	public function sendMsg($to='',$type='',$pay_no='',$msgc='',$msg='ok'){
		
		//登陆后发送短信给用户
		if($type){
			$pay_no = $_REQUEST['pay_no'];
			if($type == 'login'){
				$msgc = '【biex】**'.substr($to,7).'在'.date("Y-m-d H:i:s",time()).'登录了de.biex App。';
			} else if($type == 'wyfk'){
				$msgc = '【biex】对方已付款，参考号：'.$pay_no.' 请及时确认是否到账再进行处理。';
			} else if($type == 'wysk'){
				$msgc = '【biex】对方已收款，参考号：'.$pay_no.' 请及时确认币是否到账。';
			} else if($type == 'cs'){
				$msgc = '【biex】有用户已卖出币给您，参考号：'.$pay_no.' 请及时进行付款和确认操作';
			}
			$post_data = array(
						'userid' => 2998,//企业id,网站查看
						'account' => 'daerwen123',
						'password' => 'daerwen456',
						'content' => $msgc,//短信内容
						'mobile' => $to,//电话
						'action' => 'send',
						'sendtime' => ''
			);
		} else {
			$post_data = array(
						'userid' => 2998,//企业id,网站查看
						'account' => 'daerwen123',
						'password' => 'daerwen456',
						'content' => "【biex】验证码：".$msgc.'，如非本人操作，请忽略。',//短信内容
						'mobile' => $to,//电话
						'action' => 'send',
						'sendtime' => ''
			);
		}
		
		$o='';
		foreach ($post_data as $k=>$v){
		   $o.="$k=".urlencode($v).'&';
		}
		$post_data=substr($o,0,-1);
		
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_POST, 1);
		curl_setopt($ch, CURLOPT_HEADER, 0);
		curl_setopt($ch, CURLOPT_URL,'http://114.215.78.213:8888/sms.aspx');
		curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		
		$result = curl_exec($ch);
	}
	
	/**
	 * 交易大厅 出售  1-未付款 2-已付款 3-已完成 4-申诉中 9-已取消(关闭)
	 */
	public function trade_sell(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		
		$member = $this->db->query('select * from f_user where fid='.$uid)->row_array();
		
		if(empty($member['frealname'])){
			$success = array('msg' => fc_lang('未实名认证，请先实名认证'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		$psw = $_REQUEST['deal_psw'];
		if(empty($member['ftradepassword'])){
			$success = array('msg' => fc_lang('未设置交易密码'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		$pay_type1 = $this->db->where('uid',$uid)->get('member_bank')->row_array();
		$pay_type2 = $this->db->where('uid',$uid)->get('member_alipay')->row_array();
		$pay_type3 = $this->db->where('uid',$uid)->get('member_weixin')->row_array();
		
		
		if(empty($pay_type1) && empty($pay_type2) && empty($pay_type3)){
			$success = array('msg' => fc_lang('请设置支付信息，方便给您汇款'), 'code' => 0);
			$this->jsoncallback($success);
		}
		//判断操作用户是否支持发布用的付款方式
		$fabu_paytype = array();
		$caozuo_paytype = array();
		$trade = $this->db->where('id',$trade_id)->get('1_trade')->row_array();
		$pay_types = explode(',',$trade['pay_type']);
		foreach($pay_types as $k=>$v){
			if($v == '银行卡'){
				$fabu_paytype[] = 1;
			}
			if($v == '支付宝'){
				$fabu_paytype[] = 2;
			}
			if($v == '微信'){
				$fabu_paytype[] = 3;
			}
		}
		if($pay_type1){
			$caozuo_paytype[] = 1;
		}
		if($pay_type2){
			$caozuo_paytype[] = 2;
		}
		if($pay_type3){
			$caozuo_paytype[] = 3;
		}
		
		$new_arr = array_merge($caozuo_paytype,$caozuo_paytype);
		if (count($new_arr) == count(array_unique($new_arr))) {
		   	$success = array('msg' => fc_lang('支付方式不匹配，请检查您的支付方式'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		if(base64_encode(md5($psw)) == $member['ftradepassword']){
			//出售数量
			$sell_num = $_REQUEST['volume'];
			//总价
			$total_price = $_REQUEST['price'];
			
			$trade_id = $_REQUEST['trade_id'];
			
			$trade = $this->db->where('id',$trade_id)->get('1_trade')->row_array();
			
			if($trade['uid'] == $uid){
				$success = array('msg' => fc_lang('自己不能卖给自己'), 'code' => 0);
				$this->jsoncallback($success);
			}
			
			if($sell_num < $trade['min_value'] || $sell_num > $trade['max_value']){
				$success = array('msg' => fc_lang('超出交易范围'), 'code' => 0);
				$this->jsoncallback($success);
			}
			
			if($trade['order_volume'] < $sell_num){
				$success = array('msg' => fc_lang('商品数量不足'), 'code' => 0);
				$this->jsoncallback($success);
			}
			
			if($trade['status'] != 9){
				$success = array('msg' => fc_lang('商品已下架'), 'code' => 0);
				$this->jsoncallback($success);
			}
			
			$f = 0;
			$wallet = $this->db->query('select * from user_coin_wallet where uid='.$uid.' and coin_id='.$trade['symbol'])->row_array();
			if($wallet['total']<$sell_num){
				$success = array('msg' => fc_lang('余额不足'), 'code' => 0);
				$this->jsoncallback($success);
			}
			$insert_id = $this->db->insert('c2c_order',array(
												'sn'=>date('Ymd').str_pad(mt_rand(1, 999999), 6, '0', STR_PAD_LEFT).$f,
												'pay_no'=>substr(date('Ymd').str_pad(mt_rand(1, 999999), 6, '0', STR_PAD_LEFT).$f,5,9),
												'mid'=>'trade',
												'mid_id'=>$trade_id,
												'buy_uid'=>$trade['uid'],
												'buy_username'=>$trade['author'],
												'buy_step'=>0,
												'sell_uid'=>$uid,
												'sell_username'=>$member['frealname'] ? $member['frealname'] : $member['floginname'],
												'order_volume'=>$sell_num,
												'order_time'=>time(),
												'order_status'=>1,
												'price'=>$_REQUEST['price'],
												'order_price'=>$total_price,
												'order_score'=>0,
												'pay_type'=>$trade['pay_type'],
												'pay_id'=>0,
												'pay_status'=>0,
												'pay_time'=>0,
												'tableid'=>0,
												'flag'=>1,
												));
			if($insert_id > 0){
				$order_id = $this->db->insert_id();
				$trade = $this->db->where('id',$trade_id)->get('1_trade')->row_array();
				$this->db->where('id',$trade_id)->set('order_volume','order_volume-'.$sell_num,FALSE)->update('1_trade');
				
				//先冻结出售的个数
				$this->db->query('update user_coin_wallet set frozen=frozen+'. $sell_num .',total=total-'. $sell_num .' where uid='.$uid.' and coin_id='.$trade['symbol']);
				
				//发短息通知对方
				$order = $this->db->where('id',$order_id)->get('c2c_order')->row_array();
				$member_info = $this->db->query('select * from f_user where `fid`='.$order['buy_uid'])->row_array();
//				print_r($member_info); exit;
				$to = $member_info['ftelephone'];
				if(empty($to)){
					$success = array('msg' => fc_lang('操作成功，买家手机未绑定，无法短信通知'), 'code' => 1, 'data'=>$order_id);
					$this->jsoncallback($success);
				}
				//   https://de.biex.com/api/shortmessage/jianzhou.php?to=18580967515&type=cs&pay_no=921337150
//				$url = SITE_URL.'api/shortmessage/jianzhou.php?to='.$to.'&type=cs&pay_no='.$order['pay_no'];
				$this->sendMsg($to,'cs',$order['pay_no']);
//				$result = json_decode(file_get_contents($url),TRUE);
				
				$success = array('msg' => fc_lang('操作成功'), 'code' => 1, 'data'=>$order_id);
				$this->jsoncallback($success);
			} else {
				$success = array('msg' => fc_lang('下单失败'), 'code' => 0);
				$this->jsoncallback($success);
			}
			
		} else {
			$success = array('msg' => fc_lang('交易密码错误'), 'code' => 0);
			$this->jsoncallback($success);
		}
	}
	

	
	/**
	 * 交易大厅 买入  1-未付款 2-已付款 3-已完成 4-申诉中 9-已取消(关闭)
	 */
	public function trade_buy(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$member = $this->db->query('select * from f_user where fid='.$uid)->row_array();
		
		if(empty($member['frealname'])){
			$success = array('msg' => fc_lang('未实名认证，请先实名认证'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		$psw = $_REQUEST['deal_psw'];
		
		if(empty($member['ftradepassword'])){
			$success = array('msg' => fc_lang('未设置交易密码'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		if(base64_encode(md5($psw)) == $member['ftradepassword']){
			//买入数量
			$buy_num = $_REQUEST['volume'];
			//总价
			$total_price = $_REQUEST['price'];
			
			$trade_id = $_REQUEST['trade_id'];
			
			$trade = $this->db->where('id',$trade_id)->get('1_trade')->row_array();
			
			if($trade['uid'] == $uid){
				$success = array('msg' => fc_lang('不能购买自己的交易'), 'code' => 0);
				$this->jsoncallback($success);
			}
			
			if($buy_num < $trade['min_value'] || $buy_num > $trade['max_value']){
				$success = array('msg' => fc_lang('超出交易范围'), 'code' => 0);
				$this->jsoncallback($success);
			}
			
			if($trade['order_volume'] < $buy_num){
				$success = array('msg' => fc_lang('商品数量不足'), 'code' => 0);
				$this->jsoncallback($success);
			}
			
			if($trade['status'] != 9){
				$success = array('msg' => fc_lang('商品已下架'), 'code' => 0);
				$this->jsoncallback($success);
			}
			
			$f = 0;
			$insert_id = $this->db->insert('c2c_order',array(
												'sn'=>date('Ymd').str_pad(mt_rand(1, 999999), 6, '0', STR_PAD_LEFT).$f,
												'pay_no'=>substr(date('Ymd').str_pad(mt_rand(1, 999999), 6, '0', STR_PAD_LEFT).$f,5,9),
												'mid'=>'trade',
												'mid_id'=>$trade_id,
												'buy_uid'=>$uid,
												'buy_username'=>$member['frealname'] ? $member['frealname'] : $member['floginname'],
												'buy_step'=>0,
												'sell_uid'=>$trade['uid'],
												'sell_username'=>$trade['author'],
												'order_volume'=>$buy_num,
												'order_time'=>time(),
												'order_status'=>1,
												'price'=>$_REQUEST['price'],
												'order_price'=>$total_price,
												'order_score'=>0,
												'pay_type'=>$trade['pay_type'],
												'pay_id'=>0,
												'pay_status'=>0,
												'pay_time'=>0,
												'tableid'=>0,
												'flag'=>0,
												));
			if($insert_id > 0){
				$order_id = $this->db->insert_id();
				$trade = $this->db->where('id',$trade_id)->get('1_trade')->row_array();
				$this->db->where('id',$trade_id)->set('order_volume','order_volume-'.$buy_num,FALSE)->update('1_trade');
				
				$success = array('msg' => '操作成功', 'code' => 1, 'data'=>$order_id);
				$this->jsoncallback($success);
			} else {
				$success = array('msg' => '下单失败', 'code' => 0);
				$this->jsoncallback($success);
			}
			
		} else {
			$success = array('msg' => fc_lang('交易密码错误'), 'code' => 0);
			$this->jsoncallback($success);
		}
	}
	
	//取消订单
	public function cancel_order(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$id = $_REQUEST['id'];
		
		$order = $this->db->where('id',$id)->get('c2c_order')->row_array();
		if($order['order_status'] == 2){
			$success = array('msg' => fc_lang('订单已付款，不能取消'), 'code' => 0);
			$this->jsoncallback($success);
		}
		if($order['order_status'] == 3){
			$success = array('msg' => fc_lang('订单已完成，不能取消'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		if($order['order_status'] == 9){
			$success = array('msg' => fc_lang('订单已取消，无需再取消'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		//取消订单，返还给发布者
		//如果订单是卖出类型  取消订单，返还给发布者
		$trade = $this->db->where('id',$order['mid_id'])->get('1_trade')->row_array();
		$this->db->where('id',$order['mid_id'])->set('order_volume','order_volume+'.$order['order_volume'],FALSE)->update('1_trade');
		if($trade['deal_type'] == 2){
			//如果交易类型是买入 就把卖家生成的订单冻结的金额退回去
			$this->db->query('update user_coin_wallet set frozen=frozen-'.$order['order_volume'].',total=total+'.$order['order_volume'].' where uid='.$order['sell_uid'].' and coin_id='.$trade['symbol']);
		}
		$this->db->where('id',$id)->set('order_status',9,FALSE)->update('c2c_order');
		$success = array('msg' => fc_lang('取消成功'), 'code' => 1);
		$this->jsoncallback($success);
	}
	//标记为我已付款
	public function mark_pay(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$id = $_REQUEST['id'];
		$order_info = $this->db->where('id',$id)->get('c2c_order')->row_array();
		if($order_info['order_status'] == 9){
			$success = array('msg' => fc_lang('操作失败，订单已失效'), 'code' => 0);
			$this->jsoncallback($success);
		}
		if(time()-$order_info['order_time'] > timeStamp15){
			//取消订单，返还给发布者
			$this->db->where('id',$order_info['mid_id'])->set('order_volume','order_volume+'.$order_info['order_volume'],FALSE)->update('1_trade');
			$this->db->where('id',$id)->update('c2c_order',array('order_status'=>9));
			$success = array('msg' => fc_lang('操作失败，订单已失效'), 'code' => 0);
			$this->jsoncallback($success);
		}
		$this->db->where('id',$id)->set('order_status',2,FALSE)->update('c2c_order');
		
		//发短息通知对方
		$order = $this->db->select('sell_uid,sn,pay_no')->where('id',$id)->get('c2c_order')->row_array();
		$member = $this->db->query('select * from f_user where `fid`='.$order['sell_uid'])->row_array();
		$to = $member['ftelephone'];
		if(empty($to)){
			$success = array('msg' => fc_lang('操作成功，卖家手机未绑定，无法短信通知'), 'code' => 1);
			$this->jsoncallback($success);
		}
//		$url = SITE_URL.'api/shortmessage/jianzhou.php?to='.$to.'&type=wyfk&pay_no='.$order['pay_no'];
		$this->sendMsg($to,'wyfk',$order['pay_no']);
//		$result = json_decode(file_get_contents($url),TRUE);
		
		$success = array('msg' => fc_lang('操作成功'), 'code' => 1);
		$this->jsoncallback($success);
	}
	
	//标记为完成 我已收款
	public function mark_receved(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$id = $_REQUEST['id'];
		$order = $this->db->where('id',$id)->get('c2c_order')->row_array();
		if($order['order_status'] == 9){
			$success = array('msg' => fc_lang('操作失败，订单已失效'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
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
		
		//再查询一次
		$trade = $this->db->where('id',$order['mid_id'])->get('1_trade')->row_array();
		//如果发布的商品为余量为0 就自动标记为已完成
		if($trade['order_volume'] == 0 && $trade['success_total'] == $trade['trade_total']){
			$this->db->where('id',$order['mid_id'])->update('1_trade',array('status'=>0));
		}
		
		//发短息通知对方
		$member = $this->db->query('select * from f_user where `fid`='.$order['buy_uid'])->row_array();
		$to = $member['ftelephone'];
		if(empty($to)){
			$success = array('msg' => fc_lang('操作成功，买家手机未绑定，无法短信通知'), 'code' => 1);
			$this->jsoncallback($success);
		}
//		$url = SITE_URL.'api/shortmessage/jianzhou.php?to='.$to.'&type=wysk&pay_no='.$order['pay_no'];
		$this->sendMsg($to,'wysk',$order['pay_no']);
//		$result = json_decode(file_get_contents($url),TRUE);
		
		$success = array('msg' => fc_lang('操作成功'), 'code' => 1);
		$this->jsoncallback($success);
	}
	
	//申诉
	public function shensu(){
		$uid = $this->getUidByUsername();
		if(empty($uid)){
			$this->relogin();
			exit;
		}
		$orderId = $_REQUEST['orderId'];
		$order_info = $this->db->where('id',$orderId)->get('c2c_order')->row_array();
		if($order_info['order_status'] == 9){
			$success = array('msg' => fc_lang('操作失败，订单已失效'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		if($order_info['order_status'] == 3){
			$success = array('msg' => fc_lang('操作失败，订单已完成'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
		if($order_info['order_status'] == 4){
			$success = array('msg' => fc_lang('操作失败，订单申诉中'), 'code' => 0);
			$this->jsoncallback($success);
		}
		
//		if(time()-$order_info['order_time'] > timeStamp15){
//			$this->db->where('id',$orderId)->update('c2c_order',array('order_status'=>9));
//			$success = array('msg' => fc_lang('操作失败，订单已失效'), 'code' => 0);
//			$this->jsoncallback($success);
//		}
		
		$imgUrl = $_REQUEST['imgUrl'];
		$description = $_REQUEST['description'];
		$fm_path = '';
		/** -------------申诉图-------------- **/
		$base64_str1 = $imgUrl;
		$base64_str_arr1 = explode(",",$base64_str1);
		$img1 = base64_decode($base64_str_arr1[1]);  //记得要将base64解码，还有去除base64码前缀
		$filename1 = 'uid_'.$uid.'_shensu_'.time().'_'.rand(10,99).'.png';
		$path1 = $_SERVER['DOCUMENT_ROOT'].'/uploadfile/shensu/'.$filename1;
		$a1 = file_put_contents($path1, $img1);
		if($a1 > 0){
			$fm_path = '/uploadfile/shensu/'.$filename1;
		}
		
		$this->db->where('id',$orderId)->set('order_status',4)->set('imgUrl',$fm_path)->set('description',$description)->update('c2c_order');
		$success = array('msg' => fc_lang('申诉成功'), 'code' => 1);
		$this->jsoncallback($success);
	}
	
	//发送短信
	public function vitid(){
		$to = "";
		if (!empty($_REQUEST['phone'])) {
		    $to = $_REQUEST['phone'];
		    $msgc = rand(1000, 9999);
		} else {
			echo json_encode(array('msg' => 'fail', 'code' => 0, 'return' => '手机号不能为空'), JSON_UNESCAPED_UNICODE);
			exit();
		}
		$url = SITE_URL.'api/shortmessage/jianzhou.php?to='.$to.'&msgc='.$msgc;
		$result = json_decode(file_get_contents($url),TRUE);
		if($result['returnstatus'] == 'Success'){
			$regist_data = $this->db->where('phone',$to)->get('regist_code')->result_array();
			if($regist_data){
				$this->db->where('phone',$to)->update('regist_code',array('code'=>$msgc));
			} else {
				$this->db->insert('regist_code',array('phone'=>$to,'code'=>$msgc));
			}
			echo json_encode(array('msg' => 'ok', 'code' => 1, 'return' => $msgc), JSON_UNESCAPED_UNICODE);
			exit();
		} else {
			echo json_encode(array('msg' => 'fail', 'code' => 0, 'return' => '发送失败'), JSON_UNESCAPED_UNICODE);
			exit();
		}
	}
    
    
    /** 这下面不是彭俊杰加的 上面才是 **/
    
	
	/**
	 * 用户获取coin
	 */
	 public function getCoin() {
	 	if (IS_POST) {
	 		
	 	} else {
	 		//获取代币
	 		$id = @$_REQUEST['id'];
			if (empty($id)) {
				$id = 1;
			}
			$this->template->assign(array(
				'id' => $id
			));
	 		$this->template->display('getcoin.html');
	 	}
	 	
	 }
	 
	 /**
	  * 发币方个人中心
	  */
	 public function memberinfo() {
	 	$this->template->display('memberinfo.html');
	 }
	 
	 
	 
}