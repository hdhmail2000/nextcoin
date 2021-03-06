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

                    <div class="col-xs-12 release-table">
                        <table class="col-xs-12">
                            	<colgroup></colgroup>
                            <colgroup></colgroup>
                            <colgroup></colgroup>
                            <colgroup></colgroup>
                            <colgroup></colgroup>
                            <colgroup></colgroup>
                            <colgroup></colgroup>
                            <thead class="release-table-hd">
                                <tr>
                                    <th>交易类型</th>
                                    <th>交易量</th>
                                    <th>成交量</th>
                                    <th>单价</th>
                                    <th>时间</th>
                                    <th>状态</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody class="release-table-tb">
                            		<?php if (is_array($data)) { $count=count($data);foreach ($data as $k=>$t) { ?>
                                <tr class="release-table-item">
                                    <td><?php if ($t['deal_type'] == 1) { ?>卖出<?php } else { ?>买入<?php } ?></td>
                                    <td><?php echo $t['trade_total']; ?>&nbsp;<?php echo $t['symbol_name']; ?></td>
                                    <td><?php echo $t['success_total']; ?>&nbsp;<?php echo $t['symbol_name']; ?></td>
                                    <td><?php echo $t['order_price']; ?> CNY</td>
                                    <td><?php echo dr_date($t['inputtime']); ?></td>
                                    <td>
                                    		<?php if ($t['status'] == 9) { ?>
                                        <p class="danger">发布中</p>
                                        <?php } else if ($t['status'] == 10) { ?>
                                        <p class="hint">已取消</p>
                                        <?php } else if ($t['status'] == 0) { ?>
                                        <p class="success">已完成</p>
                                        <?php }  if ($t['deal_type'] == 1) { ?>
                                        <a href="/index.php?s=trade&c=home&m=releaseSell&id=<?php echo $t['id']; ?>">查看详情</a>
                                        <?php } else { ?>
                                        <a href="/index.php?s=trade&c=home&m=releaseBuy&id=<?php echo $t['id']; ?>">查看详情</a>
                                        <?php } ?>
                                    </td>
                                    <td>
                                    		<?php if ($t['status'] == 9) { ?>
                                        <button type="button" data-toggle="modal" data-target="#myModal-<?php echo $t['id']; ?>">下线</button>
                                        <?php } ?>
                                    </td>
                                </tr>
                                <?php } } ?>
                                <!--<tr class="release-table-item">
                                    <td>买入</td>
                                    <td>14.000 BTC</td>
                                    <td>3.000 BTC</td>
                                    <td>42,016 CNY</td>
                                    <td>2018-5-14 14：25</td>
                                    <td>
                                        <p class="success">已完成</p>
                                        <a href="/c2c.php?c=home&m=releaseBuy">查看详情</a>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr class="release-table-item">
                                    <td>卖出</td>
                                    <td>14.000 BTC</td>
                                    <td>3.000 BTC</td>
                                    <td>42,016 CNY</td>
                                    <td>2018-5-14 14：25</td>
                                    <td>
                                        <p class="hint">已取消</p>
                                        <a href="/c2c.php?c=home&m=releaseSell">查看详情</a>
                                    </td>
                                    <td></td>
                                </tr>-->
                            </tbody>
                        </table>

                        <div class="col-xs-12 release-table-img" style="display: none;">
                            <img src="/statics/c2c/img/icon_13.png" alt="" />
                            <p>还没发布任何交易！</p>
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
        </div>
		<?php if (is_array($data)) { $count=count($data);foreach ($data as $k=>$t) { ?>
        <div class="modal fade" id="myModal-<?php echo $t['id']; ?>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="background: #27262E;">
            <div class="modal-dialog" style="background: #27262E;">
                <div class="modal-content"  style="background: #27262E;">
                    <div class="modal-header" style="border: 0;">
                        <button style="text-shadow:none;opacity: 1;color: #6B6C7E;" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body" style="text-align: center;font-size: 24px; background: #27262E;margin-top: 20px;">是否下线</div>
                    <div class="modal-footer" style="margin-top: 20px; text-align: center;background: #27262E;border: 0;box-shadow:none;">
                        <button type="button" class="btn btn-default" data-dismiss="modal" style="border: 0; padding: 0; background: transparent;line-height: 48px;margin-right: 20px;box-shadow: none;color: #666;margin-bottom: 15px;">关闭</button>
                        <a href="###" onclick="underline(<?php echo $t['id']; ?>);" class="btn btn-primary" style="padding:0;background: #3473C9;width: 100px; height: 38px;line-height: 38px;margin-bottom: 15px;">确定</a>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
        <?php } }  if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>

        <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script type="text/javascript">
        	       function _switch(e){
                       var _this = $(e.target);
                       $(".merchandise-header .left a").removeClass("active");
                       _this.addClass("active");
                   }
        	       function underline(id){
        	       		$.ajax({
        	       			type:"post",
        	       			url:"/index.php?s=trade&c=home&m=underline&id="+id,
        	       			dataType:"json",
        	       			success:function(data){
        	       				if(data.code == 1){
        	       					layer.msg(data.msg);
        	       					window.location.href = "/index.php?s=trade&c=home&m=c2cReleaseList";
        	       				}
        	       			},
        	       			error:function(){
        	       				layer.msg("下线接口错误");
        	       			}
        	       		});
        	       }
        </script>
        
		<script type="text/javascript">
			var pagesize = <?php echo $pagecount; ?>;
			var str = "";
			var page = <?php echo $page; ?>;
			if(pagesize){
				for(var i=1;i<=pagesize;i++){
					if(page == i){
						str += '<li><a href="/index.php?s=trade&c=home&m=c2cReleaseList&page='+ i + '"' +' class="active">'+ i +'</a></li>';
					} else {
						str += '<li><a href="/index.php?s=trade&c=home&m=c2cReleaseList&page='+ i + '"' +'>'+ i +'</a></li>';
					}
				}
				$("#pages").html(str);
			}
		</script>
	</body>
</html>
