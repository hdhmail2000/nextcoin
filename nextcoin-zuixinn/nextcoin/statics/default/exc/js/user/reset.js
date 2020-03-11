var reset = {
    findPassEmail: function (btnele) {
        var email = $("#reset-email").val();
        var idcard = $("#reset-idcard").val();
        var idcardno = $("#reset-idcardno").val();
        var imgcode = $("#reset-imgcode").val();
        if (email == "" || !util.checkEmail(email)) {
            util.layerAlert("", util.getLan("user.tips.18"));
            return;
        }
        if (imgcode == "" || imgcode.length != 4) {
            util.layerAlert("", util.getLan("user.tips.16"));
            return;
        }
        var url = "/index.php?s=exc&c=userController&m=findBack";
        var param = {
            email: email,
            idcard: idcard,
            idcardno: idcardno,
            imgcode: imgcode
        };
        var callback = function (data) {
            if (data.code == 200) {
                util.layerAlert("", util.getLan("user.tips.19"), 1);
            } else {
                util.layerAlert("", data.msg, 2);
            }
        };
        util.network({btn: btnele, url: url, param: param, success: callback});
    },
    resetEmailPass: function (btnele) {
        var regu = /^[0-9]{6}$/;
        var re = new RegExp(regu);
        var totpCode = 0;
        var newPassword = $("#reset-newpass").val();
        var newPassword2 = $("#reset-confirmpass").val();
        var fid = $("#fid").val();
        var uuid = $("#uuid").val();
        var msg = util.isPassword(newPassword);
        if (msg != "") {
            util.layerAlert("", msg);
            $("#reset-newpass").val("");
            return;
        }
        if (newPassword != newPassword2) {
            util.layerAlert("", util.getLan("user.tips.17"));
            $("#reset-confirmpass").val("");
            return;
        }
        // if ($("#reset-googlecode").length > 0) {
        //     totpCode = util.trim($("#reset-googlecode").val());
        //     if (!re.test(totpCode)) {
        //         util.layerAlert("", util.getLan("comm.tips.2"));
        //         return;
        //     }
        // }
        var url = "/index.php?s=exc&c=userController&m=resetPassword";
        var param = {
            totpCode: totpCode,
            newPassword: newPassword,
            newPassword2: newPassword2,
            fid: fid
        };
        var callback = function (data) {
            if (data.code == 200) {
                util.layerAlert("", "重置成功");
                window.location = '/index.php?s=exc&c=userController&m=login';
                $("#secondstep").hide();
                $("#successstep").show();
                $("#resetprocess2").removeClass("active");
                $("#resetprocess3").addClass("active");
            } else {
                util.layerAlert("", data.msg);
                if (data.code == -3) {
                    $("#reset-confirmpass").val("");
                }
                if (data.code == -4) {
                    $("#reset-newpass").val("");
                    $("#reset-confirmpass").val("");
                }
                if (data.code == -8) {
                    $("#totpCode").val("");
                }
            }
        };
        util.network({btn: btnele, url: url, param: param, success: callback});
    },
    checkPhoneCode:function (btnele) {
        var area =  $("#form-site").data('select');
        var phone = $("#reset-email").val();
//      var code = $("#reset-imgcode").val();
		var code = $("#reset-msgcode").val();
        var idcardno = $("#reset-idcardno").val();
        var url = "/index.php?s=exc&c=userController&m=phoneCheck";
        var param = {
            area:area,
            phone:phone,
            code:code,
            idcardno:idcardno
        };
        var callback = function (data) {
            if (data.code == 200) {
                window.location.href = '/index.php?s=exc&c=userController&m=resetPhone';
            } else {
                util.layerAlert("", data.msg);
            }
        };
        util.network({btn: btnele, url: url, param: param, success: callback});
    },
    resetPasswdPhone:function (btnele) {
        var regu = /^[0-9]{6}$/;
        var re = new RegExp(regu);
        var totpCode = 0;
        var newPassword = $("#reset-newpass").val();
        var newPassword2 = $("#reset-confirmpass").val();
        var msg = util.isPassword(newPassword);
        if (msg != "") {
            util.layerAlert("", msg);
            $("#reset-newpass").val("");
            return;
        }
        if (newPassword != newPassword2) {
            util.layerAlert("", util.getLan("user.tips.17"));
            $("#reset-confirmpass").val("");
            return;
        }
        // if ($("#reset-googlecode").length > 0) {
        //     totpCode = util.trim($("#reset-googlecode").val());
        //     if (!re.test(totpCode)) {
        //         util.layerAlert("", util.getLan("comm.tips.2"));
        //         return;
        //     }
        // }
        var url = "/index.php?s=exc&c=userController&m=resetPasswordByPhone";
        var param = {
            totpCode: totpCode,
            newPassword: newPassword,
            newPassword2: newPassword2,
        };
        var callback = function (data) {
            if (data.code == 200) {
                //util.layerAlert("", '修改成功！');
                $("#secondstep").hide();
                $("#successstep").show();
                $("#resetprocess2").removeClass("active");
                $("#resetprocess3").addClass("active");
                layer.confirm('修改成功！', {
				  btn: ['确定'] //可以无限个按钮
				  
				}, function(index, layero){
				  window.location.href = '/index.php?s=exc&c=userController&m=login';
				});
            } else {
                util.layerAlert("", data.msg);
                if (data.code == -3) {
                    $("#reset-confirmpass").val("");
                }
                if (data.code == -4) {
                    $("#reset-newpass").val("");
                    $("#reset-confirmpass").val("");
                }
                if (data.code == -8) {
                    $("#totpCode").val("");
                }
            }
        };
        util.network({btn: btnele, url: url, param: param, success: callback});
    }
};

