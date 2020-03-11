<?php if ($fn_include = $this->_include("header.html")) include($fn_include);  if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
	    <!--[if lt IE 9]>对不起，浏览器版本太低~！<![endif]-->
	    <div class="container-fluid loging"  style="text-align: center;">
	        <div class=" loging-body">
                <div class="">
                    <div class="loging-body-box">
                        <h2 class="loging-body-tetil" style="color:white;"><?php echo $login['welcome']; ?></h2>
                        <div class="form-wrap">
                            <form class="form">
                                <?php echo csrf_field(); ?>
                                <div class="form-item">
                                    <span class="iconfont icon-zhuanghao"></span>
                                    <label for="">
                                        <input type="text" id="login-account" value="" placeholder="<?php echo $login['phone']; ?>" autocomplete="off"/>
                                    </label>
                                </div>
                                <div class="form-item">
                                    <span class="iconfont icon-mima"></span>
                                    <label for="">
                                        <input type="text" id="login-password" value="" placeholder="<?php echo $login['password']; ?>"/>
                                    </label>
                                </div>

                                <div class="clearfix form-box">
                                    <div class="left form-search">
                                        <label>
                                            <input type="checkbox" id="remember" value="" />
                                            <?php echo $login['remember']; ?>
                                        </label>
                                    </div>
                                    <div class="right back-psd-btn">
                                        <a href="/index.php?s=exc&c=userController&m=resetEmail"><?php echo $login['forget']; ?></a>
                                    </div>
                                </div>

                            </form>
                            <div class="form-btn">
                                <a href="javascript:void(0)" id="login-submit"><?php echo $login['login']; ?></a>
                            </div>
                            <div class="form-hint">
                                <p><?php echo $login['account']; ?><a href="/index.php?s=exc&c=userController&m=register"><?php echo $login['reg']; ?></a></p>
                            </div>
                        </div>
                    </div>
                </div>
	        </div>
	    </div>
        <input type="hidden" id="forwardUrl" value="">
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/custom.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/plugin/layer/layer.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/util.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/comm.js" type="text/javascript" charset="utf-8"></script>
        <?php if (!isset($_COOKIE['oex_lan']) || $_COOKIE['oex_lan'] =='zh_TW') { ?>
            <script src="<?php echo HOME_THEME_PATH; ?>exc/js/language/language_zh_TW.js" type="text/javascript" charset="utf-8"></script>
        <?php } else { ?>
            <script src="<?php echo HOME_THEME_PATH; ?>exc/js/language/language_en_US.js" type="text/javascript" charset="utf-8"></script>
        <?php } ?>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/user/login.js" type="text/javascript" charset="utf-8"></script>
        <script>
			setTimeout(function removeReadonly(){
		        $("#login-password").attr('type','password');
		    },20);
        </script>
<?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>
