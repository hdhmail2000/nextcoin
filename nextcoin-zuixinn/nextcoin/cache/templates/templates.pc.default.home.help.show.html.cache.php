<?php if ($fn_include = $this->_include("header.html", "/")) include($fn_include);  if ($fn_include = $this->_include("top.html", "/")) include($fn_include); ?>
	    <!--[if lt IE 9]>对不起，浏览器版本太低~！<![endif]-->
        <style type="text/css">
               .serve-item-btn img{
                   transition: transform .3s ease;
                   width: 8px;
                   transform: rotateZ(45deg);
                   float: right;
               }
               .serve-list-item .serve-item-nav{
                   transform:all .3s ease;
               }
        	       .serve-list-item.active .serve-item-nav{
        	           display: block;
        	       }
        	       .serve-list-item.active .serve-item-btn img{
        	           transform: rotateZ(0deg) !important;
        	       }
        </style>
        <div class="container">
            <div class="row serve-wrap">
                <div class="col-xs-12 serve">
<!------------------------------- 边栏 ----------------------------->
                    <div class="col-xs-3">
                        <ul class="serve-list">
							<?php if ($catid == 9 || $catid == 10) {  $return_t = array();$list_temp_t = $this->list_tag("action=category module=help id=$catid  return=t"); if ($list_temp_t) extract($list_temp_t); $count_t=count($return_t); if (is_array($return_t)) { foreach ($return_t as $key_t=>$t) { ?>
                            <li class="serve-list-item <?php if ($i==0) { ?>active<?php } ?>">
                                <a href="###"  class="serve-item-btn clearfix" style="width: 100%;display: inline-block;">
                                    <?php echo $t['name']; ?>
                                    <!--<?php if ($data['fabout']['fabouttype'] == $types['fid']) { ?>
                                    <i class="iconfont icon-shang"></i>
                                    <?php } else { ?>
                                    <i class="iconfont icon-xia"></i>
                                    <?php } ?>-->
                                    <img src="/statics/exc/dota/img/guanbi.png" alt="" />
                                </a>


                                <?php if (true) { ?>
                                <ul class="serve-item-nav" style="display: block">
                                <?php } else { ?>
                                <ul class="serve-item-nav">
                                <?php }  $return_h = array();$list_temp_h = $this->list_tag("action=module module=help catid=$t[id]  return=h"); if ($list_temp_h) extract($list_temp_h); $count_h=count($return_h); if (is_array($return_h)) { foreach ($return_h as $key_h=>$h) { ?>
                                    <li class="serve-item-navbar">
                                        <a <?php if ($id==$h['id']) { ?> class="active"<?php } ?> href="<?php echo $h['url']; ?>">
                                        	<?php echo $h['title']; ?>
                                        </a>
                                    </li>
                                    <?php } } ?>
                            		</ul>

                            </li>
                            <?php } }  } else {  $return_t = array();$list_temp_t = $this->list_tag("action=category module=help pid=4  return=t"); if ($list_temp_t) extract($list_temp_t); $count_t=count($return_t); if (is_array($return_t)) { foreach ($return_t as $key_t=>$t) { ?>
                            <li class="serve-list-item <?php if ($i==0) { ?>active<?php } ?>">
                                <a href="###"  class="serve-item-btn clearfix" style="width: 100%;display: inline-block;">
                                    <?php echo $t['name']; ?>
                                    <!--<?php if ($data['fabout']['fabouttype'] == $types['fid']) { ?>
                                    <i class="iconfont icon-shang"></i>
                                    <?php } else { ?>
                                    <i class="iconfont icon-xia"></i>
                                    <?php } ?>-->
                                    <img src="/statics/exc/dota/img/guanbi.png" alt="" />
                                </a>


                                <?php if (true) { ?>
                                <ul class="serve-item-nav">
                                <?php } else { ?>
                                <ul class="serve-item-nav">
                                <?php }  $return_h = array();$list_temp_h = $this->list_tag("action=module module=help catid=$t[id] num=10  return=h"); if ($list_temp_h) extract($list_temp_h); $count_h=count($return_h); if (is_array($return_h)) { foreach ($return_h as $key_h=>$h) { ?>
                                    <li class="serve-item-navbar">
                                        <a <?php if ($id==$h['id']) { ?> class="active"<?php } ?> href="<?php echo $h['url']; ?>">
                                        	<?php echo $h['title']; ?>
                                        </a>
                                    </li>
                                    <?php } } ?>
                            		</ul>

                            </li>
                            <?php } }  } ?>
                        </ul>
                    </div>
                    <?php $return = array();$list_temp = $this->list_tag("action=content id=$id module=help"); if ($list_temp) extract($list_temp); $count=count($return); if (is_array($return)) { foreach ($return as $key=>$t) { ?>

                    <div class="col-xs-9 serve-content">
                        <h1 class="col-xs-12 serve-tetil"><?php echo $t['title']; ?></h1>
                        <div class="col-xs-12 serve-data">
                            <div class="serve-data-box" style="max-height: 100%;">
                                <?php echo $t['content']; ?>
                            </div>
                        </div>

                    </div>
                    <?php } } ?>
                </div>
            </div>
        </div>

        <script src="/statics/exc/dota/pjj/js/custom.js" type="text/javascript" charset="utf-8"></script>

        <script type="text/javascript">
        	       $(".serve-item-btn").on({
        	           click:function(){
        	               $(this).parent().toggleClass("active");
        	               $(this).parent().siblings().removeClass('active');
        	           }
        	       })

        	       $(".serve-item-navbar a").on({
        	           click:function(){
        	               $(".serve-item-navbar a").removeClass("active");
        	               $(this).addClass("active");
        	           }
        	       })


        </script>

	</body>
    <?php if (!isset($_COOKIE['oex_lan']) || $_COOKIE['oex_lan'] =='zh_TW') { ?>
        <script src="/statics/exc/dota/js/language/language_zh_TW.js" type="text/javascript" charset="utf-8"></script>
    <?php } else { ?>
        <script src="/statics/exc/dota/js/language/language_en_US.js" type="text/javascript" charset="utf-8"></script>
    <?php }  if ($fn_include = $this->_include("footer.html", "/")) include($fn_include); ?>
