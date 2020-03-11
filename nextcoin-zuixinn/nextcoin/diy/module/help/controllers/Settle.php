<?php
//统计昨日挖矿数据
$url = 'https://de.biex.com/index.php?s=help&c=member_controller&m=statistics_mining';
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_HEADER, 0);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);//绕过ssl验证
curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
$data = curl_exec($ch);
curl_close($ch);
?>