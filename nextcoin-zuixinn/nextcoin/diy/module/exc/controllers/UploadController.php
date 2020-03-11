<?php
if (!defined('BASEPATH')) exit('No direct script access allowed');
define('DR_PATH', 'dota/');
require dirname(__FILE__).'/Controller.php';
include_once WEBPATH . 'api/oss-sdk/autoload.php';
class UploadController extends Controller
{
	private $ossClient = null;
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $accessKeyId = "LTAI3MAst7Lhpeo6";
        $accessKeySecret = "G2OaPUaPbvxwcbgneDcCTBtmQnIDoK";
        $endpoint = "http://oss-ap-southeast-1.aliyuncs.com";
		
		try {
		    $this->ossClient = new OssClient($accessKeyId, $accessKeySecret, $endpoint);
		} catch (OssException $e) {
		    print $e->getMessage();
		}
    }
    
	/**
	 * 文件上传
     */
    public function upload() {
    		//$type = input('type');
		$fileParts = pathinfo($_FILES['trackdata']['name']);
		$filename = date('Ymd').'/'.date('Y-m-d-H-i-s') . '-' . uniqid() . '.' . $fileParts['extension'];
		
		$object = "daerwencoin/upload/args/" . $filename;
	    try{
	        $data = $this->ossClient->uploadFile('biex-web', $object, $_FILES['trackdata']['tmp_name']);
	    } catch(OssException $e) {

	        printf(__FUNCTION__ . ": FAILED\n");
	        printf($e->getMessage() . "\n");
	        return;
	    }
		echo $data['info']['url'];
		exit();
		
    }
}
