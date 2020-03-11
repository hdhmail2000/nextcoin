<?php
if (!defined('BASEPATH')) exit('No direct script access allowed');

require dirname(__FILE__).'/Controller.php';
class FinanceController extends Controller
{

	private $userRMBRecharge_jsonUrl = '/user/userRMBRecharge_json.html';
	
	private $userRechargeUSDT_jsonUrl = '/user/userRechargeUSDT_json.html';
	
    private $depositUrl = '/deposit/coin_deposit_json.html';

    private $withdrawUrl = '/withdraw/coin_withdraw_json.html';

    private $cnywithdrawUrl = '/withdraw/cny_withdraw_json.html';

    private $assetUrl = '/financial/index_json.html';

    private $recordUrl = '/financial/record_json.html';

    private $accountcoinUrl = '/financial/accountcoin_json.html';

    private $financesUrl = '/financial/finances_json.html';

    private $commissionUrl = '/financial/commission_json.html';

    private $saveWithdrawUrl = '/user/save_withdraw_address.html';

    private $withdraw = '/withdraw/coin_manual.html';

    private $delWithdrwaAddr = '/user/del_withdraw_address.html';

    private $clacCountUrl = '/get_finances.html';

    private $submitUrl = '/submit_finances.html';

    private $saveBankInfoUrl = '/user/save_bankinfo.html';

    private $cnyWithdrawsUrl = '/withdraw/cny_manual.html';

    private $smsBankUrl = '/user/send_bank_sms.html';
    
    private $recordManualUrl = '/financial/manual_record_json.html';

	public  function __construct() {
        parent::__construct();
		$this->load->model('content_model');
		$this->template->assign(array(
    			'finance' => $this->lang->line('finance'),
        ));
    }

