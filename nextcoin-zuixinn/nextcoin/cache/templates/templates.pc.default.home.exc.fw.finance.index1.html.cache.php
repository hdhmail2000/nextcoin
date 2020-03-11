<?php if ($fn_include = $this->_include("header.html")) include($fn_include);  if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
        <div class="container">
            <div class="row financial-wrap">
                <div class="col-xs-12 financial">
<!-------------------------------- 边栏导航 -------------------------------->
                    <?php if ($fn_include = $this->_include("fw/finance/left.html")) include($fn_include); ?>
<!---------------------------------- 内容详情 ----------------------------->
                    <div class="col-xs-9 financial-data financial-data-bg account-data">
                        <div class="col-xs-12 account-tetil">
                            <div class="text-left"><?php echo $finance['account_asset']; ?></div>
                            <a href="/index.php?s=exc&c=financeController&m=record" class="text-right"><?php echo $finance['account_record']; ?></a>
                        </div>
                        <div class="col-xs-12 account-table">
                            <table class="col-xs-12">
                                	<tr class="account-table-header">
                                	    <th><?php echo $finance['asset_name']; ?></th>
                                	    <th><?php echo $finance['asset_use']; ?></th>
                                	    <th><?php echo $finance['asset_froze']; ?></th>
                                	    <th><?php echo $finance['asset_total']; ?></th>
                                	    <th><?php echo $finance['asset_operation']; ?></th>
                                	</tr>
                                	<?php if (is_array($data)) { $count=count($data);foreach ($data as $item) { ?>
                                	<tr class="account-table-item">
                                	    <td><?php echo $item['shortName']; ?></td>
                                	    <td><?php echo sprintf("%.4f",$item['total']); ?></td>
                                	    <td><?php echo sprintf("%.4f",$item['frozen']); ?></td>
                                	    <td><?php echo sprintf("%.4f",$item['total'] + $item['frozen']); ?></td>
                                	    <td>
                                	        <div class="account-nav">
                                	        	   <?php if ($item['shortName'] == 'USDT') { ?>
                                	        	   <a class="account-navbar" href="/index.php?s=trade&c=home&m=c2cSell&id=45"><?php echo $finance['account_gobuy']; ?></a>
                                            <!--<button class="account-navbar account-save" id="usdt-cz" data="<?php echo $item['coinId']; ?>"><?php echo $finance['account_deposit']; ?></button>-->
                                                <?php } else if ($item['shortName'] == 'CNYT') { ?>
                                                <button class="account-navbar account-save" onclick="czcny(<?php echo $item['coinId']; ?>);"><?php echo $finance['account_deposit']; ?></button>
                                                <?php } else if ($item['shortName'] == 'SCC') { ?>
                                                <button class="account-navbar account-save" >暂停充值</button>
                                            <?php } else { ?>
                                            <button class="account-navbar account-save" onclick="cz(<?php echo $item['coinId']; ?>);"><?php echo $finance['account_deposit']; ?></button>
                                            <?php } ?>
                                            <div class="save-window account-nav-box">
                                                <div class="account-nav-up save-window-up"></div>
                                                <div class="clearfix take-data" id="cz-<?php echo $item['coinId']; ?>">
                                                	
                                                </div>
                                                
                                            </div>
                                            <?php if ($item['shortName'] == 'USDT') { ?>
                                            <!--<button class="account-navbar account-take" onclick="tx_usdt(<?php echo $item['coinId']; ?>)"><?php echo $finance['account_withdraw']; ?></button>-->
                                                <?php } else if ($item['shortName'] == 'CNYT') { ?>
                                                <a class="account-navbar" href="/index.php?s=trade&c=home&m=c2cSell&id=44"><?php echo $finance['account_withdraw']; ?></a>
                                            <?php } else { ?>
                                            <button class="account-navbar account-take" onclick="tx(<?php echo $item['coinId']; ?>)"><?php echo $finance['account_withdraw']; ?></button>
                                            <?php } ?>
                                            
                                            <div class="take-window account-nav-box">
                                                <div class="account-nav-up take-window-up"></div>
                                                <div class="clearfix account-form" id="tx-<?php echo $item['coinId']; ?>">
                                                		
                                                </div>
                                            </div>
                                            
                                        </div>
                                	    </td>
                                	</tr>
                                	<?php } } ?>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <div class="form-model">
            <div class="form-model-body">
                <button class="form-model-hide iconfont icon-guanbi form-model-gb"></button>
                <div class="form-model-tetil">
                    <h2>短信验证码</h2>
                </div>
                <div class="form-model-item">
                    <p>输入 152****3456 的验证码</p>
                    <label for="">
                        <input type="text" name="" id="" value="" />
                        <button>获取验证码</button>
                    </label>
                </div>
                <div class="form-model-btn">
                    <button class="form-model-hide">确定</button>
                </div>
            </div>
        </div>

