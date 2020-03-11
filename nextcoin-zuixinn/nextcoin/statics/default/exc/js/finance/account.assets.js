var assets = {
	addCoinAddress : function(ele) {
		var coinName = $("#coinName").val();
		var withdrawAddr = util.trim($("#withdrawBtcAddr").val());
		var withdrawRemark = util.trim($("#withdrawBtcRemark").val());
		var withdrawBtcPass = util.trim($("#withdrawBtcPass").val());
		var withdrawBtcAddrTotpCode = 0;
		var withdrawBtcAddrPhoneCode = 0;
		var symbol = $("#symbol").val();
		if (withdrawAddr == "") {
			util.showerrortips("binderrortips", util.getLan("finance.tips.9"));
			return;
		}
		var start = withdrawAddr.substring(0, 1);
		if ($("#withdrawBtcAddrTotpCode").length > 0) {
			withdrawBtcAddrTotpCode = util.trim($("#withdrawBtcAddrTotpCode").val());
			if (!/^[0-9]{6}$/.test(withdrawBtcAddrTotpCode)) {
				util.showerrortips("binderrortips", util.getLan("comm.tips.2"));
				$("#withdrawBtcAddrTotpCode").val("");
				return;
			}
		}
		if ($("#withdrawBtcAddrPhoneCode").length > 0) {
            withdrawBtcAddrPhoneCode = util.trim($("#withdrawBtcAddrPhoneCode").val());
            if (!/^[0-9]{6}$/.test(withdrawBtcAddrPhoneCode)) {
                util.showerrortips("binderrortips", util.getLan("comm.tips.3"));
                $("#withdrawBtcAddrPhoneCode").val("");
                return;
            }
        }
		util.hideerrortips("binderrortips");
		var url = "/index.php?s=exc&c=financeController&m=saveWithdrawAddress";
		var param = {
			withdrawAddr : withdrawAddr,
			totpCode : withdrawBtcAddrTotpCode,
			phoneCode : withdrawBtcAddrPhoneCode,
			symbol : symbol,
			password : withdrawBtcPass,
			remark : withdrawRemark
		};
		var callback = function(result) {
			if (result.code == 200) {
				window.location.reload(true);
			} else {
				util.showerrortips("binderrortips", result.msg);
			}
		};
		util.network({
			btn : ele,
			url : url,
			param : param,
			success : callback,
		});
	},
	detelCoinAddress : function(fid) {
		util.layerConfirm("确定删除？", function() {
			var url = "/index.php?s=exc&c=financeController&m=delWithdrawAddr";
			var symbol = $("#symbol").val();
			var param = {
				fid : fid,
				symbol : symbol
			};
			var callback = function(data) {
				if (data.code == 200) {
					util.layerAlert("", data.msg, 1);
				} else {
					util.layerAlert("", data.msg, 2);
				}
			};
			util.network({
				url : url,
				param : param,
				success : callback,
			});
		});
	},
	showcode : function() {
		var code = $(".addresscode");
		$.each(code, function(key, value) {
			var fid = $(value).data().fid;
			var text = $(value).data().text;
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				$('#qrcode' + fid).qrcode({
					text : text,
					width : "145",
					height : "146",
					render : "table"
				});
			} else {
				$('#qrcode' + fid).qrcode({
					text : text,
					width : "145",
					height : "146"
				});
			}
			$(value).parent().css("z-index", 1000 - Number(fid));
		});
	}
};
$(function() {
	$("#withdrawBtcAddrBtn").on("click", function() {
		assets.addCoinAddress(this);
	});
	$(".coin-item-del").on("click", function() {
		assets.detelCoinAddress($(this).data().fid);
	});
	assets.showcode();
	$("#withdrawAccountAddr2").bind("copy cut paste", function(e) {
		return false;
	});
	$('.send-msg').click(function(){

        var obj = this;
        var url = "/index.php?s=exc&c=financeController&m=smsBank";
        var param = {

        };
        var callback = function(data) {
            if( data.code ==200){
                util.counter(obj,120,'重新发送');
               util.layerAlert('','发送成功',1,function(){})
            } else{
                util.layerAlert('',data.msg,2,function(){})
            }
        };
        util.network({url : url,
            param : param,
            success : callback});
    });
});