    /**
     * 充值DOTA申请
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function userRMBRecharge_json(){
		
        $url = $this->host . $this->userRMBRecharge_jsonUrl;
       	$url .= '?coinid=24';
		
		$amount = $request->input('amount');
		if(!empty($amount)){
            $url .= '&amount=' .$amount;
        }
		
		$account = $request->input('account');
		if(!empty($account)){
            $url .= '&account=' .$account;
        }

		$receiptAddress = $request->input('receiptAddress');
		if(!empty($receiptAddress)){
            $url .= '&receiptAddress=' .$receiptAddress;
        }
		
		$rechargeType = $request->input('rechargeType');
		if(!empty($rechargeType)){
            $url .= '&rechargeType=' .$rechargeType;
        }
		
		$rechargeImgURL = $request->input('rechargeImgURL');
		if(!empty($rechargeImgURL)){
            $url .= '&rechargeImgURL=' .$rechargeImgURL;
        }
		
		if (empty($amount) || empty($account) || empty($receiptAddress) || 
				empty($rechargeType) || empty($rechargeImgURL)) {
			echo json_encode(array('code'=>'201', 'msg'=>'数据不完整，请检查！'), JSON_UNESCAPED_UNICODE);
			exit();
		}
		$response = $this->httpGet($url);
		$response = $this->parseResponse($response);
		echo json_encode($response, JSON_UNESCAPED_UNICODE);
		exit();
    }
	 /**
     * 充值DOTA申请
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function userRechargeUSDT_json(){
		$coinId = $_REQUEST['coinId'];
        $url = $this->host . $this->userRechargeUSDT_jsonUrl;
       	$url .= '?coinId=' .$coinId;
		
		$amount = $_REQUEST['amount'];
		if(!empty($amount)){
            $url .= '&amount=' .$amount;
        }
		
		$account = $_REQUEST['account'];
		if(!empty($account)){
            $url .= '&account=' .$account;
        }

		$receiptAddress = $_REQUEST['receiptAddress'];
		if(!empty($receiptAddress)){
            $url .= '&receiptAddress=' .$receiptAddress;
        }
		
		$rechargeType = $_REQUEST['rechargeType'];
		if(!empty($rechargeType)){
            $url .= '&rechargeType=' .$rechargeType;
        }
		
		$rechargeImgURL = $_REQUEST['rechargeImgURL'];
		if(!empty($rechargeImgURL)){
            $url .= '&rechargeImgURL=' .$rechargeImgURL;
        }
		
		if (empty($amount) || empty($account) || empty($receiptAddress) || 
				empty($rechargeType) || empty($rechargeImgURL)) {
			echo json_encode(array('code'=>'201', 'msg'=>'数据不完整，请检查！'), JSON_UNESCAPED_UNICODE);
			exit();
		}
		$response = $this->httpGet($url);
		$response = $this->parseResponse($response);
		echo json_encode($response, JSON_UNESCAPED_UNICODE);
		exit();
    }

    /**
     * 充值
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function coinDeposit(){
    	$mining = $this->db->get(SITE_ID.'_mining_status')->row_array();
		$member = $this->db->where('username',USERNAME)->get('member')->row_array();
		$member_data = $this->db->where('uid',$member['uid'])->get('member_data')->row_array();
		if($mining['status'] == 2 && time() < $mining['time'] + 7 * 86400 ){
			//创世挖矿期
			if(!$member_data['tixian'] == 1){
				echo -1;
				exit;
			}
		}
		
        $symbol = $_REQUEST['symbol'];


		$url = $this->host . $this->depositUrl;
		if(!empty($symbol)){
		    $url .= '?symbol=' .$symbol;
		}
		$response = $this->httpGet($url);
		//dd($response);
        $response = $this->parseResponse($response);
        $data = $response['data'];
        $fuid = $data['rechargeAddress']['fuid'];
        //如果币种是SC
		if($symbol == '50'){
		    $addres = $this->db->query("select * from set_walletaddr where `uid`='".$fuid."' and `coinid` ='".$data['rechargeAddress']['fcoinid']."'")->row_array();
		    if($addres['addr'] !=''){
                //如果数据库里有地址
                $data['rechargeAddress']['fadderess'] = $addres['addr'];
            }else{
		        //数据库里没有地址，那就重新获取
                $url="http://api1.foxssl.com/okniex/scnewaddr.php";
                $response = $this->httpGet($url);
                $response = $this->parseResponse($response);

                $insert_id = $this->db->query('INSERT INTO `set_walletaddr` (uid,coinid,addr) VALUES ("'.$fuid.'","'.$data['rechargeAddress']['fcoinid'].'","'.$response['addr'].'")');
                if($insert_id > 0){
                    $data['rechargeAddress']['fadderess'] = $response['addr'];
                }
            }


        }
//        print_r("<pre>");
//        print_r($data);
//        print_r($insert_id);
        $wxzh =  json_decode($this->redis->get('ARGS_wechatRechargeAccount'));
        $data['wxzh'] = $wxzh->extObject;
        $zfbzh =  json_decode($this->redis->get('ARGS_alipayRechargeAccount'));
        $data['zfbzh'] = $zfbzh->extObject;
        $yhzh =  json_decode($this->redis->get('ARGS_bankRechargeAccount'));
        $data['yhzh'] = $yhzh->extObject;
        $data['response'] = $response;


        //dd($data);
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/coinDeposit.html');

    }


    /**
     * 充值
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function cnyDeposit(Request $request){
        $symbol = $_REQUEST['symbol'];
        $url = $this->host . $this->depositUrl;
        if(!empty($symbol)){
            $url .= '?symbol=' .$symbol;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		
		$wxzh =  json_decode(Redis::get('ARGS_wechatRechargeAccount'));
		$data['wxzh'] = $wxzh->extObject;
		$zfbzh =  json_decode(Redis::get('ARGS_alipayRechargeAccount'));
		$data['zfbzh'] = $zfbzh->extObject;
		$yhzh =  json_decode(Redis::get('ARGS_bankRechargeAccount'));
		$data['yhzh'] = $yhzh->extObject;
		
		$usdt_price =  json_decode(Redis::get('ARGS_rechargeUSDTPrice'));
		$data['usdt_price'] = $usdt_price->extObject;
		$data['symbol'] = $symbol;
//        $data['coinList'] = array_merge($data['cnyList'],$data['coinList']);
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/cnyDeposit.html');
    }


    /**
     * 提现
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function coinWithdraw(){
    	$mining = $this->db->get(SITE_ID.'_mining_status')->row_array();
		$member = $this->db->where('username',USERNAME)->get('member')->row_array();
		$member_data = $this->db->where('uid',$member['uid'])->get('member_data')->row_array();
		if($mining['status'] == 2 && time() < $mining['time'] + 7 * 86400 ){
			//创世挖矿期
			if(!$member_data['tixian'] == 1){
				echo -1;
				exit;
			}
		}
		
        $symbol = $_REQUEST['symbol'];
        $url = $this->host . $this->withdrawUrl;
        if(!empty($symbol)){
            $url .= '?symbol=' .$symbol;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/coinWithdraw.html');
    }


    /**
     * 提现
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function cnyWithdraw(){
        $symbol = $_REQUEST['symbol'];
        $url = $this->host . $this->cnywithdrawUrl;
        if(!empty($symbol)){
            $url .= '?symbol=' .$symbol;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		$data['symbol'] = $symbol;
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/cnyWithdraw.html');
    }
	/**
	 * 转账
	 */
	public function transferAccounts() {
		$symbol = $_REQUEST['symbol'];
		$url = $this->host . $this->withdrawUrl;
        if(!empty($symbol)){
            $url .= '?symbol=' .$symbol;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/transferAccounts.html');
	}
	/**
	 * 转账记录
	 */
	public function transferLog() {
		$username = USERNAME;
		$coin_id = $_GET['coin_id']?$_GET['coin_id']:0;
		$fuser = $this->db->query("SELECT fid,floginname,ftradepassword FROM `f_user` WHERE `floginname` = '".$username."'")->row_array();
		$data = array();
		if($fuser) {
			$sql = "SELECT a.*,b.short_name FROM `e_1_coin_record` a 
					LEFT JOIN `system_coin_type` b ON a.coin_id=b.id WHERE a.fid=".$fuser['fid']." AND note='转账'";
			if($coin_id) {
				$sql .= ' AND coin_id='.$coin_id;
			}
			$sql .= ' ORDER BY a.inputtime DESC';
			$data = $this->db->query($sql)->result_array();
		}
		$coin = $this->db->query('SELECT id,short_name,status FROM `system_coin_type` WHERE `status` = 1')->result_array();
		$this->template->assign(array(
    			'data' => $data,
    			'coin' => $coin,
    			'coin_id' => $coin_id
        ));
		$this->template->display(DR_PATH.'finance/transferLog.html');
	}
	/**
	 * 转账
	 */
	public function transferPost() {
		$withdrawAddr = $_POST['withdrawAddr'];//账户
		$withdrawAmount = $_POST['withdrawAmount'];//数量
		$tradePwd = $_POST['tradePwd'];//交易密码
		$symbol = $_POST['symbol'];
		$fid = $_POST['fid'];
		if(empty($withdrawAddr)) {
			echo json_encode(array('code'=>0,'msg'=>'到账账户不能为空'));
			exit;	
		}
		if($fid) {
			$fuser = $this->db->query('SELECT fid,floginname,ftradepassword FROM `f_user` WHERE `fid` = '.$fid)->row_array();
			if(!$fuser) {
				echo json_encode(array('code'=>0,'msg'=>'账户异常'));
				exit;
			}
			//查找币种是否开启
			$coin = $this->db->query('SELECT id,name,status FROM `system_coin_type` WHERE `id` = '.$symbol.' AND `status` = 1')->row_array();
			if(!$coin) {
				echo json_encode(array('code'=>0,'msg'=>'币种未开启，请联系客服'));
				exit;	
			}
			if(empty($fuser['ftradepassword'])) {
				echo json_encode(array('code'=>-1,'msg'=>'未设置交易密码'));
				exit;	
			}
			$tradePwd = base64_encode(md5($tradePwd,TRUE));
			if($tradePwd != $fuser['ftradepassword']) {
				echo json_encode(array('code'=>0,'msg'=>'交易密码不正确'));
				exit;	
			}
			if($symbol == -1) {//平台币
				//查看转账人平台币是否足够
				$this->db->select('uid,username,score');
				$member = $this->db->where('username',$fuser['floginname'])->get('member')->row_array();
				if($member['score'] < $withdrawAmount) {
					echo json_encode(array('code'=>0,'msg'=>'账户余额不足'));
					exit;	
				}
				$result = $this->member_model->update_score(1, $member['uid'], -$withdrawAmount, 'zhuanzhang', '转账');
				//接受人
				$this->db->select('uid,username');
				$accept = $this->db->where('username',$withdrawAddr)->get('member')->row_array();
				if($accept) {
					$result = $this->member_model->update_score(1, $accept['uid'], $withdrawAmount, 'zhuanzhang', '转账');
				}
			}else{
				//查看转账人币是否足够
				$fuser = $this->db->query('SELECT uid,total FROM `user_coin_wallet` WHERE `uid` = '.$fid.' AND `coin_id` = '.$symbol)->row_array();
				if($fuser['total'] < $withdrawAmount) {
					echo json_encode(array('code'=>0,'msg'=>'账户余额不足'));
					exit;	
				}
				//接受人
				$accept = $this->db->query("SELECT fid,floginname,ftradepassword FROM `f_user` WHERE `floginname` = '".$withdrawAddr."'")->row_array();
				if(!$accept) {
					echo json_encode(array('code'=>0,'msg'=>'转账账户不存在，请重新输入'));
					exit;	
				}
				$this->db->trans_start();//开启事务
				$result = $this->db->query('UPDATE `user_coin_wallet` SET `total` = `total`-'.$withdrawAmount.' WHERE `uid` = '.$fid.' AND `coin_id` = '.$symbol);
				$this->db->insert('1_coin_record',array(
					'fid' => $fid,
					'coin_id' => $symbol,
					'value' => -$withdrawAmount,
					'note' => '转账',
					'inputtime' => time()
				));
				$result = $this->db->query('UPDATE `user_coin_wallet` SET `total` = `total`+'.$withdrawAmount.' WHERE `uid` = '.$accept['fid'].' AND `coin_id` = '.$symbol);
				$this->db->insert('1_coin_record',array(
					'fid' => $accept['fid'],
					'coin_id' => $symbol,
					'value' => $withdrawAmount,
					'note' => '转账',
					'inputtime' => time()
				));
				if ($this->db->trans_status() === FALSE) {
				    $this->db->trans_rollback();
				    echo json_encode(array('code'=>0,'msg'=>'操作失败，请重试'));
					exit;
				}else {
				    $this->db->trans_commit();
				}		
			}
		}else{
			echo json_encode(array('code'=>-2,'msg'=>'登录失效'));
			exit;	
		}
		echo json_encode(array('code'=>200,'msg'=>'操作成功'));
		exit;	
	}
	/**
	 * 信息挖矿
	 */
	public function authentication() {
		$data = $this->getstatusInformation(USERNAME);
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/authentication.html');
	}
	/**
	 * 信息挖矿提交数据
	 */
	public function authenticationPost() {
		$data = $_POST['data'];
		$data['uid'] = FID;
		$data['author'] = USERNAME;
		$data['inputtime'] = time();
		$this->db->insert('1_form_xinxiwakuang_data_0',array(
			'uid' => FID
		));
		$id = $this->db->insert_id();
		$data['id'] = $id;
		$result = $this->db->insert('1_form_xinxiwakuang',$data);
		if($result) {
			echo json_encode(array('code'=>200,'msg'=>'操作成功'));
			exit;
		}else{
			echo json_encode(array('code'=>0,'msg'=>'操作失败，请重试'));
			exit;
		}
	}
	/**
	 * 信息挖矿状态
	 */
	public function getstatusInformation($username) {
		$form = $this->db->where('author',$username)->get('1_form_xinxiwakuang')->row_array();
		return $form;
	}
    /**
     * 个人资产
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     * @internal param Request $request
     */
    public function index(){
    	
        $url = $this->host . $this->assetUrl;
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data']['userWalletList'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/index1.html');
    }


