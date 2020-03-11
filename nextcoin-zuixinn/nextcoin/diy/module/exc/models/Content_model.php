<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/**
 * Dayrui Website Management System
 *
 * @since		version 2.0.0
 * @author		Dayrui <dayrui@gmail.com>
 * @license     http://www.dayrui.com/license
 * @copyright   Copyright (c) 2011 - 9999, Dayrui.Com, Inc.
 */

require FCPATH.'branch/fqb/C_Model.php';
 
class Content_model extends C_Model {

	/*
	 * 构造函数
	 */
    public function __construct() {
        parent::__construct();
    }
	public function httpPost($url,$data=[]) {
		$header[] =  'X-Forwarded-For: ' . $this->getClietnIp();
        $header[] =  'cookie: ' . $this->getCookie();

		$curl = curl_init();
		curl_setopt($curl, CURLOPT_TIMEOUT, 60); //设置超时
        //设置抓取的url
        curl_setopt($curl, CURLOPT_URL, $url);
        //设置获取的信息以文件流的形式返回，而不是直接输出。
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($curl, CURLOPT_HTTPHEADER, $header);
		//设置post方式提交
		$data = http_build_query($data);
		curl_setopt($curl, CURLOPT_POST, 1);
    		curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
	    //执行命令
	    $result = curl_exec($curl);
	   //关闭URL请求
	   curl_close($curl); 
	   $result = json_decode($result,TRUE);
	   return $result;
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
	    //执行命令
	    $result = curl_exec($curl);
	   //关闭URL请求
	   curl_close($curl); 
	   $result = json_decode($result,TRUE);
	   return $result;
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
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 30);
        curl_setopt($ch, CURLOPT_TIMEOUT, 30);
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
        //print_r($httpInfo);


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