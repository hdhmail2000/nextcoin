<div class="container-fluid footer" style="padding-bottom: 0;padding-top: 0;background: #12131f;">
    <div class="container">
        <div class="row">
            <style type="text/css">
            	   .footer-nav-navbar{
            	       padding:12px 0 !important;
            	   }
            	   .footer-nav-navbar a{
            	       padding: 0 18px;
            	   }
            </style>
            <div class="col-xs-12 footer-nav">
                <ul class="clearfix">
                		<?php $i = 0;  $return_t = array();$list_temp_t = $this->list_tag("action=module module=help flag=1  return=t"); if ($list_temp_t) extract($list_temp_t); $count_t=count($return_t); if (is_array($return_t)) { foreach ($return_t as $key_t=>$t) { ?>
                    <li class="left footer-nav-navbar">
                        <a href="<?php echo $t['url']; ?>" style="<?php if (0 == $i) { ?>color: #fff;<?php } ?>"><?php echo $t['title']; ?></a>
                        <?php if (0 == $i) {  $i++; ?><span>/</span><?php } ?>
                    </li>
                    <?php } } ?>

                		<!--<?php $return_t = array();$list_temp_t = $this->list_tag("action=module module=help flag=1  return=t"); if ($list_temp_t) extract($list_temp_t); $count_t=count($return_t); if (is_array($return_t)) { foreach ($return_t as $key_t=>$t) { ?>-->
                    <!--<li class="left footer-nav-navbar">
                        <a href="<?php echo $t['url']; ?>"><?php echo $t['title']; ?></a>
                    </li>
                    <?php } } ?>
                    <li class="left footer-nav-navbar">
                        <a href="/index.php?s=exc&c=helpController&m=question"><?php echo $head['answer']; ?></a>
                    </li>
                    <li class="left footer-nav-navbar">
                        <a href="/index.php?s=help"><?php echo $head['help']; ?></a>
                    </li>-->
                </ul>
                <ul class="clearfix" style="float: right;">
                	<?php $i = 0;  $return_t = array();$list_temp_t = $this->list_tag("action=module module=help flag=2  return=t"); if ($list_temp_t) extract($list_temp_t); $count_t=count($return_t); if (is_array($return_t)) { foreach ($return_t as $key_t=>$t) { ?>
                    <li class="left footer-nav-navbar">
                        <a href="<?php echo $t['url']; ?>" style=" <?php if (0 == $i) { ?>color: #fff;<?php } ?>"><?php echo $t['title']; ?></a>
                        <?php if (0 == $i) {  $i++; ?><span>/</span><?php } ?>
                    </li>
                    <?php } } ?>
                </ul>
            </div>
            <div class="col-xs-12" style="height: 180px;padding-top: 45px">
                    <div class="col-xs-7" >
                            <div class="logo">
                                <img src="./uploadfile/201808/logon.png" style="width: 109px;margin-top: -10px">
                            </div>
                                <p style="padding-left:12px;text-align:left;font-size: 16px;color: #A5A4A4;">安全 稳定 可信 资产交易平台</p>
                    </div>
                    <div class="col-xs-5" >
                        <table width="100%">

                            <tr style="height: 30px">
                                <!--<th  style="color:#fff;text-align: right;">旗下产品</th>-->
                                <th  style="color:#fff;text-align: right;">关于我们</th>
                                <th  style="color:#fff;text-align: right;">服务协议</th>
                                <th  style="color:#fff;text-align: right;">支持</th>
                            </tr>
                            <tr style="height: 30px">
                                <!--<td style="font-size: 16px;color: #A5A4A4;text-align: right;line-height: 22px;"><a target="_blank" href="http://www.bishu.io" style="color: #A5A4A4">币书</a></td>-->
                                <td style="font-size: 16px;color: #A5A4A4;text-align: right;line-height: 22px;"><a target="_blank" href="/index.php?s=help&c=show&id=18" style="color:#A5A4A4">关于我们</a></td>
                                <td style="font-size: 16px;color: #A5A4A4;text-align: right;line-height: 22px;">费率说明</td>
                                <td style="font-size: 16px;color: #A5A4A4;text-align: right;line-height: 22px;"><a target="_blank" style="color: #A5A4A4" href="/index.php?s=help">帮助中心</a></td>
                            </tr>
                            <tr style="height: 30px">
                                <!--<td style="font-size: 16px;color: #A5A4A4;text-align: right;line-height: 22px;cursor: pointer;"><a target="_blank" href="https://www.bithello.me" style="color: #A5A4A4">bithello</a></td>-->

                                <td style="font-size: 16px;color: #A5A4A4;text-align: right;line-height: 22px;"><a target="_blank" href="/index.php?s=help&c=show&id=20" style="color: #A5A4A4">联系我们</a></td>
                                <td style="font-size: 16px;color: #A5A4A4;text-align: right;line-height: 22px;"></td>
                                <td style="font-size: 16px;color: #A5A4A4;text-align: right;line-height: 22px;"></td>
                            </tr>

                        </table>
                        </div>

                <!--<p style="text-align: center;padding: 60px 0;">Copyright © 2016-2018  All Rights Reserved </p>-->
            </div>
        </div>
    </div>
</div>

	<!--<script src="<?php echo HOME_THEME_PATH; ?>exc/js/jquery-2.2.3.min.js" type="text/javascript" charset="utf-8"></script>-->

	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/custom.js" type="text/javascript" charset="utf-8"></script>
	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/util.js" type="text/javascript" charset="utf-8"></script>
<div style="display: none">
<script src="https://s13.cnzz.com/z_stat.php?id=1274088262&web_id=1274088262" language="JavaScript"></script>
</div>
	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/comm.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript">
        	       function _show(e){
        	           if($(e).parents(".mian-news").hasClass("extended")){
        	               $(e).css({
        	                   transform: 'rotate(0deg)'
        	               });
        	               $(e).parents(".mian-news").removeClass("extended");
        	           }else{
        	               $(e).css({
                               transform: 'rotate(180deg)'
                           });
                       $(e).parents(".mian-news").addClass("extended");
        	           }
        	       }



        	       $(".orderform-type>span").on({
        	           click:function(){
        	               var num = $(this).index();
        	               $(".orderform-type>span").removeClass("cur");
        	               $(this).addClass("cur");
        	               $(".orderforms .orderform-main").addClass("orderforms-hidden");
        	               $(".orderforms .orderform-main").eq(num).removeClass("orderforms-hidden");
        	           }
        	       })


        	       function _show(e){
        	           e.children(".info").toggle();
        	       }
        </script>
<script src="https://qiyukf.com/script/81e860d0e09050b75140d15762b7f341.js"></script>
        <?php if (!isset($_COOKIE['oex_lan']) || $_COOKIE['oex_lan'] =='zh_TW') { ?>
		    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/language/language_zh_cn.js" type="text/javascript" charset="utf-8"></script>
		<?php } else { ?>
		    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/language/language_en_US.js" type="text/javascript" charset="utf-8"></script>
		<?php } ?>
	</body>
</html>