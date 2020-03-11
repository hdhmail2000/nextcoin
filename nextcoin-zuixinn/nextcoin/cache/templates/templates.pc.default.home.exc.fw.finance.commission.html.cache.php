<?php if ($fn_include = $this->_include("header.html")) include($fn_include);  if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
<style type="text/css">
	.mode-table-item #qrcode td{
		padding: 0;
	}
	.mode-table-link{
	    width: 100%;
	}
	/*.mode-table-link div{
	    width: 100%;
	}*/
	.mode-table-code{
	    margin-top: 20px;
	}
	.mode-table-code p{
	    margin-bottom: 10px;
	}
	.mode-table-user p{
	    margin-bottom: 10px;
	}
	.mode-table-user input{
	    height: 50px;
        line-height: 50px;
        background: #27262E;
        border: 1px solid #474652;
        padding: 0 16px 0 16px;
        font-size: 12px;
        color: #C9D6E8;
        width: 100%;
	}
	.mode-table-imgCode{
	    position: absolute;
	    left: 50%;
	    transform: translateX(-50%);
	    width: 33%;
	    bottom: 35px;
	}
</style>
        <div class="container">
            <div class="row">
                <div class="col-xs-12 financial">
                    <!-------------------------------- 边栏导航 -------------------------------->
                    <?php if ($fn_include = $this->_include("fw/finance/left.html")) include($fn_include); ?>
                    <!---------------------------------- 内容详情 ----------------------------->

                    <div class="col-xs-10 financial-data">
                        <div class="col-xs-12 account-tetil">
                            <div class="text-left"><?php echo $finance['account_recommend']; ?></div>
                        </div>
                        <div class="col-xs-12 mode-table">
                            <div class="col-xs-12 mode-table">
                            <table class="col-xs-12">
                            	   <tr class="mode-table-header">
                            	       <th><?php echo $finance['recommend_qrcode']; ?></th>
                            	       <th style="width: 30%;text-align: center;"><?php echo $finance['account_recommend']; ?>ID</th>
                            	       <th><?php echo $finance['recommend_link']; ?></th>
                            	   </tr>
                            	   <tr class="mode-table-item">
                            	       <td class="mode-table-img" id="qrcode" style="background-color:#FFFFFF;padding-left: 10px;padding-top: 10px;padding-bottom: 10px;"></td>
                            	       <td class="mode-table-id" style="vertical-align: middle;"><div style="margin: 0 auto;"><?php echo $data['introId']; ?></div></td>
                            	       <td style="vertical-align: middle;">
                            	           <div class="mode-table-link">
                            	               <div style="overflow: hidden; text-overflow:ellipsis; white-space: nowrap;"><?php echo $data['introurl']; ?></div>
                            	               <input type="hidden" readonly id="introUrl" value="<?php echo $data['introurl']; ?>" />
                            	               <button id="copy"><?php echo $finance['recommend_copy']; ?></button>
                            	           </div>
                            	       </td>
                            	   </tr>
                            </table>
                        </div>


                        <div class="col-xs-12 recommend-pep">
                            <div class="col-xs-12 recommend-pep-tetil">
                                <h2><?php echo $finance['recommend_already']; ?></h2>
                            </div>
                            <div class="col-xs-12 recommend-pep-num">
                                <p><?php echo $finance['recommend_already']; ?><strong><?php echo $data['introCount']; ?></strong></p>
                            </div>
                        </div>


                        <div class="col-xs-12 income-data-wrap">
                            <div class="col-xs-12 account-tetil">
                                <div class="text-left"><?php echo $finance['recommend_income']; ?></div>
                            </div>
                            <div class="col-xs-12 income-data-tabel">
                                <table class="col-xs-12">
                                    <tr class="income-table-header">
                                        <th><?php echo $finance['recommend_coin']; ?></th>
                                        <th style="width:100px;"><?php echo $finance['recommend_number']; ?></th>
                                        <th style="margin-left: 25px;"><?php echo $finance['recommend_note']; ?></th>
                                        <th><?php echo $finance['recommend_time']; ?></th>
                                    </tr>
                                    <!--<?php if (is_array($data['record'])) { $count=count($data['record']);foreach ($data['record'] as $item) {  if ($item['shortName'] == 'F1') { ?>
                                    <tr class="income-table-item">
                                        <td><?php echo $item['shortName']; ?></td>
                                        <td><?php echo $item['amount']; ?></td>
                                    </tr>
                                    <?php }  } } ?>-->
                                    <?php if (is_array($list)) { $count=count($list);foreach ($list as $t) { ?>
                                    <tr class="income-table-item">
	                                    <td><?php if ($t['coin_id'] == 37) { ?>CC<?php } ?></td>
	                                    <td><?php echo $t['value']; ?></td>
	                                    <td style="padding: 0 20px;"><?php echo $t['note']; ?></td>
	                                    <td><?php echo date('Y-m-d H:i',$t['inputtime']); ?></td>
                                    </tr>
                                    <?php } } ?>
                                </table>
                            </div>
                        </div>

                        <div class="col-xs-12 financial-data-text">
                            <div class="col-xs-12 account-tetil">
                                <div class="text-left"><?php echo $finance['recommend_rule']; ?></div>
                            </div>
                            <div class="financial-text-box" style="margin-top:50px;">
                                <p style="color: #FFFFFF;"><?php echo $finance['recommend_rule1']; ?></p>
                                <p style="color: #FFFFFF;"><?php echo $finance['recommend_rule2']; ?></p>
                                <p style="color: #FFFFFF;"><?php echo $finance['recommend_rule3']; ?></p>
                                <p style="color: #FFFFFF;"><?php echo $finance['recommend_rule4']; ?></p>
                                <p style="color: #FFFFFF;"><?php echo $finance['recommend_rule5']; ?></p>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/jquery.qrcode.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/custom.js" type="text/javascript" charset="utf-8"></script>
    <script>
        //复制推广链接到粘贴板
        $('#copy').click(function(){
            var content = $('#introUrl').val();
            var oInput = document.createElement('input');
            oInput.value = content;
            document.body.appendChild(oInput);
            oInput.select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
            oInput.className = 'oInput';
            oInput.style.display='none';
            util.layerAlert('',util.getLan('finance.tips.45'),1,function(){});
        })
        //生成二维码
        $('#qrcode').html("").qrcode({text: $('#introUrl').val(), width: "80", height: "80", render: "canvas"});
    </script> </div>
<?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>

