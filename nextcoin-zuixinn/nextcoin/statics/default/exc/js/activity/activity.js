var activity = {
    exchange: function (that) {
        var code = util.trim($("#exchangeCode").val());
        var patrn = /[a-zA-Z0-9]+/;
        if (code == "" || code.length != 16 || !patrn.exec(code)) {
            desc = util.getLan("activity.tips.1");
            util.layerAlert("", desc, 2);
            return;
        }

        url = "/activity/exchange.html";
        var param = {
            code: code
        };

        var callback = function (data) {
            if (data.code == 200) {
                util.layerAlert("", data.msg, 1);
            } else {
                util.layerAlert("", data.msg, 2);
            }
        };

        util.network({
            btn: that,
            url: url,
            param: param,
            success: callback,
        });
    },
    getExchangeLog: function (currentPage) {
        var url = "/activity/index_json.html";
        var callback = function (res) {
            if (res.code == '200') {
                var data = res.data;
                var content = data.frewardcodes;
                var len = content.length;
                var item;
                var str = '';
                for (var i = 0; i < len; i++) {
                    item = content[i];
                    str += '<tr class="financial-table-item">' +
                        '<td>' + (i + 1) + '</td>' +
                        '<td>' + item.ftype_s + '充值</td>' +
                        '<td>' + item.famount + '</td>' +
                        '<td>' + item.fcode + '</td>' +
                        '<td>' + (item.fstate ? "充值成功" : "充值失败") + '</td>' +
                        '<td>' + util.timeFormat(item.fcreatetime) + '</td>' +
                        '</tr>';
                }
                $('#exchange-data').html(str);
                //调用分页
                util.pageNation('exchange-page', currentPage, data.totalPages);
            }
        }
        util.network({url: url, success: callback});
    }
};

$(function() {
	$("#exchange").click(function() {
		activity.exchange(this);
	});
	$(".rewordtitle").on("click", function() {
		util.recordTab($(this));
	});
});