<?php if ($fn_include = $this->_include("header.html")) include($fn_include);  if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
		<div class="container">
            <div class="row financial-wrap">
                <div class="col-xs-12 financial">
                    <div class="col-xs-12 center-tetil">
                        <h1><?php echo $user['account_security']; ?></h1>
                    </div>
                    <div class="col-xs-12 center-data">
                        <div class="col-xs-12 center-data-tetil">
                            <h3><?php echo $user['essential_information']; ?></h3>
                        </div>
                        <div class="col-xs-12 center-data-list"  >
                            <div class="col-xs-12 center-data-item">
                                <div class="col-xs-8">
                                    <div class="center-data-left"><?php echo $user['account']; ?></div>
                                    <div class="center-data-num"><?php echo $data['fuser']['floginname']; ?></div>
                                </div>
                            </div>
                            <div class="col-xs-12 center-data-item">
                                <div class="col-xs-8">
                                    <div class="center-data-left"><?php echo $user['security_loginPwd']; ?></div>
                                    <div class="center-data-num">********</div>
                                </div>
                                <a href="/index.php?s=exc&c=securityController&m=alterLogingPsw" class="col-xs-4 text-right"><?php echo $user['security_alter']; ?></a>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-12 center-tebel-wrap">
                        <div class="col-xs-12 center-data-tetil">
                            <h3><?php echo $user['two_factor_authentication']; ?></h3>
                        </div>
                        <div class="col-xs-12 center-tebel">
                            <table class="col-xs-12">
                            	   <tbody>
                            	       <tr class="center-tebel-item">
                            	           <td><?php echo $user['security_level']; ?>：</td>
                            	           <?php if ($data['securityLevel']>=2) { ?>
                            	           <td class="center-passege-box clearfix">
                            	               <div class="center-tebel-passege">
                            	                   <div style="width: <?php echo $jd; ?>%;"></div>
                            	               </div>
                            	               <span style="margin-left: 6px; color: #F5F5F5;font-size: 12px;"><?php echo $user['security_high']; ?></span>
                            	           </td>
                            	          <?php } else { ?>
                            	           <td class="center-passege-box clearfix">
                            	               <div class="center-tebel-passege">
                            	                   <div style="width: <?php echo $jd; ?>%;"></div>
                            	               </div>
                            	               <span style="margin-left: 6px; color: #F5F5F5;font-size: 12px;"><?php echo $user['security_low']; ?></span>
                            	           </td>
                            	           <?php } ?>
                            	           <td><?php echo $user['security_tit']; ?></td>
                            	           <td></td>
                            	       </tr>
                            	       <tr class="center-tebel-item">
                            	       	   <?php if ($data['fuser']['fistelephonebind']) { ?>
                            	           <td><i class="iconfont icon-yiyanzheng success"></i><?php echo $user['security_phone']; ?></td>
                            	           <td class="center-tebel-num"><?php echo $data['phoneString']; ?></td>
                            	           <?php } else { ?>
                            	           <td><i class="iconfont icon-weiyanzheng danger"></i><?php echo $user['security_phone']; ?></td>
                            	           <td class="center-tebel-num"></td>
                            	           <?php }  if ($data['fuser']['fistelephonebind']) { ?>
                            	           <td><?php echo $user['security_phoneT']; ?>: <?php echo $data['phoneString']; ?></td>
                            	           <?php } else { ?>
                            	           <td></td>
                            	           <?php }  if ($data['fuser']['fistelephonebind']) { ?>
                            	           <td></td>
                            	           <?php } else { ?>
                            	           <td><a href="/index.php?s=exc&c=securityController&m=phoneHtml"><?php echo $user['security_bind']; ?></a></td>
                            	           <?php } ?>
                            	           
                            	       </tr>
                            	       <tr class="center-tebel-item">
                            	           	<td>
                            	           		<?php if ($data['fuser']['fismailbind']) { ?>
                            	           		<i class="iconfont icon-yiyanzheng success"></i>
                            	           		<?php } else { ?>
                            	           		<i class="iconfont icon-weiyanzheng danger"></i>
                            	           		<?php }  echo $user['security_email']; ?>
                            	           	</td>
                            	           	<?php if ($data['fuser']['fismailbind']) { ?>
                            	           	<td class="center-tebel-num"><?php echo $data['emaString']; ?></td>
                            	           	<td><?php echo $user['security_emailT']; ?></td>
                            	           	<td></td>
                            	           	<?php } else { ?>
                            	           	<td class="center-tebel-num"></td>
                            	           	<td><?php echo $user['security_emailF']; ?></td>
                            	           	<td><a href="/index.php?s=exc&c=securityController&m=emailHtml"><?php echo $user['security_bind']; ?></a></td>
                            	           	<?php } ?>
                            	       </tr>
                            	       <tr class="center-tebel-item">
                            	           <td>
                            	           		<?php if ($data['fuser']['fgooglebind']) { ?>
                            	           		<i class="iconfont icon-yiyanzheng success"></i>
                            	           		<?php } else { ?>
                            	           		<i class="iconfont icon-weiyanzheng danger"></i>
                            	           		<?php }  echo $user['security_topcode']; ?>
                            	           </td>
                            	           <td class="center-tebel-num"></td>
                            	           <?php if ($data['fuser']['fgooglebind']) { ?>
                            	           <td><?php echo $user['security_topcodeT']; ?></td>
                            	           <?php } else { ?>
                            	           <td><?php echo $user['security_topcodeF']; ?></td>
                            	           <?php }  if ($data['fuser']['fgooglebind']) { ?>
                            	           <td><a id="unbindgoogle" href="###"><?php echo $user['security_alter']; ?></a></td>
                            	           <?php } else { ?>
                            	           <td><a href="/index.php?s=exc&c=securityController&m=googleHtml"><?php echo $user['security_bind']; ?></a></td>
                            	           <?php } ?>
                            	       </tr>
                            	       <tr class="center-tebel-item">
                            	           <td>
                            	       			<?php if ($data['bindTrade']) { ?>
                            	       			<i class="iconfont icon-yiyanzheng success"></i>
                            	       			<?php } else { ?>
                            	           		<i class="iconfont icon-weiyanzheng danger"></i>
                            	           		<?php }  echo $user['security_tradePassword']; ?>
                            	           </td>
                            	           <td class="center-tebel-num"><a href="/index.php?s=exc&c=securityController&m=forgetTransPsw"><?php echo $user['security_forgetTrade']; ?>?</a></td>
                            	           <?php if ($data['bindTrade']) { ?>
                            	           <td><?php echo $user['security_tradPasswordT']; ?></td>
                            	           <?php } else { ?>
                            	           <td><?php echo $user['security_tradPasswordF']; ?></td>
                            	           <?php }  if ($data['bindTrade']) { ?>
                            	           <td><a href="/index.php?s=exc&c=securityController&m=transAlterPsw"><?php echo $user['security_alter']; ?></a></td>
                            	           <?php } else { ?>
                            	           <td><a href="/index.php?s=exc&c=securityController&m=transpsw"><?php echo $user['security_set']; ?></a></td>
                            	           <?php } ?>
                            	       </tr>
                            	       <tr class="center-tebel-item">
                            	           <td>
                            	           		<?php if ($data['fuser']['fhasrealvalidate']) { ?>
                            	           		<i class="iconfont icon-yiyanzheng success"></i>
                            	           		<?php } else { ?>
                            	           		<i class="iconfont icon-weiyanzheng danger"></i>
                            	           		<?php }  echo $user['security_truename']; ?>
                            	           </td>
                            	           <td class="center-tebel-num"></td>

                            	           <?php if ($data['fuser']['fhasrealvalidate']) { ?>
                            	           <td><?php echo $user['security_truenameT']; ?></td>
                            	           <?php } else if ($data['identity']) {  if ($data['identity']['fstatus'] == 2) { ?>
                            	           		<td><?php echo $user['security_truenameB']; ?></td>
                            	           		<td><a href="/index.php?s=exc&c=securityController&m=namevalidate"><?php echo $user['security_author']; ?></a></td>
                            	           		<?php } else if ($data['identity']['fstatus'] == 0) { ?>
                            	           		<td><?php echo $user['security_truenameC']; ?></td>
                            	           		<?php }  } else { ?>
                            	           <td><?php echo $user['security_truenameF']; ?></td>
                            	           <td><a href="/index.php?s=exc&c=securityController&m=namevalidate"><?php echo $user['security_author']; ?></a></td>
                            	           <?php } ?>
                            	       </tr>
                            	   </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="col-xs-12 center-tebel-wrap" style="min-height: 331px;margin-bottom: 60px;">
                        <div class="col-xs-12 center-nav">
                            <button <?php if (empty($type)) { ?>class="active"<?php } ?> id="loging-log-btn" onclick="logingLog(1);"><?php echo $user['log_login']; ?></button>
                            <button id="safe-log-btn" <?php if (!empty($type)) { ?>class="active"<?php } ?> onclick="safeLog(1);"><?php echo $user['log_history']; ?></button>
                        </div>
                        <div class="col-xs-12 center-list <?php if (empty($type)) { ?>show<?php } ?>">
                            <table class="col-xs-12" id="loging-log">
                            	
                            </table>
                        </div>
                        <div class="col-xs-12 center-list <?php if (!empty($type)) { ?>show<?php } ?>">
                            <table class="col-xs-12" id="safe-log">
                            	   
                            </table>
                        </div>
                        <div id="pagination" class="pagination-wrap col-xs-12 center-list-btn" style="margin-top: 0px;padding-top: 0;padding-bottom: 0;">
                                    <nav aria-label="Page navigation" class="pagination" id="logingPage">
                                        <ul id="pages">
                                        		
                                        </ul>
                                    </nav>
                        	</div>
                    </div>

                </div>
            </div>
        </div>


