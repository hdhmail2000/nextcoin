<?php
class Polling{
	public $host = '172.21.198.136:8080';
	private $realPriceUrl = '/real/markets.html';
	private $klineUrl = '/kline/fullperiod.html';
	private $fullDepthUrl = '/kline/fulldepth.html';
	private $marketUrl = '/real/market.html';
	/**
     * 获取实时价格
     * @param Request $request
     * @return IndexController|string
     */
    public function realPrice(){
		$param['symbol'] = $_REQUEST['symbol'];
        $url = $this->host . $this->realPriceUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }
	/**
     * @param Request $request
     * @return string
     */
    public function kline() {
    		$param['symbol'] = $_REQUEST['symbol'];
		$param['step'] = $_REQUEST['step'];
        $url = $this->host . $this->klineUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }
	/**
     * 深度图数据
     * @param Request $request
     * @return string
	 * TradeController
     */
    public function fulldepth(){
		$param['symbol'] = $_REQUEST['symbol'];
        $url = $this->host . $this->fullDepthUrl;

        echo $this->parseApi($this->httpPost($url,$param));
    }
	/**
     * 实时交易
     * @param Request $request
     * @return string
	 * TradeController
     */
    public function market(){
    		$param['symbol'] = $_REQUEST['symbol'];
		$param['buysellcount'] = $_REQUEST['buysellcount'];
		$param['successcount'] = $_REQUEST['successcount'];

        $url = $this->host . $this->marketUrl;

        echo $this->parseApi($this->httpGet($url,$param));
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
}
?>