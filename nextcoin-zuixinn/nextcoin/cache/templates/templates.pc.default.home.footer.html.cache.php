<div class="container-fluid footer" style="padding-bottom: 0;padding-top: 0;">
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
            <div class="col-xs-12 footer-num">
                <p style="text-align: center;padding: 60px 0;">Copyright © 2016-2018  All Rights Reserved </p>
            </div>
        </div>
    </div>
</div>


	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/custom.js" type="text/javascript" charset="utf-8"></script>
	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/layer/layer.js" type="text/javascript" charset="utf-8"></script>
	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/util.js" type="text/javascript" charset="utf-8"></script>
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
        <?php if (!isset($_COOKIE['oex_lan']) || $_COOKIE['oex_lan'] =='zh_TW') { ?>
		    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/language/language_zh_cn.js" type="text/javascript" charset="utf-8"></script>
		<?php } else { ?>
		    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/language/language_en_US.js" type="text/javascript" charset="utf-8"></script>
		<?php } ?>
	</body>
</html>