<?php
if (!defined('BASEPATH')) exit('No direct script access allowed');

require dirname(__FILE__).'/Controller.php';

class HelpController extends Controller
{
    private $aboutUrl = '/about/about_json.html';

    private $questionUrl = '/online_help/index_json.html';

    private $questionListUrl = '/online_help/help_list_json.html';

    private $saveQuestionUrl = '/online_help/help_submit.html';

    private $delQuestionUrl = '/online_help/help_delete.html';
	
    public  function __construct() {
        parent::__construct();
		$this->load->model('content_model');
		$this->template->assign(array(
    			'about' => $this->lang->line('about'),
    			'question' => $this->lang->line('question'),
        ));
    }
    /**
     * 帮助首页
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function about() {
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
		$this->template->display(DR_PATH.'about/about.html');
    }


    /**
     * 在线问答页面
     */
    public function question() {
        $url = $this->host .$this->questionUrl;
        $response = $this->parseResponse($this->httpGet($url));
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'about/online.html');
    }


    /**
     * 在线问答页面
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function questionList() {
		$param['currentPage'] = $_REQUEST['currentPage'];
        $url = $this->host .$this->questionListUrl;
        if(!empty($param)){
            $url .= http_build_query($param);
        }

        $response = $this->parseResponse($this->httpGet($url));
        $data = $response['data'];
		$this->template->assign(array(
    			'data' => $data
        ));
		$this->template->display(DR_PATH.'about/onlineList.html');
    }


    /**
     * 提交反馈问题
     * @param Request $request
     * @return string
     */
    public function saveQuestion(){
    		$param['questiontype'] = $_REQUEST['questiontype'];
		$param['questiondesc'] = $_REQUEST['questiondesc'];
        $url = $this->host . $this->saveQuestionUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }


    /**
     * 删除问题
     * @param Request $request
     * @return string
     */
    public function delQuestion(){
    		$param['fid'] = $_REQUEST['fid'];
        $url = $this->host . $this->delQuestionUrl;
        echo $this->parseApi($this->httpPost($url,$param));
    }
}
