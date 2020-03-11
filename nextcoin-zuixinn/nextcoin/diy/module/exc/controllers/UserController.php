<?php
if (!defined('BASEPATH')) exit('No direct script access allowed');
require dirname(__FILE__).'/Controller.php';
class UserController extends Controller
{
    private $loginUrl = '/login.html'; //登录接口地址

    private $vailImg = '/servlet/ValidateImageServlet.html'; //验证图片地址

    private $checkUserUrl = '/user/check_user_exist.html'; //检查用户是否存在接口地址

    private $sendSMSUrl = '/user/send_sms.html'; //发送短信接口

    private $sendEmailUrl = '/user/send_reg_email.html'; //发送注册短信

    private $bindPhoneCodeUrl = '/validate/send_bindphone_sms.html'; //发送绑定手机短信

    private $registerUrl = '/register.html'; //注册接口
    
    private $findBackUrl = '/validate/send_findbackmail.html';

    private $logout= '/user/logout.html';  //退出

    private $resetEmailUrl = '/validate/reset_email_json.html';

    private $resrtPasswordUrl = '/validate/reset_password.html';

    private $phoneRegUrl = '/user/phonereg_json.html';

    private $findPhoneUrl = '/validate/sendFindPhoneCode.html';

    private $checkPhoneUrl = '/validate/reset_password_check.html';

    private $resetByPhoneUrl = '/validate/reset_password_phone.html';

    private $resetPhoneJson = '/validate/reset_phone_json.html';

    private $vailMailUrl = '/validate/mail_validate_json.html';//邮箱验证

    public  function __construct() {
        parent::__construct();
		$this->load->model('content_model');
		$this->template->assign(array(
    			'reg' => $this->lang->line('reg'),
    			'login' => $this->lang->line('login'),
    			'vail' => $this->lang->line('vail'),
        ));
    }

    /**
     *
     * 登录页面
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function login() {
    		$this->template->assign(array(
    			'login' => $this->lang->line('login'),
        ));
		$this->template->display(DR_PATH.'login.html');
    }

    /**
     * 退出
     * @param Request $request
     * @return \Illuminate\Http\RedirectResponse|\Illuminate\Routing\Redirector
     */
    public function logout(){
        //cms退出登录
		$this->load->model('member_model');
		$this->member_model->logout();
		session_destroy(); 
		$this->template->assign(array(
    			'member' => '',
        ));
		//$request->session()->flush();
        $url = $this->host . $this->logout;
        $this->httpGet($url,[]);
		echo "<script>window.location.href='/index.php?s=exc&c=indexController';</script>";
		//$this->member_msg(fc_lang('您已经成功退出了').$this->member_model->logout(), '/index.php?s=exc&c=indexController', 1, 3);
        //return redirect()->route('main');
    }


