<?php
if (!defined('BASEPATH')) exit('No direct script access allowed');
define(PAGESIZE, 20);
require dirname(__FILE__).'/Controller.php';
class IndexController extends Controller
{

    private $indexJson = '/index_json.html';

    private $indexMarketUrl = '/real/indexmarket.html';

    private $realPriceUrl = '/real/markets.html';

    private $klineUrl = '/kline/fullperiod.html';
    
    private $newsUrl = '/articles_json.html';

    private $switchlan='/real/switchlan.html';

    private $noticeUrl = '/notice/index_json.html';

    private $userWalletUrl = '/real/userWallet_json.html';
    
    private $helpUrl = '/help.html';
    
    private $aboutUrl = '/about/about_json.html';
	/**
     * 构造函数
     */
    public function __construct() {
        parent::__construct();
		$this->load->model('content_model');
    }
    public function index(){
    	//挖矿记录
    	$mining = $this->db->order_by('id','desc')->limit(1)->get(SITE_ID.'_mining')->row_array();
		//每小时挖矿记录
		$time_mining = $this->db->query('select sum(num) as total from f_rebate_log where inputtime >="'.date('Y-m-d H:00:00',time()).'" and inputtime <="'.date('Y-m-d H:59:59',time()).'"')->row_array();
		$coin['total'] = 50000;//每小时挖矿总额
		$jd = $time_mining['total'] / $coin['total'] * 100;//进度



		
		//当前流通量
		$total['total'] = 100000000;
		//查询今日挖矿数量
		$mining_total['total'] = 0;
		if(FID){
			$start_time = date('Y-m-d',time());
			$mining_total = $this->db->query('select sum(num) as total from f_rebate_log where uid='.FID.' and status=2 and inputtime="'.$start_time.'"')->row_array();
		}
		
		//USDT 汇率
		$url = $this->host . $this->realPriceUrl;
        $url  .= '?' . http_build_query(array('symbol' => 55));
		$response = $this->httpGet($url);
		$usdt = json_decode($response,true)['data'];
		$usdt_huilv = $usdt[0]['p_new'];
		//BTC汇率
		$url = $this->host . $this->realPriceUrl;
        $url  .= '?' . http_build_query(array('symbol' => 54));
		$response = $this->httpGet($url);
		$hl1 = json_decode($response,true)['data'];
		$hl1 = $hl1[0]['p_new'];
			
		$url = $this->host . $this->realPriceUrl;
        $url  .= '?' . http_build_query(array('symbol' => 53));
		$response = $this->httpGet($url);
		$hl2 = json_decode($response,true)['data'];
		$hl2 = $hl2[0]['p_new'];
		
		$arr = array(
				'coin_total' => number_format($total['total'],'2','.',''),
				'coin_surplus' => $coin['total'] - $time_mining['total'],
				'mining_total' => number_format($time_mining['total'] ,'2','.',''),
				'jd' => $jd,
				'coin_usdt' =>  $total['total'] * $usdt_huilv,
				'mining_usdt' => number_format($total['total'] * $usdt_huilv,'2','.',''),
				'mining_btc' =>  number_format($mining['total'] / $hl1 / $hl2,'2','.',''),
				'time_slot' => date('H:00:00',time()).'~'.date('H:59:59',time()),
				'mining' => number_format($mining['total'],'2','.','')
		);
		
		$param = $_REQUEST['currentPage'];
        $url = $this->host . $this->indexJson;
        if(!empty($param)){
            $param = http_build_query($param);
            $url .= '?' . $param;
        }


        //获取公告
        $notice = array();
        $noticeUrl = $this->host . $this->noticeUrl;
        $noticeUrl .= '?' . http_build_query(array('id'=>2,'currentPage'=>1));
        $response = $this->httpGet($noticeUrl);
        if($response !=false){
            $response = json_decode($response,true);
            if($response && $response['code']=='200'){
                $notice = $response['data']['farticles'];
            }
        }


         // 读取banner
        $banner = array();
        for($i = 1;$i<=5;$i++){
            $item =  $this->redis->get('ARGS_bigImage'.$i);
		    if(!empty($item)){
				$banner[] = json_decode($item)->extObject;
		    }
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		$this->template->assign(array(
    			'notice' => $notice,
    			'banner' => $banner,
    			'data' => $data,

    			'arr' => $arr,
    			'mining_total' => $mining_total['total']
        ));
		$this->template->display(DR_PATH.'index.html');
    }
    /*
     * 挖矿和回收
     * */
    public function dig_circle(){
        //挖矿记录
        $mining = $this->db->order_by('id','desc')->limit(1)->get(SITE_ID.'_mining')->row_array();
        //每小时挖矿记录
        $time_mining = $this->db->query('select sum(num) as total from f_rebate_log where inputtime >="'.date('Y-m-d H:00:00',time()).'" and inputtime <="'.date('Y-m-d H:59:59',time()).'"')->row_array();
        $coin['total'] = 50000;//每小时挖矿总额
        $jd = $time_mining['total'] / $coin['total'] * 100;//进度

        //当前流通量
        $total['total'] = 100000000;
        //查询今日挖矿数量
        $mining_total['total'] = 0;
        if(FID){
            $start_time = date('Y-m-d',time());
            $mining_total = $this->db->query('select sum(num) as total from f_rebate_log where uid='.FID.' and status=2 and inputtime="'.$start_time.'"')->row_array();
        }
        //USDT 汇率
        $url = $this->host . $this->realPriceUrl;
        $url  .= '?' . http_build_query(array('symbol' => 55));
        $response = $this->httpGet($url);
        $usdt = json_decode($response,true)['data'];
        $usdt_huilv = $usdt[0]['p_new'];
        //BTC汇率
        $url = $this->host . $this->realPriceUrl;
        $url  .= '?' . http_build_query(array('symbol' => 54));
        $response = $this->httpGet($url);
        $hl1 = json_decode($response,true)['data'];
        $hl1 = $hl1[0]['p_new'];

        $url = $this->host . $this->realPriceUrl;
        $url  .= '?' . http_build_query(array('symbol' => 53));
        $response = $this->httpGet($url);
        $hl2 = json_decode($response,true)['data'];
        $hl2 = $hl2[0]['p_new'];

        $arr = array(
            'coin_total' => number_format($total['total'],'2','.',''),
            'coin_surplus' => $coin['total'] - $time_mining['total'],
            'mining_total' => number_format($time_mining['total'] ,'2','.',''),
            'jd' => $jd,
            'coin_usdt' =>  $total['total'] * $usdt_huilv,
            'mining_usdt' => number_format($total['total'] * $usdt_huilv,'2','.',''),
            'mining_btc' =>  number_format($mining['total'] / $hl1 / $hl2,'2','.',''),
            'time_slot' => date('H:00:00',time()).'~'.date('H:59:59',time()),
            'mining' => number_format($mining['total'],'2','.',''),
            'total_dig'=> $mining_total['total']
        );

        echo json_encode($arr);
        exit;
    }
    /**
     * 语言切换
     * @param $lang
     * @return bool
     * @internal param Request $request
     */
    public function change_lang($lang) {
        if($lang == 'en'){
            setcookie('oex_lan','en_US',time()+3600*24*30,'/',null,null,true);
            return 1;
        }else{
            setcookie('oex_lan','zh_TW',time()+3600*24*30,'/',null,null,true);
            return 1;
        }

    }
	
	/**
	 * 挖矿明细
	 */
	public function mining_detail(){
		$page = $_GET['page'] ?: 1;
		$list = $this->db->query('select * from f_coin_record where uid='.FID.' and coin_id=37 and value>0 order by id desc limit '.($page-1)*PAGESIZE.','.PAGESIZE)->result_array();
		
		$total = $this->db->query('select * from f_coin_record where uid='.FID.' and coin_id=37 and value>0 order by id desc')->num_rows();
		if($total > PAGESIZE){
		$pages = $this->show('/index.php?s=exc&c=indexController&m=mining_detail', $total,$page);
		}
		$this->template->assign(array(
							'list' => $list,
							'pages' => $pages
		));
		$this->template->display(DR_PATH.'mining.html');
	}


    /**
     * 获取钱包
     * @param Request $request
     * @return string
     */
//  public function getWallet() {
//      $url = $this->host . $this->userWalletUrl;
//      $response = $this->httpPost($url,array());
//      echo $this->parseApi($response);
//
//  }

    public function main(){
		$param = $_REQUEST['currentPage'];
        $url = $this->host . $this->indexJson;
        if(!empty($param)){
            $param = http_build_query($param);
            $url .= '?' . $param;
        }


        //获取公告
        $notice = array();
        $noticeUrl = $this->host . $this->noticeUrl;
        $noticeUrl .= '?' . http_build_query(array('id'=>2,'currentPage'=>1));

        $response = $this->httpGet($noticeUrl);
        if($response !=false){
            $response = json_decode($response,true);
            if($response && $response['code']=='200'){
                $notice = $response['data']['farticles'];
            }
        }


        //读取banner
        $banner = array();
        for($i = 1;$i<=5;$i++){
            $item =  Redis::get('ARGS_bigImage'.$i);
	    if(!empty($item)){
		$banner[] = json_decode($item)->extObject;
	    }
        }

        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
        $data['notice'] = $notice;
        $data['banner'] = $banner;
        return view('main',$data);
//		return view('weihu',$data);
    }


    /**
     * 首页
     * @return string
     */
    public function indexMarket(){
        $url = $this->host . $this->indexMarketUrl;
        echo $this->parseApi($this->httpPost($url,array()));

    }


    /**
     * 获取实时价格
     * @param Request $request
     * @return IndexController|string
     */
//  public function realPrice(){
//		$param['symbol'] = $_REQUEST['symbol'];
//      $url = $this->host . $this->realPriceUrl;
//      echo $this->parseApi($this->httpPost($url,$param));
//  }

    /**
     * @param Request $request
     * @return string
     */
//  public function kline() {
//  		$param['symbol'] = $_REQUEST['symbol'];
//		$param['step'] = $_REQUEST['step'];
//      $url = $this->host . $this->klineUrl;
//      echo $this->parseApi($this->httpPost($url,$param));
//  }
    
    public function kline1() {
    		$param['symbol'] = $_REQUEST['symbol'];
		$param['step'] = $_REQUEST['step'];
        $url = $this->host . $this->klineUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }
    
    /**
     * 帮助中心
     */
    public function help() {
        $param['id'] = $_REQUEST['id'];
        $url = $this->host . $this->aboutUrl;
        $url .= '?'.http_build_query($param);
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
//		dd($data);
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'help.html');
    }
	
	
	/**
	 * 分页
	 */
	public function show($url,$total,$page,$pageSize = PAGESIZE){
		$this->total=$total;
        $this->page=$page;
        

         $this->perpage=$pageSize;
        
        $cnt=ceil($this->total/$pageSize);
		if(empty($url)) {
			$parse=parse_url($_SERVER['REQUEST_URI']);
	        parse_str($parse['query'],$param);
	        //删除page单元
	        unset($param['page']);
	        //拼接url
	        $url = $parse['path'] . '?';
	        if(!empty($param)) {
	            $param = http_build_query($param);
	            $url = $url . $param . '&';
	        }
		}
        //生成页码链接的html代码
        $link=array();
        $link[0]='<li><a class="btn btn-white active">' . $this->page . '</a></li>';//当前页码的html代码
        for($left=$this->page-1,$right=$this->page+1;($left>=1||$right<=$cnt)&&count($link)<5;){
            if($left>=1){
                array_unshift($link,'<li><a class="btn btn-white" href="' . $url . '&page=' . $left . '">' . $left . '</a></li>');
                $left--;
            }
            if($right<=$cnt){
                array_push($link,'<li><a class="btn btn-white" href="' . $url . '&page=' . $right . '">' . $right . '</a></li>');
                $right++;
            }
        }
        
		//插入下一页
		if($this->page+1 <= $cnt) {
			$next = $this->page+1;
		}else{
			$next = $this->page;
		}
		if($this->page != $cnt && $cnt > 1){
			array_push($link,'<li><a class="btn btn-white" href="' . $url . '&page=' . $next . '">下一页</a></li>');
		}
		//插入上一页
		if($this->page-1 >1) {
			$last = $this->page-1;
		}else{
			$last = 1;
		}
		if($this->page > 1){
			array_unshift($link,'<li><a class="btn btn-white" href="' . $url . '&page=' . $last . '">上一页</a></li>');
		}
		
        return implode('',$link);

    }
    public function diff_time($time1,$time2,$days){
        $diff = sprintf("%.2f",($time1*1 - $time2*1) / 86400);
        if ($diff*1 > $days*1){
            return true;
        }else{
            return false;
        }
    }

