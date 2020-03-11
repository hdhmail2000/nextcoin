$(".navbar-language-item").on({
    click: function() {
        var _text = $(this).children("p").html();
        $(".navbar-language-text p").html(_text);
    }
})

$(".navbar-loging-box").on({
    mouseover: function() {
        $(this).children(".navbar-loging-text").children("i").removeClass("icon-xia").addClass("icon-shang");
    },
    mouseout: function() {
        $(this).children(".navbar-loging-text").children("i").addClass("icon-xia").removeClass("icon-shang");
    }
})

$(".news-data-navbar a").on({
    click: function() {
        $(".news-data-navbar a").removeClass("active");
        $(this).addClass("active");
    }
})

$(".loging-body-nav a").on({
    click: function() {
        $(".loging-body-nav a").removeClass("active");
        $(this).addClass('active');
    }
})

$("#mail_btn").on({
    click: function() {
        $("#mail_form").show();
        $("#phone_form").hide();
    }
})
$("#phone_btn").on({
    click: function() {
        $("#mail_form").hide();
        $("#phone_form").show();
    }
})

$(".form-search-btn").on({
    click: function() {
        $(".pact").show();
    }
})

function _pact_hide() {
    $(".pact").hide();
}

var _time = 60;
var _send_captcha_mail = _time;
var _send_captcha_phone = _time;

$(".send-captcha-mail").on({
    click: function() {
        $(this).unbind('click');
        $(this).attr('disabled', 'disabled');
        timeCountMail();
    }
})
var timeCountMail = function() {
    window.setTimeout(function() {
        if(_send_captcha_mail > 0) {
            _send_captcha_mail -= 1;
            $(".send-captcha-mail").html("重新获取(" + _send_captcha_mail + ")");
            timeCountMail();

        } else {
            $(".send-captcha-mail").html("重新发送");
            $(".send-captcha-mail").remove('disabled');
        };
    }, 1000);
}


$(".send-captcha-phone").on({
    click: function() {
        $(this).unbind('click');
        $(this).attr('disabled', 'disabled');
        timeCountPhone();
    }
})

var timeCountPhone = function() {
    window.setTimeout(function() {
        if(_send_captcha_phone > 0) {
            _send_captcha_phone -= 1;
            $(".send-captcha-phone").html("重新获取(" + _send_captcha_phone + ")");
            timeCountPhone();

        } else {
            $(".send-captcha-phone").html("重新发送");
            $(".send-captcha-phone").remove('disabled');
        };
    }, 1000);
}





$(".form-select-box").on({
    click:function(){
        $(this).siblings(".form-select").toggle();
        if($(this).children(".form-select-img").hasClass("icon-xia")){
            $(this).children(".form-select-img").removeClass("icon-xia").addClass("icon-shang");
        }else{
            $(this).children(".form-select-img").addClass("icon-xia").removeClass("icon-shang");
        }
    }
})

$(".form-select-nav p").on({
    click:function(){
        $(this).parents(".form-select").hide();
        var _text = $(this).html();
        $(this).parents(".form-select").siblings(".form-select-box").children("span").html(_text);
    }
})


$(".financial-address-btn").on({
    click:function(){
        $(".financial-address-img").toggle();
    }
})


$(".financial-data-navbar").on({
    click:function(){
        $(".financial-data-navbar").removeClass("active");
        $(this).addClass("active");
    }
})




$(".nav-box").on({
    click:function(){
        if($(this).hasClass("active")){
            $(this).removeClass("active");
            $(this).children("i").addClass("icon-xia").removeClass("icon-shang");
            $(this).siblings(".nav-list").hide();
        }else{
            $(this).siblings(".nav-list").show();
            $(this).addClass("active");
            $(this).children("i").removeClass("icon-xia").addClass("icon-shang");
        }
    }
})


$(".nav-list-item").on({
    click:function(){
        var _text = $(this).html();
        $(this).parents(".nav-list").hide();
        $(this).parents(".nav-list").siblings(".nav-box").removeClass("active");
        $(this).parents(".nav-list").siblings(".nav-box").children("i").addClass("icon-xia").removeClass("icon-shang");
        $(this).parents(".nav-list").siblings(".nav-box").children("span").html(_text);
    }
})


