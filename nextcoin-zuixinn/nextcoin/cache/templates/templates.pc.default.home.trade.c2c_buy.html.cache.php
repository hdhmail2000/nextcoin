<?php if ($fn_include = $this->_include("header.html")) include($fn_include); ?>
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
			        </style>
                    
                    <!--交易列表-->

                    <div class="col-xs-12 merchandise-table-wrap">
                        <table class="col-xs-12 merchandise-table">
                            <colgroup style="width: 20%;"></colgroup>
                            <colgroup></colgroup>
                            <colgroup></colgroup>
                            <colgroup></colgroup>
                            <colgroup></colgroup>
                            <colgroup></colgroup>
                                <thead>
                                    <tr class="merchandise-table-tr">
                                        <th>商家</th>
                                        <th>数量</th>
                                        <th>限量</th>
                                        <th>单价</th>
                                        <th>支付方式</th>
                                        <th style="text-align: center;">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                		<?php if (is_array($data)) { $count=count($data);foreach ($data as $k=>$v) { ?>
                                    <tr class="merchandise-table-tr" style="position: relative;">
                                        <td>
                                            <span><?php echo mb_substr($v['author'],0,1); ?></span>
                                            <font style="font-size: 14px;text-overflow:ellipsis; white-space: nowrap;display: inline-block;width: 40px;"><?php echo $v['author']; ?></font>
                                            <font style="font-size: 14px;display: inline-block;"></font>
                                        </td>
                                        <td><?php echo $v['order_volume']; ?>&nbsp;<?php echo $v['symbol_name']; ?></td>
                                        <td><?php echo $v['min_value']; ?>-<?php echo $v['max_value']; ?>&nbsp;<?php echo $v['symbol_name']; ?></td>
                                        <td>
                                            <font style="font-size: 14px;font-weight: 500;" class="success"><?php echo $v['order_price']; ?> CNY</font>
                                        </td>
                                        <td>
	                                        	<!--<div style="display: none;">-->
	                            	        		<?php $arr = explode(",",$v['pay_type']) ?>
	                            	        		<!--</div>-->
                                            <?php if (is_array($arr)) { $count=count($arr);foreach ($arr as $k1=>$v1) {  if ($v1 == "银行卡") { ?>
	                            	        		<img src="<?php echo HOME_THEME_PATH; ?>c2c/img/icon_10.png" alt="" />
	                            	        		<?php } else if ($v1 == "支付宝") { ?>
	                            	        		<img src="<?php echo HOME_THEME_PATH; ?>c2c/img/icon_11.png" alt="" />
	                            	        		<?php } else if ($v1 == "微信") { ?>
	                            	        		<img src="<?php echo HOME_THEME_PATH; ?>c2c/img/icon_12.png" alt="" />
	                            	        		<?php }  } } ?>
                                        </td>
                                        <td style="text-align: center;">
                                            <a href="###" class="btn  btn-sty" onclick="_show($(this))">购买</a>
                                            <div class="clearfix merchandise-table-box" >
                                                <div class="col-xs-12 mall-form">
                                                    <div class="col-xs-12">
                                                        <div class="left">
                                                            <span class="left" style="margin: 0;"><?php echo mb_substr($v['author'],0,1); ?></span>
                                                            <div class="mall-form-use">
                                                                <p class="sky use-name" style="font-size: 16px;"><?php echo $v['author']; ?></p>
                                                                <p style="font-size: 12px;">数量 <?php echo $v['order_volume']; ?>&nbsp;<?php echo $v['symbol_name']; ?></p>
                                                            </div>
                                                        </div>
                                                        <div class="left mall-form-num">
                                                            <p class="success all-num"><?php echo $v['order_price']; ?> CNY</p>
                                                            <p style="font-size: 12px;"><?php echo $v['min_value']; ?>-<?php echo $v['max_value']; ?>&nbsp;<?php echo $v['symbol_name']; ?></p>
                                                        </div>
                                                        <div class="left mall-form-data">
                                                            <label for="">
                                                                <input type="text" id="pp_<?php echo $v['id']; ?>" oninput="formatMoneyWith6digts2(this);trade_fanwei($(this),<?php echo $v['min_value']; ?>,<?php echo $v['max_value']; ?>,<?php echo $v['order_volume']; ?>,<?php echo $v['order_price']; ?>,'p_<?php echo $v['id']; ?>');" placeholder="请输入购买数量" style="padding-right: 52px;"/>
                                                                <font><?php echo $v['symbol_name']; ?></font>
                                                            </label>
                                                            <i class="iconfont icon-jiaohuan"></i>
                                                            <label for="">
                                                                <input type="text" readonly="readonly" id="p_<?php echo $v['id']; ?>" style="padding-right: 52px;"/>
                                                                <font>CNY</font>
                                                            </label>
                                                        </div>
                                                        <div class="left">
                                                            <label for="" class="mall-form-data">
                                                                <input type="password" id="psw_<?php echo $v['id']; ?>" placeholder="请输入交易密码"/>
                                                                <input type="hidden" id="trade_<?php echo $v['id']; ?>" value="<?php echo $v['id']; ?>"/>
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 mall-list-wrap">
                                                        <ul class="left mall-list">
                                                        		<?php if (is_array($arr)) { $count=count($arr);foreach ($arr as $k1=>$v1) {  if ($v1 == "银行卡") { ?>
                                                            <li class="mall-list-item">
                                                                <img src="<?php echo HOME_THEME_PATH; ?>c2c/img/icon_10.png" alt="" />
                                                                <font>银行卡</font>
                                                            </li>
                                                            <?php } else if ($v1 == "支付宝") { ?>
                                                            <li class="mall-list-item">
                                                                <img src="<?php echo HOME_THEME_PATH; ?>c2c/img/icon_11.png" alt="" />
                                                                <font>支付宝</font>
                                                            </li>
                                                            <?php } else if ($v1 == "微信") { ?>
                                                            <li class="mall-list-item">
                                                                <img src="<?php echo HOME_THEME_PATH; ?>c2c/img/icon_12.png" alt="" />
                                                                <font>微信</font>
                                                            </li>
                                                            <?php }  } } ?>
                                                        </ul>
                                                        <div class="right mall-list-btn">
                                                            <button onclick="_hide($(this))">取消</button>
                                                            <a href="###" onclick="trade_buy('pp_<?php echo $v['id']; ?>','p_<?php echo $v['id']; ?>','psw_<?php echo $v['id']; ?>','trade_<?php echo $v['id']; ?>');" class="btn btn-danger">购买</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <?php } } ?>
                                    	</tbody>
	                        </table>
	                    </div>
						<div class="col-xs-12" style="text-align: center; margin-top: 80px;">
                            <ul class="pagination" id="pages">
                                <!--<li><a href="#">&laquo;</a></li>-->
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

        <?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>

        <script type="text/javascript">
                   function _switch(e){
                       var _this = $(e.target);
                       $(e.target).parent().siblings().children("a").removeClass("active");
                       _this.addClass("active");
                   }


                   function _hide(e){
                       e.parents(".merchandise-table-box").hide();
                   }

                   function _show(e){
                       $(".merchandise-table-box").hide();
                       e.siblings(".merchandise-table-box").show();
                   }
                   
                   function trade_fanwei(e,min,max,order_volume,order_price,id){
               			if(e.val() > order_volume){
               				layer.msg("超出余量");
               				e.val("");
               			}
               			$("#"+id).val((e.val()*order_price).toFixed(2));
               }
               
               function trade_buy(volume_id,price_id,psw_id,trade_id){
               		if($("#"+volume_id).val() == "" || $("#"+volume_id).val() == 0 || $("#"+volume_id).val() == null
               		  || $("#"+price_id).val() == "" || $("#"+price_id).val() == 0 || $("#"+price_id).val() == null
               		  || $("#"+psw_id).val() == "" || $("#"+psw_id).val() == 0 || $("#"+psw_id).val() == null){
               			layer.msg("填写内容不能为空");
               		} else {
               			layer.load(1);
               			var deal_psw = $("#"+psw_id).val();
               			var volume = $("#"+volume_id).val();
               			var price = $("#"+price_id).val();
               			var trade_id1 = $("#"+trade_id).val();
               			$.ajax({
               				type:"post",
               				url:"/index.php?s=trade&c=home&m=trade_buy",
               				data:{deal_psw:deal_psw,volume:volume,price:price,trade_id:trade_id1},
               				dataType:"json",
               				success:function(data){
               					if(data.code == 1){
               						layer.msg(data.msg);
               						window.location.href = "/index.php?s=trade&c=home&m=buyOrder&orderId="+data.data;
               					} else {
               						layer.closeAll('loading');
               						layer.msg(data.msg);
               					}
               				},
               				error:function(){
               					layer.msg("买入接口出错");
               				}
               			});
               		}
               }
               
               function formatMoneyWith6digts2( obj ){
					var p1 = /[^\d\.]/g;	// 过滤非数字及小数点 /g :所有范围中过滤
					var p2 = /(\.\d{6})\d*$/g;
					var p4 = /(\.)(\d*)\1/g;
					obj.value = obj.value.replace(p1, "").replace(p2, "$1").replace(p4,"$1$2");
					
				 
					obj.value=obj.value.replace(/[^0-9.]/g, '');
				 
				 
					// fix bug: many char'.'
					var p5 = /\.+/g;	//多个点的话只取1个点，屏蔽1....234的情况
					obj.value = obj.value.replace(p5, ".")
				 
					var p6 = /(\.+)(\d+)(\.+)/g; //屏蔽1....234.的情况
					obj.value = obj.value.replace(p6, "$1$2")// 屏蔽最后一位的.
					// end fix bug: many char'.'
				}
        </script>
        
        <script type="text/javascript">
			var pagesize = <?php echo $pagecount; ?>;
			var str = "";
			var page = <?php echo $page; ?>;
			if(pagesize){
				for(var i=1;i<=pagesize;i++){
					if(page == i){
						str += '<li><a href="/index.php?s=trade&c=home&m=c2cBuy&id=<?php echo $id; ?>&page='+ i + '"' +' class="active">'+ i +'</a></li>';
					} else {
						str += '<li><a href="/index.php?s=trade&c=home&m=c2cBuy&id=<?php echo $id; ?>&page='+ i + '"' +'>'+ i +'</a></li>';
					}
				}
				$("#pages").html(str);
			}
		</script>
        
    </body>
</html>
