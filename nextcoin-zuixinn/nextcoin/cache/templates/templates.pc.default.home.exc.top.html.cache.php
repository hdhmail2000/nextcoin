<style>
	.take-data-img{
        background: #fff !important;
        right: 15px;
        top: 30px;
    }
    .navbar-box-text>span{
        vertical-align: middle;
    }
    .header {
        /*background: #030126;*/
        background-color: rgba(0,0,0,.1)!important;
    }
</style>
<div class="header header-shadow quites-header clearfix">
            <div class="logo">
                <a href="/index.php?s=exc&c=indexController" style="background: url('<?php echo dr_get_file(dr_block('wangzhanicon')); ?>') no-repeat;background-size: 100%;width: 160px;"></a>
            </div>
            <div class="navbar">
                <div class="navbar-list">
                    <div class="navbar-list-item">
                        <a href="/index.php?s=exc&c=indexController" <?php if ($controller=='indexController' && $method=='') { ?>class="active"<?php } ?>><?php echo $head['home']; ?></a>
                    </div>
                    <div class="navbar-list-item">
                        <a href="/index.php?s=exc&c=TradeController" <?php if ($controller=='TradeController') { ?>class="active"<?php } ?>><?php echo $head['market']; ?></a>
                    </div>

                    <!--<div class="navbar-list-item">-->
                        <!--<a href="/index.php?s=exc&c=financeController" <?php if ($controller=='financeController') { ?>class="active"<?php } ?>><?php echo $head['finance']; ?></a>-->
                    <!--</div>-->
                     <div class="navbar-list-item">
				        <a id="top_help" href="/index.php?s=trade&c=home&m=c2cBuy&id=44" <?php if ($_GET['s']=='trade') { ?>class="active"<?php } ?>><?php echo $head['Currency_Transaction']; ?></a>
				    </div>
                    <!--<div class="navbar-list-item">-->
				        <!--<a id="top_dt" href="/index.php?s=help&c=member_controller&m=dt" <?php if ($_GET['c']=='member_controller') { ?>class="active"<?php } ?>><?php echo $head['DT']; ?></a>-->
				    <!--</div>-->
				    <!--<div class="navbar-list-item">-->
				        <!--<a id="top_notice" href="/index.php?s=help&c=show&id=14" <?php if ($_GET['s']=='help' && $_GET['c']=='show') { ?>class="active"<?php } ?>><?php echo $head['notice']; ?></a>-->
				    <!--</div>-->
                    <!--<div class="navbar-list-item">-->
				        <!--<a id="top_help" href="/index.php?s=help" <?php if ($_GET['s']=='help' && $_GET['c']=='') { ?>class="active"<?php } ?>><?php echo $head['help']; ?></a>-->
				    <!--</div>-->

                    <?php if (!empty($USERNAME)) { ?>
                        <div class="navbar-list-item">
                            <a href="/index.php?s=exc&c=securityController" id="top_security" <?php if ($controller=='securityController') { ?>class="active"<?php } ?>><?php echo $head['personal']; ?></a>
                        </div>
                    <div class="navbar-list-item">
                        <a href="/index.php?s=exc&c=financeController" id="top_finance" <?php if ($controller=='financeController') { ?>class="active"<?php } ?>><?php echo $head['finance']; ?></a>
                    </div>
                    <?php } ?>

                </div>
                <div class="navbar-loging" style="top: 0px;">
                    <?php if (empty($USERNAME)) { ?>
                        <a href="/index.php?s=exc&c=userController&m=login" class="navbar-loging-btn"><?php echo $head['login']; ?></a>
                        |
                        <!--<a href="/index.php?s=exc&c=userController&m=register" class="navbar-loging-btn"><?php echo $head['register']; ?></a>-->
                        <a href="/index.php?s=exc&c=userController&m=phonereg" class="navbar-loging-btn"><?php echo $head['register']; ?></a>
                    <?php } else { ?>
                        <div class="navbar-loging-box">
                        <span class="navbar-loging-text">
                            <strong style="font-weight: 400;margin-right: 5px;">
                            	<?php if ($groupid == 6) {  echo $head['silver_member'];  } else if ($groupid == 7) {  echo $head['gold_member'];  } else if ($groupid == 8) {  echo $head['diamond_member'];  } ?>
                            </strong><a style="color: #A5A4A4" href="/index.php?s=exc&c=securityController"><?php echo $head['hi']; ?>, <?php echo $USERNAME; ?></a>
                            <!--<i class="iconfont icon-xia"></i>-->
                        </span>
                            <!----------------------------------- 登录后的悬浮窗 ----------------------->
                            <!--<div class="my-box" style="background: #22242E;">-->
                                <!--<div class="clearfix my-box-header" style="border-bottom: 1px solid #474652;border-bottom-width: 1px;border-bottom-style: solid;border-bottom-color: rgb(71, 70, 82);">-->
                                    <!--<div class="left my-box-num">-->
                                        <!--<h2 style="color: #CAD7E8;"><?php echo $USERNAME; ?><strong style="margin-left: 15px;"><?php if ($groupid == 6) { ?>-->
                            	<!--<?php echo $head['silver_member']; ?>-->
                            	<!--<?php } else if ($groupid == 7) { ?>-->
                            	<!--<?php echo $head['gold_member']; ?>-->
                            	<!--<?php } else if ($groupid == 8) { ?>-->
                            	<!--<?php echo $head['diamond_member']; ?>-->
                            	<!--<?php } ?></strong></h2>-->
                                        <!--<p style="display: none;">UID:<?php echo $FID; ?></p>-->
                                    <!--</div>-->
                                    <!--&lt;!&ndash;<a href="/index.php?s=exc&c=securityController" class="right my-box-header-btn"><?php echo $head['setup']; ?></a>&ndash;&gt;-->
                                <!--</div>-->
                                <!--<div class="my-box-body" id="assetsDetail">-->
                                    <!--<table class="my-box-table">-->
                                        <!--<tr>-->
                                            <!--<th><?php echo $head['currency']; ?></th>-->
                                            <!--<th><?php echo $head['available']; ?></th>-->
                                            <!--<th><?php echo $head['frozen']; ?></th>-->
                                        <!--</tr>-->
                                    <!--</table>-->
                                <!--</div>-->
                                <!--<div class="my-box-btn">-->
                                    <!--<a href="/index.php?s=exc&c=financeController" class="btn"><?php echo $head['recharge']; ?></a>-->
                                <!--</div>-->
                            <!--</div>-->
                        </div>
                        |
                        <a href="/index.php?s=exc&c=userController&m=logout" class="navbar-loging-btn"><?php echo $head['logout']; ?></a>
                    <?php } ?>
                </div>
                <div class="navbar-box">
                    <div class="navbar-language-text clearfix">
                        <p class="navbar-box-text"><span class="<?php echo $head['language_css']; ?>"></span><?php echo $head['language']; ?></p>
                        <i class="iconfont icon-xia"></i>
                    </div>
                    <div class="navbar-language">
                        <div class="navbar-language-item clearfix" onclick="changeLang('zh-cn')">
                            <p class="navbar-box-text"><span class="language-cn"></span>中文</p>
                        </div>
                        <div class="navbar-language-item clearfix" onclick="changeLang('english')">
                            <p class="navbar-box-text"><span class="language-usa"></span>English</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
