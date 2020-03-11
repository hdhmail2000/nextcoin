<?php
define('DR_PATH', 'fw/');
class Controller extends M_Controller {

    public $host = '172.21.196.141:8080';
    public $redis;
    public $redis_host = '172.21.196.137';
    public $redis_password = 'relang123';
    public function __construct(){
    		parent::__construct();
		//连接redis
		$this->redis = new Redis();
		if ($this->redis->connect($this->redis_host, 6379,3) == false) {
		   die($this->redis->getLastError());
		}
		  /* user:password 拼接成AUTH的密码 */
		if ($this->redis->auth($this->redis_password) == false) {
		   	die($this->redis->getLastError());
		 }
        $this->getUserName();
//      if(!session('lang')){
//          session(['lang'=>'cn']);
//      }
		if(!$this->session->userdata('language')) {
			$this->session->set_userdata('language','zh-cn');
		}
		
		//用户会员组
		$member = $this->db->wherE('username',USERNAME)->get('member')->row_array();
		
		$this->lang->load('exc_fw', $this->session->userdata('language'));
		//print_r($this->redis->keys('*'));
		$this->template->assign(array(
    			'lang' => $this->session->userdata('language'),
    			'head' => $this->lang->line('header'),
    			'controller' => $_GET['c'],
    			'method' => $_GET['m'],
    			'USERNAME' => USERNAME,
    			'FID' => FID,
    			'groupid' => $member['groupid']
        ));
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
     * 获取用户名称
     * @return bool
     */
    public function getUserName(){
        if(!isset($_COOKIE['token'])){
            $username = false;
            define('USERNAME',$username);
			define('FID',false);
            return false;
        }
        $token = $_COOKIE['token'];
        $user = $this->redis->get($token);
        if(empty($user)){
            $username = false;
            define('USERNAME',$username);
			define('FID',false);
            return false;
        }
        $userarr = json_decode($user,true);
        $username = $userarr['extObject']['floginname'];
		$fid = $userarr['extObject']['fid'];
        if(isset($userarr['extObject']['floginname'])){
            $username = $userarr['extObject']['floginname'];
        }
        define('USERNAME',$username);
		define('FID',$fid);
        return true;
    }


    /**
     *  获取cookie
     * @return string
     * @internal param Request $request
     */
    public function getCookie(){

        $cookie['token']  = isset($_COOKIE['token']) ? $_COOKIE['token'] : '';
        $cookie['CHECKCODE']  = isset($_COOKIE['CHECKCODE']) ? $_COOKIE['CHECKCODE'] : '';
        $cookie['oex_lan']  = isset($_COOKIE['oex_lan']) ? $_COOKIE['oex_lan'] : '';
        $cookie['phone_reset']  = isset($_COOKIE['phone_reset']) ? $_COOKIE['phone_reset'] : '';
        $cookie['phone_reset_Second']  = isset($_COOKIE['phone_reset_Second']) ? $_COOKIE['phone_reset_Second'] : '';
        $cookieStr = '';
        foreach ($cookie as $key=>$value){
            if(empty($value)){
                unset($cookie[$key]);
            }else{
                $cookieStr .= $key . '=' . $value .'; ';
            }

        }
        return $cookieStr;
    }

    /**
     * 获取客户端ip
     * @return mixed
     */
    public function getClietnIp(){

        if(isset($_SERVER['HTTP_X_FORWARDED_FOR'])){
            return  $_SERVER['HTTP_X_FORWARDED_FOR'];
        }
      return $_SERVER['REMOTE_ADDR'];
    }


    /**
     * 从api获取数据
     * @param $url
     * @param array $params
     * @param $header_out
     * @return mixed
     * @internal param null $header
     * @internal param $impost
     */
    public function httpGet($url,$params=null,$header_out=false) {


        $header[] =  'X-Forwarded-For: ' . $this->getClietnIp();
        $header[] =  'cookie: ' . $this->getCookie();

        $httpInfo = array();
        $ch = curl_init();

        curl_setopt($ch, CURLOPT_HTTP_VERSION, CURL_HTTP_VERSION_1_1);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);
        curl_setopt($ch, CURLOPT_TIMEOUT, 20);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        curl_setopt($ch, CURLINFO_HEADER_OUT, TRUE);//获取请求头信息

//        curl_setopt($ch, CURLOPT_COOKIEJAR, $cookieFile);//用来存放登录成功的cookie
//        curl_setopt($ch, CURLOPT_COOKIEFILE, $cookieFile); //使用cookie

        if(!empty($header_out)){
            curl_setopt($ch, CURLOPT_HEADER, true);
        }

        if ($params) {
            $params = http_build_query($params);
            curl_setopt($ch, CURLOPT_URL, $url . '?' . $params);
        } else {
            curl_setopt($ch, CURLOPT_URL, $url);
        }
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        $httpInfo = curl_getinfo($ch);
//        print_r($httpInfo);


        return $response;
    }


