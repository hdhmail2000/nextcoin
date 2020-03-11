<?php
if (!defined('BASEPATH')) exit('No direct script access allowed');

require dirname(__FILE__).'/Controller.php';

class TradeController extends Controller
{

    private $indexUrl = '/trademarket_json.html';

    private $marketUrl = '/real/market.html';

    private $fullDepthUrl = '/kline/fulldepth.html';

    private $fullPeriodUrl = '/kline/fullperiod.html';

    private $cnyBuyUrl = '/trade/cny_buy.html';

    private $cnySellUrl = '/trade/cny_sell.html';

    private $cnyCancelUrl = '/trade/cny_cancel.html';

    private $assetUrl = '/real/userassets.html';

    private $entructsUrl = '/real/getEntruts.html';

    private $entrustMoreUrl = '/trade/cny_entrust_json.html';

    private $rateUrl = '/market/rate.html';

    private $entrustLogUrl = '/trade/cny_entrustLog.html';
	/**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
		$this->load->model('content_model');
		$this->template->assign(array(
    			'market' => $this->lang->line('market'),
        ));
    }
    /**
     * 市场首页
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function index() {
		$param['symbol'] = $_REQUEST['symbol'];
		$param['type'] = $_REQUEST['type'];
		$param['sb'] = $_REQUEST['sb'];
        foreach ($param as $key=>$value) {
            if(empty($value)){
                unset($param[$key]);
            }
        }
        $url = $this->host . $this->indexUrl;
        if(!empty($url)){
            $url .= '?' . http_build_query($param);
        }
        $response = $this->parseResponse($this->httpGet($url));
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		
		$this->template->display(DR_PATH.'trade/index.html');
    }


    /**
     * 委托单更多页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function order(){
    		$data['symbol'] = $_REQUEST['symbol'];
		$data['type'] = $_REQUEST['type'];
		$data['sb'] = $_REQUEST['sb'];
		$data['plat'] = $_REQUEST['plat'];
        $data['symbols'] = explode('_',$data['sb']);
		
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'trade/order.html');
    }


    /**
     * 实时交易
     * @param Request $request
     * @return string
     */
    public function market(){
    		$param['symbol'] = $_REQUEST['symbol'];
		$param['buysellcount'] = $_REQUEST['buysellcount'];
		$param['successcount'] = $_REQUEST['successcount'];

        $url = $this->host . $this->marketUrl;

        echo $this->parseApi($this->httpGet($url,$param));
    }


    /**
     * 深度图数据
     * @param Request $request
     * @return string
     */
//  public function fulldepth(){
//		$param['symbol'] = $_REQUEST['symbol'];
//      $url = $this->host . $this->fullDepthUrl;
//
//      echo $this->parseApi($this->httpPost($url,$param));
//  }


    /**
     * k线图数据
     * @param Request $request
     * @return string
     */
//  public function fullperiod(){
//  		$param['symbol'] = $_REQUEST['symbol'];
//      $param = $request->only(['symbol','step']);
//
//      $url = $this->host . $this->fullPeriodUrl;
//
//      echo $this->parseApi($this->httpPost($url,$param));
//  }

    /**
     * 买币
     * @param Request $request
     * @return string
     */
    public function cnyBuy(){
		$param['tradeAmount'] = $_REQUEST['tradeAmount'];
		$param['tradePrice'] = $_REQUEST['tradePrice'];
		$param['tradePwd'] = $_REQUEST['tradePwd'];
		$param['symbol'] = $_REQUEST['symbol'];
		$param['limited'] = $_REQUEST['limited'];

        $url = $this->host . $this->cnyBuyUrl;

        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * 卖币
     * @param Request $request
     * @return string
     */
    public function cnySell(){
		$param['tradeAmount'] = $_REQUEST['tradeAmount'];
		$param['tradePrice'] = $_REQUEST['tradePrice'];
		$param['tradePwd'] = $_REQUEST['tradePwd'];
		$param['symbol'] = $_REQUEST['symbol'];
		$param['limited'] = $_REQUEST['limited'];

        $url = $this->host . $this->cnySellUrl;

        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * 撤单
     * @param Request $request
     * @return string
     */
    public function cnyCancel(){
		$param['id'] = $_REQUEST['id'];

        $url = $this->host . $this->cnyCancelUrl;

        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * 获取个人资产
     * @param Request $request
     * @return string
     */
    public function asset(){
		$param['tradeid'] = $_REQUEST['tradeid'];	

        $url = $this->host . $this->assetUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * 获取委托记录
     * @param Request $request
     * @return string
     */
//  public function getEntruts(){
//		$param['symbol'] = $_REQUEST['symbol'];	
//		$param['count'] = $_REQUEST['count'];	
//
//      $url = $this->host . $this->entructsUrl;
//
//      echo $this->parseApi($this->httpPost($url,$param));
//  }


    /**
     * 更多委托单
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function entrust(){
    		$param['symbol'] = $_REQUEST['symbol'];	
		$param['status'] = $_REQUEST['status'];
		$param['currentPage'] = $_REQUEST['currentPage'];
        foreach ($param  as $key =>$item){
            if(empty($param)){
                unset($param[$key]);
            }
        }
//        $url = $this->host . $this->entructsUrl;
        $url = $this->host . $this->entrustMoreUrl;
        $url = $url . '?' . http_build_query($param);

        $response = $this->parseResponse($this->httpGet($url));
        $data = $response['data'];
        $data['fentrusts'] = isset($data['fentrusts']) ? $data['fentrusts']:[];

//        print_r("<pre>");
//        print_r($response);die;

		
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'trade/entrust.html');
    }


    /**
     * 获取历史委托单详情
     * @param Request $request
     * @return string
     */
    public function entrustLog() {
    		$param['id'] = $_REQUEST['id'];	

        $url = $this->host . $this->entrustLogUrl;

        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     *
     * 获取人民币汇率
     * @return string
     */
    public function rate(){
        $url = $this->host . $this->rateUrl;
        echo $this->parseApi($this->httpPost($url,[]));
    }

}


