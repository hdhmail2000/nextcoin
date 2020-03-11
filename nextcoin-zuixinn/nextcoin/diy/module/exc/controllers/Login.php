<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');
define('DR_PATH', 'first/');
/**
 * Dayrui Website Management System
 *
 * @since		version 2.0.2
 * @author		Dayrui <dayrui@gmail.com>
 * @license     http://www.dayrui.com/license
 * @copyright   Copyright (c) 2011 - 9999, Dayrui.Com, Inc.
 */

class Login extends M_Controller {

    /**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
		$this->load->model('content_model');
		if(!$this->session->userdata('language')) {
			$this->session->set_userdata('language','zh-cn');
		}
		$this->lang->load('exc', $this->session->userdata('language'));
		$this->template->assign(array(
    			'lang' => $this->session->userdata('language')
        ));
    }
	public function register() {
		if($this->member) {
			$this->member_msg(fc_lang('您已经登录了，不能注册'));
		}
		$regType = $this->input->get('regType');
		if(IS_POST) {
			$data = $this->input->post('data');
			if($data['regType'] == 1) {//邮箱注册
				if(empty($data['email'])) {
					$this->content_model->outputjson(0,'邮箱不能为空');
				}
				if(!preg_match("/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/",$data['email'])){ 
					$this->content_model->outputjson(0,'邮箱格式不正确');
				}
				$param['regName'] = $data['email'];
				$cms_data['email'] = $data['email'];
			}else{//手机号注册
				if(!preg_match("/^[1][3-9][0-9]{9}$/",$data['phone'])){
					$this->content_model->outputjson(0,'手机号格式不正确'); 
				}
				$param['regName'] = $data['phone'];
				$param['areaCode'] = $data['areaCode'];
				$cms_data['phone'] = $data['phone'];
			}
	        $param['password'] = $data['password'];
	        $param['regType'] = $data['regType'];//1:邮箱注册；0:手机注册
	        $param['vcode'] = $data['vcode'];//图形验证码
	        $param['ecode'] = $data['ecode'];//邮箱验证
	        $param['pcode'] = $data['ecode'];
	        $param['intro_user'] = $data['intro_user'];//邀请人
	        $url = $url = API_HOST.'/register.html';
	        $res = $this->content_model->httpPost($url,$param);
			if($res['code'] == 200) {//注册成功
				$cmsurl = SITE_URL.'index.php?s=member&c=register&m=index';
				$cms_data['type'] = '1';
				$cms_data['password'] = $data['password'];
				$cms_data['password2'] = $data['password2'];
				$cmsres = $this->content_model->cmsPost($cmsurl,$cms_data);
				if($cmsres['status'] == 1) {
					$this->content_model->outputjson(1,'注册成功',array('syncurl'=>$cmsres['syncurl'])); 
				}
			}else{
				$this->content_model->outputjson(0,$res['msg']); 
			}
		}
		$this->template->assign(array(
    			'lang_Register' => $this->lang->line('Register'),
    			'regType' => $regType
        ));
		if($regType == 1) {
			$this->template->display(DR_PATH.'register.html');
		}else{
			$this->template->display(DR_PATH.'register_phone.html');
		}
		
	}
	/**
     * 发送手机验证码
     * @param Request $request
     * @return mixed
     */
    public function sendSms(){
        $param['type'] = $this->input->post('type');
        $param['msgtype'] = 1;
        $param['area'] = $this->input->post('area');
        $param['phone'] = $this->input->post('phone');

        $url = API_HOST.'/user/send_sms.html';
		$res = $this->content_model->httpPost($url,$param);
		if($res['code'] == 200) {
			$this->content_model->outputjson(1,'成功'); 
		}else{
			$msg = isset($res['msg'])?$res['msg']:'发送失败，请重新发送';
			$this->content_model->outputjson(0,$msg); 
		}
    }
	/**
	 * 发送邮箱验证码
	 */
	public function sendEmailCode() {
		$param['address'] = $this->input->post('address');
		$param['type'] = 1;
        $param['msgtype'] = 203;
		if(empty($param['address'] )) {
			$this->content_model->outputjson(0,'邮箱不能为空');
		}
		if(!preg_match("/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/",$param['address'] )){ 
			$this->content_model->outputjson(0,'邮箱格式不正确');
		}
		$url = API_HOST.'/user/send_reg_email.html';
		$res = $this->content_model->httpPost($url,$param);
		if($res['code'] == 200) {
			$this->content_model->outputjson(1,'成功'); 
		}else{
			$this->content_model->outputjson(0,'发送失败'); 
		}
	}
	public function login() {
		if(IS_POST) {
			$data = $_POST['data'];
			$data['type'] = 1;
			$request_data = array(
				'loginName' => $data['username'],
				'password' => $data['password'],
				'type' => '1',
			);
			$url = API_HOST.'/login.html';
			$javares = $this->content_model->httpPost($url,$request_data);
			if($javares['code'] == 200) {
				setcookie('token',$javares['data'],time()+7000,'/',null,null,true);
				$url = SITE_URL.'index.php?s=member&c=login';
				$cmsres = $this->content_model->cmsPost($url,$data);
				if($cmsres['status'] == 1) {
					$this->content_model->outputjson(1,'登录成功',array('syncurl'=>$cmsres['syncurl'])); 
				}
			}else{
				$this->content_model->outputjson(0,$javares['msg']); 
			}
		}
		$this->template->assign(array(
    			'lang_Register' => $this->lang->line('Register')
        ));
		$this->template->display(DR_PATH.'login.html');
	}
	/**
	 * 检查用户是否存在
	 */
	public function checkUser() {
		$url = API_HOST.'/user/check_user_exist.html';
		$param['regUserName'] = $data['username'];
    		$param['regType'] = 1;
		$javares = $this->content_model->httpPost($url,$param);
		print_r($javares);exit;
	}
	/**
     * 获取用户注册图片验证码
     * @return mixed
     */
    public function getVailImg(){

        $url = API_HOST . '/servlet/ValidateImageServlet.html' . '?' . time();
        $content =  $this->content_model->httpGet($url,null,true);
        preg_match('/Set-Cookie:(.*);/iU',$content,$str);
		
        $cookie = substr($str[1],strpos($str[1],'CHECKCODE=')+10);
		$this->session->set_userdata('cookies',$cookie);
        $img = substr($content,strpos($content,"\r\n\r\n")+4);
        setcookie('CHECKCODE',$cookie);
		header('Content-Type:image/jpeg;charset=UTF-8');
		echo $img;
    }
}