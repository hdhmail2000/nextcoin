
<?php if ($fn_include = $this->_include("header.html")) include($fn_include); ?>
    	<!--<link type="text/css" rel="stylesheet" href="/statics/exc/dota/css/csrstyle.css">-->
        <!--[if lt IE 9]>对不起，浏览器版本太低~！<![endif]-->
        <?php if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
<!--------------------------- banner ---------------------------------->

    <style>
        .js-swiper-container-enroll .swiper-slide a{
            display: initial !important;
        }
        .header{
            /*background: #030126;*/
			background-color: rgba(0,0,0,.1);
			position: absolute;
        }
        .footer{
            background: #03052d;
        }
        .header .logo a{
            margin-bottom: 0;
        }
        .qr-container{
            width: 800px;
            margin: 0 auto;
            margin-top:20px;
        }

        .left2{
            float: left;
            margin-top:20px;
            margin-left:30px;
        }

        .child-center{
            text-align: center;
        }



        .download-app{

            height: 40px;
            line-height: 40px;
            border-style: solid;
            border-radius: 25px;
            border-color: #EA5B25;
            border-width: 1px;
            font-size: 20px;
        }

         .download-app a{
             height: 40px;
             width: 200px;
             text-decoration: none;
             color: #aaa;
         }

         .download-app a:hover{
             color: #EA5B25;
         }

          .download-app a:visited{
             color: #aaa;
         }

         .download-app a:link{
             color: #aaa;
         }

         .qr-img{
             width: 220px;
             height: 220px;

         }


        .swiper-pagination-bullet{
            border-radius: 0;
            width: 20px;
            height: 7px;
            background: rgba(255,255,255,.3);
        }
        .swiper-pagination-bullet-active{
            background: -webkit-linear-gradient(left, #0100c0 , #5d007c) !important;
            background: -o-linear-gradient(right, #0100c0, #5d007c) !important;
            background: -moz-linear-gradient(right, #0100c0, #5d007c) !important;
            background: linear-gradient(to right, #0100c0 , #5d007c) !important;
        }
        .area-wrap{
            background: #0b052f;
        }
        .area-btns{
            background:#030126 ;
        }
        .area-btns-item a{
            border: 0;
            width: 100%;
            text-align: center;
			height: 45px;
			line-height: 30px;
            padding: 0;
            font-size: 16px;
			font-weight: bold;
        }
        .area-btns-item .active{
            background: #fff;
            /*border-color: #0078ff;*/
            /*color: #333 !important;*/
			border: none;
			color: #4B4DA2 !important;
        }
        .area-btns-item{
			width: 11%;
        }
        .area-table th{
            border: 0;
        }
        .area-table td{
            font-size: 14px;
        }
        .area-table-item:nth-child(even) td{
            background: #fff;
            border: 0;
        }
        .area-table-item:nth-child(odd) td{
            background: #fff;
            border: 0;
        }

        .navbar-loging-box:hover .navbar-loging-text {
		    background: #22242E;
		    /* color: #333; */
		}

		.my-box-header-btn {
		    font-size: .16rem;
		     color: #337ab7;
		}
		.my-box-btn a {
		    display: inline-block;
		    width: 100%;
		    text-align: center;
		    color: #fff;
		    background: #3473C9;
		    font-size: .18rem;
		    padding: 17px 0;
		    border-radius: 2px;
		}

		.my-box-table th {
		    padding: 13px 0;
		    color: #aaa;
		    font-weight: 300;
		}

		.header .navbar-loging a:active, .header .navbar-loging a:hover {
		    outline: 0;
		    text-decoration: none;
		    color: #337ab7;
		}

		.area-btns-item .active {
		    color: #fff;
		}
		.area-btns-item a:hover {
		    color: #3473C9;
		}
        .swiper-container-horizontal>.swiper-pagination-bullets, .swiper-pagination-custom, .swiper-pagination-fraction{
            bottom: 75px;
        }
		.littleban{
			width: 1280px;
			position: absolute;
			left: 0;
			right: 0;
			/*top: 398px;*/
			bottom: 14%;
			z-index: 1;
			margin: 0 auto;
		}
		.mcenter {
			display: flex;
			flex-direction: row;
			justify-content: center;
		}
		.banneradbox_chinese {
			width: 1280px;
			margin: 0 auto;
			/* margin-top: 330px; */
		}
		.mdefault {
			display: flex;
			flex-direction: row;
		}
		.mcenter > img{
			width: 276px;
			height: 110px;
			transition: all 0.6s;
		}
		.mcenter > img:hover{
			width: 276px;
			height: 110px;

			transform: scale(1.1);
		}
		.innerbox{
			width: 320px;
		}

    </style>
<!--------------------------- banner ---------------------------------->
        <div class="container-fluid banner-warp" style="padding: 0;">
	        <div class="row" style="margin: 0;">
	            <div class="col-xs-12 banner">
	                <div class="swiper-container swiper-container-horizontal banner-swiper">
	                    <div class="swiper-wrapper" style="z-index: auto">

                        	<div class="swiper-slide swiper-slide-active banner-swiper-item">
                            <a href="">
                                <img  src="/uploadfile/201811/bb2a7ea720.jpg" alt="" />
                                <!--<div class="swiper-lazy-preloader"></div>-->
                            </a>
                        	</div>

                        </div>

                        <!--<div class="swiper-pagination swiper-pagination-bullets">-->
                        		<!--&lt;!&ndash;<?php $return = array();$list_temp = $this->list_tag("action=navigator type=0 pid=1"); if ($list_temp) extract($list_temp); $count=count($return); if (is_array($return)) { foreach ($return as $key=>$t) { ?>&ndash;&gt;-->
                            <!--<span class="swiper-pagination-bullet" style=""></span>-->
                            <!--&lt;!&ndash;<?php } } ?>&ndash;&gt;-->
                        <!--</div>-->
                    </div>


	               <!------------- 公告 ------------------>
                    <!--<div class="col-xs-12 banner-text" style="background: rgba(0,0,0,.5); position: absolute;bottom: 0;z-index: 10;">-->
                        <!--<div class="container">-->
                            <!--<div class="row">-->
                                <!--<div class="col-xs-12 banner-text-box">-->
                                    <!--<div class="swiper-container js-swiper-container-enroll swiper-container-vertical" style="height: 50px;">-->
                                        <!--<div class="swiper-wrapper">-->
                                        <!--<?php $return = array();$list_temp = $this->list_tag("action=module module=help catid=9"); if ($list_temp) extract($list_temp); $count=count($return); if (is_array($return)) { foreach ($return as $key=>$t) { ?>-->
                                            <!--<div class="swiper-slide" style="text-align: left;-webkit-justify-content:flex-start;justify-content:flex-start;align-items: center;line-height: 50px;">-->
                                                <!--<span style="color: #f8a137;font-size: 16px;display: inline-block;float: left;">公告：</span>-->
                                                <!--<a href="<?php echo $t['url']; ?>" target="_blank" class="enroll-item" style="color: white;font-size: 16px;float: left;height: 50px;line-height: 50px;">-->
                                                    <!--<?php echo $t['title']; ?>-->
                                                <!--</a>-->
                                            <!--</div>-->
                                          <!--<?php } } ?>-->
                                        <!--</div>-->
                                    <!--</div>-->
                                <!--</div>-->
                            <!--</div>-->
                        <!--</div>-->
                    <!--</div>-->


					<div>
						<div class="littleban">
							<div class="mdefault banneradbox banneradbox_chinese">
								<a href="/index.php?s=help&c=show&id=39" target="_blank" class="mcenter innerbox"><img src="https://ex.foxssl.com/uploadfile/201811/d09cea3c95.png" class="img" /></a>
								<a href="/index.php?s=exc&c=financeController" target="_blank" class="mcenter innerbox"><img src="https://ex.foxssl.com/uploadfile/201811/5a79b1e064.png" class="img" /></a>
								<a href="/index.php?s=exc&c=TradeController&sb=sc_cnyt&type=1&symbol=63" target="_blank" class="mcenter innerbox"><img src="https://ex.foxssl.com/uploadfile/201811/e876371dbb.png" class="img" /></a>
								<a href="/index.php?s=help&c=show&id=40" target="_blank" class="mcenter innerbox"><img src="https://ex.foxssl.com/uploadfile/201811/5a79b1e064.png" class="img" /></a>
							</div>
						</div>
						<!------------- 新公告 ------------------>
						<div class="col-xs-12 banner-text" style="background: rgba(0,0,0,.5); position: absolute;bottom: 0%;z-index: 10;">
							<div class="container">
								<div class="row">
									<div class="col-xs-12 banner-text-box">
										<div class="swiper-container js-swiper-container-enroll swiper-container-vertical" style="height: 50px;">
											<div class="swiper-wrapper">
												<?php $return = array();$list_temp = $this->list_tag("action=module module=help catid=9"); if ($list_temp) extract($list_temp); $count=count($return); if (is_array($return)) { foreach ($return as $key=>$t) { ?>
												<div class="swiper-slide" style="text-align: left;-webkit-justify-content:flex-start;justify-content:flex-start;align-items: center;line-height: 50px;">
													<!--<span style="color: #f8a137;font-size: 16px;display: inline-block;float: left;">公告：</span>-->
													<a href="<?php echo $t['url']; ?>" target="_blank" class="enroll-item" style="color: white;font-size: 16px;float: left;height: 50px;line-height: 50px;">
														<?php echo $t['title']; ?>
													</a>
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
        </div>

        <!--------------新增-------------->
        <style type="text/css">
        	   .dig{
        	       padding: 45px 0;
        	   }
        	   .dig-tetil{
        	       text-align: center;
        	   }
        	   .dig-tetil h2{
        	       font-size: 24px;
        	       color: #f7c069;
        	       margin-bottom: 15px;
        	   }
        	   .dig-tetil h3{
        	       font-size: 14px;
        	       color: #fcfcfc;
        	       margin-bottom: 15px;
        	   }
        	   .dig-tetil a{
        	       font-size: 12px;
        	       color: #f7c069;
        	   }
        	   .dig-box{
        	       padding: 36px 0;
        	   }
        	   .dig-box-text{
        	       padding:10px 0;
        	   }
        	   .dig-box-text h3{
        	       font-size: 14px;
        	       color: #f7c069;
        	       line-height: 20px;
        	   }
        	   .dig-box-text p{
        	       font-size: 12px;
        	       color: #fff;
        	   }
        	   .dig-box-data{
        	       padding: 0;
        	   }
        	   .dig-box-progress{
        	       padding: 0;
        	       border: 1px solid #f8d97e;
        	       background: #f8d97e;
        	       height: 52px;
        	       line-height: 52px;
        	   }
        	   .dig-box-progress div{
        	       float: left;
        	       background: #080808;
        	       height: 100%;
        	       line-height: 50px;
        	   }
        	   .dig-box-progress div img{
        	       float: right;
        	       padding: 8px 12px;
        	   }
        	   .dig-progress-left,.dig-progress-right{
        	       margin-top: 15px;
        	       color: #f7c069;
        	       font-size: 12px;
        	   }
        	   .dig-progress-left{
        	       float: left;
        	   }
        	   .dig-progress-right{
        	       float: right;
        	   }
        	   .dig-addrass{
        	       padding: 0;
        	       text-align: center;
        	       color: #f7c069;
        	       margin-top: 10px;
        	       font-size: 12px;
        	   }
        	   .dig-num-list{
        	       padding: 20px 0 50px 0;
        	   }
        	   .dig-num-item{
        	       padding: 0;
        	   }
        	   .dig-num-item p{
        	       font-size: 18px;
        	       color: #fff;
        	   }
        	   .dig-num-item strong{
        	       font-weight: 400;
        	       color: #f7c069;
        	   }
        	   .dig-num-box{
        	       padding: 0;
        	   }
        	   .dig-num-data{
        	       padding: 0 10px 0 25px;
        	       border-left: 1px solid #f7c069;
        	       min-height: 94px;
        	   }
        	   .dig-num-data p{
        	       font-size: 14px;
        	       color: #fff;
        	   }
        	   .dig-num-data h3{
        	       font-size: 28px;
        	       color: #f7c069;
        	       padding: 19px 0;
        	   }
        	   .dig-num-data a{
        	       font-size: 28px;
        	       color: #f7c069;
        	       margin-top: 18px;
        	       display: inline-block;
        	   }
        	   .area-star img:hover{
        	       cursor: pointer;
        	   }

        </style>
        <div class="container-fluid" style="background: #080808;">
            <div class="container">
                <div class="row">
                    <!-- <div class="col-xs-12 dig">
                        <div class="col-xs-12 dig-tetil">
                            <h2><?php echo $head['mining_title']; ?></h2>
                            <h3><?php echo $head['mining_rule']; ?></h3>
                            <p><a href="/index.php?s=help&c=member_controller&m=dt"><?php echo $head['mining_show_rule']; ?> ></a></p>
                        </div>
                        <div class="col-xs-12 dig-box">
                            <div class="col-xs-2 dig-box-text">
                                <h3><?php echo $head['mining_speed']; ?></h3>
                                <p><?php echo $arr['time_slot']; ?></p>
                            </div>
                            <div class="col-xs-10 dig-box-data">
                                <div class="dig-box-progress col-xs-12">
                                    <div >
                                        <img src="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/push_20180822.png" alt="" />
                                    </div>
                                </div>
                                <div class="dig-progress-left"><?php echo $head['mining_digg']; ?><span><?php echo $arr['mining_total']; ?></span></div>
                                <div class="dig-progress-right"><?php echo $head['mining_surplus']; ?> <span><?php echo $arr['coin_surplus']; ?></span> CC</div>
                                <div class="col-xs-12 dig-addrass">
                                    <p>CC <?php echo $head['mining_address']; ?>：0xa0B6efc05115C66d20B344c4cB72F62788fb302F</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 dig-num-list">
                            <div class="col-xs-4 dig-num-item">
                                <p>CC <?php echo $head['mining_volume']; ?>：<strong> <?php echo $arr['coin_total']; ?> CC </strong></p>
                                <p style="padding: 18px 0;">CC <?php echo $head['mining_total']; ?>：<strong> <?php echo $arr['coin_usdt']; ?> USDT</strong></p>
                                <p>CC <?php echo $head['mining_marketvalueofcirculation']; ?>：<strong> <?php echo $arr['mining_usdt']; ?> USDT</strong></p>
                            </div>
                            <div class="col-xs-8 dig-num-box">
                                <div class="col-xs-4 dig-num-data" id="total_product">
                                    <p><?php echo $head['mining_output_total']; ?>（CC）</p>
                                    <h3><?php echo $arr['mining']; ?> </h3>
                                    <p><?php echo $head['mining_discount']; ?>BTC：<span><?php if ($arr['mining_btc'] == 'inf') { ?>1<?php } else { ?>0<?php } ?></span></p>
                                </div>
                                <div class="col-xs-4 dig-num-data" id="circle">
                                    <p><?php echo $head['mining_quantity']; ?>（CC）</p>
                                    <h3>0  </h3>
                                    <p><?php echo $head['mining_discount']; ?>BTC：0</p>
                                </div>
                                <div class="col-xs-4 dig-num-data">
                                    <p><?php echo $head['mining_output_today']; ?>（CC）</p>
                                    <div style="display: none;">
                                       <h3>32,183.256 </h3>
                                        <p><?php echo $head['mining_discount']; ?>BTC：0.0055555555</p>
                                    </div>
                                    <div>
                                    	<?php if ($FID) { ?>
                                    	<a  href="/index.php?s=exc&c=indexController&m=mining_detail"><h3><span id="total_dig"><?php echo $mining_total?$mining_total:0; ?></span> </h3></a>
                                    	<?php } else { ?>
                                        <a href="/index.php?s=exc&c=userController&m=login"><?php echo $head['login']; ?></a>
                                        <?php } ?>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div> -->
                </div>
            </div>
        </div>


        <div class="container-fluid area-wrap" style="background: #f7f9fa;">
            <div class="container" style="background: #fff;border: 1px #F0F0F0 solid;padding: 20px 15px;">
                <div class="row">
                    <style type="text/css">
                       .area-table td a{
                           color: #333 !important;
                       }
                       .section-navbar{
                           color: #333;
                       }
                       .area-btns-item a{
                           color: #333;
                       }
                    	   .section{
                    	       padding: 0;
                    	   }
                    	   .section-nav{
                    	       padding: 0;
                    	   }
                    	   .section-nav ul{
                    	       padding: 0;
                    	   }
                    	   .section-navbar{
                    	       padding: 0;
                    	       text-align: center;
                    	       height: 50px;
                    	       line-height: 50px;
                    	       border-bottom: 2px solid transparent;
                    	   }
                    	   .section-navbar:hover{
                    	       cursor: pointer;
                    	       color: #3473C9;
                    	   }
                    	   .section-navbar.active{
                    	       border-color: #0078ff;
                    	   }
                    	   .section-search{
                    	       padding: 7px 15px;
                    	   }
                    	   .section-search input{
                    	       width: 100%;
                    	       height: 36px;
                    	       line-height: 36px;
                    	       padding: 0 20px 0 50px;
                    	       background: transparent;
                    	       border-width: 1px;
                    	       border-style: solid;
                    	       border-color: #dcdcdc;
                    	   }
                    	   .section-search button{
                    	       border: 0;
                    	       padding: 0;
                    	       background: transparent;
                    	       font-size: 20px;
                    	       position: absolute;
                    	       left: 35px;
                    	       top: 14px;
                    	       color: #787878;
                    	   }
                    	   .area-table-nav{
                       	display: none;
                       }
                       .area-table-nav.active{
                       	display: table;
                       }
                       .js-area-data tbody{
                       	width: 100%;
                       }
                       .area-table span{
                           color: #c4c4c4;
                       }
                    </style>
                    <!--<div class="col-xs-12 section">-->
                        <!--<div class="col-xs-8 section-nav">-->
                            <!--<ul class="col-xs-8">-->
                            		<!--<?php if (is_array($data['blockMap'])) { $count=count($data['blockMap']);foreach ($data['blockMap'] as $index=>$type) { ?>-->
                                <!--<li class="col-xs-3 section-navbar trade-tab <?php if ($index==$data['blockFirst']) { ?>active<?php } ?>" onclick="show_()" data-market="<?php echo $index; ?>"><?php echo $type; ?></li>-->
                                <!--<?php } } ?>-->
                                <!--<li class="col-xs-3 section-navbar trade-tab" data-market="0" onclick="zixuan()">自选版</li>-->
                                <!--&lt;!&ndash;<li class="col-xs-3 section-navbar">主板</li>-->
                                <!--<li class="col-xs-3 section-navbar">创业板</li>-->
                                <!--<li class="col-xs-3 section-navbar">复活板</li>&ndash;&gt;-->
                            <!--</ul>-->
                        <!--</div>-->
                        <!--<div class="col-xs-4 section-search">-->
                            <!--<input type="" name="" id="search_symbol" value="" />-->
                            <!--<button type="button" id="search_btn" class="iconfont icon-shousuo"></button>-->
                        <!--</div>-->
                    <!--</div>-->
                </div>
                <div class="row" id="block1">

                    	<?php if (is_array($data['blockMap'])) { $count=count($data['blockMap']);foreach ($data['blockMap'] as $k=>$tt) {  if ($k == $data['blockFirst']) {  if (is_array($data['typeMap'])) { $count=count($data['typeMap']);foreach ($data['typeMap'] as $index=>$type) { ?>
	                        <div class="col-xs-12 area-table-box market-con" id="<?php echo $k; ?>" <?php if ($index==$data['typeFirst']) { ?>style="display: block ;padding: 0;"<?php } else { ?>style="display: none ;padding: 0;"<?php } ?>>
	                        		<div class="col-xs-12 area" style="padding: 0;">
			                        <div class="col-xs-12 area-btns" style="padding-right: 0px;background: #fff;">
			                            <?php if (is_array($data['SymbolMap'])) { $count=count($data['SymbolMap']);foreach ($data['SymbolMap'] as $i=>$t) { ?>
			                            <div class="area-btns-item">
			                                <a href="javascript:;" class="area-bt-item <?php if ($i==$data['typeFirst']) { ?>active<?php } ?>" data-market="<?php echo $k; ?>_<?php echo $i; ?>" data-title="<?php echo $t; ?>"><?php echo $t;  echo $head['exchange']; ?></a>
			                            </div>
			                            <?php } } ?>
			                        </div>
			                    </div>
	                            <div class="col-xs-12 area-table-wrap">
	                                <table border frame=hsides class="area-table" id="marketType<?php echo $index; ?>">
	                                    <tr>
	                                        <th style="width: 5%;"></th>
	                                        <th style="width: 15%;"><?php echo $head['currency']; ?></th>
	                                        <th style="width: 10%;"><?php echo $head['change_today']; ?></th>
	                                        <th style="width: 15%;"><?php echo $head['last']; ?></th>
	                                        <th style="width: 20%;"><?php echo $head['high']; ?><span class="jiaoyiqu-title">(<?php echo $data['SymbolMap'][$data['typeFirst']]; ?>)</span></th>
	                                        <th style="width: 20%;"><?php echo $head['low']; ?><span class="jiaoyiqu-title">(<?php echo $data['SymbolMap'][$data['typeFirst']]; ?>)</span></th>
	                                        <th style="width: 15%;"><?php echo $head['volume']; ?></th>
	                                        <!--<th></th>-->
	                                    </tr>
	                                </table>
									<?php if (is_array($data['SymbolMap'])) { $count=count($data['SymbolMap']);foreach ($data['SymbolMap'] as $i=>$t) { ?>
	                                <table border frame=above class="area-table area-table-nav js-area-data <?php if ($i==$data['typeFirst']) { ?>active<?php } ?>" id="marketType<?php echo $k; ?>_<?php echo $i; ?>_data" style="background: #0b052f;">
	                                </table>
	                                <?php } } ?>
	                                <!--<table class="area-table" id="marketType<?php echo $index; ?>_data" style="background: #0b052f;">

	                                </table>-->
	                            </div>
	                        </div>
	                        <?php } }  } else {  if (is_array($data['typeMap'])) { $count=count($data['typeMap']);foreach ($data['typeMap'] as $index=>$type) { ?>
	                        <div class="col-xs-12 area-table-box market-con" id="<?php echo $k; ?>" style="display: none ;padding: 0;">
	                        		<div class="col-xs-12 area" style="padding: 0;">
			                        <div class="col-xs-12 area-btns" style="padding-right: 0px;background: #fff;">
			                            <?php if (is_array($data['SymbolMap'])) { $count=count($data['SymbolMap']);foreach ($data['SymbolMap'] as $i=>$t) { ?>
			                            <div class="area-btns-item" onclick="_areaMunu(event)">
			                                <a href="javascript:;" class="area-bt-item <?php if ($i==$data['typeFirst']) { ?>active<?php } ?>" data-market="<?php echo $k; ?>_<?php echo $i; ?>" data-title="<?php echo $t; ?>"><?php echo $t;  echo $head['exchange']; ?></a>
			                            </div>
			                            <?php } } ?>
			                        </div>
			                    </div>
	                            <div class="col-xs-12 area-table-wrap">
	                                <table class="area-table" id="marketType<?php echo $index; ?>">
	                                    <tr>
	                                        <th style="width: 5%;"></th>
	                                        <th style="width: 10%;"><?php echo $head['currency']; ?></th>
	                                        <th style="width: 10%;"><?php echo $head['change_today']; ?></th>
	                                        <th style="width: 20%;"><?php echo $head['last']; ?></th>
	                                        <th style="width: 20%;"><?php echo $head['high']; ?><span class="jiaoyiqu-title">(<?php echo $data['SymbolMap'][$data['typeFirst']]; ?>)</span></th>
	                                        <th style="width: 20%;"><?php echo $head['low']; ?><span class="jiaoyiqu-title">(<?php echo $data['SymbolMap'][$data['typeFirst']]; ?>)</span></th>
	                                        <th style="width: 15%;"><?php echo $head['volume']; ?></th>
	                                        <!--<th></th>-->
	                                    </tr>
	                                </table>
									<?php if (is_array($data['SymbolMap'])) { $count=count($data['SymbolMap']);foreach ($data['SymbolMap'] as $i=>$t) { ?>
	                                <table class="area-table area-table-nav js-area-data <?php if ($i==$data['typeFirst']) { ?>active<?php } ?>" id="marketType<?php echo $k; ?>_<?php echo $i; ?>_data" style="background: #0b052f;">
	                                </table>
	                                <?php } } ?>
	                                <!--<table class="area-table" id="marketType<?php echo $index; ?>_data" style="background: #0b052f;">

	                                </table>-->
	                            </div>
	                        </div>
	                        <?php } }  }  } } ?>
                </div>
				<!--<div class="row" id="block2" style="display:none;">-->
					<!--<div class="col-xs-12 area" style="padding: 0;">-->
						<!--<div class="col-xs-12 area-btns" style="padding-right: 0px;background: #fff;">-->
			                            <!--<div class="area-btns-item" onclick="zx_change()">-->
			                                <!--<a href="javascript:;" class="area-bt-item zx-btn-item active" data-title="USDT">USDT <?php echo $head['exchange']; ?></a>-->
			                            <!--</div>-->
			                            <!--<div class="area-btns-item" onclick="zx_change()">-->
			                                <!--<a href="javascript:;" class="area-bt-item zx-btn-item" data-title="BTC">BTC <?php echo $head['exchange']; ?></a>-->
			                            <!--</div>-->
			                            <!--<div class="area-btns-item" onclick="zx_change()">-->
			                                <!--<a href="javascript:;" class="area-bt-item zx-btn-item" data-title="ETH">ETH <?php echo $head['exchange']; ?></a>-->
			                            <!--</div>-->
			                        <!--</div>-->
			      <!--</div>-->
	                        <!--<div class="col-xs-12 area-table-box" id="zixuan-con" style="display: block;">-->
	                            <!--<div class="col-xs-12 area-table-wrap">-->
	                                <!--<table class="area-table" id="marketTypezixuan">-->
	                                    <!--<tr>-->
	                                    	<!--<th style="width: 5%;"></th>-->
	                                        <!--<th style="width: 10%;"><?php echo $head['currency']; ?></th>-->
	                                        <!--<th style="width: 10%;"><?php echo $head['change_today']; ?></th>-->
	                                        <!--<th style="width: 20%;"><?php echo $head['last']; ?></th>-->
	                                        <!--<th style="width: 20%;"><?php echo $head['high']; ?><span class="jiaoyiqu-title">(<?php echo $data['SymbolMap'][$data['typeFirst']]; ?>)</span></th>-->
	                                        <!--<th style="width: 20%;"><?php echo $head['low']; ?><span class="jiaoyiqu-title">(<?php echo $data['SymbolMap'][$data['typeFirst']]; ?>)</span></th>-->
	                                        <!--<th style="width: 15%;"><?php echo $head['volume']; ?></th>-->
	                                        <!--&lt;!&ndash;<th></th>&ndash;&gt;-->
	                                    <!--</tr>-->
	                                <!--</table>-->
	                                <!--<table class="area-table area-table-nav js-area-data" id="zixuanhtml" style="display:table;background: #0b052f;">-->
	                                <!--</table>-->
	                            <!--</div>-->
	                        <!--</div>-->
                <!--</div>-->


            </div>
        </div>

<!--------------------- 优势 --------->

        <!--<div class="container-fluid advantage-wrap" style="background: #040226;">
            <div class="container">
                <div class="row">
					<div class="col-xs-12 mian-tetil">
						<h1 style="color: #fff;"><?php echo $head['four_advantages']; ?></h1>
						<p style="text-align: center;margin-top: 10PX;font-size: 11px;">Four major advantages</p>
					</div>
					<div class="col-xs-12 advantage">
                <div class="clearfix banner-list">
                    <style type="text/css">
                                   .banner-list-item{
                                       color: #fff;
                                       padding: 0;
                                       position: relative;
                                       width: 100%;
                                       transition: all .5s ease;
                                   }
                                   .banner-list-item>div{
                                       position: absolute;
                                       left: 0;
                                       top: 50%;
                                       transform: translateY(-50%);
                                       width: 100%;
                                       padding: 0 22px;
                                   }
                                   .banner-list-item h3{
                                       font-size: 24px;
                                       margin-top: 27px;
                                       font-weight: 300;
                                   }
                                   .banner-list-item p{
                                       font-size: 12px;
                                       line-height: 20px;
                                       margin-top: 15px;
                                       display: none;
                                       transition: all .5s ease;
                                       font-weight: 300;
                                   }
                                   .banner-list-item:hover{
                                       background: -webkit-linear-gradient(left, #0100c0 , #5d007c) !important;
                                   background: -o-linear-gradient(right, #0100c0, #5d007c) !important;
                                   background: -moz-linear-gradient(right, #0100c0, #5d007c) !important;
                                   background: linear-gradient(to right, #0100c0 , #5d007c) !important;
                                   color: #fff;
                                   }
                                   .banner-list-item:hover h3{
                                       font-size: 19px;
                                   }
                                   .banner-list-item:hover p{
                                       display: block;
                                   }
                                   .banner-list-img{
                                       display: inline-block;
                                   }
                                   .banner-list-item-wrap:nth-child(2) .banner-list-img{
                                       width: 42px;
                                       height: 45px;
                                       background: url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/icon_082.png)no-repeat;
                                       background-size:100%;
                                   }
                                   .banner-list-item-wrap:nth-child(3) .banner-list-img{
                                       width: 41px;
                                       height: 46px;
                                       background: url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/icon_092.png)no-repeat;
                                       background-size:100%;
                                   }
                                   .banner-list-item-wrap:nth-child(4) .banner-list-img{
                                       width: 46px;
                                       height: 46px;
                                       background: url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/icon_102.png)no-repeat;
                                       background-size:100%;
                                   }
                                   .banner-list-item-wrap:nth-child(5) .banner-list-img{
                                       width: 42px;
                                       height: 50px;
                                       background: url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/icon_112.png)no-repeat;
                                       background-size:100%;
                                   }
                                   .banner-list-item-wrap:nth-child(2) .banner-list-item:hover .banner-list-img{
                                       background: url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/icon_081.png)no-repeat;
                                   }
                                   .banner-list-item-wrap:nth-child(3) .banner-list-item:hover .banner-list-img{
                                       background: url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/icon_091.png)no-repeat;
                                   }
                                   .banner-list-item-wrap:nth-child(4) .banner-list-item:hover .banner-list-img{
                                       background: url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/icon_101.png)no-repeat;
                                   }
                                   .banner-list-item-wrap:nth-child(5) .banner-list-item:hover .banner-list-img{
                                       background: url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/icon_111.png)no-repeat;
                                   }
                        </style>
                    <div class="left banner-list-item-wrap" style="width: 25%;">


                        <a href="javascript:void;" class="left banner-list-item" style="background:url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/20180727-d4.png) ;">
                            <div>
                                <div style="text-align: center;">
                                    <span class="banner-list-img"></span>
                                </div>
                                <h3 style="text-align: center;"><?php echo $head['youshi1_title']; ?></h3>
                                <p><?php echo $head['youshi1_description']; ?></p>
                            </div>
                        </a>
                    </div>
                    <div class="left banner-list-item-wrap" style="width: 25%;">

                        <a href="javascript:void;" class="left banner-list-item" style="background:url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/20180727-d1.png) ;">
                            <div>
                                <div style="text-align: center;">
                                    <span class="banner-list-img"></span>
                                </div>
                                <h3 style="text-align: center;"><?php echo $head['youshi2_title']; ?></h3>
                                <p><?php echo $head['youshi2_description']; ?></p>
                            </div>
                        </a>
                    </div>
                    <div class="left banner-list-item-wrap" style="width: 25%;">


                        <a href="javascript:void;" class="left banner-list-item" style="background:url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/20180727-d2.png) ;">
                            <div>
                                <div style="text-align: center;">
                                    <span class="banner-list-img"></span>
                                </div>
                                <h3 style="text-align: center;"><?php echo $head['youshi3_title']; ?></h3>
                                <p><?php echo $head['youshi3_description']; ?></p>
                            </div>
                        </a>
                    </div>
                    <div class="left banner-list-item-wrap" style="width: 25%;">
                        <a href="javascript:void;" class="left banner-list-item" style="background:url(<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/20180727-d4.png) ;">
                            <div>
                                <div style="text-align: center;">
                                    <span class="banner-list-img"></span>
                                </div>
                                <h3 style="text-align: center;"><?php echo $head['youshi4_title']; ?></h3>
                                <p><?php echo $head['youshi4_description']; ?></p>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
				</div>
            </div>
        </div>-->

		<!--<div class="container-fluid">-->
            <!--<div class="row">-->
                <!--<div class="col-xs-12" style="padding: 0;">-->
                    <!--<img src="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/20180627-d1.png" alt="" style="width: 100%;"/>-->
                <!--</div>-->
                <!--&lt;!&ndash;<div class="col-xs-12" style="padding: 0;">-->
                    <!--<img src="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/20180627-d2.png" alt=""  style="width: 100%;"/>-->
                <!--</div>&ndash;&gt;-->
                <!--<div class="col-xs-12" style="padding: 0;">-->
                    <!--&lt;!&ndash;<img src="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/20180627-d3.png" alt=""  style="width: 100%;"/>&ndash;&gt;-->
                    <!--<img src="<?php echo dr_get_file(dr_block('hezuohuoban')); ?>" alt=""  style="width: 100%;"/>-->
                <!--</div>-->
                <!--&lt;!&ndash;<div class="col-xs-12" style="padding: 0;">-->
                    <!--<img src="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/20180627-d4.png" alt=""  style="width: 100%;"/>-->
                <!--</div>&ndash;&gt;-->
                <!--&lt;!&ndash;<div class="col-xs-12" style="padding: 0;">-->
                    <!--<img src="<?php echo HOME_THEME_PATH; ?>exc/<?php echo $drpath; ?>img/20180627-d5.png" alt=""  style="width: 100%;"/>-->
                <!--</div>&ndash;&gt;-->
            <!--</div>-->
        <!--</div>-->



<!------------------- 多平台终端接入 -->

        <!--<div class="container-fluid terminal-wrap">
            <div class="container">
                <div class="row">
                    <div class="col-xs-12 mian-tetil">
                        <h1><?php echo $head['tips5']; ?></h1>
                        <p><?php echo $head['tips5_1']; ?></p>
                    </div>
                    <div class="col-xs-12 terminal">
                        <div class="col-xs-6 platform-item">
                            <div class="text-right platform-box">
                                <div class="platform-code">
                                    <img src="<?php echo dr_get_file(dr_block('azxzewm')); ?>" alt="" />
                                </div>
                                <i class="iconfont icon-Android"></i>
                                <p><?php echo $head['android_version']; ?></p>
                            </div>
                        </div>
                        <div class="col-xs-6 platform-item">
                            <div class="text-left  platform-box">
                                <div class="platform-code">
                                    <img src="<?php echo dr_get_file(dr_block('iosxzewm')); ?>" alt="" />
                                </div>
                                <i class="iconfont icon-ios"></i>
                                <p><?php echo $head['ios_version']; ?></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>-->
		<style>

			h2{
				font-size: 32px;
				line-height: 46px;
			}

			.contentdiv{
				height: 350px;
			}
			.contentdiv .titleleft{
				margin-top: 120px;
				font-size: 32px;
				line-height: 58px;
			}
			.contentdiv .titletres{
				margin-top: 28px;
				font-size: 24px;
			}
			.contentdiv>div>img{
				height: 350px;
				width: 350px;
				min-height: 350px;
				min-width: 350px;
			}
			.contentdiv .titleleft2{
				margin-top: 120px;
				font-size: 32px;
				line-height: 58px;
			}
			.contentdiv .titletres2{
				margin-top: 28px;
				font-size: 24px;
			}
			.solet-text{
				text-align: left;
			}
			.solet-text>.titleleft2{
				font-size: 40px;
				line-height: 180px;
			}
			.solet-text>.titletres2{
				color:#fff;
				font-size: 16px;
				line-height: 20px;
			}
		</style>
		<div>
			<div style="text-align: center">
					<div style="height: 200px;text-align: center;padding-top: 60px">
						<h2>我们的优势</h2>
						<p style="">Our advantages</p>
					</div>
					<div class="contentdiv">
						<div class="col-xs-2"></div>
						<div class="col-xs-3" style="margin: 0 auto">
							<img src="https://ex.foxssl.com/uploadfile/201811/d9ee94b22c.png" alt="">
						</div>
						<div class="col-xs-1"></div>
								<div class="col-xs-4" style="text-align: right">
									<h4 class="titleleft">多币种 CNYT</h4>
									<div class="titletdos" style="   color: #fff;font-size: 12px;">TMulti currency CNYT</div>
									<div class="titletres">采用稳定价值CNYT作为币价衡量</div>
								</div>
						<div class="col-xs-2"></div>
					</div>

					<div class="contentdiv">
						<div class="col-xs-2"></div>
						<div class="col-xs-4" style="text-align: left">
								<div class="solet-text2">
									<h4 class="titleleft2">方便</h4>

									<div class="titletdos2" style="    color: #fff;font-size: 12px;">convenient</div>
									<div class="titletres2">认证代理商户提供便捷的服务</div>
								</div>
							</div>
						<div class="col-xs-1"></div>
						<div class="col-xs-3" style="margin: 0 auto">
								<img src="https://ex.foxssl.com/uploadfile/201811/eb5fb2a154.png" alt="">
							</div>
						<div class="col-xs-1"></div>
					</div>

					<div class="contentdiv">
						<div class="col-xs-2"></div>
						<div class="col-xs-3" style="margin: 0 auto">
								<img src="https://ex.foxssl.com/uploadfile/201811/ac9c6168fb.png" alt="">
						</div>
						<div class="col-xs-1"></div>
						<div class="col-xs-4" style="text-align: right">
								<div class="solet-text" style="text-align: right">
									<h4 class="titleleft">安全</h4>
									<div class="titletdos" style="    color: #fff;font-size: 12px;">security</div>
									<div class="titletres">采用高级加密程序保障系统安全</div>
								</div>

						</div>
						<div class="col-xs-1"></div>
					</div>

					<div class="contentdiv" style="margin-bottom: 30px;">
						<div class="col-xs-2"></div>
								<div class="col-xs-4" style="text-align: left">
									<h4 class="titleleft2">高效</h4>

									<div class="titletdos2" style="color: #fff;font-size: 12px;">High efficiency</div>
									<div class="titletres2">高性能交易服务节点分布全球多地</div>
								</div>
						<div class="col-xs-1"></div>
						<div class="col-xs-3" style="margin: 0 auto">
								<img src="https://ex.foxssl.com/uploadfile/201811/6d5cc5ffb5.png" alt="">
							</div>
						<div class="col-xs-1"></div>
					</div>

					<div style="width:100%;">
						<div style="height:1px;background:#5a5959;width:90%;margin:0 auto;"></div>
					</div>
					<div style="margin-top:80px;height: 350px">
							<div class="col-xs-6" style="margin: 0 auto">
								<img src="https://ex.foxssl.com/uploadfile/201811/7da9420da1.png" alt="" style="width: 350px">
							</div>
						<div class="col-xs-1"></div>
							<div class="col-xs-5">
								<div class="solet-text">
									<h4 class="titleleft2">全新区块链资产交换平台</h4>
									<div class="titletres2" style="color:#fff;font-size: 16px;line-height: 26px">认证代理商户提供方便快捷的服务</div>
									<div class="titletres2" style="color:#fff;font-size: 16px;line-height: 26px">采用具有稳定价值的CNYT进行交换</div>
									<div class="titletres2" style="color:#fff;font-size: 16px;line-height: 26px">高性能的撮合系统及高级别加密服务机制</div>
									<div class="titletres2" style="color:#fff;font-size: 16px;line-height: 26px">数据服务分布全球多地采用多重备份保障数据稳定</div>
								</div>

							</div>
					</div>
					<!--<div class="bannertitle" style="margin-bottom:150px;">-->
							<div class="col-xs-12" style="margin-top: 80px">
								<h2>马上交易</h2>
								<p style="font-size:20px;color: #fff;">Immediate Trading</p>
							</div>
						<div style="margin-top: 86px;height: 300px;">
							<div class="col-xs-4" style="margin-top: 50px"></div>
							<div class="col-xs-2" style="margin-top: 70px">
								<a style="text-align:center;width:160px;padding: 8px 60px;background: rgba(0,0,0,0);border: 1px solid #fff;cursor: pointer;color: #fff" href="/index.php?s=exc&c=userController&m=phonereg">注册</a>
							</div>

							<div class="col-xs-2" style="margin-top: 70px">
								<a style="text-align:center;width:160px;padding: 9px 61px;background: #35A0F2 28%;cursor: pointer;border: none;color: #fff" href="/index.php?s=exc&c=userController&m=login">登录</a>
							</div>
							<div class="col-xs-4" style="margin-top: 50px"></div>
						</div>
					<!--</div>-->

			</div>
		</div>


        <?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>

    </body>

	<script type="text/javascript">
	    $(function(){
	        if(location.href.indexOf('main')!=-1){
	            $(".navbar-list-item a").removeClass("active");
	            $("#top_main").addClass("active");
	        }
	        if(location.href.indexOf('coin_deposit')!=-1||location.href.indexOf('withdraw/')!=-1||location.href.indexOf('financial/')!=-1){
	            $(".navbar-list-item a").removeClass("active");
	            $("#top_coinDeposit").addClass("active");
	        }
	        if(location.href.indexOf('user/')!=-1||location.href.indexOf('activity/')!=-1){
	            $(".navbar-list-item a").removeClass("active");
	            $("#top_security").addClass("active");
	        }
	        if(location.href.indexOf('about')!=-1){
	            $(".navbar-list-item a").removeClass("active");
	            $("#top_help").addClass("active");
	        }

	        if(location.href.indexOf('order')!=-1 ||location.href.indexOf('trade')!=-1 ){
	            $(".navbar-list-item a").removeClass("active");
	            $("#top_market").addClass("active");
	        }

	    });


	</script>
	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/index/index.js" type="text/javascript" charset="utf-8"></script>
	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/custom.js" type="text/javascript" charset="utf-8"></script>
	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/index/lazysizes.js" type="text/javascript" charset="utf-8"></script>
	<script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/jquery.cookie.js" type="text/javascript" charset="utf-8"></script>
    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/echarts.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/pako.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/swiper/swiper.min.js" type="text/javascript" charset="utf-8"></script>
    <script>
        $(function(){
            var mianswiper = new Swiper('.banner-swiper', {
                autoplay:{
                    delay:12000,
                },
                loop:false,
                lazy: {
                    loadPrevNext: true,
                  },
                pagination: {
                    el: '.swiper-pagination',
                    clickable :true,
                },
            });

            $(".banner-swiper .swiper-slide").mouseover(function () {//滑过悬停
                mianswiper.autoplay.stop();//Swiper 为上面你swiper实例化的名称
            }).mouseout(function(){//离开开启
                mianswiper.autoplay.start();
            });

            //公告轮播
            //   var swiper_enroll = new Swiper('.js-swiper-container-enroll', {
            //       direction: 'vertical',
            //       loop: true,
            //       autoplay: {
            //           delay:8000,
            //       },
            //       autoplayDisableOnInteraction: false,
            //   })
        });


        function _section(e){
            var _this = $(e.target);
            _this.siblings().removeClass("active");
            _this.addClass("active");
        }
		function _areaMunu(e){
            var _this = $(e.target);
            _this.parent().siblings().removeClass("active");
            _this.parent().addClass("active");
            console.log(e.target);
        }
		
		function show_(){
			$('#block1').show();
    		$('#block2').hide();
		}
    </script>

</html>