    /**
     * 邮箱注册页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function register(){
        $introId = $_REQUEST['intro'];
		$this->template->assign(array(
    			'intro' => $introId,
        ));
		$this->template->display(DR_PATH.'register.html');
    }

    /**
     * 手机号注册页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function phonereg(){

        $url = $this->host . $this->phoneRegUrl;

        $introId = $_REQUEST['intro'];

        $response = $this->parseResponse($this->httpGet($url));
        $data = $response['data'];
		$this->template->assign(array(
    			'intro' => $data['intro'],
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'phonereg.html');
    }

    /**
     * 用户登录操作
     * @param Request $request
     * @return Request
     */
    public function userLogin(){
		$param = $_POST;
		unset($param['_t']);
		
        //$param = $request->except('_t');
        $response = $this->httpPost($this->host . $this->loginUrl,$param);
        if($response == false) {
            echo json_encode(array('code'=>'300','msg'=>'server error'));
			exit;
        }else{
            if(json_decode($response)->code==200){
            		setcookie('token',json_decode($response)->data,time()+7000,'/',null,null,true);
				//邀请注册送币
//				if($param['loginName']) {
//					$this->getscoreInvite($param['loginName']);
//				}
				$url = SITE_URL.'index.php?s=member&c=login&m=index';
				$data = array(
					'username' => $param['loginName'],
					'password' => $param['password'],
					'type' => 1
				);
				
				$cmsres = $this->cmsPost($url,$data);
				
				if($cmsres['status'] == 1) {
					$this->outputjson(200,'登录成功',array(
						'syncurl'=>$cmsres['syncurl'],
						'token'=>json_decode($response)->data
					)); 
				}else{
					if($cmsres['code'] == '会员不存在') {//注册
						$cmsurl = SITE_URL.'index.php?s=member&c=register&m=index';
						$cms_data['type'] = '1';
						$cms_data['username'] = $param['loginName'];
						
						$cms_data['password'] = $param['password'];
						$cms_data['password2'] = $param['password'];
						
						$cmsres = $this->cmsPost($cmsurl,$cms_data);
						
						if($cmsres['status'] == 1) {//注册成功
							$this->outputjson(200,'登录成功',array(
								'syncurl'=>$cmsres['syncurl'],
								'token'=>json_decode($response)->data
							)); 
						}
					}else{
						$this->outputjson(0,$cmsres['code']); 
					}
				}
//                $user = Redis::get(json_decode($response)->data);
//
//                session(['user_name' => $userarr['extObject']['fnickname']]);
//                if(isset($userarr['extObject']['frealname'])){
//                    session(['user_name'=> $userarr['extObject']['frealname']]);
//                }

//                setcookie('token',json_decode($response)->data);
            }else{
            		echo json_encode(array('code'=>json_decode($response)->code,'msg'=>json_decode($response)->msg));
				exit;
            }
        }
    }
	/**
	 * 邀请得币
	 */
	public function getscoreInvite($username='',$intro_user='') {
		$this->load->model('member_model');
		$num = $this->db->where('mark', 'invite')->count_all_results('member_scorelog');
		
		if($num < dr_block('yqdbslxz')) {
			//查找币种是否开启
			$coin = $this->db->query('SELECT id,name,status FROM `system_coin_type` WHERE `id` = 36 AND `status` = 1')->row_array();
			if($coin) {
				if($intro_user) {//邀请人
					$inviter = $this->db->query('SELECT fid,floginname FROM `f_user` WHERE `fid` = '.$intro_user)->row_array();
					if($inviter) {
						$result = $this->db->query('UPDATE `user_coin_wallet` SET `total` = `total`+'.dr_block('yqzsptbsl').' WHERE `uid` = '.$fid.' AND `coin_id` = 36');
						$this->db->insert('1_coin_record',array(
							'fid' => $intro_user,
							'coin_id' => '36',
							'value' => dr_block('yqzsptbsl'),
							'note' => '邀请好友注册,奖励平台币',
							'inputtime' => time()
						));
					}
				}elseif($username) {//被邀请注册
					$fuser = $this->db->query('SELECT fid,floginname,fintrouid FROM `f_user` WHERE `floginname` = '.$username)->row_array();
					if($fuser['fintrouid']) {
						$result = $this->db->query('UPDATE `user_coin_wallet` SET `total` = `total`+'.dr_block('yqzsptbsl').' WHERE `uid` = '.$fuser['fid'].' AND `coin_id` = 36');
						$this->db->insert('1_coin_record',array(
							'fid' => $fuser['fid'],
							'coin_id' => '36',
							'value' => dr_block('yqzsptbsl'),
							'note' => '被邀请注册,奖励平台币',
							'inputtime' => time()
						));
					}
				}else{
					return FALSE;
				}
			}
		}else{
			return TRUE;
		}
		return TRUE;
	}
    /**
     * 获取用户注册图片验证码
     * @return mixed
     */
    public function getVailImg(){
        $url = $this->host . $this->vailImg . '?' . time();
        $content =  $this->httpGet($url,null,true);
        preg_match('/Set-Cookie:(.*);/iU',$content,$str);

        $cookie = substr($str[1],strpos($str[1],'CHECKCODE=')+10);


        $img = substr($content,strpos($content,"\r\n\r\n")+4);
        setcookie('CHECKCODE',$cookie);
		header('Content-Type:image/jpeg;charset=UTF-8');
		echo $img;
//      return response($img, 200, [
//          'Content-Type' => 'image/jpeg;charset=UTF-8',
//      ]);
    }


    /**
     * 检查用户是否注册
     * @param Request $request
     * @return mixed|string
     */
    public function checkUser(){
        $param['regUserName'] = $_REQUEST['regUserName'];
        $param['regType'] = $_REQUEST['regType'];

        $url = $this->host . $this->checkUserUrl;
        $response = $this->httpGet($url);

        echo $this->parseApi($response);
    }


    /**
     * 发送手机验证码
     * @param Request $request
     * @return mixed
     */
    public function sendSms(){
        $param['type'] = $_REQUEST['type'];
        $param['msgtype'] = $_REQUEST['msgtype'];
        $param['area'] = $_REQUEST['area'];
        $param['phone'] = $_REQUEST['phone'];
        $param['vcode'] = $_REQUEST['vcode'];
        $url = $this->host . $this->sendSMSUrl;
        $response = $this->httpPost($url,$param);

        echo $this->parseApi($response);
    }


