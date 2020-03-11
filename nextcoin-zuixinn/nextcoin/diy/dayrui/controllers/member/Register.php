<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');
define('CONTACT_SECRET', 'vyLTRA1ZMEP');
define('CONTACT_KEY', 'z3v5yqkbz18x0');
define("SIGN_CODE",'future493nm=lnrfu90h');//加密随机码
class Register extends M_Controller {

	/**
	 * 注册
	 */
	public function index() {
		// 会员配置
		$MEMBER = $this->get_cache('MEMBER');
		// 来路认证
		$json = 0;
		$auth = $this->input->get('auth');
		if ($auth) {
			$auth != md5(SYS_REFERER) && exit($this->callback_json(array(
				'msg' => '移动端api密钥-不正确',
				'code' => 0
			)));
			!IS_POST && exit($this->callback_json(array(
				'msg' => '请用POST方式提交',
				'code' => 0,
			)));
			$json = 1;
			$MEMBER['setting']['regcode'] = 0;
		}
		$groupid = (int)$this->input->get('groupid');
		$groupid && $groupid == 3 && $groupid = 0;


		// 判断是否开启注册
		if (!$MEMBER['setting']['register']) {
			$json && exit($this->callback_json(array(
				'msg' => fc_lang('站点已经关闭了会员注册'),
				'code' => 0
			)));
			$this->member_msg(fc_lang('站点已经关闭了会员注册'));
		} elseif ($groupid && !$MEMBER['group'][$groupid]['allowregister']) {
			// 指定模型注册验证
			$json && exit($this->callback_json(array(
				'msg' => fc_lang('此会员组模型（%s）系统不允许注册', $groupid),
				'code' => 0
			)));
			$this->member_msg(fc_lang('此会员组模型（%s）系统不允许注册', $groupid));
		} elseif ($this->member && !$json) {
			// 已经登录不允许注册
			$this->member_msg(fc_lang('您已经登录了，不能注册'));
		}

		if (IS_POST) {
			$data = $this->input->post('data', TRUE);
			$data['username'] = preg_replace('/[ ]/', '', $data['username']);
			$back_url = $_POST['back'] ? urldecode($this->input->post('back', true)) : '';
			$back_url = $back_url && strpos($back_url, 'register') === FALSE ? $back_url : dr_member_url('home/index');
			if (!$json && $MEMBER['setting']['regcode'] && !$this->check_captcha('code')) {
				$error = array('name' => 'code', 'msg' => fc_lang('验证码不正确'));
			} elseif (@in_array('username', $MEMBER['setting']['regfield'])
				&& $result = $this->is_username($data['username'])) {
				$error = array('name' => 'username', 'msg' => $result);
			} elseif (!$data['password']) {
				$error = array('name' => 'password', 'msg' => fc_lang('密码不能为空'));
			} elseif ($data['password'] !== $data['password2']) {
				$error = array('name' => 'password2', 'msg' => fc_lang('两次密码输入不一致'));
			} elseif (isset($data['email'])
				&& $result = $this->is_email($data['email'])) {
				$error = array('name' => 'email', 'msg' => $result);
			} else {
				$this->hooks->call_hook('member_register_before', $data); // 注册之前挂钩点
				$id = $this->member_model->register($data, $groupid);
				if ($id > 0) {
					// 注册成功
					$data['uid'] = $id;
					$this->hooks->call_hook('member_register_after', $data); // 注册之后挂钩点
					// 注册后的登录
					$code = $this->member_model->login($id, $data['password'], $data['auto'] ? 8640000 : $MEMBER['setting']['loginexpire'], 0, 1);
					strlen($code) > 3 && $this->hooks->call_hook('member_login', $data); // 登录成功挂钩点
					$json && exit($this->callback_json(array(
						'msg' => 'ok',
						'code' => 1,
						'uid' => $id,
						'return' => dr_member_info($id)
					)));
				} elseif ($id == -1) {
					$error = array('name' => 'username', 'msg' => fc_lang('该会员【%s】已经被注册', $data['username']));
				} elseif ($id == -2) {
					$error = array('name' => 'email', 'msg' => fc_lang('邮箱格式不正确'));
				} elseif ($id == -3) {
					$error = array('name' => 'email', 'msg' => fc_lang('该邮箱【%s】已经被注册', $data['email']));
				} elseif ($id == -4) {
					$error = array('name' => 'username', 'msg' => fc_lang('同一IP在限制时间内注册过多'));
				} elseif ($id == -5) {
					$error = array('name' => 'username', 'msg' => fc_lang('Ucenter：会员名称不合法'));
				} elseif ($id == -6) {
					$error = array('name' => 'username', 'msg' => fc_lang('Ucenter：包含不允许注册的词语'));
				} elseif ($id == -7) {
					$error = array('name' => 'username', 'msg' => fc_lang('Ucenter：Email格式有误'));
				} elseif ($id == -8) {
					$error = array('name' => 'username', 'msg' => fc_lang('Ucenter：Email不允许注册'));
				} elseif ($id == -9) {
					$error = array('name' => 'username', 'msg' => fc_lang('Ucenter：Email已经被注册'));
				} elseif ($id == -10) {
					$error = array('name' => 'phone', 'msg' => fc_lang('手机号码必须是11位的整数'));
				} 
//				elseif ($id == -11) {
//                  $error = array('name' => 'phone', 'msg' => fc_lang('该手机号码已经注册'));
//              }
				 else {
					$error = array('name' => 'username', 'msg' => fc_lang('注册失败'));
				}
			}
			$json && exit($this->callback_json(array(
				'msg' => $error['msg'],
				'code' => 0
			)));
			if (IS_AJAX || isset($data['type'])) {
				$error && exit(dr_json(0, $error['msg']));
				$id > 0 && exit(json_encode(array(
					'status' => 1,
					'backurl' => $back_url,
					'syncurl' => dr_member_sync_url($code))));
			}
			$code && $this->member_msg(fc_lang('注册成功').$code, $back_url, 1, 3);
            exit;
		} else {
			$data = array();
			$back_url = $this->input->get('back') ? $this->input->get('back') : (isset($_SERVER['HTTP_REFERER']) ? (strpos($_SERVER['HTTP_REFERER'], 'login') !== false ? '' : $_SERVER['HTTP_REFERER']) : '');
		}

		$this->template->assign(array(
			'data' => $data,
			'code' => $MEMBER['setting']['regcode'],
			'back_url' => $this->security->xss_clean($back_url),
			'regfield' => $MEMBER['setting']['regfield'],
			'meta_title' => fc_lang('会员注册'),
		));
		$tpl = 'register'.($groupid ? '_'.$groupid : '').'.html';
		$this->template->display(is_file(TPLPATH.'pc/member/'.MEMBER_TEMPLATE.'/common/'.$tpl) ? $tpl : 'register.html');
	}
	//	获取融云token
	public function getContactToken() {
		$fid = trim($_REQUEST['fid']);
		if(!$fid) {
			echo json_encode(array('code'=>0,'msg'=>'参数错误'));
			exit;
		}
		$member = $this->db->query('SELECT fid,floginname,fnickname,frytoken FROM `f_user` WHERE `fid` = '.$fid)->row_array();
		if(!$member) {
			echo json_encode(array('code'=>0,'msg'=>'会员不存在'));
			exit;
		}
		//判断在cms是否注册
		$cms_member = $this->db->where('username',$member['floginname'])->get('member')->row_array();
		if(empty($cms_member)) {
			$password = $_GET['password'];
			$step = $this->appRegister($member['floginname'],$password);
		}
		if(empty($member['frytoken'])) {
			// 重置随机数种子。
			srand((double)microtime()*1000000);
			
			$appSecret = CONTACT_SECRET; // 开发者平台分配的 App Secret。
			$nonce = rand(); // 获取随机数。
			$timestamp = time(); // 获取时间戳。
			
			$signature = sha1($appSecret.$nonce.$timestamp);
			
			if(empty($member['favatar'])) {
				$member['favatar'] = 'https://futureex.cc/img/dude.jpg';
			}
			if(empty($member['fnickname'])) {
				$member['fnickname'] = '未知用户';
			}
			$post_data = array(
				'userId' => $member['fid'],
				'name' => $member['fnickname'],
				'portraitUri' => $member['favatar'],
			);
			$header = array(
				'POST /user/getToken.json HTTP/1.1',
				'Host: api.cn.ronghub.com',
				'App-Key: '.CONTACT_KEY,
				'Nonce: '.$nonce,
				'Timestamp: '.$timestamp,
				'Signature: '.$signature,
				'Content-Type: application/x-www-form-urlencoded',
				/*'Content-Length: 78'*/
			);
			$ch = curl_init();
			curl_setopt($ch, CURLOPT_HEADER, false);
			curl_setopt($ch, CURLOPT_POST, true);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
			$post_data = http_build_query($post_data);
			curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);
			curl_setopt($ch, CURLOPT_URL, 'http://api.cn.ronghub.com/user/getToken.json');
			curl_setopt($ch,CURLOPT_HTTPHEADER , $header);
			curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false); 
			curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
			$result=curl_exec($ch);
			curl_close($ch);
			/*{
			    "code": 200,
			    "userId": "10008261",
			    "token": "7e+EqZOSvEZVP5uja4S06lTu/TgGBoGz5fE553uAo3Q55cLUUTCudIhJIC7J7azII62H9NVM2oML9iygNOOpO2LABjb60ZCG"
			}*/
			$res = json_decode($result,true);
			//file_put_contents(ROOT_PATH.'/readme.txt', $res,FILE_APPEND);
			if($res['code'] == 200) {
				$result = $this->db->query("UPDATE `f_user` SET `frytoken` = '".$res['token']."' WHERE `fid` = ".$fid);
				if($result) {
					echo json_encode(array('code'=>1,'msg'=>'操作成功','return'=>array('frytoken'=>$res['token'])));
					exit;
				}
			}else{
				echo json_encode(array('code'=>0,'msg'=>$res['errorMessage']));
				exit;
			}
		}else{
			echo json_encode(array('code'=>1,'msg'=>'操作成功','return'=>array('frytoken'=>$member['frytoken'])));
			exit;
		}
	}
	public function appRegister($floginname,$password) {
		$cmsurl = SITE_URL.'index.php?s=member&c=register';
		$cms_data['type'] = '1';
		$cms_data['username'] = $floginname;
		$cms_data['password'] = $password;
		$cms_data['password2'] = $password;
		$cmsres = $this->cmsPost($cmsurl,$cms_data);
		return $cmsres;
	}
	public function cmsPost($url,$data=array()) {
		$request['data'] = $data;
		$curl = curl_init();
		curl_setopt($curl, CURLOPT_TIMEOUT, 60); //设置超时
        //设置抓取的url
        curl_setopt($curl, CURLOPT_URL, $url);
        //设置获取的信息以文件流的形式返回，而不是直接输出。
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	    //设置post方式提交
	    $request = http_build_query($request);
	    curl_setopt($curl, CURLOPT_POST, 1);
	    curl_setopt($curl, CURLOPT_POSTFIELDS, $request);
	    //执行命令
	    $result = curl_exec($curl);
	   //关闭URL请求
	   curl_close($curl); 
	   $result = json_decode($result,TRUE);
	   return $result;
	}
}