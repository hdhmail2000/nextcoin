<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

/**
 * Dayrui Website Management System
 *
 * @since		version 2.0.2
 * @author		Dayrui <dayrui@gmail.com>
 * @license     http://www.dayrui.com/license
 * @copyright   Copyright (c) 2011 - 9999, Dayrui.Com, Inc.
 */

class Home extends M_Controller {
	 private $indexJson = '/index_json.html';

    private $indexMarketUrl = '/real/indexmarket.html';

    private $realPriceUrl = '/real/markets.html';

    private $klineUrl = '/kline/fullperiod.html';

    private $newsUrl = '/articles_json.html';

    private $switchlan='/real/switchlan.html';

    private $noticeUrl = '/notice/index_json.html';

    private $userWalletUrl = '/real/userWallet_json.html';
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
    			'lang' => $this->session->userdata('language'),
    			'member' => $this->member
        ));
    }

    /**
     * 首页
     */
    public function index() {
 		$param = $this->input->get('currentPage');
        $url = API_HOST . $this->indexJson;
        if(!empty($param)){
            $param = http_build_query($param);
            $url .= '?' . $param;
        }


        //获取公告
        $notice = array();
        $noticeUrl = API_HOST . $this->noticeUrl;
        $noticeUrl .= '?' . http_build_query(array('id'=>2,'currentPage'=>1));

        $response = $this->content_model->httpGet($noticeUrl);
        if($response !=false){
            $response = json_decode($response,TRUE);
            if($response && $response['code']=='200'){
                $notice = $response['data']['farticles'];
            }
        }
        //读取banner
//      $banner = array();
//      for($i = 1;$i<=5;$i++){
//          $item =  Redis::get('ARGS_bigImage'.$i);
//		    if(!empty($item)){
//				$banner[] = json_decode($item)->extObject;
//		    }
//      }

        $response = $this->content_model->httpGet($url);
		$response = json_decode($response,TRUE);
		$this->template->assign(array(
    			'lang_Home' => $this->lang->line('Home'),
    			'notice' => $notice,
    			'response' => $response['data']
        ));
		parent::_index();
		
    }
	/**
     * 首页
     * @return string
     */
    public function indexMarket(){
        $url = API_HOST . $this->indexMarketUrl;
		$res = $this->content_model->httpPost($url,[]);
		print_r($res);
    }
	 /**
     * 获取实时价格
     * @param Request $request
     * @return IndexController|string
     */
    public function realPrice(){
        $param = $request->only(['symbol']);
        $url = API_HOST . $this->realPriceUrl;
        return $this->parseApi($this->httpPost($url,$param));
    }
	/**
	 * 切换网站语言
	 */
	public function changeLang() {
		$lang = $this->input->get('lang');
		if($lang == 'english'){
            setcookie('oex_lan','en_US',time()+3600*24*30,'/',null,null,true);
        }else{
            setcookie('oex_lan','zh_TW',time()+3600*24*30,'/',null,null,true);
        }
		$this->session->set_userdata('language',$lang);
		echo json_encode(1);
		exit;
	}
}