<script src="<?php echo HOME_THEME_PATH; ?>exc/js/menu.js" type="text/javascript" charset="utf-8"></script>
<script src="<?php echo HOME_THEME_PATH; ?>exc/js/custom.js" type="text/javascript" charset="utf-8"></script>
<script src="<?php echo HOME_THEME_PATH; ?>exc/js/finance/account.withdraw.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
            $(".account-take").on({
                click: function() {
//                  var data = '<div class="account-nav-up take-window-up"></div>';
                    $(".save-window").hide();
                    $("body .take-window").not($(this).siblings(".take-window")).hide();

                    $(this).siblings(".take-window").toggle();
//                  $(this).siblings(".take-window").html(data);

//                  $(this).siblings(".take-window").append(str);
                    $(document).click(function(e) {
                        var target = $(e.target);
                        if(target.closest(".menu-text").length != 0) return;
                        $(".menu-list").hide();
                    });
                    $(".menu-text").on({
                        click: function() {
                            $(".menu-list").not($(this).siblings()).hide();
                            $(this).siblings().fadeToggle(300);
                        }
                    });
                    $(".menu-item").on({
                        click: function() {
                            var _list_html = $(this).html();
                            $(this).parent().prev().children("span").html(_list_html);
                            $(this).parent().hide();
                        }
                    })
                    $(".account-form-btn").on({
                        click: function() {
                            $(".form-model").show();
                        }
                    })
                }
            })

            $(".account-save").on({
                click: function() {
                    var data = '<div class="account-nav-up save-window-up"></div>';
                    $("body .save-window").not($(this).siblings(".save-window")).hide();
                    $(".take-window").hide();
                    $(this).siblings(".save-window").toggle();
//                  $(this).siblings(".save-window").html(data);

                }
            })
            
            $("#usdt-cz").click(function(){
            		var symbol_id = $("#usdt-cz").attr('data');
            		window.location.href = "/index.php?s=exc&c=financeController&m=c2cDeposit&symbol="+symbol_id;
            });
            
            function cz(coinId){
            		$.ajax({
					type: 'get',
					url: '/index.php?s=exc&c=financeController&m=coinDeposit&symbol='+coinId,
					// 告诉jQuery不要去处理发送的数据
					processData: false,
					// 告诉jQuery不要去设置Content-Type请求头
					contentType: false,
					dataType: 'text',
					success: function(res) {
						//console.log(res);
						if(res == -1){
							layer.msg('创世挖矿期间关闭提现和充值功能');
							return;
						}
						var str = "#cz-" + coinId;
						$(str).html(res);
					}
				});
            }

            function czcny(coinId){
                $.ajax({
                    type: 'get',
                    url: '/index.php?s=exc&c=financeController&m=cnytDeposit&symbol='+coinId,
                    // 告诉jQuery不要去处理发送的数据
                    processData: false,
                    // 告诉jQuery不要去设置Content-Type请求头
                    contentType: false,
                    dataType: 'text',
                    success: function(res) {
                        //console.log(res);
                        if(res == -1){
                            layer.msg('创世挖矿期间关闭提现和充值功能');
                            return;
                        }
                        var str = "#cz-" + coinId;
                        $(str).html(res);
                    }
                });
            }

            
            function tx(coinId){
            		$.ajax({
					type: 'get',
					url: '/index.php?s=exc&c=financeController&m=coinWithdraw&symbol='+coinId,
					// 告诉jQuery不要去处理发送的数据
					processData: false,
					// 告诉jQuery不要去设置Content-Type请求头
					contentType: false,
					dataType: 'text',
					success: function(res) {
//						console.log(res);
						if(res == -1){
							layer.msg('创世挖矿期间关闭提现和充值功能');
							return;
						}
						var str = "#tx-" + coinId;
						$(str).html(res);
					}
				});
            }
            
            function tx_usdt(coinId){
            		$.ajax({
					type: 'get',
					url: '/index.php?s=exc&c=financeController&m=cnyWithdraw&symbol='+coinId,
					// 告诉jQuery不要去处理发送的数据
					processData: false,
					// 告诉jQuery不要去设置Content-Type请求头
					contentType: false,
					dataType: 'text',
					success: function(res) {
						console.log(res);
						var str = "#tx-" + coinId;
						$(str).html(res);
					}
				});
            }
            
</script>
<?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>