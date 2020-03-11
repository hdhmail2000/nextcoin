<?php if ($fn_include = $this->_include("header.html")) include($fn_include);  if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
    <style>

        .form-site{
            width: 100%;
            padding: 17px 16px 17px 47px;
            color: #444;
            font-size: .14rem;
            line-height: 1;
            border: 1px solid #D3DADA;
            border-radius: 2px;
            cursor: text;
        }
        .form-site-list{
            display: none;
            position: absolute;
            width: 100%;
            height: 200px;
            overflow-y: scroll;
            border: 1px solid #D3DADA;
            cursor: hand;
            z-index: 999;


        }
        .form-site-list li{
            background: white;
            width: 100%;
            height: 30px;
            line-height: 30px;
            padding-left:20px;

        }
        .form-site-list li:hover{
            background: #ECECEC;
        }

    </style>

        <!--[if lt IE 9]>对不起，浏览器版本太低~！<![endif]-->
        <div class="container-fluid loging"   style="text-align: center;">
            <div class=" loging-body">
                <div class="">
                    <div class="loging-body-box">
                        <h2 class="loging-body-tetil" style="color:white;"><?php echo $reg['welcome']; ?></h2>
                        <div class="clearfix loging-body-nav">
                        	 	<?php if ($intro) { ?>

                                <a href="/index.php?s=exc&c=userController&m=intro&intro=<?php echo $intro; ?>" class="left" id="phone_btn"><?php echo $reg['phoneRegister']; ?></a>
                                <a href="/index.php?s=exc&c=userController&m=register&intro=<?php echo $intro; ?>" class="left active" id="mail_btn"><?php echo $reg['emailRegister']; ?></a>
                            <?php } else { ?>

                                <a href="/index.php?s=exc&c=userController&m=phonereg" class="left" id="phone_btn"><?php echo $reg['phoneRegister']; ?></a>
                                <a href="/index.php?s=exc&c=userController&m=register" class="left active" id="mail_btn"><?php echo $reg['emailRegister']; ?></a>
                            <?php } ?>
                        </div>
                        <div class="form-wrap">
<!--------------------------------- 邮箱注册 -------------------------------->
                            <form class="form" id="mail_form">
                                <input type="hidden" value="1" id="regType">
                                <div class="form-item">
                                    <span class="iconfont icon-youxiang"></span>
                                    <label for="">
                                        <input type="text"  id="register-email" value="" placeholder="<?php echo $reg['place_email']; ?>"/>
                                    </label>
                                </div>
                                <div class="form-item">
                                    <span class="iconfont icon-mima"></span>
                                    <label for="">
                                        <input type="password"  id="register-password" value=""  placeholder="<?php echo $reg['place_password']; ?>"/>
                                    </label>
                                </div>
                                <div class="form-item">
                                    <span class="iconfont icon-mima"></span>
                                    <label for="">
                                        <input type="password"  id="register-confirmpassword" value="" placeholder="<?php echo $reg['place_passwordC']; ?>"/>
                                    </label>
                                </div>
                                <div class="form-item form-item-box clearfix">
                                    <span class="iconfont icon-youxiangyanzheng"></span>
                                    <label for="">
                                        <input type="text"  id="register-email-code" value=""  placeholder="<?php echo $reg['place_codeE']; ?>"/>
                                    </label>
                                    <!--send-captcha-mail-->
                                    <button type="button" id="register-sendemail"  data-msg-type="203"  data-tipsid="register-email-code" class="form-item-btn  btn-sendemailcode"><?php echo $reg['getCode']; ?></button>
                                </div>
                                <div class="form-item form-item-box clearfix">
                                    <span class="iconfont icon-yanzhengma"></span>
                                    <label for="">
                                        <input type="text"  id="register-imgcode" value="" placeholder="<?php echo $reg['place_codeV']; ?>"/>
                                    </label>
                                    <a href="###" class="form-item-img">
                                        <img  class="btn-imgcode" alt="" src="/index.php?s=exc&c=userController&m=getVailImg" />
                                    </a>
                                </div>
                                <!--邀请码-->
                                <?php if ($intro) { ?>
                                    <div class="form-item">
                                        <span class="iconfont icon-mima"></span>
                                        <label for="">
                                            <input type="text"  id="register-intro" value="<?php echo $intro; ?>" placeholder="<?php echo $reg['place_intro']; ?>" disabled="disabled" />
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
<!------------------------------ 手机注册 -------------------------------->

                            <div class="form-btn">
                                <a  id="register-submit" href="javascript:void(0)"><?php echo $reg['reg']; ?></a>
                            </div>
                            <div class="form-hint">
                                <p><?php echo $reg['account']; ?><a href="/index.php?s=exc&c=userController&m=login"><?php echo $reg['login']; ?></a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


<!------------------------------------ 用户协议 -------------------------------->

        <input type="hidden" name="" id="" value="cn">
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