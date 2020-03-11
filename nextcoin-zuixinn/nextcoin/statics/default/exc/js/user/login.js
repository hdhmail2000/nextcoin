var login = {
    checkLoginUserName: function () {
        var uName = $("#login-account").val();
        if (uName == "") {
            util.layerTips("login-account", util.getLan("user.tips.1"));
            return false;
        }
        return true;
    },
    checkLoginPassword: function () {
        var password = $("#login-password").val();
        var des = util.isPassword(password);
        if (des != "") {
            util.layerTips("login-password", des);
            return false;
        }
        return true;
    },
    login: function () {
        if (login.checkLoginUserName() && login.checkLoginPassword()) {
            var url = "/index.php?s=exc&c=userController&m=userLogin";
            var uName = $("#login-account").val();
            var pWord = $("#login-password").val();
            var remmeber = document.getElementById('remember').checked;
            if(remmeber){
                localStorage.setItem("uName",uName);
                localStorage.setItem("pWord",pWord);
            } else {
                localStorage.removeItem("uName");
                localStorage.removeItem("pWord");
            }
            var longLogin = 0;
            if (util.checkEmail(uName)) {
                longLogin = 1;
            }

            var param = {
                loginName: uName,
                password: pWord,
                type: longLogin
            };
            var callback = function (data) {
                var forwardUrl = util.getUrlParam('forwardUrl');
                if (data.code != 200) {
                    util.layerTips("login-password", data.msg);
                    $("#login-password").val("");
                } else {
                		document.cookie = 'token='+data.data.token;
                    console.log(data);
                    if (forwardUrl == null || forwardUrl == '/index.php?s=exc&c=userController&m=userLogin') {
                    		var sync_url = data.data.syncurl;
		                // 发送同步登录信息
		                for(var i in sync_url){
		                    $.ajax({
		                        type: "GET",
		                        async: false,
		                        url:sync_url[i],
		                        dataType: "jsonp",
		                        success: function(json){ },
		                        error: function(){ }
		                    });
		                }
                        window.location.href = "/index.php?s=exc&c=indexController";
                    } else {
                        window.location.href = forwardUrl;
                    }

                }
            };
            util.network({btn: $("#login-submit")[0], url: url, param: param, success: callback, enter: true});
        }
    }
};

var loginType;
// $('#login-account').input(){
//
//     var username = this.value;
//     if(username.indexOf('@')>0){
//         loginType = 1;
//         $()
//     }else{
//         loginType = 0;
//     }
// }
$(function () {
    $("#login-password").on("focus", function () {
        util.callbackEnter(login.login);
    });
    $("#login-submit").on("click", function () {
        login.login();
    });
    var uName = localStorage.getItem('uName');
    if('undefined'!=typeof(uName)){
        $('#login-account').val(uName);
        $("#login-password").val(localStorage.getItem('pWord'));
        //$('#remember').attr('checked',true);
    }
});