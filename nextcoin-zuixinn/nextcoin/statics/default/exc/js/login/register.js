var util = {
	ltrim : function(s) {
		return (s + "").replace(/^\s*/, "");
	},

	rtrim : function(s) {
		return (s + "").replace(/\s*$/, "");
	},

	trim : function(s) {
		return this.rtrim(this.ltrim(s));
	},
	checkPassWord : function(pass) {
		filter = /^[a-zA-Z0-9\u0391-\uFFE5]{2,20}/;
		if (!filter.test(this.trim(pass))) {
			return false;
		} else {
			return true;
		}
	},
	checkNumber : function(num) {
		filter = /^-?([1-9][0-9]*|0)(\.[0-9]+)?$/;
		if (!filter.test(this.trim(num))) {
			return false;
		} else {
			return true;
		}
	},
	checkNumberInt : function(num) {
		filter = /^-?([1-9][0-9]*|0)$/;
		if (!filter.test(this.trim(num))) {
			return false;
		} else {
			return true;
		}
	},
	checkEmail : function(email) {
		filter = /^([a-zA-Z0-9_\-\.\+]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if (!filter.test(this.trim(email))) {
			return false;
		} else {
			return true;
		}
	},
	checkMobile : function(mobile) {
		filter = /^1[3|4|5|8|7][0-9]\d{8}$/;
		if (!filter.test(this.trim(mobile))) {
			return false;
		} else {
			return true;
		}
	}
};
var timeCount = function() {
    window.setTimeout(function() {
        if(_send_captcha > 0) {
            _send_captcha -= 1;
            $(".fill-yz").html(_send_captcha + "s");
            timeCount();
            $(".fill-yz").css({
                color:'#AFAFAF',
                'border-color':'#AFAFAF'
            })
        } else {
            $(".fill-yz").html("重新发送");
            $(".fill-yz").remove('disabled');
            $(".fill-yz").css({
                color:'#333',
                'border-color':'#333'
            })
        };
    }, 1000);
}
var _time = 60;
var _send_captcha = _time;
$(function () {
	$('.btn-sendemailcode').click(function() {
		var address = $("#register-email").val();
	    if (address == "") {
	        alert('请输入邮箱地址');
	        return;
	    }
	    if (!util.checkEmail(address)) {
	        alert('邮箱格式不正确');
	        return;
	    }
	    var params = {
	        address:address,
	        msgtype:$(this).data('msg-type')
	    };
	    $.ajax({
		    	type:"post",
		    	url:"/index.php?s=exc&c=login&m=sendEmailCode",
		    	async:true,
		    	data:params,
		    	dataType:'json',
		    	success:function(res) {
		    		if(res.code==1) {
		    			$(this).unbind('click');
				    $(this).attr('disabled', 'disabled');
				    timeCount();
		    		}else{
		    			alert(res.msg);
		    		}
		    	}
	    });
	})
	$(".btn-sendphonecode").on("click", function () {
        var phone = $("#register-phone").val();
        var area = $('#register-area').val();
        if (phone == "") {
        		alert('请输入手机号');
	        return;
        }
        var params = {
	        phone:phone,
	        type:$(this).data('msg-type'),
	        area:area
	    };
        $.ajax({
		    	type:"post",
		    	url:"/index.php?s=exc&c=login&m=sendSms",
		    	async:true,
		    	data:params,
		    	dataType:'json',
		    	success:function(res) {
		    		if(res.code==1) {
		    			$(this).unbind('click');
				    $(this).attr('disabled', 'disabled');
				    timeCount();
		    		}else{
		    			alert(res.msg);
		    		}
		    	}
	    });
    });
})
function dr_regist() {
	$.ajax({
		type:"post",
		url:"/index.php?s=exc&c=login&m=register",
		async:true,
		data:$('#myform').serialize(),
		dataType:"json",
		success:function(data) {
			if (data.code == 1) {
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
                alert('注册成功');
                window.location.href = '/index.php?s=exc';
            } else {
                //$('#dr_code_img').click();
                alert(data.msg);
                //dr_tips(data.code);
            }
		}
	});
}
function dr_login() {
	$.ajax({
		type:"post",
		url:"/index.php?s=exc&c=login&m=login",
		async:true,
		data:$('#myform').serialize(),
		dataType:"json",
		success:function(data) {
			if (data.code == 1) {
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
                alert('登录成功');
                window.location.href = '/index.php?s=exc';
            } else {
                //$('#dr_code_img').click();
                alert(data.msg);
                //dr_tips(data.code);
            }
		},
		error: function(HttpRequest, ajaxOptions, thrownError) {
            alert(HttpRequest.responseText);
        }
	});
}
