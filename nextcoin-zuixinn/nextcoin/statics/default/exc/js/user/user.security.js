var security = {
    loadGoogleAuth: function () { //绑定谷歌验证码
        var url = "/index.php?s=exc&c=securityController&m=getGoogleQR";
        var param = null;
        var callback = function (data) {
            if (data.code == 200) {
            		
                if (navigator.userAgent.indexOf("MSIE") > 0) {
                    $('#qrcode').html("").qrcode({text: data.qecode, width: "140", height: "140", render: "table"});
                } else {
                		console.log(data.data.qecode);
                    $('#qrcode').html("").qrcode({text: data.data.qecode, width: "140", height: "140"});
                }
                $("#bindgoogle-key").html(data.data.totpKey);
                $("#bindgoogle-key-hide").val(data.data.totpKey);
                $('#google_model').show();
            } else {
                util.layerAlert("", data.msg, 2);
            }
        };
        util.network({url: url, param: param, success: callback});
    },

    sendBindEmail: function (ele, email, flag) { //发送绑定邮件
        var desc = '';
        if (email.indexOf(" ") > -1) {
            desc = util.getLan("user.tips.5");
        } else if (email == '') {
            desc = util.getLan("user.tips.6");
        } else if (!util.checkEmail(email)) {
            desc = util.getLan("user.tips.7");
        }
        if (desc != "") {
            util.layerAlert("", desc);
            return;
        }
        var url = "/index.php?s=exc&c=securityController&m=sendEmailCode";
        var param = {
            email: email
        };
        var callback = function (data) {
            if (data.code == 200) {
                if (flag) {
                    util.layerAlert('', data.msg, 1);
                } else {
                    util.layerAlert("", data.msg, 1);
                }
            } else {
                if (flag) {
                    util.layerAlert('', data.msg, 2);
                } else {
                    util.layerAlert("", data.msg, 2);
                }
            }
        };
        util.network({btn: ele, url: url, param: param, success: callback});
    },

    saveBindGoogle: function (ele) { //保存谷歌的绑定
        var code = util.trim($("#bindgoogle-topcode").val());
        var totpKey = $("#bindgoogle-key-hide").val();
        if (!/^[0-9]{6}$/.test(code)) {
            util.layerAlert("", util.getLan("comm.tips.2"));
        }
        var url = "/index.php?s=exc&c=securityController&m=bindGoogleCode";
        var param = {
            code: code,
            totpKey: totpKey
        };
        var callback = function (data) {
            if (data.code == 200) {
                util.layerAlert("", data.msg, 1);
                window.setTimeout(function () {
                    window.location.href = '/index.php?s=exc&c=securityController';
                }, 1000);
            } else {
                util.layerAlert("", data.msg, 2);
                $("#bindgoogle-topcode").val("");
            }
        };
        util.network({btn: ele, url: url, param: param, success: callback});
    },

    lookBindGoogle: function (ele) { //加载谷歌的绑定
        var totpCode = util.trim($("#unbindgoogle-topcode").val());

        if (!/^[0-9]{6}$/.test(totpCode)) {
            util.layerAlert("", util.getLan("comm.tips.2"));
            return ;
        }

        var url = "/index.php?s=exc&c=securityController&m=getGoogleCode";
        var param = {
            totpCode: totpCode
        };
        var callback = function (data) {
            if (data.code == 200) {
                $('#unqrcode').html('');
                if (navigator.userAgent.indexOf("MSIE") > 0) {
                    $('#unqrcode').qrcode({text: data.data.qecode, width: "140", height: "140", render: "table"});
                } else {
                    $('#unqrcode').qrcode({text: data.data.qecode, width: "140", height: "140"});
                }
                $("#unbindgoogle-key").html(data.data.totpKey);
                $("#unbindgoogle-hide-box").show();
                $("#unbindgoogle-show-box").hide();
                centerModals();
            } else if (data.code == -1) {
                util.layerAlert("", data.msg, 2);
                $("#unbindgoogle-topcode").val("");
            } else {
                util.layerAlert("", data.msg, 2);
            }
        };
        util.network({btn: ele, url: url, param: param, success: callback});
    },
    saveModifyPwd: function (ele, pwdType, istrade) { //修改密码

        var originPwdEle = "";
        var newPwdEle = "";
        var reNewPwdEle = "";
        var totpCodeEle = "";
        var errorEle = "";
        var identityEle = "";
        if (pwdType == 0) {
            //登录密码
            if (istrade) {  //istrade = true 修改密码
                originPwdEle = "#unbindloginpass-oldpass";
                newPwdEle = "#unbindloginpass-newpass";
                reNewPwdEle = "#unbindloginpass-confirmpass";
                totpCodeEle = "#unbindloginpass-googlecode";
            } else {
                originPwdEle = "#bindloginpass-oldpass";
                newPwdEle = "#bindloginpass-newpass";
                reNewPwdEle = "#bindloginpass-confirmpass";
                totpCodeEle = "#bindloginpass-googlecode";
            }
        } else { //交易密码
            if (istrade) { //修改交易密码
                originPwdEle = "#unbindtradepass-oldpass";
                newPwdEle = "#unbindtradepass-newpass";
                reNewPwdEle = "#unbindtradepass-confirmpass";
                totpCodeEle = "#unbindtradepass-googlecode";
            } else {//重置交易密码
                originPwdEle = "#bindtradepass-oldpass";
                newPwdEle = "#bindtradepass-newpass";
                reNewPwdEle = "#bindtradepass-confirmpass";
                totpCodeEle = "#bindtradepass-googlecode";
                identityEle = "#bindtradepass-identityno";
            }
        }
        if (istrade) {
            var originPwd = util.trim($(originPwdEle).val());
        }
        var newPwd = util.trim($(newPwdEle).val());
        var reNewPwd = util.trim($(reNewPwdEle).val());
        var newPwdTips = util.isPassword(newPwd);
        var reNewPwdTips = util.isPassword(reNewPwd);
        if (newPwdTips != "") {
            util.layerAlert("", newPwdTips);
            return;
        }
        if (reNewPwdTips != "") {
            util.layerAlert("", reNewPwdTips);
            return;
        }
        if (newPwd != reNewPwd) {
            util.layerAlert("", util.getLan("user.tips.17"));
            $(reNewPwdEle).val("");
            return;
        }
        var totpCode = "";
        var identityCode = "";
        if ($(totpCodeEle).length > 0) {
            totpCode = util.trim($(totpCodeEle).val());
            if (!/^[0-9]{6}$/.test(totpCode)) {
                util.layerAlert("", util.getLan("comm.tips.2"));
                return;
            }
        }
        if ($(totpCodeEle).length <= 0) {
            util.layerAlert("", util.getLan("comm.tips.1"));
            return;
        }
        if (pwdType != 0 && !istrade && $(identityEle).length > 0) {
            identityCode = util.trim($(identityEle).val());
            if (identityCode == "") {
                util.layerAlert("", util.getLan("user.tips.24"));
                return;
            }
        }
        util.hideerrortips(errorEle);
        var url = "/index.php?s=exc&c=securityController&m=modifyPassword";
        var param = {
            pwdType: pwdType,
            originPwd: originPwd,
            newPwd: newPwd,
            reNewPwd: reNewPwd,
            totpCode: totpCode,
            identityCode: identityCode
        };
        var callback = function (data) {
            if (data.code == 200) {
                if (istrade) {
                    util.layerAlert("", pwdType == 0 ? util.getLan("user.tips.25") : util.getLan("user.tips.26"), 1);
                } else {
                    util.layerAlert("", pwdType == 0 ? util.getLan("user.tips.27") : util.getLan("user.tips.28"), 1);
                }
            } else {
                util.layerAlert("", data.msg, 2);
            }
        };
        util.network({btn: ele, url: url, param: param, success: callback});
    },
    saveRealName: function (ele) { //保存真实资料
        var realname = $("#bindrealinfo-realname").val();
        var address = $("#bindrealinfo-address").find("option:selected").text();
        var identitytype = $("#bindrealinfo-identitytype").val();
        var identityno = $("#bindrealinfo-identityno").val();
        var idCardZmImgURL = $("#idCardZmImgURL").val();
        var idCardFmImgURL = $("#idCardFmImgURL").val();
        var idCardScImgURL = $("#idCardScImgURL").val();
        var ckinfo = document.getElementById("bindrealinfo-ckinfo").checked;
        if (!ckinfo) {
            util.layerAlert("", util.getLan("user.tips.29"));
            return;
        }
        if (realname.length > 10 || realname.trim() == "") {
            util.layerAlert("", util.getLan("user.tips.30"));
            return;
        }
        var url = "/index.php?s=exc&c=securityController&m=saveRealName";
        var param = {
            realname: realname,
            identitytype: identitytype,
            identityno: identityno,
            address: address.trim(),
            idCardZmImgURL: idCardZmImgURL,
            idCardFmImgURL: idCardFmImgURL,
            idCardScImgURL: idCardScImgURL
        };
        var callback = function (data) {
            if (data.code == 200) {
                util.layerAlert("", data.msg, 1);
                // location.reload();
            } else {
                util.layerAlert("", data.msg, 2);
            }
        };
        util.network({btn: ele, url: url, param: param, success: callback});
    },
    sendPhoneSms: function (ele) { //发送手机短信
        var phone = $("#bindphone-phone").val();
        var area  = "86";
        $('#phone-tips').show();

        BindPhoneMsg.sendcode("phone-tips","bindphone-send-code",phone,area);
    },
    bindPhone: function (ele) { //绑定手机
        var phone = $("#bindphone-phone").val();
        var area  = "86";
        var code = $("#bindphone-phone-code").val();

        var url = "/index.php?s=exc&c=securityController&m=bindPhone";
        var param = {
            phone: phone,
            area: area,
            code:code
        };
        var callback = function (data) {
            if (data.code == 200) {
                util.layerAlert("", data.msg, 1);
                location.reload();
            } else {
                util.layerAlert("", data.msg, 2);
            }
        };
        util.network({btn: ele, url: url, param: param, success: callback});
    }



};
$(function () {
    //初始化数据
    // security.init();
    $(".btn-imgcode").on("click", function () {
        this.src = "/servlet/ValidateImageServlet.html?r=" + Math.round(Math.random() * 100);
    });
//  $('#bindgoogle').on("click", function () {
        security.loadGoogleAuth();
//  });
    //邮箱绑定
    $("#bindemail-Btn").on("click", function () {
        var email = $("#bindemail-email").val();
        security.sendBindEmail(this, email, false);
    });
    //发送邮箱验证码
    $("#bindemail-send").on("click", function () {
        var email = $("#bindemail-send-email").val();
        security.sendBindEmail("", email, true);
    });

    //绑定谷歌验证码
    $("#bindgoogle-Btn").on("click", function () {
        security.saveBindGoogle(this);
    });

    $("#unbindgoogle-Btn").on("click", function () {
        security.lookBindGoogle(this);
    });
    // 修改登录密码
    $("#unbindloginpass-Btn").on("click", function () {
        security.saveModifyPwd(this, 0, true);
    });

    $("#bindloginpass-Btn").on("click", function () {
        security.saveModifyPwd(this, 0, false);
    });
    //设置交易密码
    $("#unbindtradepass-Btn").on("click", function () {
        security.saveModifyPwd(this, 1, true);
    });

    //交易密码
    $("#bindtradepass-Btn").on("click", function () {
        security.saveModifyPwd(this, 1, false);
    });


    //真实信息
    $("#bindrealinfo-Btn").on("click", function () {
        security.saveRealName(this);
    });

    //发送绑定手机验证码
    $("#bindphone-send-code").on('click',function(){
        security.sendPhoneSms(this);
    });

    //绑定手机
    $("#bindphone-Btn").on('click',function () {
        security.bindPhone(this);
    });
    //显示修改交易密码model
    $('#safe_transaction_btn').click(function () {
        $('#transaction_model1').show();
    });
    //显示设置交易密码model
    $('#safe_setting_btn').click(function () {
        $('#transaction_model').show();
    });

    //显示查看谷歌密码的框
    $('#unbindgoogle').click(function(){
        $('#unbindgoogle-show-box').show();
    });
    //显示修改登录密码框
    $("#safe_loging_btn").on({
        click:function(){
            $("#loging_psd_model").show();
        }
    })
    //显示真实信息绑定框
    $("#safe_real_name_btn").on({
        click:function(){
            $("#real_name_model").show();
        }
    })
    //手机绑定框
    $("#safe_phone_btn").on({
        click:function(){
            $("#phone_model").show();
        }
    })
    //安全邮箱绑定框
    $("#safe_mali_btn").on({
        click:function(){
            $("#mali_model").show();
        }
    })
});
