<?php
$msg = 'ok';
$to = $_REQUEST['to'];
$msgc = $_REQUEST['msgc'];
$type = $_REQUEST['type'];

//登陆后发送短信给用户
if($type){
	$pay_no = $_REQUEST['pay_no'];
	if($type == 'login'){
		$msgc = '【biex】**'.substr($to,7).'在'.date("Y-m-d H:i:s",time()).'登录了biex App。';
	} else if($type == 'wyfk'){
		$msgc = '【biex】对方已付款，参考号：'.$pay_no.' 请及时确认是否到账再进行处理。';
	} else if($type == 'wysk'){
		$msgc = '【biex】对方已收款，参考号：'.$pay_no.' 请及时确认币是否到账。';
	} else if($type == 'cs'){
		$msgc = '【biex】有用户已卖出币给您，参考号：'.$pay_no.' 请及时进行付款和确认操作';
	}
	$post_data = array(
				'userid' => 2998,//企业id,网站查看
				'account' => 'daerwen123',
				'password' => 'daerwen456',
				'content' => $msgc,//短信内容
				'mobile' => $to,//电话
				'action' => 'send',
				'sendtime' => ''
	);
} else {
	$post_data = array(
				'userid' => 2998,//企业id,网站查看
				'account' => 'daerwen123',
				'password' => 'daerwen456',
				'content' => "【biex】验证码：".$msgc.'，如非本人操作，请忽略。',//短信内容
				'mobile' => $to,//电话
				'action' => 'send',
				'sendtime' => ''
	);
}

$o='';
foreach ($post_data as $k=>$v){
   $o.="$k=".urlencode($v).'&';
}
$post_data=substr($o,0,-1);

$ch = curl_init();
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_HEADER, 0);
curl_setopt($ch, CURLOPT_URL,'http://114.215.78.213:8888/sms.aspx');
curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

$result = curl_exec($ch);
	
echo json_encode(simplexml_load_string($result));
exit();
