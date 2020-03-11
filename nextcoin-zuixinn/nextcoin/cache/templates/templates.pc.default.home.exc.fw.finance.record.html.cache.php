<?php if ($fn_include = $this->_include("header.html")) include($fn_include);  if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
        <div class="container" style="min-height: 800px;padding-bottom: 60px;">
            <div class="row">
                <div class="col-xs-12 bind-header">
                    <div class="col-xs-12 bind-header-tetil">
                        <h2><?php echo $finance['account_record']; ?></h2>
                    </div>
                    <div class="col-xs-12 bind-header-nav">
                        <ol class="breadcrumb">
                          <li><a href="/index.php?s=exc&c=financeController"><?php echo $finance['account_center']; ?></a></li>
                          <li class="active"><?php echo $finance['account_record']; ?></li>
                        </ol>
                    </div>
                </div>

                <div class="col-xs-12 calendar">
                    <!--<div class="col-xs-12 calendar-header">
                        <div class="col-xs-6 calendar-header-nav">
                            <select id="recordType" style="background: #27262E;border: 1px solid #474652;">
                            		<?php if (is_array($data['filters'])) { $count=count($data['filters']);foreach ($data['filters'] as $filter) {  if ($data['select'] == $filter['value']) { ?>
                                <option selected value="/index.php?s=exc&c=financeController&m=record&<?php echo $filter['canshu']; ?>"><?php echo $filter['value']; ?></option>
                                <?php } else { ?>
                                <option value="/index.php?s=exc&c=financeController&m=record&<?php echo $filter['canshu']; ?>"><?php echo $filter['value']; ?></option>
                                <?php }  } } ?>
                            </select>
                            
                            <input type="hidden" value="1" id="recordType" />
                            <input type="hidden" id="datetype">
                        </div>
                        <div class="col-xs-6 calendar-header-date">
                             <input size="16" type="text" placeholder="<?php echo $finance['withdraw_chioce']; ?>" id="begindate" value="<?php echo $data['begindate']; ?>}" class="form_datetime"/> - 
                             <input size="16" type="text" placeholder="<?php echo $finance['withdraw_chioce']; ?>" id="enddate"  value="<?php echo $data['enddate']; ?>}" class="form_datetime"/>
                        </div>
                    </div>-->

                    <div class="col-xs-12 calenda-table-wrap">
                        <table class="col-xs-12  calenda-table">
                                <tr class=" calenda-table-header">
                                    <th><?php echo $finance['record_time']; ?></th>
                                    <th><?php echo $finance['account_coin']; ?></th>
                                    <th><?php echo $finance['record_type']; ?></th>
                                    <th><?php echo $finance['record_number']; ?></th>
                                   <!-- <th><?php echo $finance['record_fee']; ?></th>-->
                                    <th><?php echo $finance['record_status']; ?></th>
                                </tr>
                                <?php if ($data) {  if (is_array($data)) { $count=count($data);foreach ($data as $log) { ?>
                                <tr class=" calenda-table-item">
                                    <td><?php echo date('Y-m-d H:i:s',intval($log['createDate']/1000)); ?></td>
                                    <td><?php echo $log['relationCoinName']; ?></td>
                                    <td><?php echo $log['operationDesc']; ?></td>
                                    <td><?php echo $log['amount']; ?></td>
                                    <td><?php echo $log['statusDesc']; ?></td>
                                    <!--<td><?php echo sprintf('%.3f',$log['famount']); ?></td>
                                    <td><?php echo sprintf('%.3f',$log['ffees']); ?></td>
                                    <td><?php echo $log['fstatus_s']; ?></td>-->
                                </tr>
                                <?php } }  } ?>
                        </table>
                        <div class="pagination-wrap">
                            <nav aria-label="Page navigation" class="pagination">
                                <ul>
                                    <?php echo $pagin; ?>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>

		<script src="<?php echo HOME_THEME_PATH; ?>exc/js/bootstrap-datetimepicker.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
//          $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd',minView:2,autoclose: true});


            $(".calendar-header-nav button").on({
                click:function(){
                    $(".calendar-header-nav button").removeClass("active");
                    $(this).addClass("active");
                }
            })
            
            $("#type1").click(function(){
            		$("#recordType").val(1);
            });
            
            $("#type2").click(function(){
            		$("#recordType").val(2);
            });
            
            $.getUrlParam = function (name) {
		           var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		           var r = window.location.search.substr(1).match(reg);
		             if (r != null) return unescape(r[2]); return null;
			}
	        	var begindate = $.getUrlParam('begindate');
	        	if(begindate !== "" || begindate!=="null" || begindate!==null  || begindate!== undefined){
	        		$("#begindate").val(begindate);
	        	}
	        	var enddate = $.getUrlParam('enddate');
	        	if('' != enddate || 'null' != enddate || null != enddate || undefined != enddate || 'undefined' != enddate){
	        		$("#enddate").val(enddate);
	        	}
            
        </script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/layer/bitDate.js" type="text/javascript" charset="utf-8"></script>
        <link rel="stylesheet" href="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/layer/skin/bitdate/bitDate.css">
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/finance/account.record.js" type="text/javascript" charset="utf-8"></script>
<?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>