<!--------------------------------- 查看谷歌验证码 ------------------->
            <div class="model" id="unbindgoogle-show-box">
                <div class="model-body">
                    <div class="model-form-header clearfix">
                        <div class="model-form-tetil">
                            <h2><?php echo $user['security_seeTop']; ?></h2>
                        </div>
                        <div class="model-hide">
                            <button class="iconfont icon-guanbi"></button>
                        </div>
                    </div>
                    <div class="model-form-body">
                        <div class="model-form-item">
                            <p><?php echo $user['security_enterCode']; ?>：</p>
                            <label for="">
                                <input type="text" id="unbindgoogle-topcode" value=""/>
                            </label>
                        </div>
                        <div class="model-form-item">
                            <button class="model-form-btn" id="unbindgoogle-Btn"><?php echo $user['security_submit']; ?></button>
                        </div>
                    </div>
                </div>
            </div>

        <div class="model" id="unbindgoogle-hide-box">
            <div class="model-body">
                <div class="model-form-header clearfix">
                    <div class="model-form-tetil">
                        <h2><?php echo $user['security_googleSee']; ?></h2>
                    </div>
                    <div class="model-hide">
                        <button class="iconfont icon-guanbi"></button>
                    </div>
                </div>
                <div class="model-form-body">

                    <div class="model-form-item">
                        <div class="clearfix model-item-imgs" style="text-align: center">

                            <div class="model-item-imgs-bd" style="display: inline-block">
                                <div id="unqrcode"  style="width: 150px;height: 150px; padding: 5px; background: white;display: inline-block">

                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="model-form-item">
                        <p class="model-form-item-text js-data-goodleAccount" style="color: #000000;"><?php echo $user['security_googleAccount']; ?>：<?php echo $data['device_name']; ?></p>
                        <p class="model-form-item-text" style="color: #000000;"><?php echo $user['security_googleKey']; ?>：<span id="unbindgoogle-key"></span></p>
                    </div>
                </div>
            </div>
        </div>
        
        <script type="text/javascript">
        	       $(".center-nav button").on({
        	           click:function(){
        	               $(".center-nav button").removeClass("active");
        	               console.log($(".center-list").eq(0));
        	               $(this).addClass("active");
        	               var i = $(this).index();
        	               $(".center-list").removeClass("show");
        	               $(".center-list").eq(i).addClass("show");
        	           }
        	       })
        	       var currentPage = '<?php echo $currentPage; ?>';
        	       var type = '<?php echo $type; ?>';
        	       if(currentPage == '' || currentPage == undefined) {
        	       		currentPage = 1;
        	       }
        	       if(type == '') {
        	       	  logingLog(currentPage);
        	       }else{
        	       	  safeLog(currentPage);
        	       }
        	      
        	       
        	       //时间戳转日期
        	       function timetrans(date){
				    var date = new Date(date);//如果date为13位不需要乘1000
				    var Y = date.getFullYear() + '-';
				    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
				    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
				    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
				    var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
				    var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
				    return Y+M+D+h+m+s;
				}
        	       
        	       function logingLog(num) {
					$.ajax({
						type: 'get',
						url: '/index.php?s=exc&c=securityController&m=loginLog&currentPage='+num,
						headers: {
					        'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
					    },
						// 告诉jQuery不要去处理发送的数据
						processData: false,
	//					 告诉jQuery不要去设置Content-Type请求头
	//					contentType: false,
						dataType: 'json',
						success: function(data) {
							
							if(data.res.currentPage == 1){
									$("#pages").html('');
							} else {
								$("#pages").html(data.res.pagin);
							}
							
							if (200 == data.code) {
								layer.msg("接口出错");
							} else {
								var str = 	'<tr>'+
												"<th><?php echo $user['log_loginTime']; ?></th>"+
												"<th><?php echo $user['log_loginIp']; ?></th>"+
											'</tr>';
								$.each(data.res.data, function(index, item) {
									var time = timetrans(item.fupdatetime);
									str += '<tr>'+
												'<td>' + time + '</td>'+
												'<td>'+ item.fip + '</td>'+
											'</tr>';
									
								});
								$("#loging-log").html(str);
								$("#pages").html(data.res.pagin);
							}
						}
					});
				}
				
				function safeLog(num) {
					$.ajax({
						type: 'get',
						url: '/index.php?s=exc&c=securityController&m=settingLog&currentPage='+num,
						headers: {
					        'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
					    },
						// 告诉jQuery不要去处理发送的数据
						processData: false,
	//					 告诉jQuery不要去设置Content-Type请求头
	//					contentType: false,
						dataType: 'json',
						success: function(data) {
							
							if(data.res.currentPage == 1){
									$("#pages").html('');
							} else {
								$("#pages").html(data.res.pagin);
							}
							
							if (200 == data.code) {
								layer.msg("接口出错");
							} else {
								var str = 	'<tr>'+
												"<th><?php echo $user['log_loginTime']; ?></th>"+
												"<th><?php echo $user['log_security']; ?></th>"+
												"<th><?php echo $user['log']-loginIp; ?></th>"+
											'</tr>';
								$.each(data.res.data, function(index, item) {
									var time = timetrans(item.fupdatetime);
									str += '<tr>'+
												'<td>' + time + '</td>'+
												'<td>' + item.ftype_s + '</td>'+
												'<td>'+ item.fip + '</td>'+
											'</tr>';
									
								});
								$("#safe-log").html(str);
							}
						}
					});
				}
        	       
        </script>
<script src="<?php echo HOME_THEME_PATH; ?>exc/js/custom.js" type="text/javascript" charset="utf-8"></script>
<script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/jquery.qrcode.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<?php echo HOME_THEME_PATH; ?>exc/js/user/user.security.js" type="text/javascript" charset="utf-8"></script>
<script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/msg.js" type="text/javascript" charset="utf-8"></script>
<?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>