    /**
     * 发送邮箱验证码
     * @param Request $request
     * @return mixed
     */
    public function emailCode() {
        $param['type'] = $_REQUEST['type'];
        $param['msgtype'] = $_REQUEST['msgtype'];
        $param['address'] = $_REQUEST['address'];
        $param['vcode'] = $_REQUEST['vcode'];
        $url = $this->host . $this->sendEmailUrl;
        $response = $this->httpPost($url,$param);
        echo $this->parseApi($response);
    }


    /**
     * 用户注册接口
     * @param Request $request
     * @return mixed
     */
    public function reg(){
        $param['regName'] = $_REQUEST['regName'];
        $param['password'] = $_REQUEST['password'];
        $param['regType'] = $_REQUEST['regType'];
        $param['vcode'] = $_REQUEST['vcode'];
        $param['ecode'] = $_REQUEST['ecode'];
        $param['pcode'] = $_REQUEST['pcode'];
        $param['intro_user'] = $_REQUEST['intro_user'];
        $param['areaCode'] = $_REQUEST['areaCode'];
        $url = $this->host . $this->registerUrl;
        $response = $this->httpPost($url,$param);
        if($response == false) {
            return json_encode(array('code'=>'300','msg'=>'server error'));
        }else{
        		
              if(json_decode($response)->code==200){
				//判断是否在创世挖矿期间
				$mining = $this->db->get(SITE_ID.'_mining_status')->row_array();
				if($mining['status'] == 2 && time() < $mining['time'] + 10 * 86400 && $mining['num'] < 5000){
					//在创世挖矿时间内 并且注册人数未超过5000 奖励10平台币
					$user = $this->db->query('select * from f_user where floginname='.$param['regName'])->row_array();
					if($this->db->query('select * from user_coin_wallet where coin_id=37 and uid='.$user['fshowid'])->row_array()){
						$this->db->query('update user_coin_wallet set total=total+10 where coin_id=37 and uid='.$user['fshowid']);
					}else{
						$this->db->query('insert user_coin_wallet(uid,coin_id,total,gmt_create,gmt_modified) values('.$user['fshowid'].',100,10,"'.date('Y-m-d H:i:s',time()).'","'.date('Y-m-d H:i:s',time()).'")');
					}
					
					//增加记录
					$this->db->query('insert into f_coin_record(uid,coin_id,value,note,inputtime) values('.$user['fshowid'].',100,10,"创世挖矿期间注册",'.time().')');
					$this->db->where('id',1)->set('num','num+1',FALSE)->update(SITE_ID.'_mining_status');
					$this->db->query('insert into f_mining_register(uid) values('.$user['fshowid'].')');
				}
				
              	 //邀请注册送币
//				if($param['intro_user']) {
//					$this->getscoreInvite('',$param['intro_user']);
//				}
//                $user = Redis::get(json_decode($response)->data);
//                session(['user_name' => json_decode($user)->extObject->fnickname]);
//                session(['user_token' => json_decode($response)->data]);
//                $user = Redis::get(json_decode($response)->data);
//                $userarr = json_decode($user,true);
//                session(['user_name' => $userarr['extObject']['fnickname']]);
//                session(['user_token' => json_decode($response)->data]);

				$cmsurl = SITE_URL.'index.php?s=member&c=register&m=index';
				$cms_data['type'] = '1';
				$cms_data['username'] = $param['regName'];
				$cms_data['password'] = $param['password'];
				$cms_data['password2'] = $param['password'];
				$cmsres = $this->cmsPost($cmsurl,$cms_data);
				if($cmsres['status'] == 1) {
					$this->outputjson(200,'注册成功',array('syncurl'=>$cmsres['syncurl'])); 
				}else{
					$this->outputjson(0,$cmsres['code']); 
				}
              }else{
              	 echo $this->parseApi($response);
              }
        }
       
    }


    /**
     * @param Request $request
     * @return string
     */
    public function bindPhoneSMS() {
    		$param['area'] = $_REQUEST['area'];
		$param['phone'] = $_REQUEST['phone'];

        $url = $this->host . $this->bindPhoneCodeUrl;

        $response = $this->httpPost($url,$param);
        echo $this->parseApi($response);
    }