$("#btn-imgcode").on("click", function () {
    this.src = "/index.php?s=exc&c=userController&m=getVailImg&r=" + Math.round(Math.random() * 100);
});
var isPhone =true;
$('#reset-email').bind('input propertychange', function(){
    console.log(this.value);
    if((this.value).indexOf('@')>=0){
        $('.reset-phone').hide();
        isPhone = false;
    } else{
        $('.reset-phone').show();
        isPhone =true;
    }
});


//显示国家选择框
$('.form-site').click(function(e){
    e.stopPropagation();
    $('.form-site-list').show();
});
//选择国家
$('.form-site-list').on('click','li',function(e){
    e.stopPropagation();
    var that =$(this);
    var formSite = $('.form-site');
    formSite.children('p').html(that.html());
    formSite.attr('select-data',that.attr('code'));
    $('.form-site-list').hide();
});

$(document).click(function(){
    //e.stopPropagation();
    $('.form-site-list').hide();
});

$(function () {
    //获取验证码

    // $("#register-sendemail").on("click", function () {
    //     if (this.id == "reset-sendmessage") {
    //         var areacode = $.trim($("#form-site").attr('select-data'));
    //         var phone = $("#reset-email").val();
    //         var imgcode = $("#reset-imgcode").val();
    //         FindPhoneMsg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone, imgcode);
    //     } else {
    //         var fid = $("#fid").val();
    //         FindPhoneMsg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, null, null, null, fid);
    //     }
    // });

    $(".btn-sendphonecode").on("click", function () {
        if (this.id == "reset-sendmessage") {
            var areacode = $("#form-site").data('select');
            var phone = $("#reset-email").val();
            var imgcode = $("#reset-imgcode").val();
            FindPhoneMsg.sendMsgCode($(this).data().tipsid, this.id,phone,areacode,imgcode);
        }
    });




    //下一步
    $("#btn-email-next").on("click", function () {

        if(isPhone){
            reset.checkPhoneCode(this);
        } else{
            reset.findPassEmail(this);
        }

    });
    //修改密码
    $("#btn-email-success").on("click", function () {
        reset.resetEmailPass(this);
    });

        //手机修改
    $("#btn-phone-success").on("click", function () {
        reset.resetPasswdPhone(this);
    });
});