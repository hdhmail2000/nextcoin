<?php if ($fn_include = $this->_include("header.html")) include($fn_include);  if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
        

        <style type="text/css">
        	.js-swiper-container-enroll{
        	    height: 48px !important;
        	}
        </style>
        <div class="container-fluid">
            <div class="row" style="padding: 0 8px;">
<!--------------------------------- 公告 ---------------------->
                <div class="col-xs-12 announcement">
                    <span class="announcement-tetil" style="float: left;">
                        <i class="iconfont icon-gonggao"></i>
                        <?php echo $market['annoce']; ?>:
                    </span>
                    <span class="announcement-text">

                        <div class="swiper-container js-swiper-container-enroll swiper-container-vertical" style="display: inline-block;">
                            <div class="swiper-wrapper">
                            <?php $return = array();$list_temp = $this->list_tag("action=module module=help catid=9 num=1 order=inputtime_desc"); if ($list_temp) extract($list_temp); $count=count($return); if (is_array($return)) { foreach ($return as $key=>$t) { ?>
                                <div class="swiper-slide" style="text-align: left;-webkit-justify-content:flex-start;justify-content:flex-start;align-items: center;">
                                    <a href="<?php echo $t['url']; ?>" target="_blank" class="enroll-item" style="color: white;font-size: 16px;float: left;height: 50px;line-height: 50px;">
                                        <?php echo $t['title']; ?>
                                    </a>
                                </div>
                              <?php } } ?>
                            </div>
                        </div>


                    		<!--<?php $return = array();$list_temp = $this->list_tag("action=module module=help catid=9 num=1 order=inputtime_desc"); if ($list_temp) extract($list_temp); $count=count($return); if (is_array($return)) { foreach ($return as $key=>$t) { ?>
                            <a href="<?php echo $t['url']; ?>" style="color: #FFFFFF;" >
                                <?php echo $t['title']; ?>
                            </a>
                        	<?php } } ?>-->
                    </span>
                    <button class="iconfont icon-guanbi announcement-btn"></button>
                </div>


                <div class="col-xs-12 quotes">
 <!-------------------------------- 左悬浮窗 ------------------------>
                        <div class="quotes-window">
                            <div class="coin-list">
                                <div class="quotes-window-header">
                                   <?php if (!$USERNAME) { ?>
                                  <!--------- 未登录 ------->
                                    <p class="clearfix">
                                        <a href="/index.php?s=exc&c=userController&m=login"><?php echo $market['login']; ?></a>
                                        <?php echo $market['or']; ?>
                                        <a href="/index.php?s=exc&c=userController&m=register"><?php echo $market['reg']; ?></a>
                                        <?php echo $market['begin']; ?>
                                    </p>
                                  <?php } else { ?>
                                   <!--------- 已登录 -------->
                                    <dl class="clearfix">
                                    </dl>
                                    <?php } ?>
                                </div>
                                <div class="quotes-window-search-wrap clearfix">
                                    <p class="quotes-search-tetil"><?php echo $market['market']; ?></p>
                                    <!--<div class="quotes-search-text">
                                        <input type="text" name="" id="" value="" />
                                        <i class="iconfont icon-shousuo"></i>
                                    </div>
                                    <div class="quotes-search-btn">
                                        <i class="iconfont icon-qiehuan"></i>
                                        CNY
                                    </div>-->
                                </div>
                                <div class="quotes-window-nav-wrap">
                                    <ul class="quotes-window-nav clearfix coin-tab">
                                    <ul class="quotes-window-nav clearfix coin-tab">
                                    		<?php if (is_array($data['typeMap'])) { $count=count($data['typeMap']);foreach ($data['typeMap'] as $key=>$value) { ?>
                                        <li class="quotes-window-navbar">
                                            <button <?php if ($value == $data['tradeType']['buyShortName']) { ?> class="active"<?php } ?>><?php echo $value; ?></button>
                                        </li>
                                        <?php } } ?>
                                    </ul>
                                </div>
    <!--------------------------------- 悬浮窗列表 ---------------------->

                                <div class="quotes-window-list-wrap">
                                    <div class="quotes-list-header-wrap clearfix">
                                        <div class="quotes-list-header">
                                            <span><?php echo $market['coin']; ?></span>
                                            <!--<i>
                                                <b class="iconfont icon-gao"></b>
                                                <b class="iconfont icon-di-copy"></b>
                                            </i>-->
                                        </div>
                                        <div class="quotes-list-header">
                                            <span><?php echo $market['newPrice']; ?></span>
                                            <!--<i>
                                                <b class="iconfont icon-gao"></b>
                                                <b class="iconfont icon-di-copy"></b>
                                            </i>-->
                                        </div>
                                        <div class="quotes-list-header">
                                            <span><?php echo $market['zf']; ?></span>
                                            <!--<i>
                                                <b class="iconfont icon-gao"></b>
                                                <b class="iconfont icon-di-copy"></b>
                                            </i>-->
                                        </div>
                                    </div>
                                <!----------- 主区列表 ----------->
                                		<?php if (is_array($data['typeMap'])) { $count=count($data['typeMap']);foreach ($data['typeMap'] as $index=>$type) { ?>
									
                                    <div class="quotes-tabel-wrap coin-list-item"  <?php if ($index != $data['tradeType']['type']) { ?> style="display:none;" <?php } ?>>
                                    		<?php if (is_array($data['blockMap'])) { $count=count($data['blockMap']);foreach ($data['blockMap'] as $i=>$t) { ?>
                                        <table border="" cellspacing="" cellpadding="" class="quotes-tabel coin-list">
                                           <!--<thead>-->
                                               <!--<th colspan="3" style="text-align: left;color: #3473C9;" class="" ><?php echo $t; ?></th>-->
                                           <!--</thead>-->
                                           		<?php if (is_array($data['tradeTypeListMap'][$i][$index])) { $count=count($data['tradeTypeListMap'][$i][$index]);foreach ($data['tradeTypeListMap'][$i][$index] as $k=>$trade) { ?>
                                                    <tr data-symbol="<?php echo $trade['sellShortName']; ?>_<?php echo $trade['buyShortName']; ?>" data-symbol-id="<?php echo $trade['id']; ?>" type="<?php echo $trade['type']; ?>" symbol="<?php echo $trade['id']; ?>" class="<?php echo $trade['sellShortName']; ?>_<?php echo $trade['buyShortName']; ?> &nbsp;<?php echo $trade['sellShortName'];  echo $trade['buyShortName']; ?>" data-status="<?php echo $trade['isShare']; ?>">
                                                        <td class="quotes-item-box-wrap">
                                                           <span class="trade-symbol">
                                                           <?php echo strtoupper($trade['sellShortName']); ?>
                                                           </span>
                                                        </td>
                                                        <td class="trade-price trade-price<?php echo $trade['id']; ?>" data-symbol_id="<?php echo $trade['id']; ?>" data-blocktype="<?php echo $trade['tradeBlock']; ?>" type="<?php echo $trade['type']; ?>">000.00</td>
                                                        <td class="trade-rate trade-rate<?php echo $trade['id']; ?> success" data-symbol_id="<?php echo $trade['id']; ?>">+0.00%</td>
                                                    </tr>
                                               <?php } } ?>

                                        </table>
                                        <?php } } ?>
                                    </div>
                                    <?php } } ?>

                                </div>
                            </div>
                            <div class="coin-introduce" style="margin-top: 20px;color: white;display: none">
                                <div class="introduce-window-header white"><?php echo $market['coinBasics']; ?></div>
                                <?php if ($data['coinInfo']['nameZh']) { ?>
                                <h2 class="introduce-coin-name white">
                                		<?php if ($data['coinInfo']['nameZh']) {  echo $data['ccoinInfo']['nameZh'];  } else { ?>
                                		-
                                		<?php } ?>
                                </h2>
                                <p class="introduce-coin-detail dark">
                                    <?php if ($data['coinInfo']['info']) {  echo $data['coinInfo']['info'];  } else { ?>
	                                -
	                                <?php } ?>
                                </p>
                                <?php } ?>
                                <div class="introduce-other">
                                    <div class="introduce-item">
                                        <span class="introduce-item-name">
                                           <?php echo $market['coinIssueTime']; ?>
                                        </span>
                                        <span class="introduce-item-value">
                                            <?php if ($data['coinInfo']['gmtRelease']) {  echo $data['coinInfo']['gmtRelease'];  } else { ?>
			                                -
			                                <?php } ?>
                                        </span>
                                    </div>

                                    <div class="introduce-item">
                                        <span class="introduce-item-name">
                                             <?php echo $market['coinPrice']; ?>
                                        </span>
                                        <span class="introduce-item-value">
                                            <?php if ($data['coinInfo']['price']) {  echo $data['coinInfo']['price'];  } else { ?>
			                                -
			                                <?php } ?>
                                        </span>
                                    </div>

                                    <div class="introduce-item">
                                        <span class="introduce-item-name">
                                             <?php echo $market['coinCirculation']; ?>
                                        </span>
                                        <span class="introduce-item-value">
                                            <?php if ($data['coinInfo']['circulation']) {  echo $data['coinInfo']['circulation'];  } else { ?>
			                                -
			                                <?php } ?>
                                        </span>
                                    </div>

                                    <div class="introduce-item">
                                        <span class="introduce-item-name">
                                           <?php echo $market['coinTotalAmount']; ?>
                                        </span>
                                        <span class="introduce-item-value">
                                            <?php if ($data['coinInfo']['total']) {  echo $data['coinInfo']['total'];  } else { ?>
			                                -
			                                <?php } ?>
                                        </span>
                                    </div>

                                    <div class="introduce-item">
                                        <span class="introduce-item-name">
                                            <?php echo $market['coinBlockExplorer']; ?>
                                        </span>
                                        <span class="introduce-item-value">
                                            <a target="_blank" style="width:180px;display:inline-block;overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" href="<?php echo $data['coinInfo']['linkBlock']; ?>">
                                            		<?php if ($data['coinInfo']['linkBlock']) {  echo $data['coinInfo']['linkBlock'];  } else { ?>
				                                -
				                                <?php } ?>
                                            </a>
                                        </span>
                                    </div>

                                    <div class="introduce-item">
                                        <span class="introduce-item-name">
                                             <?php echo $market['coinOfficialWebsite']; ?>
                                        </span>
                                        <span class="introduce-item-value">
                                            <a target="_blank" style="width:180px;display:inline-block;overflow: hidden; text-overflow:ellipsis; white-space: nowrap;" href="<?php echo $data['coinInfo']['linkWebsite']; ?>">
                                            		<?php if ($data['coinInfo']['linkWebsite']) {  echo $data['coinInfo']['linkWebsite'];  } else { ?>
				                                -
				                                <?php } ?>
                                           </a>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>

