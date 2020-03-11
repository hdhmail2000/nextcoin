<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/* v3.1.0  */
require FCPATH.'module/exc/controllers/Controller.php';
class Member_controller extends Controller {
		
	/**
	 * 初始化
	 */
    public function __construct() {
        parent::__construct();
		$this->load->model('my_member_model');
    }
	
	
	/**
	 * DT规则
	 */
	public function dt(){
		$member = $this->db->wherE('username',USERNAME)->get('member')->row_array();
		$this->template->assign(array(
					'groupid' =>  $member['groupid'],
					'username' => $member['username']
		));
		$this->template->display('DT.html');
	}
	
	/**
	 * 会员当前等级
	 */
	public function member_group(){
		$username = $_GET['username'];//账号
		
		$member = $this->my_member_model->member_info_by_uesrname($username);
		echo json_encode(array(
						'code' => 1,
						'msg' => 'ok',
						'return' => $member['groupid']
		),JSON_UNESCAPED_UNICODE);
		exit;
	}

    /**
     * 升级会员
     */
    public function upgrade() {
    	$username = $_GET['username'];
		$now_group = $_GET['now_group'];//升级的会员
		$before_group = $_GET['before_group'];//当前等级
		if(empty($username) || $username == 'admine'){
			echo json_encode(-4);
			exit;
		}
		if($before_group > $now_group){
			//当前等级比购买会员等级高
			echo json_encode(-3);
			exit;
		}
		
		$user =  $member = $this->my_member_model->user_info_by_uesrname($username);
		$uid = $user['fshowid'];
		$user = $this->my_member_model->user_info($uid);
		if(empty($user['frealname'])){
			//未实名认证
			echo json_encode(0);
			exit;
		}
		
		if($now_group == 6){
			$num = 3000;
		}else if($now_group==7){
			$num = 16000;
		}else if($now_group == 8){
			$num = 50000;
		}else if($now_group == 5){
			$num = 90;
		}
		
		$return = $this->my_member_model->upgrade($uid,$now_group,$num);
		echo json_encode($return);
		exit;
    }
	
	/**
	 * 清算
	 */
	public function transaction_record(){
		$return = $this->my_member_model->statistics_mining();
		echo $return;
	}
	
	/**
	 * 结算
	 */
	public function settle_today(){
		$return = $this->my_member_model->settle_today();
		echo $return;
	}
	
	/**
	 * 交易挖矿
	 */
	public function trade_mining(){
		$fid = $_GET['fid'];
		$num = $_GET['num'];
		if(empty($fid) || empty($num)){
			return 1;
		}
		$this->my_member_model->trade_mining($fid,$num);
	}
	
	/**
	 * 查找交易记录
	 */
	public function trade_list(){
		$this->my_member_model->trade_list();
	}
	
	/**
	 * 统计昨日挖矿
	 */
	public function statistics_mining(){
		$this->my_member_model->statistics_mining();
	}
	
	public function test(){
		$this->my_member_model->test();
	}
}