    /**
     * @param Request $request
     * @return string
     */
    public function findPhoneSMS() {
		$param['area'] = $_REQUEST['area'];
		$param['phone'] = $_REQUEST['phone'];
		$param['imgcode'] = $_REQUEST['imgcode'];
        $url = $this->host . $this->findPhoneUrl;


        $response = $this->httpPost($url,$param);

        echo $this->parseApi($response);
    }


    /**
     * 重置邮件密码页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function resetEmail(){
		$param['uid'] = $_REQUEST['uid'];
		$param['uuid'] = $_REQUEST['uuid'];
		
		$url = $this->host . $this->phoneRegUrl;
        $response = $this->parseResponse($this->httpGet($url));
        $areaCodes = $response['data']['areaCodes'];
		$this->template->assign(array(
    			'areaCodes' => $areaCodes
        ));
        if(empty($param['uid']) || empty($param['uuid']) ){
            $this->template->display(DR_PATH.'validate/reset_email.html');
			exit;
        }
        $url = $this->host . $this->resetEmailUrl;
        $url .= '?' . http_build_query($param);
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
        if(empty($data)){
        		$this->template->display(DR_PATH.'validate/reset_email.html');
			exit;
        }
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'validate/reset_psd.html');
    }


    /**
     * 邮箱提交
     * @param Request $request
     * @return string
     */
    public function findBack(){
		$params['email'] = $_REQUEST['email'];
		$params['idcard'] = $_REQUEST['idcard'];
		$params['idcardno'] = $_REQUEST['idcardno'];
		$params['imgcode'] = $_REQUEST['imgcode'];
        $url = $this->host . $this->findBackUrl;

        echo $this->parseApi($this->httpPost($url,$params));
    }


    /**
     * 重置密码
     * @param Request $request
     * @return string
     */
    public function resetPassword(){
		$params['totpCode'] = $_REQUEST['totpCode'];
		$params['newPassword'] = $_REQUEST['newPassword'];
		$params['newPassword2'] = $_REQUEST['newPassword2'];
		$params['fid'] = $_REQUEST['fid'];
        $url = $this->host . $this->resrtPasswordUrl;

        echo $this->parseApi($this->httpPost($url,$params));
    }


    /**
     * 手机验证
     * @param Request $request
     * @return string
     */
    public function phoneCheck(){
		$params['area'] = $_REQUEST['area'];
		$params['phone'] = $_REQUEST['phone'];
		$params['code'] = $_REQUEST['code'];
		$params['idcardno'] = $_REQUEST['idcardno'];
        $url = $this->host . $this->checkPhoneUrl;

        $response = $this->parseApi($this->httpPost($url,$params));

        $data = json_decode($response,true);
        if($data['data'] !=null) {
            setcookie('phone_reset',$data['data']['phone_reset'],time()+7000,'/',null,null,true);
        }
        echo $response;
    }


    /**
     * 通过手机重新设置密码
     * @param Request $request
     * @return string
     */
    public function resetPasswordByPhone(){
		$params['totpCode'] = $_REQUEST['totpCode'];
		$params['newPassword'] = $_REQUEST['newPassword'];
		$params['newPassword2'] = $_REQUEST['newPassword2'];
        $url = $this->host . $this->resetByPhoneUrl;
        echo $this->parseApi($this->httpPost($url,$params));
    }

    public function resetPhone(){
        $url = $this->host . $this->resetPhoneJson;


        $reponse = $this->httpGet($url);

        $data = json_decode($reponse,true);

        if(isset($data['data']['phone_reset_Second'])) {
            setcookie('phone_reset_Second',$data['data']['phone_reset_Second'],time()+7000,'/',null,null,true);
        }
		$this->template->display(DR_PATH.'validate/reset_phone.html');
    }


    /**
     * 邀请注册
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function intro(){
        $introId = $_REQUEST['intro'];

        $url = $this->host . $this->phoneRegUrl;

        $response = $this->parseResponse($this->httpGet($url));
        $data = $response['data'];
        $data['intro'] = $introId;
        $this->template->assign('data',$data);
        $this->template->display(DR_PATH.'phonereg.html');
    }


    /**
     * 邮箱验证接口
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function vailMail(){
		$params['uid'] = $_REQUEST['uid'];
		$params['uuid'] = $_REQUEST['uuid'];

        $url = $this->host . $this->vailMailUrl;
        $url .= '?' .http_build_query($params);

        $response = $this->parseResponse($this->httpGet($url));
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'validate/vailMail.html');
    }


}
