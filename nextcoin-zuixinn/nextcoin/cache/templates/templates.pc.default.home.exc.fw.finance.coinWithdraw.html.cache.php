													<input type="hidden" id="symbol-<?php echo $data['coinType']['id']; ?>" value="<?php echo $data['coinType']['id']; ?>">
                        								<input type="hidden" id="coinName-<?php echo $data['coinType']['id']; ?>" value="<?php echo $data['coinType']['shortname']; ?>">
													<form action="post" class="financial-form" id="tx_form">
	                                                    <div class="col-xs-12 account-form-item">
	                                                        <p class="col-xs-12 account-form-text"><?php echo $finance['withdraw_address']; ?>
	                                                            <a href="/index.php?s=exc&c=financeController&m=addAddress&symbol=<?php echo $data['coinType']['id']; ?>" class="text-right">+<?php echo $finance['withdraw_add']; ?></a>
	                                                        </p>
	                                                        <div class="col-xs-12 account-form-select">
	                                                            <div class="menu">
	                                                                <select id="withdrawAddr-<?php echo $data['coinType']['id']; ?>" class="clearfix menu-text" style="width: 100%;">
	                                                                		<?php if (is_array($data['withdrawAddress'])) { $count=count($data['withdrawAddress']);foreach ($data['withdrawAddress'] as $addressItem) { ?>
	                                                                		 	<option value="<?php echo $addressItem['fid']; ?>" ><?php echo $addressItem['fadderess']; ?></option>
                                                       					 <?php } } ?>
	                                                                </select>
	                                                            </div>
	                                                        </div>
	                                                    </div>
	                                                    <div class="col-xs-6 account-form-item account-form-left">
	                                                        <p><?php echo $finance['withdraw_number']; ?></p>
	                                                        <input type="text" id="withdrawAmount-<?php echo $data['coinType']['id']; ?>" value="" autocomplete="off" />
	                                                    </div>
	                                                    <div class="col-xs-6 account-form-item account-form-right">
	                                                        <p><?php echo $finance['withdraw_smsCode']; ?></p>
	                                                        <input type="text" id="withdrawPhoneCode-<?php echo $data['coinType']['id']; ?>"  />
	                                                        <span type="button" id="send-msg-<?php echo $data['coinType']['id']; ?>" class="send-msg msg-button-inline" style="position: absolute;right:10px; top: 39px;cursor: pointer; "><?php echo $finance['withdraw_getSms']; ?></span>
	                                                    </div>
	                                                    <div class="col-xs-6 account-form-item account-form-left">
	                                                        <p><?php echo $finance['account_tradPassword']; ?></p>
	                                                        <input type="password"  id="tradePwd-<?php echo $data['coinType']['id']; ?>" value="" autocomplete="off" />
	                                                    </div>
	                                                    <!--<div class="col-xs-6 account-form-item account-form-right">-->
	                                                        <!--<p><?php echo $finance['account_googleCode']; ?></p>-->
	                                                        <!--<input type="text" id="withdrawTotpCode-<?php echo $data['coinType']['id']; ?>" value="" />-->
	                                                    <!--</div>-->
	                                                    <div class="col-xs-12 account-form-footer">
	                                                        <div class="text-left account-form-num">
	                                                            <p><?php echo $finance['withdraw_fees']; ?>： <span id="free-<?php echo $data['coinType']['id']; ?>">0.00000000</span> <?php echo $data['coinType']['shortname']; ?></p>
	                                                            <p><?php echo $finance['withdraw_trueget']; ?>： <span id="amount-<?php echo $data['coinType']['id']; ?>">0.0000</span><?php echo $data['coinType']['shortname']; ?></p>
	                                                        </div>
	                                                        <div class="text-right account-form-btn">
	                                                            <button type="button" id="withdrawBtcButton-<?php echo $data['coinType']['id']; ?>" class="account-form-btn"><?php echo $finance['withdraw_submit']; ?></button>
	                                                        </div>
	                                                    </div>
	                                                    <div class="col-xs-12 account-form-hint">
	                                                        <p><?php echo $finance['withdraw_notice']; ?></p>
	                                                        <p>1、<?php echo $finance['withdraw_notice01'];  echo $data['withdrawSetting']['withdrawMin'];  echo $finance['withdraw_notice02']; ?></p>
	                                                        <p>2、<?php echo $finance['withdraw_notice2']; ?></p>
	                                                    </div>
                                                    </form>

        <input id="feesRate-<?php echo $data['coinType']['id']; ?>" type="hidden" value="<?php echo $data['withdrawSetting']['withdrawFee']; ?>">
        <input type="hidden" id="max_double-<?php echo $data['coinType']['id']; ?>" value="<?php echo $data['withdrawSetting']['withdrawMax']; ?>">
        <input type="hidden" id="min_double-<?php echo $coinType['id']; ?>" value="<?php echo $data['withdrawSetting']['withdrawMin']; ?>">
        	
        	<script type="text/javascript">
        		$('#send-msg'+"-<?php echo $data['coinType']['id']; ?>").click(function(){
		        var obj = this;
		        var url = "/index.php?s=exc&c=financeController&m=smsBank";
		        var param = {
		
		        };
		        var callback = function(data) {
		            if( data.code ==200){
		                util.counter(obj,120,'重新发送');
		               util.layerAlert('','发送成功',1,function(){})
		            } else{
		                util.layerAlert('',data.msg,2,function(){})
		            }
		        };
		        util.network({url : url,
		            param : param,
		            success : callback});
		    });

                $("#withdrawAmount"+"-<?php echo $data['coinType']['id']; ?>").focus(function(event){
                    var feesRate = $("#feesRate"+"-<?php echo $data['coinType']['id']; ?>").val();
                    var feeamt =util.numFormat( feesRate,8);
                    $("#free"+"-<?php echo $data['coinType']['id']; ?>").html(feeamt);
                });

		    $("#withdrawAmount"+"-<?php echo $data['coinType']['id']; ?>").on("keypress", function(event) {
		        return util.goIngKeypress(this, event, util.ENTER_CNY_SCALE);
		    }).on("keyup", function() {
		    		
		        var amount = $("#withdrawAmount"+"-<?php echo $data['coinType']['id']; ?>").val();
	            var feesRate = $("#feesRate"+"-<?php echo $data['coinType']['id']; ?>").val();
	            if (amount == "") {
	                amount = 0;
	            }

	            //var feeamt = util.numFormat(util.accMul(amount, feesRate), 8);
				var feeamt =util.numFormat( feesRate,8);
	            $("#free"+"-<?php echo $data['coinType']['id']; ?>").html(feeamt);
	            console.log(amount);
	            console.log(feeamt);
	            if (parseFloat(feeamt) < parseFloat(amount)){
	                console.log('121');
                    $("#amount"+"-<?php echo $data['coinType']['id']; ?>").html(util.numFormat(parseFloat(amount) - parseFloat(feeamt)*parseFloat(amount), 8));
				} else {
                    $("#amount"+"-<?php echo $data['coinType']['id']; ?>").html(util.numFormat(parseFloat(0), 8));
				}
		    });
		    
        	</script>
        	
        	<script type="text/javascript">
        		$("#withdrawBtcButton"+"-<?php echo $data['coinType']['id']; ?>").on("click", function() {
	            var coinName = $("#coinName"+"-<?php echo $data['coinType']['id']; ?>").val();
	            var withdrawAddr = util.trim($("#withdrawAddr"+"-<?php echo $data['coinType']['id']; ?>").val());
	            var withdrawAmount = util.trim($("#withdrawAmount"+"-<?php echo $data['coinType']['id']; ?>").val());
	            var tradePwd = util.trim($("#tradePwd"+"-<?php echo $data['coinType']['id']; ?>").val());
	            var max_double = parseFloat(util.trim($("#max_double"+"-<?php echo $data['coinType']['id']; ?>").val()));
	            var min_double = parseFloat(util.trim($("#min_double"+"-<?php echo $data['coinType']['id']; ?>").val()));
	            var totpCode = 0;
	            var phoneCode = 0;
	            var btcfee = 0;
	            var withdrawMemo ="";
	            var symbol = $("#symbol"+"-<?php echo $data['coinType']['id']; ?>").val();
	            console.log(withdrawAddr);
	            if ($("#btcbalance"+"-<?php echo $data['coinType']['id']; ?>").length > 0 && $("#btcbalance"+"-<?php echo $data['coinType']['id']; ?>").val() == 0) {
	                layer.msg(util.getLan("comm.tips.9"));
	                return;
	            }
	            if (withdrawAddr == "" || withdrawAddr == null || withdrawAddr == "null") {
	                layer.msg(util.getLan("finance.tips.24"));
	                return;
	            }
	            var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
	            if (!reg.test(withdrawAmount)) {
	                layer.msg(util.getLan("finance.tips.25"));
	                return;
	            }
	            if (parseFloat(withdrawAmount) < parseFloat(min_double) && parseFloat(min_double) != 0) {
	                layer.msg(util.getLan("finance.tips.26", min_double, coinName));
	                return;
	            }
	            if (parseFloat(withdrawAmount) > parseFloat(max_double) && parseFloat(max_double) != 0) {
	                layer.msg(util.getLan("finance.tips.27", max_double, coinName));
	                return;
	            }
	            if (tradePwd == "") {
	                layer.msg(util.getLan("comm.tips.8"));
	                return;
	            }
	            // if ($("#withdrawTotpCode"+"-<?php echo $data['coinType']['id']; ?>").length > 0) {
	            //     totpCode = util.trim($("#withdrawTotpCode"+"-<?php echo $data['coinType']['id']; ?>").val());
	            //     if (!/^[0-9]{6}$/.test(totpCode)) {
	            //         layer.msg(util.getLan("comm.tips.2"));
	            //         return;
	            //     }
	            // }
	            if ($("#withdrawPhoneCode"+"-<?php echo $data['coinType']['id']; ?>").length > 0) {
	                phoneCode = util.trim($("#withdrawPhoneCode"+"-<?php echo $data['coinType']['id']; ?>").val());
	                if (!/^[0-9]{6}$/.test(phoneCode)) {
	                    layer.msg(util.getLan("comm.tips.3"));
	                    return;
	                }
	            }
	            if (totpCode == 0 && phoneCode == 0) {
	                layer.msg(util.getLan("comm.tips.1"));
	                return;
	            }
	            if ($("#feesRate"+"-<?php echo $data['coinType']['id']; ?>").length > 0) {
	                btcfee = util.trim($("#feesRate").val());
	            }
	            if ($("#withdrawMemo"+"-<?php echo $data['coinType']['id']; ?>").length > 0) {
	                withdrawMemo = util.trim($("#withdrawMemo").val());
	            }
	            util.hideerrortips("withdrawerrortips");
	            var url = "/index.php?s=exc&c=financeController&m=withDraw";
	            var param = {
	                withdrawAddr : withdrawAddr,
	                withdrawAmount : withdrawAmount,
	                tradePwd : tradePwd,
	                totpCode : totpCode,
	                phoneCode : phoneCode,
	                symbol : symbol,
	                btcfeesIndex : 0,
	                memo:withdrawMemo
	            };
	            var callback = function(result) {
	                if (result.code == 200) {
	                    util.layerAlert("", util.getLan("finance.tips.28"), 1);
	                } else {
	                    layer.msg(result.msg);
	                }
	            };
	            util.network({
//	                             btn : that,
	                             url : url,
	                             param : param,
	                             success : callback,
	                         });
        
		    });
        </script>
        	
        	
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/jquery.qrcode.min.js" type="text/javascript" charset="utf-8"></script>
