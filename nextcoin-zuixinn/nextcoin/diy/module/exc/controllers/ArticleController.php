<?php
if (!defined('BASEPATH')) exit('No direct script access allowed');

require dirname(__FILE__).'/Controller.php';
class ArticleController extends Controller
{

    private $indexUrl = '/notice/index_json.html';

    private $detailUrl = '/notice/detail_json.html';
	public  function __construct() {
        parent::__construct();
		$this->template->assign(array(
    			'news' => $this->lang->line('news'),
        ));
    }

    /**
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function index(){
		$params['currentPage'] = $_REQUEST['currentPage'];
		$params['id'] = $_REQUEST['id'];
        foreach ($params as $key=>$value ) {
             if(empty($value)) {
                 $params['$key'];
             }
        }

        $url = $this->host . $this->indexUrl;
        if(!empty($params)){
            $url .= '?' . http_build_query($params);
        }


        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];
        if(empty($data['farticles'])){
            $data['farticles'] = array();
        }
		$this->template->assign(array(
				'data' => $data,
	    ));
		$this->template->display(DR_PATH.'notice/index.html');
    }
	


    /**
     * 新闻详情页
     * @param Request $request
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function detail() {
		$params['id'] = $_REQUEST['id'];
        $url = $this->host . $this->detailUrl;
        if(!empty($params)){
            $url .= '?' . http_build_query($params);
        }

        $response = $this->httpGet($url);
        $response = $this->parseResponse($response);
        $data = $response['data'];

		$this->template->assign(array(
				'data' => $data,
	    ));
		$this->template->display(DR_PATH.'notice/detail.html');
    }

}
