<?php if ($fn_include = $this->_include("header.html")) include($fn_include);  if ($fn_include = $this->_include("top.html")) include($fn_include); ?>
        <div class=""   style="text-align: center;">
            <div class="row loging-body"    >
                <div class="">
                    <div class="loging-body-box">
                        <h2 class="loging-body-tetil"><?php echo $vail['first_find']; ?></h2>
                        <div class="form-wrap">
                            <div class="form">

                                <div class="form-item reset-phone" style="position: relative;display: none;">
                                    <div id="form-site" class="form-site clearfix" data-select="86">
                                        <span class="iconfont icon-shouji"></span>
                                        <p id="site">中国86</p>

                                    </div>
                                    <div class="form-site-list">
                                        <ul>
											<?php if (is_array($areaCodes)) { $count=count($areaCodes);foreach ($areaCodes as $item) { ?>
                                            <li class="form-site-item" code="<?php echo $item['value']; ?>"><?php echo $item['key']; ?> &nbsp;<?php echo $item['value']; ?></li>
											<?php } } ?>
                                        </ul>
                                    </div>
                                </div>
                                <div class="form-item">
                                    <span class="iconfont icon-zhuanghao"></span>
                                    <label for="">
                                        <input type="text"  id="reset-email" value="" placeholder="<?php echo $vail['first_place1']; ?>"/>
                                    </label>
                                </div>

                                <!--<div class="form-item">
                                    <label for="">
                                        <select  id="reset-idcard" style="color: #000000;">
                                            <option value="1"><?php echo $vail['first']-idcard; ?></option>
                                        </select>
                                    </label>
                                </div>-->
                                <!--<input type="hidden" value="1" id="reset-idcard" />-->
                                <!--<div class="form-item">-->
                                    <!--<span class="iconfont icon-zhengjian"></span>-->
                                    <!--<label for="">-->
                                        <!--<input type="text" id="reset-idcardno" value="" placeholder="<?php echo $vail['first_idcard']; ?>"/>-->
                                    <!--</label>-->
                                <!--</div>-->
                                <div class="form-item form-item-box clearfix">
                                    <span class="iconfont icon-yanzhengma"></span>
                                    <label for="">
                                        <input type="text" id="reset-imgcode" value="" placeholder="<?php echo $vail['first_place3']; ?>"/>
                                    </label>
                                    <a href="###" class="form-item-img">
                                        <img id="btn-imgcode" src="/index.php?s=exc&c=userController&m=getVailImg" alt="" />
                                    </a>
                                </div>

                                <div class="form-item form-item-box clearfix reset-phone" style="display: none;">
                                    <span class="iconfont icon-shoujiyanzheng"></span>
                                    <label for="">
                                        <input type="text"  id="reset-msgcode" value="" />
                                    </label>
                                    <button  id="reset-sendmessage" data-msg-type="109"  data-tipsid="register-phone-areacode" class="form-item-btn  btn-sendphonecode"><?php echo $vail['first_getCode']; ?></button>
                                </div>

                            <div class="form-btn">
                                <a href="javascript:void(0);" id="btn-email-next"><?php echo $vail['first_next']; ?></a>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>

        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/user/reset.js" type="text/javascript" charset="utf-8"></script>
        <script src="<?php echo HOME_THEME_PATH; ?>exc/js/comm/msg.js" type="text/javascript" charset="utf-8"></script>
<?php if ($fn_include = $this->_include("footer.html")) include($fn_include); ?>
