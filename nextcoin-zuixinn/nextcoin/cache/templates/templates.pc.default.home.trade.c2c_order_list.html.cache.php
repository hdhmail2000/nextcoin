<?php if ($fn_include = $this->_include("header.html")) include($fn_include); ?>

        <style type="text/css">
                   .release-order-type>.left{
                       margin-right: 30px;
                       position: relative;
                   }
                   .release-order-type .left img{
                       margin-left: 10px;
                   }
        </style>
		<div class="container">
            <div class="row">
                <div class="col-xs-12 merchandise-wrap">
                    <?php if ($fn_include = $this->_include("nav.html")) include($fn_include); ?>
					<style type="text/css">
			              .pagination {
			                display: inline-block;
			                padding-left: 0;
			                margin: 20px 0;
			                border-radius: 4px;
			            }
			            .pagination>li {
			                display: inline;
			            }
			            .pagination>li>a, .pagination>li>span {
			                position: relative;
			                float: left;
			                padding: 6px 12px;
			                margin-left: -1px;
			                line-height: 1.42857143;
			                color: #666;
			                text-decoration: none;
			                background-color: #27262E;
			                border: 1px solid rgba(255,255,255,.1);
			                transition: all .3s ease;
			            }
			            .pagination>li:first-child>a, .pagination>li:first-child>span {
			                margin-left: 0;
			                border-top-left-radius: 4px;
			                border-bottom-left-radius: 4px;
			            }
			            .pagination>li>a:focus, .pagination>li>a:hover, .pagination>li>span:focus, .pagination>li>span:hover {
			                z-index: 2;
			                color: #fff;
			                background-color: #337ab7;
			                /*border-color: #ddd;*/
			            }
			            .pagination>li>a.active{
			                color: #fff;
			                background-color: #337ab7;
			            }
			            .pagination>li:last-child>a, .pagination>li:last-child>span {
			                border-top-right-radius: 4px;
			                border-bottom-right-radius: 4px;
			            }
			            #upload:hover{
			                cursor: pointer;
			            }
			        </style>

			        <div class="col-xs-12 merchandise-header">
                        <div class="left">
                            <div class="left">
		                         <p>订单类型</p>
		                         <ul class="clearfix">
		                             <li>
		                                 <a href="/index.php?s=trade&c=home&m=c2cMyorder&type=1" <?php if ($type == 1) { ?> class="active" <?php } ?>>买入</a>
		                              </li>
		                              <li>
		                                 <a href="/index.php?s=trade&c=home&m=c2cMyorder&type=2" <?php if ($type == 2) { ?> class="active" <?php } ?>>卖出</a>
		                              </li>
		                         </ul>
		                   </div>
                        </div>
                    </div>


                    <div class="col-xs-12 release">
                        <ul class="col-xs-12 release-order">
                        		<?php if ($type == 1) {  if (is_array($mr)) { $count=count($mr);foreach ($mr as $k=>$v) { ?>
                            <li class="release-order-item">
                                <div class="release-order-header">
                                    <span><?php echo dr_date($v['order_time']); ?></span>
                                    <span>订单号：<?php echo $v['sn']; ?></span>
                                    <span>类型：买入</span>
                                    <span>卖家姓名：<?php echo $v['sell_username']; ?></span>
                                    <span>币名：<?php echo $v['symbolName']; ?></span>
                                </div>
                                <div class="release-order-table">
                                    <table>
                                        <colgroup style="width: 35%;"></colgroup>
                                        <colgroup></colgroup>
                                        <colgroup></colgroup>
                                        <colgroup></colgroup>
                                        <colgroup style="width: 10%;"></colgroup>
                                            <thead class="release-order-th">
                                                <tr>
                                                    <th>收款信息</th>
                                                    <th>价格</th>
                                                    <th>数量</th>
                                                    <th>交易金额</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr class="release-order-tr">
                                                    <td style="text-align: left;">
                                                    		<?php if ($v['bank']) { ?>
                                                        <span style="margin-right: 33px;"><?php echo $v['bank']['khm']; ?></span>
                                                        <span><?php echo $v['bank']['zhhm']; ?></span>
                                                        <span>(<?php echo $v['bank']['khh']; ?>)</span>
                                                        <?php } ?>
                                                    </td>
                                                    <td><?php echo $v['price']; ?></td>
                                                    <td><?php echo $v['order_volume']; ?></td>
                                                    <td><?php echo $v['order_price']; ?></td>
                                                    <td style="text-align: right;">
                                                    		<?php if ($v['order_status'] == 1) { ?>
                                                        <span class="danger">未付款</span>
                                                        <?php } else if ($v['order_status'] == 2) { ?>
                                                        <span class="danger">待卖方确认</span>
                                                        <?php } else if ($v['order_status'] == 3) { ?>
                                                        <span class="hint">已完成</span>
                                                        <?php } else if ($v['order_status'] == 4) { ?>
                                                        <span class="danger">申诉中</span>
                                                        <?php } else if ($v['order_status'] == 9) { ?>
                                                        <span class="hint">已取消</span>
                                                        <?php } ?>
                                                    </td>
                                                </tr>
                                            </tbody>
                                    </table>
                                </div>



                                <div class="clearfix release-order-type" style="min-height: 90px;">
                                		<?php if ($v['alipay']) { ?>
                                    <div class="left">
                                        <span>支付宝</span>
                                        <img src="<?php echo $v['alipay']['qrcode']; ?>" alt="">
                                    </div>
                                    <?php }  if ($v['weixin']) { ?>
                                     <div class="left">
                                        <span>微信</span>
                                        <img src="<?php echo $v['weixin']['qrcode']; ?>" alt="">
                                    </div>
                                    <?php } ?>
                                    <div class="right">
                                    		<?php if ($v['order_status'] == 1) { ?>
                                        <button class="release-order-fill" type="button" data-toggle="modal" data-target="#myModal_01-<?php echo $v['id']; ?>">取消交易</button>
                                        <button class="release-order-btn" data-toggle="modal" data-target="#myModal-<?php echo $v['id']; ?>">我已付款</button>
                                        <?php } else if ($v['order_status'] == 2) { ?>
                                        <button type="button" class="release-order-fill" data-toggle="modal" data-target="#myModal_00-<?php echo $v['id']; ?>">申诉</button>
                                        <?php } else if ($v['order_status'] == 3) { ?>
                                        <!--<span class="hint">已完成</span>-->
                                        <?php } else if ($v['order_status'] == 4) { ?>
                                        <!--<span class="danger">申诉中</span>-->
                                        <?php } else if ($v['order_status'] == 9) { ?>
                                        <!--<span class="hint">已取消</span>-->
                                        <?php } ?>
                                    </div>
                                </div>

                                <div class="release-order-footer">
                                    <p>付款参考号：<span><?php echo $v['pay_no']; ?></span></p>
                                </div>
                            </li>
                            <?php } }  }  if ($type == 2) {  if (is_array($mc)) { $count=count($mc);foreach ($mc as $k=>$v) { ?>
                            <li class="release-order-item">
                                <div class="release-order-header">
                                    <span><?php echo dr_date($v['order_time']); ?></span>
                                    <span>订单号：<?php echo $v['sn']; ?></span>
                                    <span>类型：卖出</span>
                                    <span>买家姓名：<?php echo $v['buy_username']; ?></span>
                                    <span>币名：<?php echo $v['symbolName']; ?></span>
                                </div>
                                <div class="release-order-table">
                                    <table>
                                        <colgroup style="width: 35%;"></colgroup>
                                        <colgroup></colgroup>
                                        <colgroup></colgroup>
                                        <colgroup></colgroup>
                                        <colgroup style="width: 10%;"></colgroup>
                                            <thead class="release-order-th">
                                                <tr>
                                                    <tr></tr>
                                                    <th>收款信息</th>
                                                    <th>价格</th>
                                                    <th>数量</th>
                                                    <th>交易金额</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr class="release-order-tr">
                                                    <td style="text-align: left;">
                                                    		<?php if ($v['bank']) { ?>
                                                        <span style="margin-right: 33px;"><?php echo $v['bank']['khm']; ?></span>
                                                        <span><?php echo $v['bank']['zhhm']; ?></span>
                                                        <span>(<?php echo $v['bank']['khh']; ?>)</span>
                                                        <?php } ?>
                                                    </td>
                                                    <td><?php echo $v['price']; ?></td>
                                                    <td><?php echo $v['order_volume']; ?></td>
                                                    <td><?php echo $v['order_price']; ?></td>
                                                    <td style="text-align: right;">
                                                        <?php if ($v['order_status'] == 1) { ?>
                                                        <span class="danger">未付款</span>
                                                        <?php } else if ($v['order_status'] == 2) { ?>
                                                        <span class="danger">待卖方确认</span>
                                                        <?php } else if ($v['order_status'] == 3) { ?>
                                                        <span class="hint">已完成</span>
                                                        <?php } else if ($v['order_status'] == 4) { ?>
                                                        <span class="danger">申诉中</span>
                                                        <?php } else if ($v['order_status'] == 9) { ?>
                                                        <span class="hint">已取消</span>
                                                        <?php } ?>
                                                    </td>
                                                </tr>
                                            </tbody>
                                    </table>
                                </div>

                                <div class="clearfix release-order-type" style="min-height: 90px;">
                                    <?php if ($v['alipay']) { ?>
                                    <div class="left">
                                        <span>支付宝</span>
                                        <img src="<?php echo $v['alipay']['qrcode']; ?>" alt="">
                                    </div>
                                    <?php }  if ($v['weixin']) { ?>
                                     <div class="left">
                                        <span>微信</span>
                                        <img src="<?php echo $v['weixin']['qrcode']; ?>" alt="">
                                    </div>
                                    <?php } ?>
                                    <div class="right">
                                        <?php if ($v['order_status'] == 1) { ?>
                                        <button class="release-order-fill" type="button" data-toggle="modal" data-target="#myModal_01-<?php echo $v['id']; ?>">取消交易</button>
                                        <?php } else if ($v['order_status'] == 2) { ?>
                                        <button class="release-order-btn" data-toggle="modal" data-target="#myModal_02-<?php echo $v['id']; ?>">我已收款</button>
                                        <?php } else if ($v['order_status'] == 3) { ?>
                                        <!--<span class="hint">已完成</span>-->
                                        <?php } else if ($v['order_status'] == 4) { ?>
                                        <!--<span class="danger">申诉中</span>-->
                                        <?php } else if ($v['order_status'] == 9) { ?>
                                        <!--<span class="hint">已取消</span>-->
                                        <?php } ?>
                                    </div>
                                </div>

                                <div class="release-order-footer">
                                    <p>付款参考号：<span><?php echo $v['pay_no']; ?></span></p>
                                </div>
                            </li>
                            <?php } }  } ?>

                        </ul>
                        <div class="col-xs-12" style="text-align: center; margin-top: 80px;">
                            <ul class="pagination" id="pages">
                                <!--<li><a href="#" class="active">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">5</a></li>-->
                                <!--<li><a href="#">&raquo;</a></li>-->
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<?php if ($type == 1) {  if (is_array($mr)) { $count=count($mr);foreach ($mr as $k=>$v) { ?>
        <div class="modal fade" id="myModal-<?php echo $v['id']; ?>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="background: #27262E;display: none;">
            <div class="modal-dialog" style="background: #27262E;">
                <div class="modal-content"  style="background: #27262E;">
                    <div class="modal-header" style="border: 0;">
                        <button style="text-shadow:none;opacity: 1;color: #6B6C7E;" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body" style="text-align: center;font-size: 24px; background: #27262E;margin-top: 20px;">是否已付款</div>
                    <div class="modal-footer" style="margin-top: 20px; text-align: center;background: #27262E;border: 0;box-shadow:none;">
                        <button type="button" class="btn btn-default" data-dismiss="modal" style="border: 0; padding: 0; background: transparent;line-height: 48px;margin-right: 20px;box-shadow: none;color: #666;margin-bottom: 15px;">关闭</button>
                        <a href="###" onclick="mark_pay(<?php echo $v['id']; ?>);" class="btn btn-primary" style="padding:0;background: #3473C9;width: 100px; height: 38px;line-height: 38px;margin-bottom: 15px;">确定</a>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
        <?php } }  }  if ($type == 1) {  if (is_array($mr)) { $count=count($mr);foreach ($mr as $k=>$v) { ?>
        <div class="modal fade" id="myModal_01-<?php echo $v['id']; ?>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="background: #27262E;display: none;">
            <div class="modal-dialog" style="background: #27262E;">
                <div class="modal-content"  style="background: #27262E;">
                    <div class="modal-header" style="border: 0;">
                        <button style="text-shadow:none;opacity: 1;color: #6B6C7E;" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body" style="text-align: center;font-size: 24px; background: #27262E;margin-top: 20px;">是否取消交易</div>
                    <div class="modal-footer" style="margin-top: 20px; text-align: center;background: #27262E;border: 0;box-shadow:none;">
                        <button type="button" class="btn btn-default" data-dismiss="modal" style="border: 0; padding: 0; background: transparent;line-height: 48px;margin-right: 20px;box-shadow: none;color: #666;margin-bottom: 15px;">关闭</button>
                        <a href="###" onclick="cancel_order(<?php echo $v['id']; ?>);" class="btn btn-primary" style="padding:0;background: #3473C9;width: 100px; height: 38px;line-height: 38px;margin-bottom: 15px;">确定</a>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
        <?php } }  }  if ($type == 2) {  if (is_array($mc)) { $count=count($mc);foreach ($mc as $k=>$v) { ?>
        <div class="modal fade" id="myModal_01-<?php echo $v['id']; ?>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="background: #27262E;display: none;">
            <div class="modal-dialog" style="background: #27262E;">
                <div class="modal-content"  style="background: #27262E;">
                    <div class="modal-header" style="border: 0;">
                        <button style="text-shadow:none;opacity: 1;color: #6B6C7E;" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body" style="text-align: center;font-size: 24px; background: #27262E;margin-top: 20px;">是否取消交易</div>
                    <div class="modal-footer" style="margin-top: 20px; text-align: center;background: #27262E;border: 0;box-shadow:none;">
                        <button type="button" class="btn btn-default" data-dismiss="modal" style="border: 0; padding: 0; background: transparent;line-height: 48px;margin-right: 20px;box-shadow: none;color: #666;margin-bottom: 15px;">关闭</button>
                        <a href="###" onclick="cancel_order(<?php echo $v['id']; ?>);" class="btn btn-primary" style="padding:0;background: #3473C9;width: 100px; height: 38px;line-height: 38px;margin-bottom: 15px;">确定</a>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
        <?php } }  }  if ($type == 1) {  if (is_array($mr)) { $count=count($mr);foreach ($mr as $k=>$v) { ?>
        <div class="modal fade" id="myModal_00-<?php echo $v['id']; ?>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="background: #27262E;display: none;">
            <div class="modal-dialog" style="background: #27262E;">
                <div class="modal-content"  style="background: #27262E;">
                    <div class="modal-header" style="border: 0;">
                        <button style="text-shadow:none;opacity: 1;color: #6B6C7E;" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body" style="text-align: center;font-size: 24px; background: #27262E;margin-top: 20px;max-height: 500px;">
                        <h2 style="margin-bottom: 20px;">提交申诉信息</h2>
                        <div>
                            <div style="margin-bottom: 15px;">
                                <p style="font-size: 14px;text-align: left;margin-bottom: 15px;">申诉截图</p>
                                <input type="file" name="" accept="image/*" id="" value="" style="display: none;" onchange="_addImg(this,<?php echo $v['id']; ?>)"/>
                                <input type="hidden" name="imgUrl" id="imgUrl-<?php echo $v['id']; ?>" value="">
                                <img src="<?php echo HOME_THEME_PATH; ?>c2c/img/upload_20180816.png" alt="" onclick="_upload($(this))" id="upload-<?php echo $v['id']; ?>" style="width: 520px;height: 173px;object-fit: cover;"/>
                            </div>
                            <div>
                                <p style="font-size: 14px;text-align: left;margin-bottom: 15px;">详细描述</p>
                                <textarea name="" rows="" cols="" id="description-<?php echo $v['id']; ?>" style="background: transparent;width: 100%;padding: 10px 15px;line-height: 20px;font-size: 14px;text-align: left;border: 1px solid  #6B6C7E;border-radius: 4px;height: 100px;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="margin-top: 20px; text-align: center;background: #27262E;border: 0;box-shadow:none;">
                        <button type="button" class="btn btn-default" data-dismiss="modal" style="border: 0; padding: 0; background: transparent;line-height: 48px;margin-right: 20px;box-shadow: none;color: #666;margin-bottom: 15px;">关闭</button>
                        <a href="###" onclick="_ss(<?php echo $v['id']; ?>);" class="btn btn-primary" style="padding:0;background: #3473C9;width: 100px; height: 38px;line-height: 38px;margin-bottom: 15px;">确定</a>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
        <?php } }  }  if ($type == 2) {  if (is_array($mc)) { $count=count($mc);foreach ($mc as $k=>$v) { ?>
        <div class="modal fade" id="myModal_02-<?php echo $v['id']; ?>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="background: #27262E;display: none;">
            <div class="modal-dialog" style="background: #27262E;">
                <div class="modal-content"  style="background: #27262E;">
                    <div class="modal-header" style="border: 0;">
                        <button style="text-shadow:none;opacity: 1;color: #6B6C7E;" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body" style="text-align: center;font-size: 24px; background: #27262E;margin-top: 20px;">是否已收款</div>
                    <div class="modal-footer" style="margin-top: 20px; text-align: center;background: #27262E;border: 0;box-shadow:none;">
                        <button type="button" class="btn btn-default" data-dismiss="modal" style="border: 0; padding: 0; background: transparent;line-height: 48px;margin-right: 20px;box-shadow: none;color: #666;margin-bottom: 15px;">关闭</button>
                        <a href="###" onclick="mark_receved(<?php echo $v['id']; ?>);" class="btn btn-primary" style="padding:0;background: #3473C9;width: 100px; height: 38px;line-height: 38px;margin-bottom: 15px;">确定</a>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
        <?php } }  }  if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>
        <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>c2c/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript">
                   function _switch(e){
                       var _this = $(e.target);
                       $(".merchandise-header .left a").removeClass("active");
                       _this.addClass("active");
                   }
                   //申诉
                   function _ss(orderId){
                   		var imgUrl = $("#imgUrl-"+orderId).val();
                   		var description = $("#description-"+orderId).val();
                   		
                   		if(imgUrl == null || imgUrl == "" || description == null || description == ""){
                   			layer.msg("提交不能为空");
                   		} else {
                   			layer.load(1);
                   			$.ajax({
	                   			type:"post",
	                   			url:"/index.php?s=trade&c=home&m=shensu&orderId="+orderId,
	                   			data:{imgUrl:imgUrl,description:description},
	                   			dataType:"json",
	                   			success: function(data){
	                   				if(data.code == 1){
	                   					layer.msg(data.msg);
	                   					window.location.reload();
	                   				} else {
	                   					layer.closeAll('loading');
	                   					layer.msg(data.msg);
	                   				}
	                   			},
	                   			error: function(){
	                   				layer.closeAll('loading');
	                   				layer.msg("申诉接口出错");
	                   			}
	                   		});
                   		}
                   }
                   //取消订单
                   function cancel_order(id){
                   		$.ajax({
                   			type:"post",
                   			url:"/index.php?s=trade&c=home&m=cancel_order&id="+id,
                   			dataType:"json",
                   			success: function(data){
                   				if(data.code == 1){
                   					layer.msg(data.msg);
                   					window.location.reload();
                   				} else {
                   					layer.msg(data.msg);
                   				}
                   			},
                   			error: function(){
                   				layer.msg("取消订单接口出错");
                   			}
                   		});
                   }

                   //我已收款
                   function mark_receved(id){
                   		$.ajax({
                   			type:"post",
                   			url:"index.php?s=trade&c=home&m=mark_receved&id="+id,
                   			dataType:"json",
                   			success: function(data){
                   				if(data.code == 1){
                   					layer.msg(data.msg);
                   					window.location.reload();
                   				} else {
                   					layer.msg(data.msg);
                   				}
                   			},
                   			error: function(){
                   				layer.msg("我已收款接口出错");
                   			}
                   		});
                   }

                   //我已付款
                   function mark_pay(id){
                   		$.ajax({
                   			type:"post",
                   			url:"/index.php?s=trade&c=home&m=mark_pay&id="+id,
                   			dataType:"json",
                   			success: function(data){
                   				if(data.code == 1){
                   					layer.msg(data.msg);
                   					window.location.reload();
                   				} else {
                   					layer.msg(data.msg);
                   				}
                   			},
                   			error: function(){
                   				layer.msg("我已付款接口出错");
                   			}
                   		});
                   }
        </script>

        <script type="text/javascript">
			var pagesize = <?php echo $pagecount; ?>;
			var str = "";
			var page = <?php echo $page; ?>;
			var type = <?php echo $type; ?>;
			if(pagesize){
				for(var i=1;i<=pagesize;i++){
					if(page == i){
						str += '<li><a href="/index.php?s=trade&c=home&m=c2cMyorder&type='+ type +'&page='+ i + '"' +' class="active">'+ i +'</a></li>';
					} else {
						str += '<li><a href="/index.php?s=trade&c=home&m=c2cMyorder&type='+ type +'&page='+ i + '"' +'>'+ i +'</a></li>';
					}
				}
				$("#pages").html(str);
			}


			function _upload(x){
			    x.siblings("input").click();
			}

			function _addImg(obj,id){
			    var file = obj.files[0];
                    //判断类型是不是图片
                   /*  if(!/image\/\w+/.test(file.type)){
                            alert("请确保文件为图像类型");
                            return false;
                    }    */
                    var reader = new FileReader();
                    reader.readAsDataURL(file);

                //对读取到的图片编码
                    reader.onload = function(e){
                        console.log(e.target.result);
                        var imgBase64Data =encodeURIComponent(e.target.result);
                        $("#upload-"+id)[0].src=this.result;
						$("#imgUrl-"+id).val(this.result);
//                       var res = (this.result);
//                       var pos = imgBase64Data.indexOf("4")+4;
//                       imgBase64Data = imgBase64Data.substring(pos);
//                       $('#logo').val(imgBase64Data);
                    }
			}
		</script>

    </body>
</html>
