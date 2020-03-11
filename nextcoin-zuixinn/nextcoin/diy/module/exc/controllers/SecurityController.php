<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

require dirname(__FILE__).'/Controller.php';

class SecurityController extends Controller
{

    private $securityJsonUrl = '/user/security_json.html';

    private $loginLogUrl = '/user/user_loginlog_json.html';

    private $settingLogUrl = '/user/user_settinglog_json.html';

    private $googleQRUrl = '/user/bind_google_device.html';
	
	private $getGoogleCode = '/user/get_google_key.html';

    private $bindGoogle = '/user/google_auth.html';

    private $sendEmailUrl = '/validate/send_bindmail.html';

    private $bindPhoneUrl = '/validate/bindphone.html';

    private $modiPasswordUrl = '/user/modify_passwd.html';

    private $saveRealUrl = '/real_name_auth.html';
    
    private $findtradepwdUrl = '/user/findtradepwd.html';
    
	public  function __construct() {
        parent::__construct();
		$this->load->model('content_model');
		$this->template->assign(array(
    			'user' => $this->lang->line('user'),
        ));
    }
    /**
     * 安全中心首页
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function index(){
    		$currentPage = $_REQUEST['currentPage'];
		$type = $_REQUEST['type'];
        $url = $this->host . $this->securityJsonUrl;
        $response = $this->httpGet($url);

        $response = $this->parseResponse($response);
        $data = $response['data'];
		$jd = $data['bindcount'] / 6 * 100;
		$this->template->assign(array(
    			'data' => $data,
    			'currentPage' => $currentPage,
    			'type' => $type,
    			'jd' => $jd
        ));
		$this->template->display(DR_PATH.'user/security1.html');
    }


    /**
     * 登录日志页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function loginLog() {
        $currentPage = $_REQUEST['currentPage'];
        $url = $this->host . $this->loginLogUrl;
        if(!empty($currentPage)){
            $url .= '?currentPage=' . $currentPage;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data']['flogs'];
        echo json_encode(array('code'=>'1', 'msg'=>'ok', 'res'=>$data), JSON_UNESCAPED_UNICODE);
		exit();
//      return view('user.loginlog',['data'=>$data]);

    }


    /**
     * 设置日志页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function settingLog() {
        $currentPage = $_REQUEST['currentPage'];
        $url = $this->host . $this->settingLogUrl;
        if(!empty($currentPage)){
            $url .= '?currentPage=' . $currentPage;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data']['flogs'];
        echo json_encode(array('code'=>'1', 'msg'=>'ok', 'res'=>$data), JSON_UNESCAPED_UNICODE);
		exit();
//      return view('user.settinglog',['data'=>$data]);

    }

    /**
     * 获取谷歌验证器密码
     * @param Request $request
     * @return mixed
     */
    public function getGoogleQR(){
        $url = $this->host .$this->googleQRUrl;
        $response = $this->httpPost($url);
        echo $this->parseApi($response);
    }


    /**
     * 绑定谷歌验证器
     * @param Request $request
     * @return string
     */
    public function bindGoogleCode(){
        $param['code'] = $_REQUEST['code'];
        $param['totpKey'] = $_REQUEST['totpKey'];
        $url = $this->host . $this->bindGoogle;
        $response = $this->httpPost($url,$param);
        echo $this->parseApi($response);
    }

	
    /**
     * 查看谷歌验证器
     * @param Request $request
     * @return string
     */
    public function getGoogleCode(){
        $param['totpCode'] = $_REQUEST['totpCode'];

//      $url = $this->host . $this->googleQRUrl;
		$url = $this->host . $this->getGoogleCode;
        $response = $this->httpPost($url,$param);
        echo $this->parseApi($response);
    }

    /**
     * 发送绑定邮件
     * @param Request $request
     * @return string
     */
    public function sendEmailCode(){
        $param['email'] = $_REQUEST['email'];

        $url = $this->host . $this->sendEmailUrl;

        $response = $this->httpPost($url,$param);

        echo $this->parseApi($response);
    }


    /**
     * 绑定手机
     * @param Request $request
     * @return string
     */
    public function bindPhone() {
        $param['phone'] = $_REQUEST['phone'];
		$param['area'] = $_REQUEST['area'];
		$param['code'] = $_REQUEST['code'];
        $url= $this->host . $this->bindPhoneUrl;

        $response = $this->httpPost($url,$param);
        echo $this->parseApi($response);
    }


    /**
     * 修改|设置  登录|交易 密码接口
     * @param Request $request
     * @return string
     */
    public function modifyPassword() {
    		$param['pwdType'] = $_REQUEST['pwdType'];//0:登录密码；1：交易密码
		$param['originPwd'] = $_REQUEST['originPwd'];//原始密码
		$param['newPwd'] = $_REQUEST['newPwd'];//新密码
		$param['reNewPwd'] = $_REQUEST['reNewPwd'];
		$param['totpCode'] = $_REQUEST['totpCode'];
		$param['identityCode'] = $_REQUEST['identityCode'];
		
        $url = $this->host . $this->modiPasswordUrl;
        $response = $this->httpPost($url,$param);
		$response = $this->parseApi($response);
		$result = json_decode($response,TRUE);
        echo $response;
    }


