					<div class="col-xs-12 merchandise-header">
                        <div class="left">
                            <div class="left">
                                <p>出售</p>
                                <ul class="clearfix" onclick="_switch(event)">
                                		<!--<?php if (is_array($coin_list)) { $count=count($coin_list);foreach ($coin_list as $k=>$t) { ?>-->
                                    <!--<li>-->
                                        <!--<a href="/index.php?s=trade&c=home&m=c2cSell&id=<?php echo $t['id']; ?>" <?php if ($id == $t['id'] && $type == 2) { ?>class="active"<?php } ?>><?php echo $t['short_name']; ?></a>-->
                                    <!--</li>-->
                                    <!--<?php } } ?>-->
                                    <li>
                                        <a href="/index.php?s=trade&c=home&m=c2cSell&id=44" <?php if ($id == 44 && $type == 2) { ?>class="active"<?php } ?>>CNYT</a>
                                    </li>
                                    <li>
                                        <a href="/index.php?s=trade&c=home&m=c2cSell&id=45" <?php if ($id == 45 && $type == 2) { ?>class="active"<?php } ?>>USDT</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="left">
                                <p>购买</p>
                                <ul class="clearfix"  onclick="_switch(event)">
                                		<!--<?php if (is_array($coin_list)) { $count=count($coin_list);foreach ($coin_list as $k=>$t) { ?>-->
                                    <!--<li>-->
                                        <!--<a href="/index.php?s=trade&c=home&m=c2cBuy&id=<?php echo $t['id']; ?>" <?php if ($id == $t['id'] && $type == 1) { ?>class="active"<?php } ?>><?php echo $t['short_name']; ?></a>-->
                                    <!--</li>-->
                                    <!--<?php } } ?>-->
                                    <li>
                                        <a href="/index.php?s=trade&c=home&m=c2cBuy&id=44" <?php if ($id == 44 && $type == 1) { ?>class="active"<?php } ?>>CNYT</a>
                                    </li>
                                    <li>
                                        <a href="/index.php?s=trade&c=home&m=c2cBuy&id=45" <?php if ($id == 45 && $type == 1) { ?>class="active"<?php } ?>>USDT</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <ul class="right">
                            <li>
                                <a href="/index.php?s=trade&c=home&m=c2cTrade" <?php if ($m == 'c2cTrade') { ?>class="active"<?php } ?>>发布交易</a>
                            </li>
                            <li>
                                <a href="/index.php?s=trade&c=home&m=c2cReleaseList" <?php if ($m == 'c2cReleaseList') { ?>class="active"<?php } ?>>我的发布</a>
                            </li>
                            <li>
                                <a href="/index.php?s=trade&c=home&m=c2cMyorder" <?php if ($m == 'c2cMyorder') { ?>class="active"<?php } ?>>我的订单</a>
                            </li>
                        </ul>
                    </div>