$(".model-hide").on({
    click:function(){
        _model_hide();
    }
})
$(".model-form-btn:not(#withdrawCnyAddrBtn)").on({
    click:function(){
        _model_hide();
    }
})

$(".financial-site-btn").on({
    click:function(){
        _model_show();
    }
})



function _model_hide(){
    $(".model").hide();
}
function _model_show(){
    $(".model").show();
}


$(".bills-nav .nav-box").on({
    click:function(){
        if($(this).children("i").hasClass("icon-shang")){
            $(this).css({
                background:"#27262E",
                'border-color':"#d3dada"
            })
        }else{
            $(this).css({
                background:"transparent",
                'border-color':"transparent"
            })
        }
    }
})
$(".bills-nav .nav-list-item").on({
    click:function(){
        $(this).parents(".nav-list").siblings(".nav-box").css({
            background:"transparent",
            'border-color':"transparent"
        })
    }
})


$(".submit-btn").on({
    click:function(){
        $(".submit-model").show();
    }
})
$(".model-body-btn button").on({
    click:function(){
        _model_hide();
    }
})

$("#safe_guoogle_btn").on({
    click:function(){
        $("#google_model").show();
    }
})
$("#safe_loging_btn").on({
    click:function(){
        $("#loging_psd_model").show();
    }
})

//$("#safe_transaction_btn").on({
//  click:function(){
//      $("#transaction_model").show();
//  }
//})

$("#safe_real_name_btn").on({
    click:function(){
        $("#real_name_model").show();
    }
})

$("#safe_phone_btn").on({
    click:function(){
        $("#phone_model").show();
    }
})

$("#safe_mali_btn").on({
    click:function(){
        $("#mali_model").show();
    }
})


$("#loging_annal_btn").on({
    click:function(){
        $(this).parents().siblings(".financial-table-wrap").hide();
        $("#loging_annal").show();
    }
})
$("#safety_set_btn").on({
    click:function(){
        $(this).parents().siblings(".financial-table-wrap").hide();
        $("#safety_set").show();
    }
})


$(".announcement-btn").on({
    click:function(){
        $(this).parent(".announcement").hide();
    }
})


$(".quotes-window-navbar button").on({
    click:function(){
        $(".quotes-window-navbar button").removeClass("active");
        $(this).addClass("active");
    }
})


$(".quotes-kgraph-header button").on({
    click:function(){
        if($(this).hasClass("icon-xia1")){
            $(this).removeClass("icon-xia1").addClass("icon-shang1");
            $(this).parent().siblings(".quotes-data-kgraph").animate({height: 'hide'});
        }else{
            $(this).addClass("icon-xia1").removeClass("icon-shang1");
            $(this).parent().siblings(".quotes-data-kgraph").animate({height: 'show'});
        }
    }
})



$(".quotes-transaction-nav").on({
    click:function(){
        if($(this).hasClass("active")){
            $(this).removeClass("active");
            $(this).children(".quotes-transaction-nav-text").children("i").removeClass("icon-shang").addClass("icon-xia");
            $(this).children(".quotes-transaction-nav-box").hide();
        }else{
            $(this).addClass("active");
            $(this).children(".quotes-transaction-nav-text").children("i").addClass("icon-shang").removeClass("icon-xia");
            $(this).children(".quotes-transaction-nav-box").show();
        }
    }
})

$(".quotes-transaction-navbar").on({
    click:function(){
        var _text = $(this).html();
        $(this).parents(".quotes-transaction-nav-box").hide();
        $(this).parents(".quotes-transaction-nav-box").siblings(".quotes-transaction-nav-text").children("span").html(_text);
    }
})


$(".quotes-header-navbar").on({
    click:function(){
        $(this).siblings(".quotes-header-navbar").removeClass("active");
        $(this).addClass("active");
    }
})