    /***
     * 锁仓解锁功能
     * 定时查询
     */
    public function unlock_user() {
        $timestamp = time();
        $locklist = $this->db->query('select * from e_user_lock_num where unlock_level!=rule_level')->result_array();

        foreach ($locklist as $k=>$v){
            $ruleexplode1 = '';
            $ruleexplode2 = '';
            $ruleexplode3 = '';
            $frozen = '';
            $unlock_level = '';
            $unlock_num_now = '';


            $ruleexplode1 = explode(';',$locklist[$k]['rule']);
            $ruleexplode2 = explode(',',$ruleexplode1[$locklist[$k]['unlock_level']]);
            if($this->diff_time($timestamp,$locklist[$k]['time'],$ruleexplode2['1'])){
                if($locklist[$k]['unlock_level'] == 0){
                    $unlock_num_now = $locklist[$k]['total'] * $ruleexplode2['2']*0.01;
                }else{
                    $ruleexplode3 = explode(',',$ruleexplode1[$locklist[$k]['unlock_level']*1 - 1]);
                    $unlock_num_now = $locklist[$k]['total'] * ($ruleexplode2['2']*1 - $ruleexplode3['2']*1)*0.01;
                }
                $usercoininfo = $this->db->query('select frozen from user_coin_wallet where uid= ' . $locklist[$k]['uid'] .' and coin_id = ' . $locklist[$k]['coin_id'])->row_array();
                $frozen =  $usercoininfo['frozen']*1 - $unlock_num_now*1;
                $total = $usercoininfo['total']*1 + $unlock_num_now*1;
                $unlock_level = $locklist[$k]['unlock_level']*1 + 1;
                $this->db->trans_begin();

                $this->db->query('update user_coin_wallet set frozen= ' . $frozen . ' where uid= ' .  $locklist[$k]['uid'] .' and coin_id = '. $locklist[$k]['coin_id']);
                $this->db->query('update user_coin_wallet set total= ' . $total . ' where uid= ' .  $locklist[$k]['uid'] .' and coin_id = '. $locklist[$k]['coin_id']);
                $this->db->query('update e_user_lock_num set unlock_level= ' . $unlock_level . ' where id= ' . $locklist[$k]['id'] );

                if ($this->db->trans_status() === FALSE)
                {
                    $this->db->trans_rollback();
                }
                else
                {
                    $this->db->trans_commit();
                }

            }

        }

    }


    /***
     * 锁仓已经解锁后处理
     * 定时查询
     */
    public function unlock_history() {
        $timestamp = time();
        $unlocklist = $this->db->query('select * from e_user_lock_num where unlock_level=rule_level')->result_array();
        foreach ($unlocklist as $k=>$v){

            $this->db->trans_begin();


            $this->db->insert('user_lock_num_history', array(
                'uid' => $unlocklist[$k]['uid'],
                'coin_id' => $unlocklist[$k]['coin_id'],
                'total' => $unlocklist[$k]['total'],
                'time' => $unlocklist[$k]['time'],
                'rule_id' => $unlocklist[$k]['rule_id'],
                'rule' => $unlocklist[$k]['rule'],
                'rule_level' => $unlocklist[$k]['rule_level'],
                'finishtime' => $timestamp,
            ));
            $this->db->delete('user_lock_num', array('id' => $unlocklist[$k]['id']));
            if ($this->db->trans_status() === FALSE)
            {
                $this->db->trans_rollback();
            }
            else
            {
                $this->db->trans_commit();
            }
        }

    }

}
