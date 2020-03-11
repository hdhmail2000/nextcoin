<?php if ($fn_include = $this->_include("header.html")) include($fn_include); ?>
	    <div class="main">

	        <!----------------- banner ------------------->

	        <div class="banner">
	            <div class="banner-text">
	                <h2>Binance - Exchange the World</h2>
	                	<?php if (!$member) { ?>
	                <div class="links">
	                    <a href="/index.php?s=exc&c=login&m=register"><?php echo $lang_Home['Regist']; ?></a>
	                    <span>|</span>
	                    <?php echo $lang_Home['Already']; ?>
	                    <a href="/index.php?s=exc&c=login&m=login"><?php echo $lang_Home['Loginbian']; ?></a>
	                </div>
	                <?php } ?>
	            </div>
	            <div class="banner-box">
	                <div class="swiper-container swiper-container-vertical">
	                    <div class="swiper-wrapper">
	                        <div class="swiper-slide clearfix">
	                            <div class="col-xs-3 banner-navbar">
	                                <a href="" class="col-xs-12">
	                                    <img src="/statics/exc/img/image_1521597630559.png" alt="" />
	                                </a>
	                            </div>
	                            <div class="col-xs-3 banner-navbar">
	                                <a href="" class="col-xs-12">
	                                    <img src="/statics/exc/img/image_1521597630559.png" alt="" />
	                                </a>
	                            </div>
	                            <div class="col-xs-3 banner-navbar">
	                                <a href="" class="col-xs-12">
	                                    <img src="/statics/exc/img/image_1521597630559.png" alt="" />
	                                </a>
	                            </div>
	                            <div class="col-xs-3 banner-navbar">
	                                <a class="col-xs-12">
	                                    <img src="/statics/exc/img/image_1521597630559.png" alt="" />
	                                </a>
	                            </div>
	                        </div>
	                        <div class="swiper-slide clearfix">
	                            <div class="col-xs-3 banner-navbar">
	                                <a href="" class="col-xs-12">
	                                    <img src="/statics/exc/img/image_1521597630559.png" alt="" />
	                                </a>
	                            </div>
	                            <div class="col-xs-3 banner-navbar">
	                                <a href="" class="col-xs-12">
	                                    <img src="/statics/exc/img/image_1521597630559.png" alt="" />
	                                </a>
	                            </div>
	                            <div class="col-xs-3 banner-navbar">
	                                <a href="" class="col-xs-12">
	                                    <img src="/statics/exc/img/image_1521597630559.png" alt="" />
	                                    <div class="banner-navbar-text">
	                                        距活动结束
	                                        <span>0</span>
	                                        天
	                                        <span>16</span>
	                                        时
	                                        <span>37</span>
	                                        分
	                                        <span>10</span>
	                                        秒
	                                    </div>
	                                </a>
	                            </div>
	                            <div class="col-xs-3 banner-navbar">
	                                <a href="" class="col-xs-12">
	                                    <img src="/statics/exc/img/image_1521597630559.png" alt="" />
	                                </a>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="swiper-pagination swiper-pagination-clickable swiper-pagination-bullets">
	                        <span class="swiper-pagination-bullet" tabindex="0" role="button" aria-label="Go to slide 1"></span>
	                        <span class="swiper-pagination-bullet" tabindex="0" role="button" aria-label="Go to slide 2"></span>
	                    </div>
	                </div>
	            </div>
	        </div>

	        <!----------------- 消息 ------------------->

	        <div class="mian-news-wrap">
	            <div class="mian-news">
	                <ul class="mian-news-nav">
	                		<?php if (is_array($notice)) { $count=count($notice);foreach ($notice as $i=>$t) {  if ($i<3) { ?>
	                    <li class="mian-news-navbar">
	                        <a href="">
	                            <span><?php echo $t['ftitle']; ?></span>
	                            <i>(<?php echo date('m-d',$t['fupdatetime']/1000); ?>)</i>
	                        </a>
	                    </li>
	                    <?php }  } } ?>
	                </ul>
	            </div>
	        </div>

	        <!----------------- 主题内容 ------------------->

	        <div class="main-content-wrap">
	            <div class="main-content">
	                <div class="main-content-box">
	                    <div class="main-content-nav">
	                        <a href="" class="main-content-link">
	                            <div class="main-content-navbar">
	                                <div class="main-item-text">
	                                    <h3>
	                                        <strong>BNB/BTC</strong>
	                                        <span class="danger">-6.75</span>
	                                    </h3>
	                                    <div class="main-item-num">
	                                        <strong>
	                                            <span class="danger">0.0024620</span>
	                                        </strong>
	                                        <div>
	                                            <span>￥106.33</span>
	                                        </div>
	                                    </div>
	                                    <p>成交额: 5,373.67 BTC</p>
	                                </div>
	                            </div>
	                        </a>
	                        <a href="" class="main-content-link">
	                            <div class="main-content-navbar">
	                                <div class="main-item-text">
	                                    <h3>
	                                        <strong>BNB/BTC</strong>
	                                        <span class="success">-6.75</span>
	                                    </h3>
	                                    <div class="main-item-num">
	                                        <strong>
	                                            <span class="success">0.0024620</span>
	                                        </strong>
	                                        <div>
	                                            <span>￥106.33</span>
	                                        </div>
	                                    </div>
	                                    <p>成交额: 5,373.67 BTC</p>
	                                </div>
	                            </div>
	                        </a>
	                        <a href="" class="main-content-link">
	                            <div class="main-content-navbar">
	                                <div class="main-item-text">
	                                    <h3>
	                                        <strong>BNB/BTC</strong>
	                                        <span class="success">-6.75</span>
	                                    </h3>
	                                    <div class="main-item-num">
	                                        <strong>
	                                            <span>0.0024620</span>
	                                        </strong>
	                                        <div>
	                                            <span>￥106.33</span>
	                                        </div>
	                                    </div>
	                                    <p>成交额: 5,373.67 BTC</p>
	                                </div>
	                            </div>
	                        </a>
	                        <a href="" class="main-content-link">
	                            <div class="main-content-navbar">
	                                <div class="main-item-text">
	                                    <h3>
	                                        <strong>BNB/BTC</strong>
	                                        <span class="danger">-6.75</span>
	                                    </h3>
	                                    <div class="main-item-num">
	                                        <strong>
	                                            <span class="danger">0.0024620</span>
	                                        </strong>
	                                        <div>
	                                            <span>￥106.33</span>
	                                        </div>
	                                    </div>
	                                    <p>成交额: 5,373.67 BTC</p>
	                                </div>
	                            </div>
	                        </a>
	                        <a href="" class="main-content-link">
	                            <div class="main-content-navbar">
	                                <div class="main-item-text">
	                                    <h3>
	                                        <strong>BNB/BTC</strong>
	                                        <span class="danger">-6.75</span>
	                                    </h3>
	                                    <div class="main-item-num">
	                                        <strong>
	                                            <span class="danger">0.0024620</span>
	                                        </strong>
	                                        <div>
	                                            <span>￥106.33</span>
	                                        </div>
	                                    </div>
	                                    <p>成交额: 5,373.67 BTC</p>
	                                </div>
	                            </div>
	                        </a>
	                    </div>
	                </div>

	                <!----------------- 市场列表 ------------------->

	                <div class="market-wrap">
	                    <div class="market">
	                        <div class="market-header">
	                            <div class="market-header-box">
	                                <div class="market-header-centent">
	                                    <div class="market-header-data">
	                                        <div class="market-header-left" wrap="nowrap">
	                                            <div class="market-header-nav">
	                                                <div class="market-header-navbar">
	                                                    <button class="iconfont icon-xingxing"></button>
	                                                    自选
	                                                </div>
	                                                <?php if (is_array($response['SymbolMap'])) { $count=count($response['SymbolMap']);foreach ($response['SymbolMap'] as $i=>$t) { ?>
	                                                <div class="market-header-navbar <?php if ($i==$response['typeFirst']) { ?>active<?php } ?>" data-market="<?php echo $i; ?>">
	                                                    <?php echo $t; ?> &nbsp;<?php echo $lang_Home['exchange']; ?>
	                                                </div>
	                                                <?php } } ?>
	                                            </div>
	                                        </div>


	                                        <div class="market-header-right">
	                                            <i class="iconfont icon-search"></i>
	                                            <input type="" name="" id="" value="" class="market-header-input" placeholder="查询 …"/>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>

	                        <!----------------- 列表 ------------------->

	                        <div class="market-table-wrap">
	                            <div class="market-table">
	                                <div class="market-table-header">
	                                    <div class="market-table-th" style="margin-left: 6px;flex: 0 0 35px;">
	                                        <span></span>
	                                    </div>
	                                    <div class="market-table-th  market-table-left market-table-select">
	                                        <span><?php echo $lang_Home['exchange']; ?></span>
	                                    </div>
	                                    <div class="market-table-th market-table-left">
	                                        <span><?php echo $lang_Home['currency']; ?></span>
	                                    </div>
	                                    <div class="market-table-th market-table-left market-table-select">
	                                        <span><?php echo $lang_Home['last']; ?></span>
	                                    </div>
	                                    <div class="market-table-th market-table-right market-table-select">
	                                        <span><?php echo $lang_Home['change_today']; ?></span>
	                                    </div>
	                                    <div class="market-table-th market-table-right market-table-select">
	                                        <span><?php echo $lang_Home['high']; ?></span>
	                                    </div>
	                                    <div class="market-table-th market-table-right market-table-select">
	                                        <span><?php echo $lang_Home['low']; ?></span>
	                                    </div>
	                                    <div class="market-table-th market-table-right market-table-select">
	                                        <span><?php echo $lang_Home['volume']; ?></span>
	                                        <i class="iconfont icon-downsj"></i>
	                                    </div>
	                                </div>

	                                <!----------------- 列表详情 ------------------->

	                                <div class="market-table-body">
	                                    <div class="market-table-box">
	                                    		<?php if (is_array($response['tradeTypeList'])) { $count=count($response['tradeTypeList']);foreach ($response['tradeTypeList'] as $i=>$t) { ?>
	                                        <div class="market-table-tr">
	                                            <div>
	                                                <a class="market-table-link">
	                                                    <div class="market-table-td market-table-btn">
	                                                        <button class="iconfont icon-xingxing"></button>
	                                                    </div>
	                                                    <div class="market-table-td  market-table-left"><?php echo $t['sellShortName']; ?>/<?php echo $t['buyShortName']; ?></div>
	                                                    <div class="market-table-td  market-table-left"><?php echo $t['sellShortName']; ?></div>
	                                                    <div class="market-table-td  market-table-left">
	                                                        <div class="market-table-pic">
	                                                            <span class="danger"><?php echo $t['maxPrice']; ?></span>
	                                                            <div>
	                                                                <span>/ ￥66.77</span>
	                                                            </div>
	                                                        </div>
	                                                    </div>
	                                                    <div class="market-table-td  market-table-right">
	                                                        <div class="market-table-pic">
                                                                <span class="success">3.88%</span>
                                                            </div>
	                                                    </div>
	                                                    <div class="market-table-td market-table-right"><?php echo $t['maxPrice']; ?></div>
	                                                    <div class="market-table-td market-table-right"><?php echo $t['minPrice']; ?></div>
	                                                    <div class="market-table-td market-table-right">10,224.22039103</div>
	                                                </a>
	                                            </div>
	                                        </div>
	                                        <?php } } ?>
	                                    </div>
	                                </div>

	                            </div>
	                        </div>

	                    </div>
	                </div>

	            </div>
	        </div>

	    </div>
	    <script type="text/javascript">

                var swiper = new Swiper('.swiper-container', {
                  direction: 'vertical',
                  pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                  },
                  autoplay:{
                      delay:10000,
                      disableOnInteraction:false,
                  }
                });

                $(".market-header-navbar").on({
                    click:function(){
                        $(".market-header-navbar").removeClass("active");
                        $(this).addClass("active");
                    }
                })

                $(".market-table-select").on({
                    click:function(){
                        $(".market-table-select").not($(this)).children("i").remove();
                        if($(this).children("i").length == 1){
                            if($(this).children("i").hasClass("icon-downsj")){
                                $(this).children("i").removeClass("icon-downsj").addClass("icon-upsj");
                            }else{
                                $(this).children("i").addClass("icon-downsj").removeClass("icon-upsj");
                            }
                        }else{
                            $(this).append('<i class="iconfont icon-downsj"></i>');
                        }
                    }
                })



                $(function(){
                    console.log($(".market-table-tr").length);
                    console.log($(".market-table-tr").eq(0))
                    for(var i=0;i<$(".market-table-tr").length;i++){
                        console.log(1);
                        var _top = i*30;
                       $(".market-table-tr").eq(i).children().children(".market-table-link").css({
                           top: _top + "px"
                       })
                    }
                })
	    </script>
<?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>