$(".quotes-intrust-item-btn").on({
    click:function(){
        if($(this).hasClass("active")){
            $(this).removeClass("active");
            $(this).children("i").removeClass("icon-gao").addClass("icon-di-copy");
            $(this).parent().next(".intrust-table-wrap").hide();
        }else{
            $(this).addClass("active");
            $(this).children("i").addClass("icon-gao").removeClass("icon-di-copy");
            $(this).parent().next(".intrust-table-wrap").show();
        }
    }
})



//$(".quotes-intrust-header button").on({
//  click:function(){
//      if($(this).hasClass("icon-xia1")){
//          $(this).parent().siblings(".quotes-intrust-wrap").hide();
//      }else{
//          $(this).addClass("icon-xia1").removeClass("icon-shang1");
//          $(this).parent().siblings(".quotes-intrust-wrap").show();
//      }
//  }
//})

$(".quotes-sgraph-header button").on({
    click:function(){
        if($(this).hasClass("icon-xia1")){
            $(this).removeClass("icon-xia1").addClass("icon-shang1");
            $(this).parent().siblings(".quotes-sgraph-box").hide();
        }else{
            $(this).addClass("icon-xia1").removeClass("icon-shang1");
            $(this).parent().siblings(".quotes-sgraph-box").show();
        }
    }
})
$(".quotes-deal-header button").on({
    click:function(){
        if($(this).hasClass("icon-xia1")){
            $(this).removeClass("icon-xia1").addClass("icon-shang1");
            $(this).parent().siblings(".quotes-deal-list-wrap").hide();
        }else{
            $(this).addClass("icon-xia1").removeClass("icon-shang1");
            $(this).parent().siblings(".quotes-deal-list-wrap").show();
        }
    }
})


$(".assets-window-item").on({
    click:function(){
        $(".assets-window-item button").removeClass("active");
        $(this).children("button").addClass("active");
    }
})



$(".transaction-nav-text").on({
    click:function(){
        $(this).siblings(".transaction-nav-list").toggle();
        $(this).parent(".transaction-nav").css({
            'border-width' : "1px 1px 0 1px",
            'border-color' : "#d3dada",
            'border-style' : "solid"
        });
        if($(this).hasClass("active")){
            $(this).removeClass("active");
            $(this).children("i").addClass("icon-xia").removeClass("icon-shang");
             $(this).parent(".transaction-nav").css({
                'border-width' : "1px 1px 0 1px",
                'border-color' : 'transparent',
                'border-style' : "solid"
            });
        }else{
            $(this).addClass("active");
            $(this).children("i").removeClass("icon-xia").addClass("icon-shang");
            $(this).parent(".transaction-nav").css({
                'border-width' : "1px 1px 0 1px",
                'border-color' : "#d3dada",
                'border-style' : "solid"
            });
        }
    }
})


$(".transaction-navbar").on({
    click:function(){
        var _text = $(this).children("dl").children(".transaction-navbar-text").children(".transaction-navbar-name").html();
        $(this).parents(".transaction-nav-list").siblings(".transaction-nav-text").removeClass("active");
        $(this).parents(".transaction-nav-list").siblings(".transaction-nav-text").children("i").addClass("icon-xia").removeClass("icon-shang");
        $(this).parents(".transaction-nav").css({
                'border-width' : "1px 1px 0 1px",
                'border-color' : "transparent",
                'border-style' : "solid"
            });
        $(this).parents(".transaction-nav-list").hide();
        $(this).parents(".transaction-nav-list").siblings(".transaction-nav-text").children("span").html(_text);
    }
})

$('.transaction-content-navbar').on({
    click:function(){
        $(".transaction-content-navbar").removeClass('active');
        $(this).addClass("active");
    }
})


$("#transaction_table_btn").on({
    click:function(){
        $("#transaction_table_wrap").show();
        $("#transaction_data_wrap").hide();
    }
})
$("#transaction_data_btn").on({
    click:function(){
        $("#transaction_data_wrap").show();
        $("#transaction_table_wrap").hide();
    }
})


$(".transaction-model-show").on({
    click:function(){
        $(".transaction-model").show();
    }
})


$(".transaction-buy-btn").on({
    click:function(){
        $(".transaction-buy-model").show();
    }
})
