<?php if ($fn_include = $this->_include("header.html")) include($fn_include);  if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
        <!--[if lt IE 9]>对不起，浏览器版本太低~！<![endif]-->
		<center>
        <div class="container-fluid loging">
            <div class="row loging-body" >
                <div class="col-xs-12">
                    <div class="loging-body-box">
                        <h2 class="loging-body-tetil" style="color:white;"><?php echo $reg['welcome']; ?></h2>
                        <div class="clearfix loging-body-nav">
                        	 	<?php if ($data['intro']) { ?>
                                <a href="/index.php?s=exc&c=userController&m=intro&intro=<?php echo $data['intro']; ?>" class="left active" id="phone_btn"><?php echo $reg['phoneRegister']; ?></a>
                                <a href="/index.php?s=exc&c=userController&m=register&intro=<?php echo $data['intro']; ?>" class="left" id="mail_btn"><?php echo $reg['emailRegister']; ?></a>
                            <?php } else { ?>
                                <a href="/index.php?s=exc&c=userController&m=phonereg" class="left active" id="phone_btn"><?php echo $reg['phoneRegister']; ?></a>
                                <a href="/index.php?s=exc&c=userController&m=register" class="left" id="mail_btn"><?php echo $reg['emailRegister']; ?></a>
                            <?php } ?>
                        </div>
                        <div class="form-wrap">
<!--------------------------------- 邮箱注册 -------------------------------->

<!------------------------------ 手机注册 -------------------------------->
                            <input type="hidden" value="0" id="regType">
                            <form class="form" id="phone_form">
                                <div class="form-item" style="position: relative">
                                    <div id="form-site" class="form-site clearfix" select-data="<?php echo $data['defaultAreaCode']; ?>">
                                        <span class="iconfont icon-shouji"></span>
                                        <p id="site" style="text-align: left;color: #ccc;"><?php echo $data['defaultAreaName']; ?>+<?php echo $data['defaultAreaCode']; ?></p>

                                    </div>
                                    <div class="form-site-list" style="background-color: #27262E;">
                                        <ul>
                                        		<?php if (is_array($data['areaCodes'])) { $count=count($data['areaCodes']);foreach ($data['areaCodes'] as $site) { ?>

                                            <li style="background: #27262E;" class="form-site-item" code="<?php echo $site['value']; ?> "> <?php echo $site['key']; ?> +<?php echo $site['value']; ?></li>

                                            <?php } } ?>

                                        </ul>
                                    </div>
                                </div>

                                <div class="form-item">
                                    <span class="iconfont icon-shouji"></span>
                                    <label for="">
                                        <input type="text"  id="register-phone" value="" placeholder="<?php echo $reg['place_phone']; ?>" />
                                    </label>
                                </div>
                                <div class="form-item">
                                    <span class="iconfont icon-mima"></span>
                                    <label for="">
                                        <input type="password"  id="register-password" value="" placeholder="<?php echo $reg['place_password']; ?>" />
                                    </label>
                                </div>

                                <div class="form-item">
                                    <span class="iconfont icon-mima"></span>
                                    <label for="">
                                        <input type="password"  id="register-confirmpassword" value="" placeholder="<?php echo $reg['place_passwordC']; ?>" />
                                    </label>
                                </div>

                                <div class="form-item form-item-box clearfix">
                                    <span class="iconfont icon-shoujiyanzheng"></span>
                                    <label for="">
                                        <input type="text"  id="register-phone-areacode" value="" placeholder="<?php echo $reg['place_codeP']; ?>" a/>
                                    </label>
                                    <button type="button"  id="register-sendemail" data-msg-type="203"  data-tipsid="register-phone" class="form-item-btn  btn-sendphonecode"><?php echo $reg['getCode']; ?></button>
                                </div>

                                <div class="form-item form-item-box clearfix">
                                    <span class="iconfont icon-yanzhengma"></span>
                                    <label for="">
                                        <input type="text"  id="register-imgcode" value="" placeholder="<?php echo $reg['place_codeV']; ?>"/>
                                    </label>
                                    <a href="###" class="form-item-img">
                                        <img   class="btn-imgcode" src="/index.php?s=exc&c=userController&m=getVailImg" />
                                    </a>
                                </div>
		
                                <?php if (isset($data['intro'])) { ?>
                                <div class="form-item">
                                    <span class="iconfont icon-mima"></span>
                                    <label for="">
                                        <input type="text"  id="register-intro" value="<?php echo $data['intro']; ?>" placeholder="<?php echo $reg['place_intro']; ?>" disabled="disabled"/>
                                    </label>
                                </div>
                                 <?php } else { ?>
                                <div class="form-item">
                                    <span class="iconfont icon-mima"></span>
                                    <label for="">
                                        <input type="text"  id="register-intro" value="" placeholder="<?php echo $reg['place_intro']; ?>" />
                                    </label>
                                </div>
								<?php } ?>
                                <div class="clearfix form-box">
                                    <div class="left form-search">
                                        <label id="agreeBox">
                                            <input type="checkbox"  id="agree" value="" />
                                            <?php echo $reg['agree']; ?><a  target="_blank" href="/index.php?s=help&c=show&id=7" class="form-search-btn"><?php echo $reg['agreement']; ?></a>
                                        </label>
                                    </div>
                                </div>
                            </form>
                            <div class="form-btn">
                                <a  id="register-submit" href="javascript:void(0);"><?php echo $reg['reg']; ?></a>
                            </div>
                            <div class="form-hint">
                                <p><?php echo $reg['account']; ?><a href="/index.php?s=exc&c=userController&m=login"><?php echo $reg['login']; ?></a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		</center>


<!------------------------------------ 用户协议 -------------------------------->

 		<input type="hidden" name="" id="" value="{{session('lang}}">
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/custom.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/layer/layer.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/util.js?v=1.1" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/comm.js" type="text/javascript" charset="utf-8"></script>
        <?php if (!isset($_COOKIE['oex_lan']) || $_COOKIE['oex_lan'] =='zh_TW') { ?>
            <script src="<?php echo HOME_THEME_PATH; ?>exc/js/language/language_zh_TW.js" type="text/javascript" charset="utf-8"></script>
        <?php } else { ?>
            <script src="<?php echo HOME_THEME_PATH; ?>exc/js/language/language_en_US.js" type="text/javascript" charset="utf-8"></script>
        <?php } ?>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/user/register.js?v=1.1" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/msg.js?v=1.1" type="text/javascript" charset="utf-8"></script>
<?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>