    /**
     * http Post
     * @param $url
     * @param array $params
     * @return mixed
     * @internal param $apiUrl
     */
    public function httpPost($url, $params=[]){

        $header[] =  'X-Forwarded-For: ' . $this->getClietnIp();
        $header[] =  'cookie: ' . $this->getCookie();

        $ch = curl_init();
        $httpInfo = array();

        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_HTTP_VERSION, CURL_HTTP_VERSION_1_1);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);
        curl_setopt($ch, CURLOPT_TIMEOUT, 20);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
//        curl_setopt($ch, CURLOPT_COOKIEJAR, $cookieFile);//用来存放登录成功的cookie
//        curl_setopt($ch, CURLOPT_COOKIEFILE, $cookieFile); //使用cookie

        $params = http_build_query($params);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $params);

        curl_setopt($ch, CURLINFO_HEADER_OUT, TRUE);//获取请求头信息
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        $httpInfo = array_merge($httpInfo, curl_getinfo($ch));
//        print_r($httpInfo);

//        if($httpCode !='200'){
//            print_r($httpInfo);
//        }
        curl_close($ch);
        return $response;
    }
	public function cmsPost($url,$data=[]) {
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
		//tiaoguoSSL 验证
		curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, '0');
		curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, '0');
	    //执行命令
	    $result = curl_exec($curl);
	   //关闭URL请求
	   curl_close($curl); 
	  
	   $result = json_decode($result,TRUE);
	   return $result;
	}

    /**
     * api请求失败处理
     * @param $response
     * @return string
     */
    public function parseApi($response){
        if($response == false){
            return json_encode(array('code'=>'301','msg'=>'server erros!'));
        }
       return $response;
    }


    /**
     * 解析页面初始化返回的数据  方便错误集中处理
     * @param $response
     * @return mixed
     * @internal param $json
     */
    public function parseResponse($response){
        if($response == false){
        		//$this->load->model('member_model');
			//$this->member_msg(fc_lang('服务维护中.....'), '/index.php?s=exc&c=indexController', 1, 3);
            //dd('服务维护中.....');
        }

        $response = json_decode($response,true);
//        dd($response);
        if($response['code'] != 200){
            if($response['code'] == 401){
            		$this->load->model('member_model');
				$this->member_model->logout();
				session_destroy(); 
                //强制重定向
                echo "<script>window.location='/index.php?s=exc&c=userController&m=login'</script>";
                exit;
            }
            //dd($response['msg']);
        }
        return $response;
    }
	/**
	 * 输出json
	 */
	public function outputjson($code,$msg,$data='') {
		if(!empty($data)) {
			exit(json_encode(array('code'=>$code,'msg'=>$msg,'data'=>$data),JSON_UNESCAPED_UNICODE));
		}else{
			exit(json_encode(array('code'=>$code,'msg'=>$msg),JSON_UNESCAPED_UNICODE));
		}
		
	}
}