    /**
     * 设置真实资料
     * @param Request $request
     * @return string
     */
    public function saveRealName() {
		$param['realname'] = $_REQUEST['realname'];
		$param['identitytype'] = $_REQUEST['identitytype'];
		$param['identityno'] = $_REQUEST['identityno'];
		$param['address'] = $_REQUEST['address'];
		$param['idCardZmImgURL'] = $_REQUEST['idCardZmImgURL'];
		$param['idCardFmImgURL'] = $_REQUEST['idCardFmImgURL'];
		$param['idCardScImgURL'] = $_REQUEST['idCardScImgURL'];
        $url = $this->host . $this->saveRealUrl;

        $response = $this->httpPost($url,$param);
		$result = json_decode($response,true);
		if($result['code'] == 200) {
			$url = 'https://'.$_SERVER['HTTP_HOST'].'/index.php?c=securityController&m=getscoreRealname&username='.USERNAME;
			$cmsres = $this->httpGet($url,'');
		}
        echo $this->parseApi($response);
    }
    /**
	 * 实名认证送币
	 */
	public function getscoreRealname() {
		$username = $_GET['username'];
		if(!$username) {
			echo json_encode(array('code'=>0,'msg'=>'error'));
			exit;
		}
		$this->load->model('member_model');
		$this->db->select('uid,username');
		$member = $this->db->where('username',$username)->get('member')->row_array();
		$isget = $this->db->where(array('uid'=>$member['uid'],'mark'=>'realname'))->get('member_scorelog')->row_array();
		if(!$isget) {
			$result = $this->member_model->update_score(1, $member['uid'], 2, 'realname', '实名认证成功，赠送平台币');
			if($result) {
				echo json_encode(array('code'=>200,'msg'=>'ok'));
				exit;
			}else{
				echo json_encode(array('code'=>0,'msg'=>'error'));
				exit;
			}
		}else{
			echo json_encode(array('code'=>200,'msg'=>'ok'));
			exit;
		}
	}
    /**
     * 绑定邮箱页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function emailHtml() {
		$this->template->display(DR_PATH.'user/emailHtml.html');
    }
    /**
     * 绑定手机页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function phoneHtml() {
		$this->template->display(DR_PATH.'user/phoneHtml.html');
    }
    /**
     * 绑定谷歌验证码页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function googleHtml() {
    		$url = $this->host . $this->securityJsonUrl;
        $response = $this->httpGet($url);

        $response = $this->parseResponse($response);
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'user/googleHtml.html');
    }
	
	/**
     * 绑定交易密码页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function transPsw() {
    		$url = $this->host . $this->securityJsonUrl;
        $response = $this->httpGet($url);

        $response = $this->parseResponse($response);
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'user/transPsw.html');
    }
    
    /**
     * 修改交易密码页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function transAlterPsw() {
    		$url = $this->host . $this->securityJsonUrl;
        $response = $this->httpGet($url);

        $response = $this->parseResponse($response);
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'user/transAlterPsw.html');
    }
    
    /**
     * 忘记交易密码页面
     */
    public function forgetTransPsw(){
    		$user = $this->db->query('select * from f_user where `floginname`="'.USERNAME.'"')->row_array();
    		if(empty($user)){
    			$user['ftelephone'] = '';
    		}
    		
    		if(IS_POST){
    			$param['phone'] = $_REQUEST['phone'];
    			$param['code'] = $_REQUEST['code'];
    			$param['totpCode'] = $_REQUEST['totpCode'];
    			$param['newPassword'] = $_REQUEST['newPassword'];
    			$param['newPassword2'] = $_REQUEST['newPassword2'];
    			$param['area'] = $_REQUEST['area'];
	        $url = $this->host . $this->findtradepwdUrl;
			
	        $response = $this->httpPost($url,$param);
			 echo $this->parseApi($response);
	        exit();
    		}
    		
    		$this->template->assign(array('phone'=>$user['ftelephone'],'areaNum'=>$user['fareacode']));
    		$this->template->display(DR_PATH.'user/forgetTransPsw.html');
    		
    }
    
    /**
     * 实名认证页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function namevalidate() {
    		$url = $this->host . $this->securityJsonUrl;
        $response = $this->httpGet($url);

        $response = $this->parseResponse($response);
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data,
    			'uploadUrl' => $this->host.'/upload.html'
        ));
		$this->template->display(DR_PATH.'user/namevalidate.html');
    }
    
    /**
     * 修改登录密码
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function alterLogingPsw() {
    		$url = $this->host . $this->securityJsonUrl;
        $response = $this->httpGet($url);

        $response = $this->parseResponse($response);
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'user/alterLogingPsw.html');
    }

}