<!---------------------------------- 主体内容 ------------------------>

                        <div class="quotes-data">
                            <div class="quotes-data-kgraph-wrap">
                                <div class="clearfix quotes-kgraph-header quotes-header">
                                    <button class="iconfont icon-xia1 left"></button>
                                    <h2 class="left quotes-header-tetil">
                                        <span id="tip-sell-buy"><?php echo strtoupper($data['tradeType']['sellShortName']); ?>/<?php echo strtoupper($data['tradeType']['buyShortName']); ?></span>
                                        <span id="tip-price"></span>
                                    </h2>
                                    <p class="left quotes-header-text">
                                        ≈
                                        <span id="tip-cny"></span>
                                        <span id="zf"><?php echo $market['zf']; ?><strong class="success">+0.85%</strong></span>
                                        <span id="open"><?php echo $market['open']; ?>&nbsp;0.00</span>
                                        <span id="high"><?php echo $market['high']; ?> 18000.00</span>
                                        <span id="low"><?php echo $market['low']; ?>17150.00</span>
                                        <span id="vol">24H<?php echo $market['volume']; ?> 8306 BTC</span>
                                    </p>
                                </div>

                         <!------------- k线图 ------------------->

                                <div class="quotes-data-kgraph" >
                                    <div class="quotes-data-list chart-panel">
                                        <ul class="col-xs-12 clearfix" style="color: white;">
                                            <li>1min</li>
                                            <li>5min</li>
                                            <li class="active">15min</li>
                                            <li>30min</li>
                                            <li>60min</li>
                                            <li>1day</li>
                                            <li>1week</li>
                                            <li>1month</li>
                                        </ul>
                                    </div>
                                    <div style="height: 720px;padding:30px 0;">
                                        <div id="chart-box" class="kline" >

                                        </div>
                                    </div>
                                </div>
                            </div>

                        <!------------------- 交易区 ---------------->

                            <div class="clearfix quotes-transaction">
                                <div class="left quotes-transaction-area">
                                    <div class="clearfix quotes-transaction-header">
                                        <h2><?php echo $market['money']; ?></h2>
                                        <a href="/index.php?s=help&c=show&id=13"><?php echo $market['fee']; ?></a>
                                    </div>
                                    <div class="clearfix quotes-transaction-box">

                            <!-------------------- 登录后 ------------->

                                        <div class="quotes-transaction-box-item">
                                            <div class="sell-trade">
                                                <div class="sell-tetil">
                                                    <h2 style="color:#aaa; font-size:.14rem;"><?php echo $market['available']; ?>
                                                        <span id="tradeBuyInt">0</span>
                                                        <span id="tradeBuyDig">.0000</span>
                                                        <span style="color:#fff;"><?php echo $data['tradeType']['buyShortName']; ?></span>

                                                    </h2>
                                                    <a href="/index.php?s=exc&c=FinanceController"><?php echo $market['charge_money']; ?></a>
                                                </div>
                                                <div class="sell-item">
                                                    <h3 class="sell-item-tetil">
                                                        <?php echo $market['buyPrice']; ?>
                                                        <strong>
                                                            <?php echo $market['bestBuyPrice']; ?>：
                                                            <font><span id="best-buy"></span><?php echo $data['tradeType']['buyShortName']; ?></font>
                                                        </strong>
                                                    </h3>
                                                    <div class="sell-item-text">
                                                        <div class="sell-item-form">
                                                            <input type="text" id="tradebuyprice"/>
                                                        </div>
                                                        <p id="tradebuyprice-cny">≈0.00 CNY</p>
                                                    </div>
                                                </div>
                                                <div class="sell-item">
                                                    <h3 class="sell-item-tetil">
                                                        <?php echo $market['buyNumber']; ?>
                                                    </h3>
                                                    <div class="sell-item-form">
                                                        <input type="text" id="tradebuyamount" />
                                                    </div>
                                                </div>
                                                <div class="sell-item" style="padding: 0px; color: white;">
                                                    <div class="slider sell-item-slider" id="buyslider" ></div>
                                                </div>
                                                <div class="sell-item">
                                                    <div class="sell-item-num" style="color: white;" >
                                                        <?php echo $market['tradeNumber']; ?> <span id="tradebuyTurnover">0.0000</span><span><?php echo $data['tradeType']['buyShortName']; ?></span>
                                                        <p id="buy-errortips" class="error-tips" style="color:red;"> </p>
                                                    </div>
                                                </div>
                                                <div class="sell-item">
                                                    <div class="sell-item-btn">
                                                        <button class="transaction-model-show" id="buybtn"><?php echo $market['buy'];  echo $data['tradeType']['sellShortName']; ?></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                            <!-------------------- 未登录 ------------->

                                        <div class="quotes-transaction-box-item">
                                            <div class="sell-trade">
                                                <div class="sell-tetil">
                                                    <h2 style="color:#aaa;font-size:.14rem;"><?php echo $market['available']; ?>
                                                        <span id="tradeCoinInt">0</span>
                                                        <span id="tradeCoinDig">.0000</span>
                                                        <span style="color:#fff;"><?php echo $data['tradeType']['sellShortName']; ?></span>

                                                    </h2>
                                                    <a href="/index.php?s=exc&c=FinanceController"><?php echo $market['charge_money']; ?></a>

                                                </div>
                                                <div class="sell-item">
                                                    <h3 class="sell-item-tetil">
                                                        <?php echo $market['sellPrice']; ?>
                                                        <strong>
                                                            <?php echo $market['bestSellPrice']; ?>：
                                                            <font> <span id="best-sell"></span><?php echo $data['tradeType']['buyShortName']; ?></font>
                                                        </strong>
                                                    </h3>
                                                    <div class="sell-item-text">
                                                        <div class="sell-item-form">
                                                            <input type="text" id="tradesellprice"/>
                                                        </div>
                                                        <p id="tradesellprice-cny">≈0.00 CNY</p>
                                                    </div>
                                                </div>
                                                <div class="sell-item">
                                                    <h3 class="sell-item-tetil">
                                                        <?php echo $market['sellNumber']; ?>
                                                    </h3>
                                                    <div class="sell-item-form">
                                                        <input type="text" name="tradesellamount" autocomplete="off" id="tradesellamount"/>
                                                    </div>
                                                </div>
                                                <div class="sell-item" style="padding: 0px; color: white;">
                                                    <div class="slider sell-item-slider" id="sellslider" ></div>
                                                </div>
                                                <div class="sell-item">
                                                    <div class="sell-item-num" style="color:white;">
                                                        <?php echo $market['tradeNumber']; ?> <span  id="tradesellTurnover">0.0000</span><?php echo $data['tradeType']['buyShortName']; ?>
                                                        <p id="sell-errortips" class="error-tips" style="color:red;"> </p>
                                                    </div>
                                                </div>
                                                <div class="sell-item">
                                                    <div class="sell-item-btn">
                                                        <button style="background: #b34747;border-color: #b34747;;" class="transaction-model-show" id="sellbtn"><?php echo $market['sell'];  echo $data['tradeType']['sellShortName']; ?></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="left quotes-transaction-tabel-wrap">
                                    <div class="quotes-transaction-tabel">
                                        <div class="quotes-tabel-header">
                                            <h2 id="header-text"></h2>
                                        </div>

                                <!------------  卖出的列表  ----------------->
										<style type="text/css">
											.js-click-sell span{
												z-index: 10;
												position: relative;
											}
											.js-click-sell b{
												z-index: 1;
											}
											.js-click-buy span{
												z-index: 10;
												position: relative;
											}
											.js-click-buy b{
												z-index: 1;
											}
										</style>
                                        <div class="quotes-transaction-tabel-data">
                                            <dl class="quotes-transaction-tabel-body">
                                            	   <dt class="quotes-transaction-tabel-tetil">
                                            	       <span class="tetil"></span>
                                            	       <span class="price" id="inner-price"><?php echo $market['price']; ?>(<?php echo $data['tradeType']['buyShortName']; ?>)</span>
                                            	       <span class="amount" id="inner-amount"><?php echo $market['number']; ?>(<?php echo $data['tradeType']['sellShortName']; ?>)</span>
                                            	       <span id="inner-sum"><?php echo $market['accu']; ?>(<?php echo $data['tradeType']['buyShortName']; ?>)</span>
                                            	   </dt>
                                            	   <dd class="quotes-transaction-tabel-item cell">
                                            	       <div class="inner js-click-sell">
                                            	           <span class="color-sell"><?php echo $market['sells']; ?> 7</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                            	           <b class="color-sell-bg" style="width: 30%;"></b>
                                            	       </div>
                                            	   </dd>
                                            	   <dd class="quotes-transaction-tabel-item cell">
                                            	       <div class="inner js-click-sell">
                                            	           <span class="color-sell"><?php echo $market['sells']; ?> 6</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                            	           <b class="color-sell-bg" style="width: 40%;"></b>
                                            	       </div>
                                            	   </dd>
                                            	   <dd class="quotes-transaction-tabel-item cell">
                                            	       <div class="inner js-click-sell">
                                            	           <span class="color-sell"><?php echo $market['sells']; ?> 5</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                            	           <b class="color-sell-bg" style="width: 25%;"></b>
                                            	       </div>
                                            	   </dd>
                                            	   <dd class="quotes-transaction-tabel-item cell">
                                            	       <div class="inner js-click-sell">
                                            	           <span class="color-sell"><?php echo $market['sells']; ?> 4</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                            	           <b class="color-sell-bg" style="width: 10%;"></b>
                                            	       </div>
                                            	   </dd>
                                            	   <dd class="quotes-transaction-tabel-item cell">
                                            	       <div class="inner js-click-sell">
                                            	           <span class="color-sell"><?php echo $market['sells']; ?> 3</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                            	           <b class="color-sell-bg" style="width: 5%;"></b>
                                            	       </div>
                                            	   </dd>
                                            	   <dd class="quotes-transaction-tabel-item cell">
                                            	       <div class="inner js-click-sell">
                                            	           <span class="color-sell"><?php echo $market['sells']; ?> 2</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                            	           <b class="color-sell-bg" style="width: 90%;"></b>
                                            	       </div>
                                            	   </dd>
                                            	   <dd class="quotes-transaction-tabel-item cell">
                                            	       <div class="inner js-click-sell">
                                            	           <span class="color-sell"><?php echo $market['sells']; ?> 1</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                            	           <b class="color-sell-bg" style="width: 30%;"></b>
                                            	       </div>
                                            	   </dd>
                                            </dl>

                                   <!------------- 买入的列表 ------------->

                                            <div class="quotes-transaction-tabel-box">
                                                <dl class="quotes-transaction-tabel-body">
                                                	   <dd class="quotes-transaction-tabel-item cell">
                                                       <div class="inner js-click-buy">
                                                           <span class="color-buy"><?php echo $market['buys']; ?> 1</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                                           <b class="color-buy-bg" style="width: 30%;"></b>
                                                       </div>
                                                   </dd>
                                                	   <dd class="quotes-transaction-tabel-item cell">
                                                       <div class="inner js-click-buy">
                                                           <span class="color-buy"><?php echo $market['buys']; ?> 2</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                                           <b class="color-buy-bg" style="width: 30%;"></b>
                                                       </div>
                                                   </dd>
                                                	   <dd class="quotes-transaction-tabel-item cell">
                                                       <div class="inner js-click-buy">
                                                           <span class="color-buy"><?php echo $market['buys']; ?> 3</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                                           <b class="color-buy-bg" style="width: 30%;"></b>
                                                       </div>
                                                   </dd>
                                                	   <dd class="quotes-transaction-tabel-item cell">
                                                       <div class="inner js-click-buy">
                                                           <span class="color-buy"><?php echo $market['buys']; ?> 4</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                                           <b class="color-buy-bg" style="width: 30%;"></b>
                                                       </div>
                                                   </dd>
                                                	   <dd class="quotes-transaction-tabel-item cell">
                                                       <div class="inner js-click-buy">
                                                           <span class="color-buy"><?php echo $market['buys']; ?> 5</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                                           <b class="color-buy-bg" style="width: 30%;"></b>
                                                       </div>
                                                   </dd>
                                                	   <dd class="quotes-transaction-tabel-item cell">
                                                       <div class="inner js-click-buy">
                                                           <span class="color-buy"><?php echo $market['buys']; ?> 6</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                                           <b class="color-buy-bg" style="width: 30%;"></b>
                                                       </div>
                                                   </dd>
                                                	   <dd class="quotes-transaction-tabel-item cell">
                                                       <div class="inner js-click-buy">
                                                           <span class="color-buy"><?php echo $market['buys']; ?> 7</span>
                                                           <span></span>
                                                           <span></span>
                                                           <span></span>
                                                           <b class="color-buy-bg" style="width: 30%;"></b>
                                                       </div>
                                                   </dd>
                                                </dl>

                                     <!--------------- 表格尾部 ------------->

                                            </div>

                                            <div class="clearfix quotes-transaction-footer">
                                                <div class="quotes-transaction-nav">
                                                </div>
                                                <a href="/index.php?s=exc&c=TradeController&m=order&sb=<?php echo $data['sb']; ?>&type=<?php echo $data['type']; ?>&symbol=<?php echo $data['symbol']; ?>&plat=<?php echo $data['isPlatformStatus']; ?>" class="quotes-transaction-footer-btn">更多</a>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>


                        <!----------------------- 当前委托及委托记录 ---------------->
                        <!----------------------- 登录后显示 ---------------->

                        <!----------------------- 当前委托 ------------------>

                            <div class="clearfix quotes-transaction" id="ensureButton">
                                <div class="clearfix  quotes-header quotes-intrust-header">
                                    <button class="iconfont icon-xia1 left"></button>
                                    <h2 class="left quotes-header-tetil quotes-sgraph-tetil"><?php echo $market['entrust']; ?></h2>
                                    <div class="quotes-header-nav-wrap">
                                        <ul class="quotes-header-nav">
                                        		<li class="quotes-header-navbar active encur-tab" data-id="ensure-all">
                                                <button><?php echo $market['all']; ?></button>
                                            </li>
                                            <li class="quotes-header-navbar encur-tab" data-id="ensure-buy">
                                                <button><?php echo $market['buy']; ?></button>
                                            </li>
                                            <li class="quotes-header-navbar encur-tab" data-id="ensure-sell">
                                                <button><?php echo $market['sell']; ?></button>
                                            </li>
                                        </ul>
                                    </div>
                                 </div>
                                 <div class="quotes-intrust-wrap">
                                     <div class="quotes-intrust">
										 <table class="quotes-intrust-list ensure-table" id="ensure-all">
                                             <thead>
                                             <tr class="quotes-intrust-list-header">
                                                 <th><?php echo $market['time']; ?></th>
                                                 <th><?php echo $market['trade']; ?></th>
                                                 <th><?php echo $market['turn']; ?></th>
                                                 <th><?php echo $market['price']; ?>（<?php echo $data['tradeType']['buyShortName']; ?>）</th>
                                                 <th><?php echo $market['number']; ?>（<?php echo $data['tradeType']['sellShortName']; ?>）</th>
                                                 <th><?php echo $market['enTotal']; ?>(<?php echo $data['tradeType']['buyShortName']; ?>)</th>
                                                 <th><?php echo $market['turnover']; ?></th>
                                                 <th><?php echo $market['over']; ?></th>
                                                 <th><?php echo $market['opera']; ?></th>
                                             </tr>
                                             </thead>
                                             <tbody id="ensure-all-data">
                                             <!----------- 无记录 ------------>

                                             </tbody>
                                         </table>
                                         <table class="quotes-intrust-list ensure-table" id="ensure-buy" style="display: none;">
                                             <thead>
                                                 <tr class="quotes-intrust-list-header">
                                                     <th><?php echo $market['time']; ?></th>
                                                     <th><?php echo $market['trade']; ?></th>
                                                     <th><?php echo $market['turn']; ?></th>
                                                     <th><?php echo $market['price']; ?>(<?php echo $data['tradeType']['buyShortName']; ?>)</th>
                                                     <th><?php echo $market['number']; ?>(<?php echo $data['tradeType']['sellShortName']; ?>）</th>
                                                     <th><?php echo $market['enTotal']; ?>(<?php echo $data['tradeType']['buyShortName']; ?>)</th>
                                                     <th><?php echo $market['turnover']; ?></th>
                                                     <th><?php echo $market['over']; ?></th>
                                                     <th><?php echo $market['opera']; ?></th>
                                                 </tr>
                                             </thead>
                                         	<tbody id="ensure-buy-data">
                                         	   <!----------- 无记录 ------------>

                                         	</tbody>
                                         </table>

                                         <table class="quotes-intrust-list ensure-table" id="ensure-sell" style="display: none;">
                                             <thead>
                                             <tr class="quotes-intrust-list-header">
                                                 <th><?php echo $market['time']; ?></th>
                                                 <th><?php echo $market['trade']; ?></th>
                                                 <th><?php echo $market['turn']; ?></th>
                                                 <th><?php echo $market['price']; ?>（<?php echo $data['tradeType']['buyShortName']; ?>）</th>
                                                 <th><?php echo $market['number']; ?>（<?php echo $data['tradeType']['sellShortName']; ?>）</th>
                                                 <th><?php echo $market['enTotal']; ?>(<?php echo $data['tradeType']['buyShortName']; ?>)</th>
                                                 <th><?php echo $market['turnover']; ?></th>
                                                 <th><?php echo $market['over']; ?></th>
                                                 <th><?php echo $market['opera']; ?></th>
                                             </tr>
                                             </thead>
                                             <tbody id="ensure-sell-data">
                                             <!----------- 无记录 ------------>

                                             </tbody>
                                         </table>
                                     </div>
                                 </div>
                            </div asid >


                        <!------------------------ 委托记录 ------------------>

                            <div class="clearfix quotes-transaction js-action-enhis">
                                <div class="clearfix  quotes-header quotes-intrust-header">
                                    <button class="iconfont icon-xia1 left"></button>
                                    <h2 class="left quotes-header-tetil "><?php echo $market['enHis']; ?></h2>
                                    <div class="quotes-header-nav-wrap">
                                        <ul class="quotes-header-nav">
                                        		<li class="quotes-header-navbar active enhis-tab" data-id="enhis-all">
                                                <button><?php echo $market['all']; ?></button>
                                            </li>
                                            <li class="quotes-header-navbar enhis-tab" data-id="enhis-buy">
                                                <button><?php echo $market['buy']; ?></button>
                                            </li>
                                            <li class="quotes-header-navbar enhis-tab" data-id="enhis-sell">
                                                <button><?php echo $market['sell']; ?></button>
                                            </li>
                                            
                                        </ul>
                                    </div>
                                 </div>
                                 <div class="quotes-intrust-wrap">
                                     <div class="quotes-intrust">
                                     	 <table class="quotes-intrust-list enhis-table" id="enhis-all">
                                             <thead>
                                             <tr class="quotes-intrust-list-header">
                                                 <th><?php echo $market['time']; ?></th>
                                                 <th><?php echo $market['trade']; ?></th>
                                                 <th><?php echo $market['turn']; ?></th>
                                                 <th><?php echo $market['price']; ?></th>
                                                 <th><?php echo $market['number']; ?></th>
                                                 <th><?php echo $market['enTotal']; ?></th>
                                                 <th><?php echo $market['turnover']; ?></th>
                                                 <th><?php echo $market['over']; ?></th>
                                                 <th><?php echo $market['fees']; ?></th>
                                                 <th><?php echo $market['opera']; ?></th>
                                             </tr>
                                             </thead>
                                             <tbody id="enhis-all-data">
                                             <!----------- 无记录 ------------>

                                             </tbody>
                                         </table>
                                         <table class="quotes-intrust-list enhis-table" id="enhis-buy" style="display: none;">
                                             <thead>
                                                <tr class="quotes-intrust-list-header">
                                                    <th><?php echo $market['time']; ?></th>
                                                    <th><?php echo $market['trade']; ?></th>
                                                    <th><?php echo $market['turn']; ?></th>
                                                    <th><?php echo $market['price']; ?>（<?php echo $data['tradeType']['buyShortName']; ?>）</th>
                                                    <th><?php echo $market['number']; ?>（<?php echo $data['tradeType']['sellShortName']; ?>）</th>
                                                    <th><?php echo $market['enTotal']; ?>(<?php echo $data['tradeType']['buyShortName']; ?>)</th>
                                                    <th><?php echo $market['turnover']; ?></th>
                                                    <th><?php echo $market['over']; ?></th>
                                                    <th><?php echo $market['opera']; ?></th>
                                             </tr>
                                             </thead>
                                            <tbody id="enhis-buy-data">
                                               <!----------- 无记录 ------------>

                                            </tbody>
                                         </table>

                                         <table class="quotes-intrust-list enhis-table" id="enhis-sell" style="display: none;">
                                             <thead>
                                             <tr class="quotes-intrust-list-header">
                                                 <th><?php echo $market['time']; ?></th>
                                                 <th><?php echo $market['trade']; ?></th>
                                                 <th><?php echo $market['turn']; ?></th>
                                                 <th><?php echo $market['price']; ?>（<?php echo $data['tradeType']['buyShortName']; ?>）</th>
                                                 <th><?php echo $market['number']; ?>（<?php echo $data['tradeType']['sellShortName']; ?>）</th>
                                                 <th><?php echo $market['enTotal']; ?>(<?php echo $data['tradeType']['buyShortName']; ?>)</th>
                                                 <th><?php echo $market['turnover']; ?></th>
                                                 <th><?php echo $market['over']; ?></th>
                                                 <th><?php echo $market['opera']; ?></th>
                                             </tr>
                                             </thead>
                                             <tbody id="enhis-sell-data">
                                             <!----------- 无记录 ------------>

                                             </tbody>
                                         </table >
                                     </div>
                                 </div>
                            </div>



                        <!----------------------- 深度图及实时成交  ----------------->

                            <div class="clearfix quotes-transaction">

                                <!---------------- 深度图 -------------------->
                                <div class="left quotes-sgraph">
                                    <div class="clearfix  quotes-header quotes-sgraph-header">
                                        <button class="iconfont icon-xia1 left"></button>
                                        <h2 class="left quotes-header-tetil quotes-sgraph-tetil"><?php echo $market['deepth']; ?></h2>
                                    </div>
                                    <div class="quotes-sgraph-box" >
                                        <div id="depth-chart" style="width: 100%;height: 485px;">

                                        </div>
                                    </div>
                                </div>

                               <!-------------- 实时成交表 ----------------------->

                               <div class="left quotes-deal-wrap">
                                   <div class="quotes-deal">
                                       <div class="clearfix  quotes-header quotes-deal-header">
                                            <button class="iconfont icon-xia1 left"></button>
                                            <h2 class="left quotes-header-tetil quotes-sgraph-tetil"><?php echo $market['intime']; ?></h2>
                                        </div>
                                        <div class="quotes-deal-list-wrap">
                                            <table class="quotes-deal-list" id="realTimeTrade">
                                               <tr class="quotes-deal-item">
                                                   <td><?php echo $market['time']; ?></td>
                                                   <td><?php echo $market['turn']; ?></td>
                                                   <td class="real-trade-price"><?php echo $market['price']; ?>(<?php echo $data['tradeType']['buyShortName']; ?>)</td>
                                                   <td class="real-trade-mount"><?php echo $market['number']; ?>(<?php echo $data['tradeType']['sellShortName']; ?>)</td>
                                               </tr>
                                           </table>
                                        </div>

                                   </div>

                               </div>

                            </div>

                        </div>
                </div>

            </div>
        </div>
        <!--交易密码-->
        <div class="model" style="display: none;" id="tradepass">
            <div class="model-body" style="background-color: #282B36;">
                <div class="model-form-header clearfix" style="background-color: #22242E;">
                    <div class="model-form-tetil">
                        <h2 style="color: #FFFFFF;"><?php echo $market['tradePassword']; ?></h2>
                    </div>
                    <div class="model-hide">
                        <button class="iconfont icon-guanbi" style="color: #FFFFFF;"></button>
                    </div>
                </div>
                <div class="model-form-body">
                    <div class="model-form-item">
                        <label for="">
                            <input type="password" id="tradePwd" autocomplete="off" placeholder="<?php echo $market['enterPassword']; ?>"/>
                        </label>
                    </div>
                    <div class="model-form-item">
                        <span id="errortips"></span>
                    </div>
                    <div class="model-form-item">
                        <button class="model-form-btn" id="modalbtn"><?php echo $market['submit']; ?></button>
                    </div>
                </div>
            </div>
        </div>.
        <!--委托历史详情-->
        <div class="model" id="entrustsdetail">
            <div class="model-body" style="background-color: #282B36;">
                <div class="model-form-header clearfix" style="background-color: #22242E;">
                    <div class="model-form-tetil">
                        <h2 class="js-entrust-title" style="color: #FFFFFF;"></h2>
                    </div>
                    <div class="model-hide">
                        <button class="iconfont icon-guanbi" style="color: #FFFFFF;"></button>
                    </div>
                </div>
                <div class="model-form-body js-entrust-body" style="padding: 20px 20px;">

                </div>
            </div>
        </div>
        <!--委托历史详情结束-->

        <input type="hidden" id="sellShortName" value="<?php echo $data['tradeType']['sellShortName']; ?>">
        <input type="hidden" id="cnyDigit" value="<?php echo $data['cnyDigit']; ?>">
        <input type="hidden" id="coinDigit" value="<?php echo $data['coinDigit']; ?>">
        <input type="hidden" id="buyShortName" value="<?php echo $data['tradeType']['buyShortName']; ?>">
        <input type="hidden" id="hide-symbol" value="<?php echo strtolower($data['tradeType']['sellShortName']); ?>_<?php echo strtolower($data['tradeType']['buyShortName']); ?>">
        <input type="hidden" id="symbol" value="<?php echo $data['tradeType']['id']; ?>">
        <?php if ($data['login']) { ?>
         <input type="hidden" id="tradePassword" value="<?php echo $data['tradePassword'] ? 'true' :''; ?>">
        <input type="hidden" id="isopen" value="<?php echo $data['needTradePasswd'] ? 'true':'false'; ?>">
         <input type="hidden" id="isTelephoneBind" value="<?php echo $data['isTelephoneBind'] ? 'true':'false'; ?>">
        <?php } ?>
        <input type="hidden" id="isPlatformStatus" value="<?php echo $data['isPlatformStatus'] ? 'true':'false'; ?>">
        <input type="hidden" id="tradePrice" value="<?php echo $data['tradeType']['priceOffset']; ?>">
        <input type="hidden" id="tradeAmount" value="<?php echo $data['tradeType']['amountOffset']; ?>">

        <input type="hidden" id="login" value="<?php echo $data['login'] ? 'true':'false'; ?>">
        <input type="hidden" id="tradeType" value="">
        <input type="hidden" id="tradeBuyCoin" value="0">
        <input type="hidden" id="tradeSellCoin" value="0">

        <?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>


        <script type="text/javascript">
            $(".area-btns-item a").on({
                click:function(){
                    $(".area-btns-item a").removeClass("active");
                    $(this).addClass("active");
                }
            })
        </script>

        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/exchangeRate.js" type="text/javascript" charset="utf-8"></script>
        <!--<script src="<?php echo HOME_THEME_PATH; ?>exc/js/custom.js" type="text/javascript" charset="utf-8"></script>-->
        <!--<script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/layer/layer.js" type="text/javascript" charset="utf-8"></script>-->
        <!--<script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/util.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/comm.js" type="text/javascript" charset="utf-8"></script>-->
        <script src="<?php echo HOME_THEME_PATH; ?>exc/font/iconfont.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/echarts.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/pako.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/slide.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/jquery-ui-slider-pips.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/swiper/swiper.min.js" type="text/javascript" charset="utf-8"></script>
	    <!--[if lt IE 9]>对不起，浏览器版本太低~！<![endif]-->
      
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/markt/HandleMarket.js?v=1.2" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/markt/trademarket2.js" type="text/javascript" charset="utf-8"></script>

        <script type="text/javascript">
        	       $(".quotes-search-btn").on({
        	           click:function(){
        	               if($(this).hasClass("active")){
        	                   $(this).removeClass("active");
        	                   $(this).css("color","#474A59");
        	               }else{
        	                   $(this).addClass("active");
        	                   $(this).css("color","#E64021");
        	               }
        	           }
        	       })



        	       $(".quotes-list-header").on({
        	           click:function(){
        	               $(this).siblings(".quotes-list-header").children("i").removeClass();
        	               var _data = $(this).children("i");
        	               if(_data.hasClass("quotes-list-header-dase")){
        	                   _data.removeClass("quotes-list-header-dase").addClass("quotes-list-header-ase");
        	               }else if(_data.hasClass("quotes-list-header-ase")){
        	                   _data.addClass("quotes-list-header-dase").removeClass("quotes-list-header-ase");
        	               }else{
        	                   _data.addClass("quotes-list-header-dase");
        	               }
        	           }
        	       })


        </script>
        <script type="text/javascript">
            $(".slider").slider({
                    min: 0,
                    max: 100,
                    step: 20,
                    orientation: "horizontal",
                    range: "min",
                    change: _slider_data
                }).slider("pips");;

            function _slider_data(){
                console.log('343');
                var _left = $(this).slider("value");
                var tag = $(this).attr('id')=='buyslider' ? 0:1;
                trade.onPortion(_left, tag);
                $(this).siblings(".sell-item-tetil").children("span").html(_left + "%")
            }
            $('.js-click-sell span:not(.color-sell)').click(function() {
            	// alert($(this).text());
				$('#tradebuyprice').val($(this).text());
			})
			$('.js-click-buy span:not(.color-sell)').click(function() {
				$('#tradesellprice').val($(this).text());
			})

			//公告轮播
              var swiper_enroll = new Swiper('.js-swiper-container-enroll', {
                  direction: 'vertical',
                  loop: true,
                  autoplay: {
                      delay:2000,
                  },
                  autoplayDisableOnInteraction: false,
              })
        </script>
	</body>
</html>