    /**
     * 账单明细
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
//  public function record(){
//      $url = $this->host . $this->recordUrl;
//		$param['datetype'] = $_REQUEST['datetype'];
//		$param['begindate'] = $_REQUEST['begindate'];
//		$param['enddate'] = $_REQUEST['enddate'];
//		$param['symbol'] = $_REQUEST['symbol'];
//		$param['type'] = $_REQUEST['type'];
//		$param['funiquenumber'] = $_REQUEST['funiquenumber'];
//      if(!empty($param)){
//          $param = http_build_query($param);
//          $url .= '?' . $param;
//      }
//      $response = $this->httpGet($url);
//      $response = $this->parseResponse($response);
//      $data = $response['data'];
//		foreach($data['filters'] as $key=>$val) {
//			$arr = explode('/', $val['key']);
//			$str = explode('?', $arr[2]);
//			$data['filters'][$key]['canshu'] = $str['1'];
//		}
//		$this->template->assign(array(
//  			'data' => $data
//      ));
//		$this->template->display(DR_PATH.'finance/record.html');
//  }
	 public function record(){
		$url = $this->host . '/financial/operation/record.html';
		$param['currentPage'] = $_REQUEST['currentPage'];
        if(!empty($param)){
            $param = http_build_query($param);
            $url .= '?' . $param;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data['data'],
                'pagin'=>$data['pagin']
        ));
		$this->template->display(DR_PATH.'finance/record.html');
    }


    /**
     * 资金账户
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function accountcoin(Request $request) {
        $url = $this->host . $this->accountcoinUrl;

        $param = $request->only(['symbol']);
        if(!empty($param)){
            $param = http_build_query($param);
            $url .= '?' . $param;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
        return view('finance.accountcoin',$data);
    }


    /**
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function finances(Request $request) {
        $param = $request->only(['currentPage']);

        $url = $this->host . $this->financesUrl;

        if(!empty($param)){
            $param = http_build_query($param);
            $url .= '?' . $param;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];

        return view('finance.finances',$data);
    }


    /**
     * 推荐奖励页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function commission() {
        $url = $this->host . $this->commissionUrl;
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
        $host = $_SERVER['HTTP_HOST'];
		
        $data['introId'] = preg_replace('/\D/s', '', $data['introurl']);//substr($data['introurl'],strpos($data['introurl'],'intro=200')+6);
       
        $data['introurl'] = SITE_URL.'index.php?s=exc&c=userController&m=intro&intro='.$data['introId'];
		
		//用户每日返还的CC
		$list = $this->db->query('select * from f_coin_record where uid='.FID.' and coin_id=37 order by inputtime desc limit 0,10')->result_array();
		
		
		$this->template->assign(array(
    			'data' => $data,
    			'list' => $list
        ));
		$this->template->display(DR_PATH.'finance/commission.html');
    }
	//生成二维码图片
	public function mkqrcode() {
		require_once dirname(dirname(__FILE__)).'/libraries/phpqrcode.php';
	    $url = $this->host . $this->commissionUrl;
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
        $host = $_SERVER['HTTP_HOST'];
        $data['introurl'] = SITE_URL.$data['introurl'];  
	    $value = $data['introurl'];                  //二维码内容  
	    $errorCorrectionLevel = 'L';    //容错级别   
	    $matrixPointSize = 7;           //生成图片大小    
	    //生成二维码图片  
	    $QR = QRcode::png($value,false,$errorCorrectionLevel, $matrixPointSize, 2);  
		ob_start();
        ob_clean();
        header("Content-type: image/png");
        ImagePng($QR);
		exit;
	}
	public function mycurl($url) {
		$header[] =  'X-Forwarded-For: ' . $this->getClietnIp();
        $header[] =  'cookie: ' . $this->getCookie();
		
		$ch = curl_init();
		curl_setopt ($ch, CURLOPT_URL, $url);
		curl_setopt ($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
		curl_setopt ($ch, CURLOPT_CONNECTTIMEOUT, 5);
		$response = curl_exec($ch);
		curl_close($ch);
		return $response;
	}
	public function watermark() {
		$dst_path = SITE_URL.'statics/exc/dota/img/demo_20180813.png';
		$src_path = SITE_URL.'index.php?s=exc&c=financeController&m=mkqrcode';
		//创建图片的实例
		$dst = imagecreatefromstring(file_get_contents($dst_path));
        $response = $this->mycurl($src_path);
		$src = imagecreatefromstring($response);
		//获取水印图片的宽高
		list($src_w, $src_h) = getimagesize($src_path);
		
		//将水印图片复制到目标图片上，最后个参数50是设置透明度，这里实现半透明效果
		imagecopymerge($dst, $src, 245, 1000, 0, 0, 260, 260, 100);
		
		//如果水印图片本身带透明色，则使用imagecopy方法
		// imagecopy($dst, $src, 10, 10, 0, 0, $src_w, $src_h);
		
		//输出图片
		list($dst_w, $dst_h, $dst_type) = getimagesize($dst_path);
		switch ($dst_type) {
		    case 1://GIF
		        header('Content-Type: image/gif');
		        imagegif($dst);
		        break;
		    case 2://JPG
		        header('Content-Type: image/jpeg');
		        imagejpeg($dst);
		        break;
		    case 3://PNG
		        header('Content-Type: image/png');
		        imagepng($dst);
		        break;
		    default:
		        break;
		}
		imagedestroy($dst);
		imagedestroy($src);
	}

    /**
     * 添加提现地址
     * @param Request $request
     * @return string
     */
    public function saveWithdrawAddress(){
    		$param['withdrawAddr'] = $_REQUEST['withdrawAddr'];
		$param['totpCode'] = $_REQUEST['totpCode'];
		$param['phoneCode'] = $_REQUEST['phoneCode'];
		$param['symbol'] = $_REQUEST['symbol'];	
		$param['password'] = $_REQUEST['password'];
		$param['remark'] = $_REQUEST['remark'];
        if(empty($param['remark'])){
            $param['remark'] = 1;
        }
        $url = $this->host . $this->saveWithdrawUrl;
//        return $this->httpPost($url,$param);
        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * 提现操作
     * @param Request $request
     * @return string
     */
    public function withDraw () {
    		$param['withdrawAddr'] = $_REQUEST['withdrawAddr'];
		$param['withdrawAmount'] = $_REQUEST['withdrawAmount'];
		$param['tradePwd'] = $_REQUEST['tradePwd'];
		$param['totpCode'] = $_REQUEST['totpCode'];
		$param['phoneCode'] = $_REQUEST['phoneCode'];
		$param['symbol'] = $_REQUEST['symbol'];
		$param['btcfeesIndex'] = $_REQUEST['btcfeesIndex'];
		$param['memo'] = $_REQUEST['memo'];
        $url = $this->host . $this->withdraw;
        echo $this->parseApi($this->httpPost($url,$param));

    }


    /**
     * 删除提现地址接口
     * @param Request $request
     * @return string
     */
    public function delWithdrawAddr(){
    		$param['fid'] = $_REQUEST['fid'];
		$param['symbol'] = $_REQUEST['symbol'];
        $url = $this->host . $this->delWithdrwaAddr;
        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * 切换存币类型接口
     * @param Request $request
     * @return string
     */
    public function clacCount() {
    		$param['symbol'] = $_REQUEST['symbol'];
        $url = $this->host . $this->clacCountUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * @param Request $request
     * @return string
     */
    public function submit(){
    		$param['symbol'] = $_REQUEST['symbol'];
		$param['type'] = $_REQUEST['type'];
		$param['count'] = $_REQUEST['count'];
		$param['tradepwd'] = $_REQUEST['tradepwd'];
		$param['googlecode'] = $_REQUEST['googlecode'];
        $url = $this->host . $this->submitUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * 保存银行卡信息
     * @param Request $request
     * @return string
     */
    public function saveBankInfo(){
    		$param['account'] = $_REQUEST['account'];
		$param['openBankType'] = $_REQUEST['openBankType'];
		$param['totpCode'] = $_REQUEST['totpCode'];
		$param['phoneCode'] = $_REQUEST['phoneCode'];
		$param['address'] = $_REQUEST['address'];
		$param['prov'] = $_REQUEST['prov'];
		$param['city'] = $_REQUEST['city'];
		$param['dist'] = $_REQUEST['dist'];
		$param['payeeAddr'] = $_REQUEST['payeeAddr'];
		$param['bankId'] = $_REQUEST['bankId'];	
        $url = $this->host . $this->saveBankInfoUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * cny提现
     * @param Request $request
     * @return string
     */
    public function cnyWithdraws(){
    		$param['tradePwd'] = $_REQUEST['tradePwd'];
		$param['withdrawBalance'] = $_REQUEST['withdrawBalance'];
		$param['phoneCode'] = $_REQUEST['phoneCode'];
		$param['totpCode'] = $_REQUEST['totpCode'];
		$param['withdrawBlank'] = $_REQUEST['withdrawBlank'];
		$param['symbol'] = $_REQUEST['symbol'];
        $url = $this->host . $this->cnyWithdrawsUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }

    /**
     * 发送银行卡短信
     * @return string
     */
    public function smsBank(){
        $url = $this->host . $this->smsBankUrl;

        echo $this->parseApi($this->httpPost($url));
    }
    
    /**
     * app -- 提现
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function tixian(){
    		$symbol = $_REQUEST['symbol'];
        $url = $this->host . $this->cnywithdrawUrl;
        if(!empty($symbol)){
            $url .= '?symbol=' .$symbol;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		$data['symbol'] = $symbol;
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/tixian.html');
    }
    
    /**
     * 提现 - 地址添加
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function addAddress(){
        $symbol = $_REQUEST['symbol'];
        $url = $this->host . $this->withdrawUrl;
        if(!empty($symbol)){
            $url .= '?symbol=' .$symbol;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
    
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/add_address.html');
    }
    
    /**
     * 提现 - USDT添加地址
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function addCode(){
        $symbol = $_REQUEST['symbol'];
        $url = $this->host . $this->cnywithdrawUrl;
        if(!empty($symbol)){
            $url .= '?symbol=' .$symbol;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		$data['symbol'] = $symbol;
        $this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/add_code.html');

    }
    
    /**
     * C2C充值
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function c2cDeposit(){
        $symbol = $_REQUEST['symbol'];
        $url = $this->host . $this->depositUrl;
        if(!empty($symbol)){
            $url .= '?symbol=' .$symbol;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		
		$wxzh =  json_decode($this->redis->get('ARGS_wechatRechargeAccount'));
		$data['wxzh'] = $wxzh->extObject;
		$zfbzh =  json_decode($this->redis->get('ARGS_alipayRechargeAccount'));
		$data['zfbzh'] = $zfbzh->extObject;
		$yhzh =  json_decode($this->redis->get('ARGS_bankRechargeAccount'));
		$data['yhzh'] = $yhzh->extObject;
		
		$usdt_price =  json_decode($this->redis->get('ARGS_rechargeUSDTPrice'));
		$data['usdt_price'] = $usdt_price->extObject;
		$data['symbol'] = $symbol;
//        $data['coinList'] = array_merge($data['cnyList'],$data['coinList']);
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/c2c.html');
    }
    
    /**
     * app提现 - USDT添加地址
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function tianjia(){
        $symbol = $_REQUEST['symbol'];
        $url = $this->host . $this->cnywithdrawUrl;
        if(!empty($symbol)){
            $url .= '?symbol=' .$symbol;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
		$data['symbol'] = $symbol;
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/tianjia.html');
    }
    
    /**
     * 手动充值明细
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function sdRecord(){
        $url = $this->host . $this->recordManualUrl;
		$param['datetype'] = $_REQUEST['datetype'];
		$param['begindate'] = $_REQUEST['begindate'];
		$param['enddate'] = $_REQUEST['enddate'];
		$param['symbol'] = $_REQUEST['symbol'];
		$param['type'] = $_REQUEST['type'];
        if(!empty($param)){
            $param = http_build_query($param);
            $url .= '?' . $param;
        }
        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);

        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'finance/sd_record.html');
    }


    /**
     * 充值CNYT
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function cnytDeposit(){

        $symbol = $_REQUEST['symbol'];


        $this->template->display(DR_PATH.'finance/cnytDeposit.html');

    }




    /***
     * 用户查询锁仓功能页面
     */

    public function unLockUser(){

//        $url = $this->host . $this->commissionUrl;
//        $response = $this->httpGet($url);
//        $response = $this->parseResponse($response);
//        $data = $response['data'];
//        $host = $_SERVER['HTTP_HOST'];
//
//        $data['introId'] = preg_replace('/\D/s', '', $data['introurl']);//substr($data['introurl'],strpos($data['introurl'],'intro=200')+6);
//
//        $data['introurl'] = SITE_URL.'index.php?s=exc&c=userController&m=intro&intro='.$data['introId'];

        //用户每日返还的CC
        $locklist = $this->db->query('select l.*,c.short_name,r.title from e_user_lock_num  l left join system_coin_type  c on l.coin_id = c.id left join e_user_lock_rule  r on l.rule_id = r.id  where l.uid ='.FID.' order by l.id desc')->result_array();
        $unlocklist = $this->db->query('select l.*,c.short_name,r.title from e_user_lock_num_history  l left join system_coin_type  c on l.coin_id = c.id left join e_user_lock_rule  r on l.rule_id = r.id where l.uid ='.FID.' order by l.id desc')->result_array();
        $this->template->assign(array(
            'locklist' => $locklist,
            'unlocklist' => $unlocklist
        ));
        $this->template->display(DR_PATH.'finance/unLockUser.html');
    }

    /***
     * 用户查询锁仓功能页面
     */

    public function unLockUserApp(){

//        $url = $this->host . $this->commissionUrl;
//        $response = $this->httpGet($url);
//        $response = $this->parseResponse($response);
//        $data = $response['data'];
//        $host = $_SERVER['HTTP_HOST'];
//
//        $data['introId'] = preg_replace('/\D/s', '', $data['introurl']);//substr($data['introurl'],strpos($data['introurl'],'intro=200')+6);
//
//        $data['introurl'] = SITE_URL.'index.php?s=exc&c=userController&m=intro&intro='.$data['introId'];

        //用户每日返还的CC
        $locklist = $this->db->query('select l.*,c.short_name,r.title from e_user_lock_num  l left join system_coin_type  c on l.coin_id = c.id left join e_user_lock_rule  r on l.rule_id = r.id  where l.uid ='.FID.' order by l.id desc')->result_array();
        $unlocklist = $this->db->query('select l.*,c.short_name,r.title from e_user_lock_num_history  l left join system_coin_type  c on l.coin_id = c.id left join e_user_lock_rule  r on l.rule_id = r.id where l.uid ='.FID.' order by l.id desc')->result_array();
        echo json_encode(array('locklist '=>$locklist,'unlocklist'=>$unlocklist));
    }


}