//          function changeLang(num) {
//              if(num==1){
//                  $.get('{{ route("change_lang",['local'=>'cn']) }}',function (data) {
//                      if(data==1){
//                          location.reload();
//                      }else{
//                          alert('语言切换失败');
//                      }
//                  });
//              }else{
//                  $.get('{{ route("change_lang",['local'=>'en']) }}',function (data) {
//                      if(data==1){
//                          location.reload();
//                      }else{
//                          alert('语言切换失败');
//                      }
//                  });
//              }
//          }
			function changeLang(lang) {
        			$.ajax({
        				type:"get",
        				url:"/index.php?s=exc&c=home&m=changeLang&lang="+lang,
        				async:true,
        				dataType:"json",
        				success:function(res) {
        					if(res) {
        						window.location.reload();
        					}else{
        						alert('语言切换失败');
        					}
        				}
        			});
        		}
//			var wallet = function() {
//				$.ajax({
//					type:"get",
//					url:"/index.php?s=exc&c=indexController&m=getWallet",
//					async:true,
//					dataType:"json",
//					success:function(data) {
//						if (data.code == 200) {
//		                     if ($("#assetsDetail").length > 0) {
//		                         var html = "";
//		                         var uid=data.data.wallet[0].uid;
//		                         $.each(data.data.wallet, function(key, value) {
//		                             html +="<tr>" +
//		                                 " <th>" +value.shortName + "</th>" +
//		                                 " <th>" +util.numFormat(value.total, util.DEF_COIN_SCALE) + "</th>" +
//		                                 " <th>" +util.numFormat(value.frozen, util.DEF_COIN_SCALE)+ "</th>" +
//		                                 "  </tr>"
//		                         });
//		                         $('.my-box-num p').html('UID:'+uid);
//		                         $("#assetsDetail table tbody").append(html);
//		                         var userinfo = data.data.userinfo;
//		                         console.log(userinfo);
//		                         $('.js-data-loginname').append(userinfo.floginname);
//		                         $('.js-data-nickname').html(userinfo.fnickname);
//		                         $('.js-data-fid').html(userinfo.fshowid);
//
//		                     }
//		                 }
//					}
//				});
//		    };
//			if ($("#assetsDetail").length > 0) {
//		        wallet();
//		    }
        </script>