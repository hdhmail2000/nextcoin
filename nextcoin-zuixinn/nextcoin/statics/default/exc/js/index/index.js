var WS_URL = 'wss://api.huobi.pro/ws';

var symbolList = new Array();

var login = {
    lastPriceList : {},
    login: function (ele) {
        var url = "/index.php?s=exc&c=userController&m=login";
        var uName = $("#indexLoginName").val();
        var pWord = $("#indexLoginPwd").val();
        var longLogin = 0;
        if (util.checkEmail(uName)) {
            longLogin = 1;
        }
        var des = util.isPassword(pWord);
        if (des != "") {
            util.layerAlert("", des);
            return;
        }
        var param = {
            loginName: uName,
            password: pWord,
            type: longLogin
        };

        var callback = function (data) {
            if (data.code == 200) {
                if ($("#forwardUrl").length > 0 && $("#forwardUrl").val() != "") {
                    var forward = $("#forwardUrl").val();
                    forward = decodeURI(forward);
                    window.location.href = forward;
                } else {
                    var whref = document.location.href;
                    if (whref.indexOf("#") != -1) {
                        whref = whref.substring(0, whref.indexOf("#"));
                    }
                    window.location.href = whref;
                }
            } else {
                util.layerAlert("", data.msg, 2);
                $("#indexLoginPwd").val("");
            }
        }
        ele = ele || $("#loginbtn")[0];
        util.network({btn: ele, url: url, param: param, success: callback, enter: true});
    },
    newsHover: function (ele) {
        $(".news-items").removeClass("active");
        $(".news-items").stop().animate({width: "345px"}, 50);
        $(ele).stop().animate({width: "450px"}, 50);
        $(ele).addClass("active");
    },
    aotoMarket: function () {
        alert('auto market');
        var green = "#72c02c";
        var red = "#e74c3c";
        var url = "/index.php?s=exc&c=indexController&m=indexMarket";
        var callback = function (result) {
            if (result.code != 200) {
                return;
            }

			var marketObject = {};
            $.each(result.data, function (index, data) {
                var market = "";
                var color = red;
                /*if(typeof(login.lastPriceList[data.tradeId]) !== "undefined" && login.lastPriceList[data.tradeId] < data.price){
                    color = green;
                }*/
                if(Number(data.rose)>=0){
                    color = green;
                }

                login.lastPriceList[data.tradeId] = data.rose;
                market += '<tr class="area-table-item">' +
                    '<td class="area-table-name">' +
                    '<dl class="clearfix">' +
                    '<dt class="area-table-name-img left">' +
                    '<img src="'+data.image+'" alt="" />' +
                    '</dt>' +
                    '<dd class="area-table-name-text">' +
                    '<h3>'+data.sellShortName+'</h3>' +
                    '<p>'+data.sellname+'</p>' +
                    '</dd>' +
                    '</dl>' +
                    '</td>' +
                    '<td>' +
                    '<div class="area-table-num danger clearfix">' +
                    '<div class="left">' +
                    '<h3>'+data.buysymbol+''+util.numFormat(data.price, data.cnyDigit)+'</h3>' +
                    '<p>￥9375.36</p>' +
                    '</div>' +
                    '<i class="iconfont icon-jiang left"></i>' +
                    '</div>' +
                    '</td>' +
                    '<td>'+util.numFormat(data.total, data.coinDigit)+''+data.sellsymbol+'</td>' +
                    '<td>$115,381,984,000</td>' +
                    '<td style="color:'+color+'">'+data.rose+'%</td>' +
                    '<td>' +
                    '<div class="area-table-img">' +
                    '<img src="/statics/exc/dota/img/kline.jpg" alt="" />' +
                    '</div>' +
                    '</td>' +
                    '<td class="hint"><a href="/index.php?s=exc&c=TradeController&symbol='+data.treadId+'&type='+data.type+'&sb='+data.sellShortName+'_'+data.buyShortName+'">'+util.getLan("index.go.trade")+'</a></td>' +
                    '</tr>';

                marketObject[data.type] =
                    (typeof marketObject[data.type] === "undefined" ? "" : marketObject[data.type]) + market;
            });
            $(".child-market", ".market").remove();
            for (var type in marketObject) {
                $("#marketType" + type).append(marketObject[type]);
            }
        };
        util.network({url: url, param: {}, success: callback});
        setTimeout(login.aotoMarket, 5000);
    },
    aotoMarket2: function () {
        var green = "#72c02c";
        var red = "#e74c3c";
        var url = "/index.php?s=exc&c=indexController&m=indexMarket";
        var callback = function (result) {
            if (result.code != 200) {
                return;
            }

			var marketObject = {};
            $.each(result.data, function (index, data) {
                var market = "";
                var color = red;

            		symbolList[data.block+"_"+data.sellShortName+ "_" +data.buyShortName] = data.treadId;
                /*if(typeof(login.lastPriceList[data.tradeId]) !== "undefined" && login.lastPriceList[data.tradeId] < data.price){
                    color = green;
                }*/
                if(Number(data.rose)>=0){
                    color = green;
                }

                login.lastPriceList[data.tradeId] = data.rose;
                market += '<tr  data-symbol="'+data.treadId+'" data-status="'+data.status+'"class="area-table-item child-market '+data.sellShortName+data.buyShortName+'">' +
                    '<td class="area-table-name">' +
                    '<h3>'+
                    '<a href="" style="color:#fff;">'+
                    '</a>'+
                    '</h3>'+
                    '</td>' +
                    '<td>' +
                    ''+
                    '</td>' +
                    '<td class="coin-price">'+
                    '<h3>'+
                    '<a href="" style="color:#fff;">'+
                    '</a>'+
                    '</h3>'+
                    '</td>'+
                    '<td class="coin-buy">'+
                    '<h3>'+
                    '<a href="" style="color:#fff;">'+
                    '</a>'+
                    '</h3>'+
                    '</td>'+
                    '<td class="coin-sell">'+
                    '<h3>'+
                    '<a href="" style="color:#fff;">'+
                    '</a>'+
                    '</h3>'+
                    '</td>'+
                    '<td class="coin-vol"></td>'+
                    '</tr>';
                marketObject[data.block+"_"+data.type] =
                    (typeof marketObject[data.block+"_"+data.type] === "undefined" ? "" : marketObject[data.block+"_"+data.type]) + market;

                if(data.status == 3) {
                    subscribeList.push(data.sellShortName + data.buyShortName);
                }else{
                    platformSubscribe.push({"symbol":data.treadId,"id":data.sellShortName + data.buyShortName});
                }
                // subscribeList.push(data.sellShortName+data.buyShortName);

            });

            $(".child-market").remove();
            for (var type in marketObject) {
                $("#marketType" + type + '_data').append(marketObject[type]);
            }
            var _data = [];
            var _category = [];
            for (var i = 0; i < 10; i++) {
                _data.push(1);
                _category.push(i);
            }

            var readers = document.getElementsByClassName('coin-trend-render');
            for(var i=0;i<readers.length;i++){
                var myChart = echarts.init(readers[i]);

                var option = {
                    tooltip: {},
                    legend: {
                        data:['销量']
                    },
                    grid:{
                        left:'5%',
                        top:'50%',
                        right:'5%',
                        bottom:'5%',
                    },
                    xAxis: {
                        show:false,
                        type:'category',
                        data: _category,
                    },
                    yAxis: {
                        show:false,
                    },
                    series: [{
                        type: 'line',
                        data: _data,
                        smooth:true,
                        symbol: 'none',
                        markLine:{
                            silent:true,
                        }
                    }],
                    animation:false,
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
            if(!isConnection){
                doConnection();
            }
        };
        util.network({url: url, param: {}, success: callback});


        function fetchRealTimePrice1() {
            var symbols = [];
            $(".child-market").each(function(index,item){
                if($(item).data().status == 1){
                    symbols.push($(item).data().symbol);
                }
            });
            var url = "/real/markets.html";
            var symbol = symbols.join(',');
            var param = {
                symbol:symbol
            };
            var callback = function (data) {
                // console.log(data);alert(1);return;
                if (data.code == 200) {
                    var ihtml = '';
                    var cnythtml = '';
                    var usdthtml = '';
                    var btchtml = '';
                    var ethhtml = '';
                    var marketObject = {};
                    var title = $('.area-bt-item.active').attr('data-title');
                    var local = localStorage.getItem('trade_'+title);
                    var tradeArr = [];
                    if(local != undefined){
                        var arr = local.split('--');
                        for(var i = 0;i < arr.length;i++){
                            var t = JSON.parse(arr[i]);
                            tradeArr.push(t.tradeId);
                        }
                    }
                    for(var i=0;i<data.data.length;i++){
                        //sellSymbol
                        var level;
                        if((data.data[i].p_new) == 0){
                            level = 0;
                        } else {
                            level= Math.floor((data.data[i].p_new-data.data[i].p_open)/data.data[i].p_new*10000);
                        }
                        /* '<h3>'+
                            '<a href="" style="color:#fff;">'+
                            '$10000.00 <span style="font-size:.12rem;">≈  ¥ 65000.00</span>'+
                            '</a>'+
                            '</h3>'+*/
                        var dataCoin = data.data[i].sellSymbol.toLowerCase()+''+data.data[i].buySymbol.toLowerCase();
                        var cArr = "market."+dataCoin+".detail".match(/\.([a-z\d]+?)(btc|eth|usdt)\./i);
                        var level = level/100;
                        var levels = data.data[i].chg.toFixed('2');
                        /*

                       var tickData = {ch:"market."+dataCoin+".detail",tick:{
                           amount:data.data[i].total,
                           count:data.data[i].total,
                           open:data.data[i].p_open,
                           close:data.data[i].p_new,
                           high:data.data[i].sell,
                           low:data.data[i].buy,
                           vol:0,
                       }};*/

                        ihtml = '<tr data-symbol="'+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()]+'" data-status="1" class="area-table-item child-market" style="background: #0b052f;">';
                        if(tradeArr.indexOf(data.data[i].tradeId) >= 0){
                            ihtml += '<td style="width:5%;" class="area-star" >'+
                                '<img src="/statics/default/exc/fw/img/ysc.png" style="width:20px;" onclick=\'favorite__('+JSON.stringify(data.data[i])+')\'/>'+
                                '</td>';
                        }else{
                            ihtml += '<td style="width:5%;" class="area-star" >'+
                                '<img src="/statics/default/exc/fw/img/wsc.png" style="width:20px;" onclick=\'favorite('+JSON.stringify(data.data[i])+')\'/>'+
                                '</td>';
                        }
                        if('CNYT' == data.data[i].buySymbol){
                            ihtml += '<td class="area-table-name" style="width: 15%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                data.data[i].sellSymbol + ' <span style="font-size:.12rem;">/ ' + data.data[i].buySymbol + '</span>' + '</a>' + '</td>';
                            ihtml += '<td style="width: 10%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                levels + '%</a></td>';
                            ihtml += '<td style="width: 15%;">'+
                                '<h3>'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">￥'+
                                data.data[i].p_new +
                                '</a>'+
                                '</h3>'+
                                '</td>';
                            ihtml += '<td style="width: 20%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                '￥'+
                                data.data[i].sell +
                                '</a>'+
                                '</td>';
                            ihtml += '<td style="width: 20%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                '￥'+
                                data.data[i].buy + '</a>' + '</td>';
                            ihtml += '<td style="width: 15%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                data.data[i].total + " "  + data.data[i].sellSymbol + '</a>' + '</td>';
                            ihtml += '</tr>';
                        }else{
                            ihtml += '<td class="area-table-name" style="width: 10%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                data.data[i].sellSymbol + ' <span style="font-size:.12rem;">/ ' + data.data[i].buySymbol + '</span>' + '</a>' + '</td>';
                            ihtml += '<td style="width: 10%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                levels + '%</a></td>';
                            ihtml += '<td style="width: 20%;">'+
                                '<h3>'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">$'+
                                data.data[i].p_new +' <span style="font-size:.12rem;">≈ ￥'+(exchangeCNY(data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase(),data.data[i].p_new)).toFixed(2) + '</span>'+
                                '</a>'+
                                '</h3>'+
                                '</td>';
                            ihtml += '<td style="width: 20%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                '$'+
                                data.data[i].sell +' <span style="font-size:.12rem;">≈ ￥'+ (exchangeCNY(data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase(),data.data[i].sell)).toFixed(2) + '</span>'+
                                '</a>'+
                                '</td>';
                            ihtml += '<td style="width: 20%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                '$'+
                                data.data[i].buy +' <span style="font-size:.12rem;">≈ ￥'+ (exchangeCNY(data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase(),data.data[i].buy)).toFixed(2) + '</span>'+ '</a>' + '</td>';
                            ihtml += '<td style="width: 15%;">'+
                                '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                                data.data[i].total + " "  + data.data[i].sellSymbol + '</a>' + '</td>';
                            ihtml += '</tr>';
                        }

                        //
                        if ('CNYT' == data.data[i].buySymbol) {
                            cnythtml += ihtml;
                            var type = 1;
                        }
                        if ('USDT' == data.data[i].buySymbol) {
                            usdthtml += ihtml;
                            var type = 2;
                        }
                        if ('BTC' == data.data[i].buySymbol) {
                            btchtml += ihtml;
                            var type = 3;
                        }
                        if ('ETH' == data.data[i].buySymbol) {
                            ethhtml += ihtml;
                            var type = 4;
                        }
                        if ('QQB' == data.data[i].buySymbol) {
                            ethhtml += ihtml;
                            var type = 5;
                        }
                        marketObject[data.data[i].block+"_"+type] =
                            (typeof marketObject[data.data[i].block+"_"+type] === "undefined" ? "" : marketObject[data.data[i].block+"_"+type]) + ihtml;
                        //保存GSET对其他币种的价格
                        exchangeRate[data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] = data.data[i].p_new;
                    }
                    $(".child-market").remove();
                    for (var type in marketObject) {
                        $("#marketType" + type + '_data').append(marketObject[type]);
                    }
//          $("#marketType1_data").html(usdthtml);
//          $("#marketType2_data").html(btchtml);
//          $("#marketType3_data").html(ethhtml);
                }
            };
            util.network({url: url, param: param, success: callback});


        }
        window.setTimeout(function () {
            fetchRealTimePrice1();
        }, 500);


    },
    
    aotoMarket3: function () {
        alert('auto market3');
        var green = "#72c02c";
        var red = "#e74c3c";
        var url = "/index.php?s=exc&c=indexController&m=indexMarket";
        var callback = function (result) {
            if (result.code != 200) {
                return;
            }

            var marketObject = {};
            $.each(result.data, function (index, data) {
                var market = "";
                var color = red;

            		symbolList[data.block+"_"+data.sellShortName+ "_" +data.buyShortName] = data.treadId;
                /*if(typeof(login.lastPriceList[data.tradeId]) !== "undefined" && login.lastPriceList[data.tradeId] < data.price){
                    color = green;
                }*/
                if(Number(data.rose)>=0){
                    color = green;
                }

                login.lastPriceList[data.tradeId] = data.rose;
                if($("#search_symbol").val() != null || $("#search_symbol").val() != ""){
                		if(data.sellShortName.trim() == $("#search_symbol").val()){
                			market += '<tr  data-symbol="'+data.treadId+'" data-status="'+data.status+'"class="area-table-item child-market '+data.sellShortName+data.buyShortName+'">' +
		                    '<td class="area-table-name">' +
		                    '<h3>'+
		                    '<a href="" style="color:#fff;">'+
		                    '</a>'+
		                    '</h3>'+
		                    '</td>' +
		                    '<td>' +
		                    ''+
		                    '</td>' +
		                    '<td class="coin-price">'+
		                    '<h3>'+
		                    '<a href="" style="color:#fff;">'+
		                    '</a>'+
		                    '</h3>'+
		                    '</td>'+
		                    '<td class="coin-buy">'+
		                    '<h3>'+
		                    '<a href="" style="color:#fff;">'+
		                    '</a>'+
		                    '</h3>'+
		                    '</td>'+
		                    '<td class="coin-sell">'+
		                    '<h3>'+
		                    '<a href="" style="color:#fff;">'+
		                    '</a>'+
		                    '</h3>'+
		                    '</td>'+
		                    '<td class="coin-vol"></td>'+
		                    '</tr>';
                		}
                }
                marketObject[data.block+"_"+data.type] =
                    (typeof marketObject[data.block+"_"+data.type] === "undefined" ? "" : marketObject[data.block+"_"+data.type]) + market;

                if(data.status == 3) {
                    subscribeList.push(data.sellShortName + data.buyShortName);
                }else{
                    platformSubscribe.push({"symbol":data.treadId,"id":data.sellShortName + data.buyShortName});
                }
                // subscribeList.push(data.sellShortName+data.buyShortName);

            });

            $(".child-market").remove();
            for (var type in marketObject) {
                $("#marketType" + type + '_data').append(marketObject[type]);
            }
            var _data = [];
            var _category = [];
            for (var i = 0; i < 10; i++) {
                _data.push(1);
                _category.push(i);
            }

            var readers = document.getElementsByClassName('coin-trend-render');
            for(var i=0;i<readers.length;i++){
                var myChart = echarts.init(readers[i]);

                var option = {
                    tooltip: {},
                    legend: {
                        data:['销量']
                    },
                    grid:{
                        left:'5%',
                        top:'50%',
                        right:'5%',
                        bottom:'5%',
                    },
                    xAxis: {
                        show:false,
                        type:'category',
                        data: _category,
                    },
                    yAxis: {
                        show:false,
                    },
                    series: [{
                        type: 'line',
                        data: _data,
                        smooth:true,
                        symbol: 'none',
                        markLine:{
                            silent:true,
                        }
                    }],
                    animation:false,
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
            if(!isConnection){
                doConnection();
            }
        };
        util.network({url: url, param: {}, success: callback});
    },
    
    switchMarket: function () {
        $(".trade-tab").on("click", function () {
            var $that = $(this);
            var dataClass = $that.data().market;
            $(".trade-tab").removeClass("active");
            $that.addClass("active");
            $(".market-con").hide();
            $("#" + dataClass).show();
        })
        $(".area-bt-item").on("click", function () {
            var $that = $(this);
            var dataClass = $that.data().market;
            var title = $that.data().title;
            $that.parent().siblings().children().removeClass("active");
            $that.addClass("active");
            $("#marketType"+dataClass+"_data").siblings().removeClass("active");
            $("#marketType"+dataClass+"_data").addClass("active");
            $('.jiaoyiqu-title').html('('+title+')');
//          $(".market-con").hide();
//          $("#" + dataClass).show();
        })
    }
};

//搜索
$("#search_btn").click(function(){
	if($("#search_symbol").val().trim() == ""){
		window.location.reload();
	} else {
		login.aotoMarket3();
	}
});


document.onkeydown = function(e){
    if(e.keyCode == 13){
    		if($("#search_symbol").val().trim() == ""){
			window.location.reload();
		} else {
			login.aotoMarket3();
		}
    }
}

var ws;

var subscribeList = [];

var platformSubscribe = [];

var isConnection = false;

//usdt_cny
var USDT_CNY = 6.5;

//需要计算btc 等于多少usdt
var BTC_USDT = 0;

var ETH_USDT = 0;


function doConnection(){
    //获取k线图数据  平台
    for(var i =0;i<platformSubscribe.length;i++){
        fetchPlatformKLine(platformSubscribe[i].symbol,platformSubscribe[i].id);
    }
    ws = new WebSocket(WS_URL);
    setMessageEvent();


    ws.onopen = function () {
        isConnection = true;
        //订阅这两个是为了计算CNY值


        ws.send(JSON.stringify({"sub":"market.btcusdt.detail","id":"btcusdt"}));
        ws.send(JSON.stringify({"sub":"market.ethusdt.detail","id":"ethusdt"}));
        if (subscribeList.length == 0){
            login.aotoMarket2();
            return;
        }

        for(var i =0;i<subscribeList.length;i++){
            var subscribe = subscribeList[i];
            var fromTime = parseInt(new Date().getTime()/1000 - 3*24*60*60);
            var toTime = parseInt(new Date().getTime()/1000);
            ws.send(JSON.stringify({"sub":"market."+subscribe+".detail","id":subscribe}));
            ws.send(JSON.stringify({"req":"market."+subscribe+".kline.60min","id":subscribe,"from":fromTime,"to":toTime}));
            ws.send(JSON.stringify({"req":"market."+subscribe+".kline.60min","id":subscribe,"from":fromTime,"to":toTime}));
        }
        //获取k线图数据
        // for(var i =0;i<platformSubscribe.length;i++){
        //     fetchPlatformKLine(platformSubscribe[i].symbol,platformSubscribe[i].id);
        // }


    };
}

function setMessageEvent(){
    ws.onmessage = function(event){
        var blob = event.data;
        var reader = new FileReader();
        reader.onload = function(e){
            var ploydata = new Uint8Array(e.target.result);
            var msg = pako.inflate(ploydata,{to:'string'});


            handleData(msg);
        };
        reader.readAsArrayBuffer(blob,"utf-8");
    };
    ws.onclose = function(){
        //console.log('connection closed');
    };
}

function handleData(msg){
    var data = JSON.parse(msg);
    if (data.ping) {
        sendHeartMessage(data.ping);
        return;
    }

    if (data.rep && data.status == 'ok') {
        handleKlineData(data);
        return;
    }

    if (data.tick) {
        handleTickData(data);
        return;
    }
}

//K线
function handleKlineData(data){

    var _data = [];
    var _category = [];
    for (var i = 0; i < data.data.length; i++) {
        var dataCell = data.data[i];
        _data.push(dataCell.close);
        _category.push(i);
    }

    var myChart = echarts.init(document.getElementsByClassName(data.id)[0].getElementsByClassName('coin-trend-render')[0]);

    // 指定图表的配置项和数据
    var option = {
        tooltip: {},
        legend: {
            data:['销量']
        },
        grid:{
            left:'5%',
            top:'5%',
            right:'5%',
            bottom:'5%',
        },
        xAxis: {
            show:false,
            type:'category',
            data: _category,
        },
        yAxis: {
            show:false,
        },
        series: [{
            type: 'line',
            data: _data,
            smooth:true,
            symbol: 'none',
            markLine:{
                silent:true,
            }
        }],
        animation:false,
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

}

function handleTickData(data){
    var level;
    if((data.tick.close) == 0){
         level = 0;
    } else {
         level= Math.floor((data.tick.close-data.tick.open)/data.tick.close*10000);
    }

    var cArr = data['ch'].match(/\.([a-z\d]+?)(btc|eth|usdt)\./i);
    var dataCoin = cArr[1] + cArr[2];

    //btc对应的usdt价格
    if (dataCoin == 'btcusdt') {
        BTC_USDT = data.tick.close;
    }

    //eth对应的usdt价格
    if (dataCoin == 'ethusdt') {
        ETH_USDT = data.tick.close;
    }

$("."+dataCoin+" .coin-trend").removeClass('icon-jiang');
    $("."+dataCoin+" .coin-trend").removeClass('icon-sheng');
    if(level>0){
        $("."+dataCoin+" .coin-chg").attr("style","color:#72c02c");
        $("."+dataCoin+" .coin-chg").html("+"+level/100+"%");
        $("."+dataCoin+" .coin-trend").addClass('icon-sheng');

}else{
        $("."+dataCoin+" .coin-chg").html(level/100+"%");
        $("."+dataCoin+" .coin-chg").attr("style","color:#e74c3c");
        $("."+dataCoin+" .coin-trend").addClass('icon-jiang');
    }

    var preData = parseFloat($("."+dataCoin).attr('pre-data'));

    //上一次价格币本次低 表示升高

    $("."+dataCoin).attr('pre-data',data.tick.close);

    $("."+dataCoin+" .coin-price").html(data.tick.close); //成交额 即 sum(每一笔成交价 * 该笔的成交量)
    $("."+dataCoin+" .coin-price-cny").html('￥'+calcCNY(cArr[2],data.tick.close).toFixed(2)); //成交额 即 sum(每一笔成交价 * 该笔的成交量)
    $("."+dataCoin+" .coin-buy").html(data.tick.low); //最高价
    $("."+dataCoin+" .coin-sell").html(data.tick.high); //最低价
    var unit = $("."+dataCoin+" .coin-vol").attr('unit');
    var mount = new Number(data.tick.amount);
    	$("."+dataCoin+" .coin-vol").html(mount.toFixed(2)); //成交量

}


function calcCNY(symbol,value){
    var USDT_CNY_RATE = 0;
    var result = 0;
    if(symbol.toLowerCase() == 'usdt'){
        result = USDT_CNY_RATE * value;
    }else if(symbol.toLowerCase() == 'gset'){
        result = value;
    }else{
        result = exchangeRate[symbol+'_gset'] * value;
    }
    if (isNaN(result) || !result){
        return 0;
    }
    return result;
}
function exchangeCNY(symbol,value){
    //var USDT_CNY_RATE = 0;
    symbol = symbol.split('_')['1'];
    var result = 0;
    if(symbol.toLowerCase() == 'usdt'){
        result = USDT_CNY_RATE * value;
    }else if(symbol.toLowerCase() == 'gset'){
        result = value;
    }else{

        result = USDT_CNY_RATE * exchangeRate[symbol+'_usdt'] * value;
    }
    if (isNaN(result) || !result){
        return 0;
    }
    return result;
}
function sendHeartMessage(ping){
    //console.log("send heart msg");
    ws.send(JSON.stringify({"pong":ping}));
}


var exchangeRate = {};
/**
 * 平台
 * 左侧的实时价格
 */
function fetchRealTimePrice() {
    var symbols = [];
    $(".child-market").each(function(index,item){
        if($(item).data().status == 1){
            symbols.push($(item).data().symbol);
        }
    });
    var url = "/real/markets.html";
    var symbol = symbols.join(',');
    var param = {
        symbol:symbol
    };
    var callback = function (data) {
        // console.log(data);alert(1);return;
        if (data.code == 200) {
            var ihtml = '';
            var cnythtml = '';
            var usdthtml = '';
            var btchtml = '';
            var ethhtml = '';
            var marketObject = {};
            var title = $('.area-bt-item.active').attr('data-title');
            var local = localStorage.getItem('trade_'+title);
            var tradeArr = [];
			if(local != undefined){
				var arr = local.split('--');
				for(var i = 0;i < arr.length;i++){
					var t = JSON.parse(arr[i]);	
					tradeArr.push(t.tradeId);
				}
			}
            for(var i=0;i<data.data.length;i++){
            		//sellSymbol
            		var level;
			    if((data.data[i].p_new) == 0){
			         level = 0;
			    } else {
			         level= Math.floor((data.data[i].p_new-data.data[i].p_open)/data.data[i].p_new*10000);
			    }
			    /* '<h3>'+
                    '<a href="" style="color:#fff;">'+
                    '$10000.00 <span style="font-size:.12rem;">≈  ¥ 65000.00</span>'+
                    '</a>'+
                    '</h3>'+*/
                var dataCoin = data.data[i].sellSymbol.toLowerCase()+''+data.data[i].buySymbol.toLowerCase();
                var cArr = "market."+dataCoin+".detail".match(/\.([a-z\d]+?)(btc|eth|usdt)\./i);
                var level = level/100;
                var levels = data.data[i].chg.toFixed('2');
 				/*

                var tickData = {ch:"market."+dataCoin+".detail",tick:{
                    amount:data.data[i].total,
                    count:data.data[i].total,
                    open:data.data[i].p_open,
                    close:data.data[i].p_new,
                    high:data.data[i].sell,
                    low:data.data[i].buy,
                    vol:0,
                }};*/

            		ihtml = '<tr data-symbol="'+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()]+'" data-status="1" class="area-table-item child-market" style="background: #0b052f;">';
            		if(tradeArr.indexOf(data.data[i].tradeId) >= 0){
	            		ihtml += '<td style="width:5%;" class="area-star" >'+
	                         '<img src="/statics/default/exc/fw/img/ysc.png" style="width:20px;" onclick=\'favorite__('+JSON.stringify(data.data[i])+')\'/>'+
	                         '</td>';
            		}else{
	            		ihtml += '<td style="width:5%;" class="area-star" >'+
	                         '<img src="/statics/default/exc/fw/img/wsc.png" style="width:20px;" onclick=\'favorite('+JSON.stringify(data.data[i])+')\'/>'+
	                         '</td>';
                    }
                    if('CNYT' == data.data[i].buySymbol){
                        ihtml += '<td class="area-table-name" style="width: 15%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            data.data[i].sellSymbol + ' <span style="font-size:.12rem;">/ ' + data.data[i].buySymbol + '</span>' + '</a>' + '</td>';
                        ihtml += '<td style="width: 10%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            levels + '%</a></td>';
                        ihtml += '<td style="width: 15%;">'+
                            '<h3>'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">￥'+
                            data.data[i].p_new +
                            '</a>'+
                            '</h3>'+
                            '</td>';
                        ihtml += '<td style="width: 20%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            '￥'+
                            data.data[i].sell +
                            '</a>'+
                            '</td>';
                        ihtml += '<td style="width: 20%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            '￥'+
                            data.data[i].buy + '</a>' + '</td>';
                        ihtml += '<td style="width: 15%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            data.data[i].total + " "  + data.data[i].sellSymbol + '</a>' + '</td>';
                        ihtml += '</tr>';
                    }else{
                        ihtml += '<td class="area-table-name" style="width: 10%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            data.data[i].sellSymbol + ' <span style="font-size:.12rem;">/ ' + data.data[i].buySymbol + '</span>' + '</a>' + '</td>';
                        ihtml += '<td style="width: 10%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            levels + '%</a></td>';
                        ihtml += '<td style="width: 20%;">'+
                            '<h3>'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">$'+
                            data.data[i].p_new +' <span style="font-size:.12rem;">≈ ￥'+(exchangeCNY(data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase(),data.data[i].p_new)).toFixed(2) + '</span>'+
                            '</a>'+
                            '</h3>'+
                            '</td>';
                        ihtml += '<td style="width: 20%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            '$'+
                            data.data[i].sell +' <span style="font-size:.12rem;">≈ ￥'+ (exchangeCNY(data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase(),data.data[i].sell)).toFixed(2) + '</span>'+
                            '</a>'+
                            '</td>';
                        ihtml += '<td style="width: 20%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            '$'+
                            data.data[i].buy +' <span style="font-size:.12rem;">≈ ￥'+ (exchangeCNY(data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase(),data.data[i].buy)).toFixed(2) + '</span>'+ '</a>' + '</td>';
                        ihtml += '<td style="width: 15%;">'+
                            '<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+symbolList[data.data[i].block+"_"+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] +'&sb='+data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
                            data.data[i].total + " "  + data.data[i].sellSymbol + '</a>' + '</td>';
                        ihtml += '</tr>';
                    }

            		//
                if ('CNYT' == data.data[i].buySymbol) {
                    cnythtml += ihtml;
                    var type = 1;
                }
                if ('USDT' == data.data[i].buySymbol) {
                		usdthtml += ihtml;
                		var type = 2;
                }
                if ('BTC' == data.data[i].buySymbol) {
                		btchtml += ihtml;
                		var type = 3;
                }
                if ('ETH' == data.data[i].buySymbol) {
                		ethhtml += ihtml;
                		var type = 4;
                }
                if ('QQB' == data.data[i].buySymbol) {
                		ethhtml += ihtml;
                		var type = 5;
                }
                 marketObject[data.data[i].block+"_"+type] =
                    (typeof marketObject[data.data[i].block+"_"+type] === "undefined" ? "" : marketObject[data.data[i].block+"_"+type]) + ihtml;
                //保存GSET对其他币种的价格
                exchangeRate[data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] = data.data[i].p_new;
            }
			$(".child-market").remove();
            for (var type in marketObject) {
                $("#marketType" + type + '_data').append(marketObject[type]);
            }
//          $("#marketType1_data").html(usdthtml);
//          $("#marketType2_data").html(btchtml);
//          $("#marketType3_data").html(ethhtml);
        }
    };
    util.network({url: url, param: param, success: callback});

    window.setTimeout(function () {
        fetchRealTimePrice();
    }, 5000);
}

/**
 * 收藏
 * @param {Object} trade
 */
function favorite(trade){
	var title = $('.area-bt-item.active').attr('data-title');
	var target = JSON.stringify(trade);
	//读取localstorage
	var str = localStorage.getItem('trade_'+title);
	var tradeArr = [];
	if(str != undefined){
		var arr = str.split('--');
		for(var i = 0;i < arr.length;i++){
			var t = JSON.parse(arr[i]);		
			if(trade.tradeId == t.tradeId){
				return;
			}
		}
		str += '--'+target;
	}else{
		str = target;
	}
	
	//存入localstorage
	localStorage.setItem('trade_'+title,str);
}

/**
 * 取消收藏
 */
function favorite_(trade){
	var title = $('.area-bt-item.active').attr('data-title');
	var arr = localStorage.getItem('trade_'+title);
	arr = arr.split('--');
	var target;
	for(var i = 0 ;i < arr.length;i++){
		var t = JSON.parse(arr[i]);
		if(trade.tradeId == t.tradeId){
			target = i;
		}
	}
	arr.splice(target,1);
	
	//存入localstorage
	if(arr.length == 0){
		localStorage.removeItem('trade_'+title);
	}else{
		localStorage.setItem('trade_'+title,arr.join('--'));
	}
	zixuan();
}

/**
 * 取消收藏2
 */
function favorite__(trade){
	var title = $('.area-bt-item.active').attr('data-title');
	var arr = localStorage.getItem('trade_'+title);
	arr = arr.split('--');
	var target;
	for(var i = 0 ;i < arr.length;i++){
		var t = JSON.parse(arr[i]);
		if(trade.tradeId == t.tradeId){
			target = i;
		}
	}
	arr.splice(target,1);
	
	//存入localstorage
	if(arr.length == 0){
		localStorage.removeItem('trade_'+title);
	}else{
		localStorage.setItem('trade_'+title,arr.join('--'));
	}
}

function zixuan(){
	var title = $('.zx-btn-item.active').attr('data-title');
    $('#block1').hide();
    $('#block2').show();
                                		
    //读取localstorage数据
    var trade = localStorage.getItem('trade_'+title);
    if(trade == null){
    	$('#zixuanhtml').html('');
    	return;
    }
    var arr = trade.split('--');
    var ihtml	 ='' ;
    for(var i = 0;i<arr.length;i++){
    	arr[i] = JSON.parse(arr[i]);
        var level;
		if((arr[i].p_new) == 0){
		     level = 0;
		} else {
		    level= Math.floor((arr[i].p_new-arr[i].p_open)/arr[i].p_new*10000);
		}
		ihtml += '<tr data-symbol="'+arr[i].tradeId+"_"+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" data-status="1" class="area-table-item child-market" style="background: #0b052f;">';
		ihtml += '<td style="width:5%;" class="area-star" onclick=\'favorite_('+JSON.stringify(arr[i])+')\'>'+
                 '<img src="/statics/default/exc/fw/img/ysc.png" style="width:20px;"/>'+
                 '</td>';
		ihtml += '<td class="area-table-name" style="width: 10%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		arr[i].sellSymbol + ' <span style="font-size:.12rem;">/ ' + arr[i].buySymbol + '</span>' + '</a>' + '</td>';
		ihtml += '<td style="width: 10%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		 level/100 + '%</a></td>';
		ihtml += '<td style="width: 20%;">'+
			'<h3>'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">$'+
		 arr[i].p_new +' <span style="font-size:.12rem;">≈ ￥'+(exchangeCNY(arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase(),arr[i].p_new)).toFixed(2) + '</span>'+
		'</a>'+
		'</h3>'+
		'</td>';
		ihtml += '<td style="width: 20%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		'$'+
		arr[i].sell +' <span style="font-size:.12rem;">≈ ￥'+ (exchangeCNY(arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase(),arr[i].sell)).toFixed(2) + '</span>'+
		'</a>'+
		'</td>';
		ihtml += '<td style="width: 20%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		'$'+
		arr[i].buy +' <span style="font-size:.12rem;">≈ ￥'+ (exchangeCNY(arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase(),arr[i].buy)).toFixed(2) + '</span>'+ '</a>' + '</td>';
		ihtml += '<td style="width: 15%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		arr[i].total + " "  + arr[i].sellSymbol + '</a>' + '</td>';
		ihtml += '</tr>';
    }
    $('#zixuanhtml').html(ihtml);
}


function zx_change(){
	var title = $('.zx-btn-item.active').attr('data-title');
    //读取localstorage数据
    var trade = localStorage.getItem('trade_'+title);
    if(trade == null){
    	$('#zixuanhtml').html('');
    	return;
    }
    var arr = trade.split('--');
    var ihtml	 ='' ;
    for(var i = 0;i<arr.length;i++){
    	arr[i] = JSON.parse(arr[i]);
        var level;
		if((arr[i].p_new) == 0){
		     level = 0;
		} else {
		    level= Math.floor((arr[i].p_new-arr[i].p_open)/arr[i].p_new*10000);
		}
		ihtml += '<tr data-symbol="'+arr[i].tradeId+"_"+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" data-status="1" class="area-table-item child-market" style="background: #0b052f;">';
		ihtml += '<td style="width:5%;" class="area-star" onclick=\'favorite_('+JSON.stringify(arr[i])+')\'>'+
                 '<img src="/statics/default/exc/fw/img/ysc.png" style="width:20px;"/>'+
                 '</td>';
		ihtml += '<td class="area-table-name" style="width: 10%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		arr[i].sellSymbol + ' <span style="font-size:.12rem;">/ ' + arr[i].buySymbol + '</span>' + '</a>' + '</td>';
		ihtml += '<td style="width: 10%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		 level/100 + '%</a></td>';
		ihtml += '<td style="width: 20%;">'+
			'<h3>'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">$'+
		 arr[i].p_new +' <span style="font-size:.12rem;">≈ ￥'+(exchangeCNY(arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase(),arr[i].p_new)).toFixed(2) + '</span>'+
		'</a>'+
		'</h3>'+
		'</td>';
		ihtml += '<td style="width: 20%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		'$'+
		arr[i].sell +' <span style="font-size:.12rem;">≈ ￥'+ (exchangeCNY(arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase(),arr[i].sell)).toFixed(2) + '</span>'+
		'</a>'+
		'</td>';
		ihtml += '<td style="width: 20%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		'$'+
		arr[i].buy +' <span style="font-size:.12rem;">≈ ￥'+ (exchangeCNY(arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase(),arr[i].buy)).toFixed(2) + '</span>'+ '</a>' + '</td>';
		ihtml += '<td style="width: 15%;">'+
		'<a href="/index.php?s=exc&c=TradeController&type=1&symbol='+arr[i].tradeId +'&sb='+arr[i].sellSymbol.toLowerCase()+'_'+arr[i].buySymbol.toLowerCase()+'" style="color:#fff;">'+
		arr[i].total + " "  + arr[i].sellSymbol + '</a>' + '</td>';
		ihtml += '</tr>';
    }
    $('#zixuanhtml').html(ihtml);
}


/**
 * 获取平台k线
 */
function fetchPlatformKLine(symbol,_id) {
    var url = "/kline/fullperiod.html";
    var param = {
        symbol:symbol,
        step:900
    };
    var callback = function (data) {
        if(data.length==0){
            return;
        }
        var _data = compatHuoBiKLine(data);
        handleKlineData({id:_id,data:_data});
    };
    util.network({url: url, param: param, success: callback});
}

function compatHuoBiKLine(data) {
    return (data != null && data.length >0)?data.map(function(item){
        return {high:item[1],open:item[2],low:item[3],close:item[4]};
    }):[];
}
function getExchangeRate() {
    //var url = "/index.php?s=exc&c=tradeController&m=rate";
    var url = "/market/rate.html";
    var callback = function(response){
        if(response.code == 200){

            USDT_CNY_RATE = response.data.CNY;
        }
    };
    util.network({
        url : url,
        success : callback
    });

    // setTimeout(function(){
    //     getExchangeRate();
    // },5000);
}
var USDT_CNY_RATE = 0;

// function dig_circle(){
//     var url = "/index.php?s=exc&c=indexController&m=dig_circle";
//     var callback = function(response) {
//         var width = response.jd+'%';
//         $('.dig-box-progress div:first').css('width',width);
//         $('.dig-progress-left span:first').text(response.mining_total);
//         $('.dig-progress-right span:first').text(response.coin_surplus);
//         $('#total_product h3').text(response.mining);
//         if (response.mining_btc == 'inf') {
//             var mining_btc = 1;
//         } else {
//             var mining_btc = 0;
//         }
//         if (response.total_dig == null) {
//             var total_dig = 0;
//         } else {
//             var total_dig = response.total_dig;
//         }
//         $('#total_product span').text(mining_btc);
//         $('#total_dig').text(total_dig);
//     };
//     util.network({
//         url:url,
//         success:callback
//     });
//
//     window.setTimeout(function () {
//         dig_circle();
//     }, 1500);
// }

$(function () {
    $("#indexLoginPwd").on("focus", function () {
        util.callbackEnter(login.login);
    });
    $("#loginbtn").on("click", function () {
        login.login(this);
    });
    $(".news-items").mouseover(function (ele) {
        login.newsHover(this);
    });
	getExchangeRate();
    login.switchMarket();
    login.aotoMarket2();

//     doConnection(); //ws连接
//     setMessageEvent();
    fetchRealTimePrice();
    // dig_circle();


});
