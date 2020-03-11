var HOST = '47.74.231.13:8080';
var upColor = '#00da3c';
var downColor = '#ec0000';

var myChart = echarts.init(document.getElementById('chart-box'));

var myChart2 = null;

var WS_URL  = 'wss://api.huobi.pro/ws';
// var WS_URL = 'wss://api.huobipro.com/ws';

var PERIOD_1MIN = "1min";

var PERIOD_5MIN = "5min";

var PERIOD_15MIN = "15min";

var PERIOD_30MIN = "30min";

var PERIOD_60MIN = "60min";

var PERIOD_1DAY = "1day";

var PERIOD_1WEEK = "1week";

var PERIOD_1MON = "1month";


var priceFixed = {'BTC':8,'USDT':4,'ETH':8,'DOTA':4};
var numberFixed = {'BTC':2,'USDT':4,'ETH':2,'DOTA':4};
//开始时间
var _from = 0;

//目标时间，默认是现在
var _to = formatDate(new Date());

//周期，默认15分钟
var _period = PERIOD_15MIN;

var periodValue = 60 * 15;

var chartAnimation = true;

//usdt_cny
var USDT_CNY = 6.5;

//需要计算btc 等于多少usdt
var BTC_USDT = 0;
var symbol_tradeId = '';
//true平台，false火币
var isPlatformTrade = $("#isPlatformStatus").val() == 'true';
// isPlatformTrade = true;

//符号 bchusdt、btcusdt
var _symbol = document.getElementById('hide-symbol').getAttribute('value').replace("_","");

var sellBuy = document.getElementById('hide-symbol').getAttribute('value').split("_");
// document.getElementById('inner-price').innerHTML = '价格('+sellBuy[1].toUpperCase()+')';
// document.getElementById('inner-amount').innerHTML = '数量('+sellBuy[0].toUpperCase()+')';
// document.getElementById('inner-sum').innerHTML = '总价('+sellBuy[1].toUpperCase()+')';

//交易对
var liArray = document.getElementsByClassName('chart-panel')[0].getElementsByTagName('li');

//var ws = new WebSocket(WS_URL);
var isConnection = false;

for (var i = 0; i < liArray.length; i++) {
    var liObj = liArray[i];
    liObj.index = i;
    liObj.addEventListener('click',function(e){
        for (var j = 0; j < liArray.length; j++) {
            liArray[j].removeAttribute('class');
        }

        liArray[this.index].setAttribute('class','active');
        _period = liArray[this.index].innerHTML;
        chartAnimation = true;
        requestData();
        subscribeData();
    });
}

initTab();

function initTab(){
    var tabItems = document.getElementsByClassName('coin-tab')[0].getElementsByTagName('button');
    var contentItem = document.getElementsByClassName('coin-list-item');
    var urlItems = document.getElementsByClassName('coin-list')[0].getElementsByTagName('tr');
    for (var i = 0; i < tabItems.length; i++) {
        var tab = tabItems[i];
        tab.index = i;


        tab.addEventListener('click',function(){
            for (var j = 0; j < tabItems.length; j++) {
                tabItems[j].removeAttribute('class');
                contentItem[j].setAttribute('style','display:none');
            }
            this.setAttribute('class','active');
            contentItem[this.index].removeAttribute('style');
        });
    }

    //url链接
    for (var i = 0; i < urlItems.length; i++) {
        urlItems[i].addEventListener('click',function(){
            if (undefined == this.getAttribute('data-symbol') || "" == this.getAttribute('data-symbol') || null == this.getAttribute('data-symbol')) {
	        		//continue;
	        } else {
	        		window.location.href="/index.php?s=exc&c=TradeController&sb="+this.getAttribute('data-symbol')+"&type="+this.getAttribute("type")+"&symbol="+this.getAttribute("symbol");

	        }

        });
    }

    // //usdt交易区
    // if (sellBuy[1] == 'usdt') {
    //     tabItems[0].setAttribute('class','tab-active');
    //     contentItem[0].removeAttribute('style');
    // }else if (sellBuy[1] == 'btc') {
    //     tabItems[1].setAttribute('class','tab-active');
    //     contentItem[1].removeAttribute('style');
    // }
}

//获取url中某个参数的值
function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

/**
 *date 可以用setTime(毫秒) 修改时间
 */
function formatDate(date,pattern){
    var month = date.getMonth() + 1;
    if (month<10) {
        month = "0" + month;
    }

    var day = date.getDate();
    if (day<10) {
        day = "0" + day;
    }

    var hour = date.getHours();
    if (hour<10) {
        hour = "0" + hour;
    }

    var minute = date.getMinutes();
    if (minute<10) {
        minute = "0" + minute;
    }

    var seconds = date.getSeconds();
    if (seconds<10) {
        seconds = "0" + seconds;
    }

    var year = date.getFullYear();

    if (pattern) {
        return hour+":"+minute+":"+seconds;
    }
    return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+seconds;
}

function calculateMA(dayCount, data) {
    var result = [];
    for (var i = 0, len = data.length; i < len; i++) {
        if (i < dayCount) {
            result.push('-');
            continue;
        }
        var sum = 0;
        for (var j = 0; j < dayCount; j++) {
            sum += data[i - j][1];
        }
        result.push(sum / dayCount);
    }
    return result;
}

function setupData(_rawData){
    rawData = _rawData;

    var tempTime = _from * 1000;
    var data = rawData.map(function (item) {
        return [item.open,item.close,item.low,item.high];
    });
//Howe update 2018-5-26
    var date = new Date();
    var dates = _rawData.map(function (item) {
        date.setTime(item.time);
        return formatDate(date);;
    });
//  var date = new Date();
//  var dates = data.map(function (item) {
//      date.setTime(tempTime);
//      tempTime += periodValue*1000;
//      return formatDate(date);
//  });
//Howe update 2018-5-26 ---End----
    var volumes = rawData.map(function(item,index){
        return [index,item.vol,item.close>item.open?1:-1];
    });

    var option = {
        backgroundColor: '#282A36', //#282A36 #181B2A
        legend: {
            data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30'],
            inactiveColor: '#777',
            textStyle: {
                color: '#fff'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false,
                type: 'cross',
                lineStyle: {
                    color: '#376df4',
                    width: 2,
                    opacity: 1
                }
            }
        },
        visualMap: {
            show: false,
            seriesIndex: 5,
            dimension: 2,
            pieces: [{
                value: 1,
                color: downColor
            }, {
                value: -1,
                color: upColor
            }]
        },
        grid:[
            {
                left:0,
                right:80,
                height:'75%'
            },
            {
                left:0,
                right:80,
                top:'87%',
                height:'10%',
            }
        ],
        xAxis: [
            {	type: 'category',
                scale: true,
                boundaryGap : false,
                offset:0,
                type: 'category',
                data: dates,
                splitNumber: 20,
                splitLine: {show: true,lineStyle:{color:"#8392A5",width:0.2,opacity:0.5}},
                axisTick: {show:false},
                axisLabel: {show:false},
                axisLine: {show:true,onZero:false,lineStyle: { color: '#8392A5' } },
                min: 'dataMin',
                max: 'dataMax',
                axisPointer: {
                    z: 100
                }
            },

            {	type: 'category',
                gridIndex: 1,
                scale: true,
                boundaryGap : false,
                offset:0,
                type: 'category',
                data: dates,
                axisLine: {show:true,onZero:false,lineStyle: { color: '#8392A5',width:0.5} },
                min: 'dataMin',
                max: 'dataMax',
                axisPointer: {
                    z: 100
                }
            }
        ],
        yAxis: [
            {
                position:'right',
                scale: true,
                axisLine: { lineStyle: { color: '#8392A5' } },
                splitLine: { show: true,lineStyle:{color:'#8392A5',width:0.2}}
            },
            {
                position:'right',
                scale: true,
                gridIndex: 1,
                splitNumber: 2,
                axisLabel: {show: true},
                axisLine: {lineStyle: { color: '#8392A5'} },
                axisTick: {show: false},
                splitLine:{show:false},
                min: 'dataMin',
                max: 'dataMax',
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                xAxisIndex: [0, 1],
                start: 5,
                end: 100
            },
            {
                show: true,
                xAxisIndex: [0, 1],
                type: 'inside',
                top: '85%',
                start: 5,
                end: 100
            }
        ],
        animation: chartAnimation,
        series: [
            {
                type: 'candlestick',
                name: '日K',
                data: data,
                itemStyle: {
                    normal: {
                        color: '#0CF49B',
                        color0: '#FD1050',
                        borderColor: '#0CF49B',
                        borderColor0: '#FD1050'
                    }
                }
            },
            {
                name: 'MA5',
                type: 'line',
                data: calculateMA(5, data),
                smooth: true,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                }
            },
            {
                name: 'MA10',
                type: 'line',
                data: calculateMA(10, data),
                smooth: true,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                }
            },
            {
                name: 'MA20',
                type: 'line',
                data: calculateMA(20, data),
                smooth: true,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                }
            },
            {
                name: 'MA30',
                type: 'line',
                data: calculateMA(30, data),
                smooth: true,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                }
            },
            {
                name: 'Volume',
                type: 'bar',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data: volumes
            }
        ]
    };
    myChart.setOption(option);
}

//请求或则订阅的数据
function getSendData() {
    _from = parseInt(calculateFromDateLong());
    _to = parseInt(new Date().getTime() / 1000);
    var req = {"req": "market." + _symbol + ".kline." + _period, "id": _symbol, "from": _from, "to": _to};
    return JSON.stringify(req);
}

/**
 * 最多300条
 * @returns {number}
 */
function calculateFromDateLong(){
    var date = new Date();
    //取5小时以前
    if (_period == PERIOD_1MIN) {
        periodValue = 60;
        return date.getTime()/1000 - 5*60*60;
    }

    //取24小时以前
    if (_period == PERIOD_5MIN) {
        periodValue = 60*5;
        return date.getTime()/1000 -5*300*60;
    }

    //80小时以前
    if (_period == PERIOD_15MIN) {
        periodValue = 60*15;
        return date.getTime()/1000 - 15*300*60;
    }

    //5天的数据
    if (_period == PERIOD_30MIN) {
        periodValue = 60*30;
        return date.getTime()/1000 - 30*300*60;
    }

    //7天的数据
    if (_period == PERIOD_60MIN) {
        periodValue = 60*60;
        return date.getTime()/1000 - 60*300*60;
    }

    //2个月之前的数据
    if (_period == PERIOD_1DAY) {
        periodValue = 60*60*24;
        return date.getTime()/1000 - 24*60*300*60;
    }

    //2个办月之前的数据
    if (_period == PERIOD_1WEEK) {
        periodValue = 60*60*24*7;
        return date.getTime()/1000 -  7*24*60*300*60;
    }

    //三个月的数据
    if (_period == PERIOD_1MON) {
        periodValue = 60*60*24*7*4;
        return date.getTime()/1000 - 4*24*60*300*60;
    }
}


function requestData(){

    if (isPlatformTrade){
        //获取平台数据

        _from = parseInt(calculateFromDateLong());
        _to = parseInt(new Date().getTime() / 1000);

        var url = "/kline/fullperiod.html";
        //var url = HOST+"/kline/fullperiod.html";
        var param = {
            symbol:$("#symbol").val(),
            step:parseInt(_period) * 60
        };
        var callback = function (data) {
            handleResponseData(data);
        };
        util.network({url: url, param: param, success: callback});
        return;
    }

    var data = getSendData();
    //ws.send(data);
}

function subscribeData(){

    if (!isPlatformTrade) {
        ws.send(JSON.stringify({"unsub": "market." + _symbol + ".depth.step0", "id": _symbol}));
        ws.send(JSON.stringify({"unsub": "market." + _symbol + ".detail", "id": _symbol}));

        ws.send(JSON.stringify({"sub": "market." + _symbol + ".depth.step0", "id": _symbol}));
        ws.send(JSON.stringify({"sub": "market." + _symbol + ".detail", "id": _symbol}));

        //请求实时成交
        ws.send(JSON.stringify({"req": "market." + _symbol + ".trade.detail", "id": _symbol}));
        //订阅实时成交
        ws.send(JSON.stringify({"sub": "market." + _symbol + ".trade.detail", "id": _symbol}));
    }

    //订阅详情
    // ws.send(JSON.stringify({"sub":"market." + _symbol + ".kline." + _period,"id":_symbol}));
    // ws.send(JSON.stringify({"sub":"market.btcusdt.detail","id":"btcusdt"}));
    // ws.send(JSON.stringify({"sub":"market.bchusdt.detail","id":"bchusdt"}));
    // ws.send(JSON.stringify({"sub":"market.ltcusdt.detail","id":"ltcusdt"}));
    // ws.send(JSON.stringify({"sub":"market.ethusdt.detail","id":"ethusdt"}));
    // ws.send(JSON.stringify({"sub":"market.etcusdt.detail","id":"etcusdt"}));
    // ws.send(JSON.stringify({"sub":"market.btmusdt.detail","id":"btmusdt"}));

    ws.send(JSON.stringify({"sub":"market.bchbtc.detail","id":"bchbtc"}));
    ws.send(JSON.stringify({"sub":"market.ethbtc.detail","id":"ethbtc"}));
    ws.send(JSON.stringify({"sub":"market.etcbtc.detail","id":"etcbtc"}));
    ws.send(JSON.stringify({"sub":"market.btmbtc.detail","id":"btmbtc"}));
    ws.send(JSON.stringify({"sub":"market.ltcbtc.detail","id":"ltcbtc"}));

    ws.send(JSON.stringify({"sub":"market.btmeth.detail","id":"btmeth"}));

}
/*
ws.onopen = function () {
    isConnection = true;

    requestData();

    subscribeData();
};

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
    isConnection = false;
};*/

function showData(xData,buyData,sellData){
    if (myChart2 == null) {
        myChart2 = echarts.init(document.getElementById('depth-chart'));
    }
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: ''
        },
        tooltip : {
            show:true,
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor:'rgb(19,21,32)'
                }
            }
        },
        backgroundColor: '#282A36',
        legend: {
            data:['','','','','']
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : xData,
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:"#fff"
                    }
                },
                splitLine:{
                    show:false,
                },
                splitNumber: 20,

            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLine:{
                    show:true,
                    lineStyle:{
                        color:"#fff"
                    }
                },
                splitLine:{
                    show:false,
                },
            }
        ],
        series : [
            {
                name:'',
                type:'line',
                stack: '总量',
                symbol:"none",
                lineStyle: {color:"#00000000",opacity:1},
                areaStyle: {color:"green",opacity:0.8},
                data:buyData,
                markPoint:{
                    symbol:'rect'
                },
                symbolSize:"50px"
            },
            {
                name:'',
                type:'line',
                stack: '总量',
                symbol:"none",
                lineStyle: {color:"#00000000",opacity:1},
                areaStyle: {color:"red",opacity:0.8},
                data:sellData
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart2.setOption(option);
}

function handleData(msg){

    var data = JSON.parse(msg);


    if (data.ping) {
        //sendHeartMessage(data.ping);
        return;
    }
    if(data.status=='error'){
        console.log(data);
    }
    //请求k线
    if ( data.status=='ok' && data.rep && data.rep.indexOf('kline')>0 ) {
        handleResponseData(data);
        return
    }

    //请求实时交易
    if (data.status == 'ok' && data.ch && data.ch.indexOf("trade")>0) {
        handleTradeDetail(data.data);
        return
    }

    //订阅实时交易
    if (data.ch && data.ch.indexOf('trade')>0 && data.tick.data) {
        handleTradeDetail(data.tick.data,true);
        return;
    }

    //订阅详情
    if (data.ch && data.ch.indexOf("detail")>0 && data.tick) {
        handleDetail(data);
        return;
    }

    //订阅最新价
    if (data.tick) {

        handleTickData(data.tick);
        //深度图
        if( data.ch && data.ch.indexOf('depth')>0){
            handleDepthTick(data.tick);
            return;
        }
        return;
    }


}

/**
 * 深度图
 * @param tick
 */
function handleDepthTick(tick){
    var bids = tick.bids;
    var asks = tick.asks;

    var xData   = [];
    var buyData = [];
    var sellData = [];
    //买入
    for (var i = 0; i < bids.length; i++) {
        var bid = bids[i];
        xData.push(bid[0].toFixed(2));
        var total = 0;
        for (var j = 0; j <=i; j++) {
            total += bids[j][1];
        }
        buyData.push(total);
    }

    //卖出
    for (var i = 0; i < asks.length; i++) {
        var ask = asks[i];
        xData.push(ask[0].toFixed(2));
        var total = 0;
        for (var j = 0; j <=i; j++) {
            total += asks[j][1];
        }
        sellData.push(total);
    }

    //买
    buyData.sort(function(x,y){
        return y - x;
    });

    sellData.sort(function(x,y){
        return x - y;
    });

    //把后面部分的数据拼接成0
    buyData =  buyData.concat(new Array(asks.length));

    //把签名部分的数据拼接成0
    sellData = new Array(bids.length).concat(sellData);

    xData.sort(function(x,y){
        return x - y;
    });

    showData(xData,buyData,sellData);
}

//实时交易数据
function handleTradeDetail(data,isInsert){
    // var timeBox = document.getElementsByClassName('market-trades-time')[0];
    // var typeBox = document.getElementsByClassName('market-trades-type')[0];
    // var priceBox = document.getElementsByClassName('market-trades-price')[0];
    // var amountBox = document.getElementsByClassName('market-trades-amount')[0];
    var dataBox = document.getElementById('realTimeTrade');
    var type;
    var typeClass;
    var dates;
    var dataCell;
    var priceSymbol = dataBox.getElementsByClassName('real-trade-price')[0].innerHTML;
    var amountSymbol = dataBox.getElementsByClassName('real-trade-mount')[0].innerHTML;
    var str = '';
    str +='<tr class="quotes-deal-item">'
        +   '<td>'+ util.getLan('trade.tips.23')+'</td>'
        +    '<td>'+util.getLan('trade.tips.24')+'</td>'
        +   '<td class="real-trade-price">'+priceSymbol+'</td>'
        +   '<td class="real-trade-mount">'+amountSymbol+'</td></tr>';

    for (var i = 0; i < data.length; i++) {
         dataCell = data[i];
        //时间
        dates = new Date();
        dates.setTime(dataCell.ts);
        typeClass = dataCell.direction == 'sell'?'danger':'success';
        type = dataCell.direction == 'sell'?util.getLan('trade.tips.19'): util.getLan('trade.tips.18');
            str +='<tr class="quotes-deal-item">';
            str += '<td>'+formatDate(dates,true)+'</td>';
            str += '<td class="typeClass">'+type+'</td>';
            str += '<td>'+dataCell.price+'</td>';
            str += '<td>'+parseFloat(dataCell.amount).toFixed(4)+'</td>';
            str +='</tr>';
        // var timeDDTag = document.createElement('dd');
        // timeDDTag.appendChild(document.createTextNode(formatDate(date,true)));
        // if (isInsert) {
        //     var _dd = timeBox.getElementsByTagName('dd')[0];
        //     timeBox.insertBefore(timeDDTag,_dd);
        // }else{
        //     timeBox.appendChild(timeDDTag);
        // }
        //
        // //类型 卖、买
        // var typeDDTag = document.createElement('dd');
        // typeDDTag.appendChild(document.createTextNode(dataCell.direction == 'sell'?'卖出':'买入'));
        // typeDDTag.className = (dataCell.direction == 'sell'?'sell-color':'buy-color');
        // if (isInsert) {
        //     var _dd = typeBox.getElementsByTagName('dd')[0];
        //     typeBox.insertBefore(typeDDTag,_dd);
        // }else{
        //     typeBox.appendChild(typeDDTag);
        // }
        //
        // //价格
        // var priceDDTag = document.createElement('dd');
        // priceDDTag.appendChild(document.createTextNode(dataCell.price));
        // if (isInsert) {
        //     var _dd = priceBox.getElementsByTagName('dd')[0];
        //     priceBox.insertBefore(priceDDTag,_dd);
        // }else{
        //     priceBox.appendChild(priceDDTag);
        // }
        //
        // //数量
        // var amountDDTag = document.createElement('dd');
        // amountDDTag.appendChild(document.createTextNode(parseFloat(dataCell.amount).toFixed(4)));
        // if (isInsert) {
        //     var _dd = amountBox.getElementsByTagName('dd')[0];
        //     amountBox.insertBefore(amountDDTag,_dd);
        // }else{
        //     amountBox.appendChild(amountDDTag);
        // }
    }
    dataBox.innerHTML = str;
}

/**
 * 心跳
 * @param ping
 */
function sendHeartMessage(ping){
    //ws.send(JSON.stringify({"pong":ping}));
}

/**
 * k线数据
 * @param data
 */
function handleResponseData(data){

    setupData(compatHuoBiKLine(data));
    setTimeout(function () {
        chartAnimation = false;
        requestData();
    },2000);
}



//交易两个平台的数据  k线图系列函数
// function compatHuoBiKLine(data) {
//     if(isPlatformTrade){
//         return (data != null && data.length >0)? data.map(function(item){
//             return {high:item[1],open:item[2],low:item[3],close:item[4]};
//         }):[];
//     }
//     return data.data;
// }

//function compatHuoBiKLine(data) {
//  if(isPlatformTrade){
//      return (data != null && data.length >0)?data.map(function(item){
//          return {open:item[1],high:item[2],low:item[3],close:item[4],vol:item[5]};
//      }):[];
//  }
//  return data.data;
//}

//Howe  update 2018-05-26
function compatHuoBiKLine(data) {
    if(isPlatformTrade){
        return (data != null && data.length >0)?data.map(function(item){
            return {time:item[0],open:item[1],high:item[2],low:item[3],close:item[4],vol:item[5]};
        }):[];
    }
    return data.data;
}

//买卖区块
function handleTickData(tick){

    var symbol = sellBuy[1].toUpperCase();

    var dl = document.getElementsByClassName('cell');

    //卖
    var asks = tick.asks;
    //买
    var bids = tick.bids;

    var total = 7;
    var asksTotal = 0.0;
    var bidsTotal = 0.0;
    var askpre =0.0;
    var bidpre =0.0;

    //卖的总量
    //Howe Update 2018-05-26
//  for (var i = 0; i < dl.length && i<7; i++) {
//      asksTotal = (parseFloat(asksTotal) + parseFloat(asks[i][1])).toFixed(numberFixed[symbol]);
//  }
//
//  for (var i = 0; i < dl.length && i <7; i++) {
//      var d = dl[total-i-1];
//      $(d).find('b').css('width',( parseFloat(asks[i][1]).toFixed(4))/ asksTotal *100 +'%');
//      if( i==0 ){
//          askpre =  0.0;
//      } else{
//          askpre = parseFloat(asks[i][1] ) + parseFloat(askpre);
//      }
//
//      var spans = d.getElementsByTagName('span');
//          spans[1].innerHTML = (parseFloat(asks[i][0]).toFixed(priceFixed[symbol]));
//          spans[2].innerHTML = (parseFloat( asks[i][1])).toFixed(numberFixed[symbol]);
//          spans[3].innerHTML = (parseFloat( asks[i][1]) + parseFloat(askpre)).toFixed(numberFixed[symbol]);
//  }
	 for (var i = 0; i < dl.length && i<7; i++) {
                if (!asks[i]) continue;
                asksTotal = (parseFloat(asksTotal) + parseFloat(asks[i][1])).toFixed(numberFixed[symbol]);
            }

            for (var i = 0; i < dl.length && i <7; i++) {
                if(!asks[total-1-i] || !asks[i]) continue;
                var d = dl[total-i-1];
                $(d).find('b').css('width',( parseFloat(asks[total-1-i][1]).toFixed(4))/ asksTotal *100 +'%');
                if( i==0 ){
                    askpre =  0.0;
                } else{
                    askpre = parseFloat(asks[i][1] ) + parseFloat(askpre);
                }

                var spans = d.getElementsByTagName('span');
                spans[1].innerHTML = (parseFloat(asks[i][0]).toFixed(priceFixed[symbol]));
                spans[2].innerHTML = (parseFloat( asks[i][1])).toFixed(numberFixed[symbol]);
                spans[3].innerHTML = (parseFloat( asks[i][1]) + parseFloat(askpre)).toFixed(numberFixed[symbol]);
            }


    //bids_data.reverse();
    for (var i = total; i < dl.length && i<14 ; i++) {
        bidsTotal = ( parseFloat(bidsTotal) + parseFloat(bids[i-total][1]) ).toFixed(numberFixed[symbol]);
    }


    for (var i = total; i < dl.length && i<14; i++) {
        var d = dl[i];
        if(i==total){
            bidpre = 0.0;
        } else{
            bidpre = parseFloat(bids[i-total-1][1]) + parseFloat(bidpre);
        }
        $(d).find('b').css('width',( parseFloat(bids[i-total][1])).toFixed(4) / bidsTotal * 100 +'%');
        var spans = d.getElementsByTagName('span');
        spans[1].innerHTML = (parseFloat( bids[i-total][0]).toFixed(priceFixed[symbol]));
        spans[2].innerHTML = (parseFloat( bids[i-total][1])).toFixed(numberFixed[symbol]);
        spans[3].innerHTML = (parseFloat( bids[i-total][1]) + parseFloat(bidpre)).toFixed(numberFixed[symbol]);
    }
}

//设置两个交易框
function handleDetail_bak(data){
    var cArr = data['ch'].match(/\.([a-z\d]+?)(dota|btc|eth|usdt|ess1)\./i);
    //console.log(cArr);
    //var dataCoin = cArr[1] +'_'+ cArr[2];//交易对
    var dataCoin = data['dataCoin'];
    var level= Math.floor((data.tick.close-data.tick.open)/data.tick.close*10000);

    if(document.getElementsByClassName(dataCoin).length == 0){
        return;
    }
    //console.log(data);
    var price = document.getElementsByClassName(dataCoin)[0].getElementsByClassName('trade-price')[0];
    var rate  = document.getElementsByClassName(dataCoin)[0].getElementsByClassName('trade-rate')[0];


    level = level/100;
    if(isNaN(level) || !level){
        level = 0.00;
    }else{
        level = parseFloat(level).toFixed(2);
    }

    price.innerHTML = data.tick.close;


    if (level>0) {
        rate.setAttribute("style","color:#72c02c");
        rate.innerHTML = "+"+level+"%";
    }else{
        rate.setAttribute("style","color:#e74c3c");
        rate.innerHTML = level+"%";
    }

    //获取btc对应的usdt价格
    if ('BTC_USDT' == dataCoin.toUpperCase()) {
        BTC_USDT = data.tick.close;
    }
	strs = dataCoin.split("_"); //字符分割
;
    if((strs[0]+strs[1]) != _symbol){
        return;
    }
    var sell = document.getElementById('hide-symbol').getAttribute('value').split("_")[0];
    var span = document.getElementById('zf');
    span.removeAttribute('style');
    $('#high').html(util.getLan('trade.tips.20')+' '+ data.tick.high);
    $('#low').html(util.getLan('trade.tips.21') + ' ' +data.tick.low);
    var m_num = new Number(data.tick.amount);
    $('#vol').html('24H'+util.getLan('trade.tips.22')+ ' '+m_num.toFixed(4)+' '+sell.toUpperCase());
    $('#tip-price').html(data.tick.close);
    // $('#tip-cny').html(calculateCNY(data.tick.close,strs[1]).toFixed(2)+' CNY');
    $('#tip-cny').html(data.tick.close+' CNY');
    if (level>0) {
        span.setAttribute("style","color:#72c02c");
        span.innerHTML = "+"+level+"%";
    }else{
        span.setAttribute("style","color:#e74c3c");
        span.innerHTML = level+"%";
    }
    // var headerText = document.getElementsByClassName('mark-depth')[0].getElementsByClassName('header-text')[0];
    if (strs[1] == sellBuy[1] && strs[0] == sellBuy[0]) {
        $('#header-text').html(util.getLan('trade.tips.17')+ ' '+data.tick.close+(sellBuy[1]).toUpperCase()+' <span></span>');
        // $('#header-text span').html('≈ '+calculateCNY(data.tick.close,strs[1]).toFixed(2)+' CNY');
        // $('#header-text span').html('≈ '+data.tick.close+(sellBuy[1]).toUpperCase()+' CNY');
    }
}


//设置两个交易框
function handleDetail(data){
    var cArr = data['ch'].match(/\.([a-z\d]+?)(dota|btc|eth|usdt|ess1)\./i);
    //console.log(cArr);
    //var dataCoin = cArr[1] +'_'+ cArr[2];//交易对
    var dataCoin = data['dataCoin'];

    if(document.getElementsByClassName(dataCoin).length == 0){
        return;
    }
    var symbol_id1 = $('.trade-price'+data.id).attr('data-symbol_id');
    var rate1_id1 = $('.trade-rate'+data.id).attr('data-symbol_id');
    var price1 = $('.trade-price'+data.id);
    var rate1  = $('.trade-rate'+data.id);

    if (symbol_id1 == data.id) {
        price1.html(util.numFormat(data.tick.close[data.id],3));
    }

    if (rate1_id1 == data.id) {
        //var levels= Math.floor((data.tick.close[data.id]-data.tick.open[data.id])/data.tick.close[data.id]*10000);
        //$('#open').html(util.getLan(data.tick.open[data.id]);
        //console.log(data.tick.open1[data.id]);
        var levels= data.tick.chg[data.id];
        //levels = levels/100;
        if(isNaN(levels) || !levels){
            levels = 0.00;
        }else{
            levels = parseFloat(levels).toFixed(2);
        }
        if (levels>0) {
            rate1.attr("style","color:#72c02c");
            rate1.html("+"+levels+"%");
        }else{
            rate1.attr("style","color:#e74c3c");
            rate1.html(levels+"%");
        }
    }
    //获取btc对应的usdt价格
    if ('BTC_USDT' == dataCoin.toUpperCase()) {
        BTC_USDT = data.tick.close;
    }
    strs = dataCoin.split("_"); //字符分割
    ;
    if((strs[0]+strs[1]) != _symbol){
        return;
    }

    symbol_tradeId = getQueryString('symbol');
    //console.log(symbol_tradeId);
    if (data.id == symbol_tradeId ){
        var sell = document.getElementById('hide-symbol').getAttribute('value').split("_")[0];
        var span = document.getElementById('zf');
        span.removeAttribute('style');
        $('#open').html(util.getLan('trade.tips.29')+' '+ data.tick.open);
        $('#high').html(util.getLan('trade.tips.20')+' '+ data.tick.high);
        $('#low').html(util.getLan('trade.tips.21') + ' ' +data.tick.low);
        var m_num = new Number(data.tick.amount);
        $('#vol').html('24H'+util.getLan('trade.tips.22')+ ' '+m_num.toFixed(4)+' '+sell.toUpperCase());
        $('#tip-price').html(data.tick.close[data.id]);
        // $('#tip-cny').html(calculateCNY(data.tick.close[data.id],strs[1]).toFixed(2)+' CNY');
        $('#tip-cny').html(data.tick.close[data.id]+' CNY');
        if (levels>0) {
            span.setAttribute("style","color:#72c02c");
            span.innerHTML = "+"+levels+"%";
        }else{
            span.setAttribute("style","color:#e74c3c");
            span.innerHTML = levels+"%";
        }
    }


    /*var sell = document.getElementById('hide-symbol').getAttribute('value').split("_")[0];
    var span = document.getElementById('zf');
    span.removeAttribute('style');
    $('#open').html(util.getLan('trade.tips.29')+' '+ data.tick.open);
    $('#high').html(util.getLan('trade.tips.20')+' '+ data.tick.high);
    $('#low').html(util.getLan('trade.tips.21') + ' ' +data.tick.low);
    var m_num = new Number(data.tick.amount);
    $('#vol').html('24H'+util.getLan('trade.tips.22')+ ' '+m_num.toFixed(4)+' '+sell.toUpperCase());
    $('#tip-price').html(data.tick.close[data.id]);
    $('#tip-cny').html(calculateCNY(data.tick.close[data.id],strs[1]).toFixed(2)+' CNY');

    if (levels>0) {
        span.setAttribute("style","color:#72c02c");
        span.innerHTML = "+"+levels+"%";
    }else{
        span.setAttribute("style","color:#e74c3c");
        span.innerHTML = levels+"%";
    }*/
    // var headerText = document.getElementsByClassName('mark-depth')[0].getElementsByClassName('header-text')[0];
    if (strs[1] == sellBuy[1] && strs[0] == sellBuy[0]) {
        $('#header-text').html(util.getLan('trade.tips.17')+ ' '+data.tick.close[data.id]+(sellBuy[1]).toUpperCase()+' <span></span>');
        // $('#header-text span').html('≈ '+calculateCNY(data.tick.close[data.id],strs[1]).toFixed(2)+' CNY');
        // $('#header-text span').html('≈ '+data.tick.close[data.id]+(sellBuy[1]).toUpperCase()+' CNY');
    }
}

function calculateCNY(value,symbol) {
    var result = 0;
    if(symbol.toLowerCase() == 'usdt'){
        result = USDT_CNY_RATE * value;
    }else if(symbol.toLowerCase() == 'dota'){
        result = value;
    }else{
        result = exchangeRate[symbol+'_dota'] * value;
    }
    if (isNaN(result) || !result){
        return 0.00;
    }
    return result;
}

//保存
var exchangeRate = {};

/**
 * 平台
 * 左侧的实时价格
 */
function fetchRealTimePrice() {
    var symbols = [];
    $(".coin-list-item tr").each(function(index,item){
        if($(item).data().status == true){
            symbols.push($(item).attr('symbol'));
        }
    });
    var url = "/real/markets.html";
    //var url = HOST+"/real/markets.html";
    var symbol = symbols.join(',');
    var param = {
        symbol:symbol
    };
    var callback = function (data) {
 		// console.log(data);
        if (data.code == 200) {
            for(var i=0;i<data.data.length;i++){

                var dataCoin = data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase();
                if(!$('.coin-list tr ').hasClass(dataCoin)) {
                		return false;
                }
                var close = [];
                var open = chg = [];
                close[data.data[i].tradeId]=data.data[i].p_new;
                open[data.data[i].tradeId]=data.data[i].p_open;
                //console.log(data.data[i]);
                //console.log(open[data.data[i].tradeId]);
                chg[data.data[i].tradeId] = data.data[i].chg;
                
                //var price = document.getElementsByClassName(dataCoin)[0].getElementsByClassName('trade-price')[0];
                //var rate  = document.getElementsByClassName(dataCoin)[0].getElementsByClassName('trade-rate')[0];
                //var level= Math.floor((data.data[i].p_new-data.data[i].p_open)/data.data[i].p_new*10000);
                //price.innerHTML = util.numFormat(data.data[i].p_new,8);


                /*level = level / 100;
                if(isNaN(level) || !level){
                    level = 0.00;
                }
                if (level>0) {
                    rate.setAttribute("style","color:#72c02c");
                    rate.innerHTML = "+"+level+"%";
                }else{
                    rate.setAttribute("style","color:#e74c3c");
                    rate.innerHTML = level+"%";
                }*/

                //保存GSET对其他币种的价格
                exchangeRate[data.data[i].sellSymbol.toLowerCase()+'_'+data.data[i].buySymbol.toLowerCase()] = data.data[i].p_new;

               /* handleDetail({"id":data.data[i].tradeId,"ch":"market."+data.data[i].sellSymbol.toLowerCase()+data.data[i].buySymbol.toLowerCase()+".detail","dataCoin":dataCoin,
                    "tick":{
                        "amount":data.data[i].total,"close":data.data[i].p_new,"open":data.data[i].p_open,"low":data.data[i].buy,"high":data.data[i].sell,"version":"1","vol":data.data[i].total
                    }}); */
                handleDetail({"id":data.data[i].tradeId,"ch":"market."+data.data[i].sellSymbol.toLowerCase()+data.data[i].buySymbol.toLowerCase()+".detail","dataCoin":dataCoin,
                    "tick":{
                        "amount":data.data[i].total,"close":close,"open":data.data[i].p_open,"open1":open,"low":data.data[i].buy,"high":data.data[i].sell,"chg":chg,"version":"1","vol":data.data[i].total
                    }});

            }
        }
    };
    util.network({url: url, param: param, success: callback});

    window.setTimeout(function () {
        fetchRealTimePrice();
    }, 2000);
}

/**
 * 平台
 * 获取实时成交
 */
function fetchRealTimeTrade(){
    var url = "/real/market.html";
    var symbol = $("#symbol").val();
    var param = {
        symbol:symbol,
        buysellcount:100,
        successcount:100
    };

    var callback = function (data) {
        if (data.code == 200) {
            var trades = data.data.trades;

            var dataBox = document.getElementById('realTimeTrade');

            var priceSymbol = dataBox.getElementsByClassName('real-trade-price')[0].innerHTML;
            var amountSymbol = dataBox.getElementsByClassName('real-trade-mount')[0].innerHTML;
            var str = '';
            str +='<tr class="quotes-deal-item">'
                +   '<td>'+util.getLan('trade.tips.23')+'</td>'
                +    '<td>'+util.getLan('trade.tips.24')+'</td>'
                +   '<td class="real-trade-price">'+priceSymbol+'</td>'
                +   '<td class="real-trade-mount">'+amountSymbol+'</td></tr>';

            for(var i=0;i<trades.length;i++){
                str +='<tr class="quotes-deal-item">';
                str += '<td>'+trades[i].time+'</td>';
                if(trades[i].type == '买入'){
                    str += '<td class="success">'+trades[i].type+'</td>';
                } else{
                    str += '<td class="danger">'+trades[i].type+'</td>';
                }

                str += '<td>'+trades[i].price+'</td>';
                str += '<td>'+trades[i].amount+'</td>';
                str +='</tr>';
            }
            dataBox.innerHTML = str;
        }
    };

    util.network({url: url, param: param, success: callback});
    window.setTimeout(function () {
        fetchRealTimeTrade();
    }, 2000);
}

var bids_data;
/**
 * 平台
 * 深度
 */


//Howe Update
function fetchRealTimeDepth() {
    var url = "/kline/fulldepth.html";
    var symbol = $("#symbol").val();
    var param = {
        symbol:symbol
    };
    var callback = function (data) {
        if (data.code == 200) {
            var dl = document.getElementsByClassName('cell');
            //卖
            var asks = data.data.depth.asks;
            //买
            var bids = data.data.depth.bids;

	    //数据不满7的时候追加
	    if(asks.length<7){
	var orgLen = 7- asks.length;
	    	for(var i = 0;i<orgLen;i++){
			asks.push([0,0]);
		}
	     }

	if(bids.length<7){
	var orgLen = 7- bids.length;
                for(var i = 0;i<orgLen;i++){
                        bids.push([0,0]);
                }
             }

           var ticker = {"asks":asks,"bids":bids};

            //显示深度图
            handleDepthTick(ticker);
            var symbol = sellBuy[1].toUpperCase();
            var total = 7;
            var asksTotal = 0.0;
            var bidsTotal = 0.0;
            var askpre =0.0;
            var bidpre =0.0;

 	    //重置样式
	    $('.color-sell-bg').css('width',0);
            //卖的总量
            for (var i = 0; i < dl.length && i<7; i++) {
                //:wif (!asks[i]) continue;
                asksTotal = (parseFloat(asksTotal) + parseFloat(asks[i][1])).toFixed(numberFixed[symbol]);

          }

            for (var i = 0; i < dl.length && i <7; i++) {
                //if(!asks[total-1-i] || !asks[i]) continue;
                var d = dl[total-i-1];
                $(d).find('b').css('width',( parseFloat(asks[i][1]).toFixed(4))/ asksTotal *100 +'%');
                if( i==0 ){
                    askpre =  0.0;
                } else{
                    askpre = parseFloat(asks[i-1][1] ) + parseFloat(askpre);
                }

                var spans = d.getElementsByTagName('span');
                // spans[1].innerHTML = (parseFloat(asks[i][0]).toFixed(priceFixed[symbol]));
                // spans[2].innerHTML = (parseFloat(asks[i][1])).toFixed(numberFixed[symbol]);
                // spans[3].innerHTML = (parseFloat(asks[i][1]) + parseFloat(askpre)).toFixed(numberFixed[symbol]);
                spans[1].innerHTML = (parseFloat(asks[i][0]));
                spans[2].innerHTML = (parseFloat(asks[i][1]));
                spans[3].innerHTML = (parseFloat(asks[i][0]*asks[i][1]).toFixed(2));
                if(asks[i][1] == 0 || asks[i][0] == 0){
			spans[1].innerHTML = '-';
                	spans[2].innerHTML = '-';
               		spans[3].innerHTML = '-';
			$(d).find('b').css('width',0);
		}

	 }

            //bids_data.reverse();

            for (var i = total; i < bids.length + total && i<14 ; i++) {
                bidsTotal = ( parseFloat(bidsTotal) + parseFloat(bids[i-total][1]) ).toFixed(numberFixed[symbol]);
            }


            for (var i = total; i < bids.length + total && i<14; i++) {
                var d = dl[i];
                if(i==total){
                    bidpre = 0.0;
                } else{
                    bidpre = parseFloat(bids[i-total-1][1]) + parseFloat(bidpre);
                }
                $(d).find('b').css('width',( parseFloat(bids[i-total][1])).toFixed(4) / bidsTotal * 100 +'%');
                var spans = d.getElementsByTagName('span');
                // spans[1].innerHTML = (parseFloat( bids[i-total][0]).toFixed(priceFixed[symbol]));
                // spans[2].innerHTML = (parseFloat( bids[i-total][1])).toFixed(numberFixed[symbol]);
                // spans[3].innerHTML = (parseFloat( bids[i-total][1]) + parseFloat(bidpre)).toFixed(numberFixed[symbol]);
                spans[1].innerHTML = (parseFloat( bids[i-total][0]));
                spans[2].innerHTML = (parseFloat( bids[i-total][1]));
                spans[3].innerHTML = (parseFloat( bids[i-total][0]*bids[i-total][1])).toFixed(2);

		if(bids[i-total][0]== 0 || bids[i-total][1]== 0){
                        spans[1].innerHTML = '-';
                	spans[2].innerHTML = '-';
               		spans[3].innerHTML = '-';
			$(d).find('b').css('width',0);
                }
}


        }
    };
    util.network({url: url, param: param, success: callback});

    window.setTimeout(function () {
        fetchRealTimeDepth();
    }, 2000);
}

//function fetchRealTimeDepth_old() {
//  var url = "/kline/fulldepth.html";
//
//  var symbol = $("#symbol").val();
//  var param = {
//      symbol:symbol
//  };
//  var callback = function (data) {
//
//      if (data.code == 200) {
//
//
//
//          //卖
//          var asks = data.data.depth.asks;
//          //买
//          var bids = data.data.depth.bids;
//
//          var ticker = {"asks":asks,"bids":bids};
//
//          //显示深度图
//          handleDepthTick(ticker);
//          var symbol = sellBuy[1].toUpperCase();
//          var total = 7;
//          var asksTotal = 0.0;
//          var bidsTotal = 0.0;
//          var askpre =0.0;
//          var bidpre =0.0;
//
//          var asklen = asks.length;
//			var dl = document.getElementsByClassName('cell');
//
//          for (var i = 0; i < 14; i++) {
//          		var d = dl[i];
//              var spans = d.getElementsByTagName('span');
//              spans[1].innerHTML = "";
//              spans[2].innerHTML = "";
//              spans[3].innerHTML = "";
//          }
//          //卖的总量
////          for (var i = 0; i < dl.length && i<7 && i< asklen; i++) {
////              asksTotal = (parseFloat(asksTotal) + parseFloat(asks[i][1])).toFixed(numberFixed[symbol]);
////          }
////
////          for (var i = 0; i < dl.length && i <7 && i<asklen; i++) {
////              var d = dl[total-i-1];
////              $(d).find('b').css('width',( parseFloat(asks[asklen-1-i][1]).toFixed(4))/ asksTotal *100 +'%');
////              if( i==0 ){
////                  askpre =  0.0;
////              } else{
////                  askpre = parseFloat(asks[i-1][1] ) + parseFloat(askpre);
////              }
////
////              var spans = d.getElementsByTagName('span');
////              spans[1].innerHTML = (parseFloat(asks[i][0]).toFixed(priceFixed[symbol]));
////              spans[2].innerHTML = (parseFloat( asks[i][1])).toFixed(numberFixed[symbol]);
////              spans[3].innerHTML = (parseFloat( asks[i][1]) + parseFloat(askpre)).toFixed(numberFixed[symbol]);
////          }
// for (var i = 0; i < dl.length && i<7; i++) {
//              if (!asks[i]) continue;
//              asksTotal = (parseFloat(asksTotal) + parseFloat(asks[i][1])).toFixed(numberFixed[symbol]);
//          }
//
//          for (var i = 0; i < dl.length && i <7; i++) {
//              if(!asks[total-1-i] || !asks[i]) continue;
//              var d = dl[total-i-1];
//              $(d).find('b').css('width',( parseFloat(asks[total-1-i][1]).toFixed(4))/ asksTotal *100 +'%');
//              if( i==0 ){
//                  askpre =  0.0;
//              } else{
//                  askpre = parseFloat(asks[i][1] ) + parseFloat(askpre);
//              }
//
//              var spans = d.getElementsByTagName('span');
//              spans[1].innerHTML = (parseFloat(asks[i][0]).toFixed(priceFixed[symbol]));
//              spans[2].innerHTML = (parseFloat( asks[i][1])).toFixed(numberFixed[symbol]);
//              spans[3].innerHTML = (parseFloat( asks[i][1]) + parseFloat(askpre)).toFixed(numberFixed[symbol]);
//          }
//
//          //bids_data.reverse();
//
//          for (var i = total; i < bids.length + total && i<14 ; i++) {
//              bidsTotal = ( parseFloat(bidsTotal) + parseFloat(bids[i-total][1]) ).toFixed(numberFixed[symbol]);
//          }
//
//
//          for (var i = total; i < bids.length + total && i<14; i++) {
//              var d = dl[i];
//              if(i==total){
//                  bidpre = 0.0;
//              } else{
//                  bidpre = parseFloat(bids[i-total-1][1]) + parseFloat(bidpre);
//              }
//              $(d).find('b').css('width',( parseFloat(bids[i-total][1])).toFixed(4) / bidsTotal * 100 +'%');
//              var spans = d.getElementsByTagName('span');
//              spans[1].innerHTML = (parseFloat( bids[i-total][0]).toFixed(priceFixed[symbol]));
//              spans[2].innerHTML = (parseFloat( bids[i-total][1])).toFixed(numberFixed[symbol]);
//              spans[3].innerHTML = (parseFloat( bids[i-total][1]) + parseFloat(bidpre)).toFixed(numberFixed[symbol]);
//          }
//
//      //     var total = 7;
//      //     var bids_data = [];
//      //     var bidsTotal = 0;
//      //     var asksTotal = 0;
//      //     for (var i = total; i >=0 ; i--) {
//      //         asksTotal += parseFloat(parseFloat(asks[total - i][0]).toFixed(4));
//      //     }
//      //     for (var i = total; i >=0 ; i--) {
//      //         var d = dl[i];
//      //         $(d).find('b').css('width',(parseFloat(asks[total - i][0]).toFixed(4))/asksTotal *100 + '%');
//      //         var spans = d.getElementsByTagName('span');
//      //         spans[1].innerHTML = '';
//      //         spans[2].innerHTML = '';
//      //         spans[3].innerHTML = '';
//      //         if(sellBuy[1].toUpperCase() == 'USDT'){
//      //             if(asks[total-i]) {
//      //                 spans[1].innerHTML = (parseFloat(asks[total - i][0]).toFixed(2));
//      //             }
//      //         }else{
//      //             if (sellBuy[0].toUpperCase() == 'BTM'){
//      //                 if(asks[total-i]){
//      //                     spans[1].innerHTML = (parseFloat(asks[total-i][0]).toFixed(8));
//      //                 }
//      //             }else{
//      //                 if (asks[total-i]){
//      //                     spans[1].innerHTML = (parseFloat(asks[total-i][0]).toFixed(6));
//      //                 }
//      //             }
//      //         }
//      //         if(asks[total-i]) {
//      //             spans[2].innerHTML = (parseFloat(asks[total-i][1])).toFixed(4);
//      //             spans[3].innerHTML = (asks[total-i][0] * asks[total-i][1]).toFixed(4);
//      //         }
//      //     }
//      //     bids_data = bids;
//      //     //bids_data.reverse();
//      //     total += 1;
//      //     for (var i = total; i < dl.length; i++) {
//      //         bidsTotal += parseFloat(parseFloat(bids_data[i - total][0]).toFixed(4));
//      //     }
//      //
//      //     for (var i = total; i < dl.length; i++) {
//      //         var d = dl[i];
//      //         $(d).find('b').css('width',(parseFloat(bids_data[i - total][0]).toFixed(4)) /bidsTotal *100 +'%' );
//      //         var spans = d.getElementsByTagName('span');
//      //         spans[1].innerHTML = '';
//      //         spans[2].innerHTML = '';
//      //         spans[3].innerHTML = '';
//      //         if(sellBuy[1].toUpperCase() == 'USDT') {
//      //             if(bids_data[i-total]) {
//      //                 spans[1].innerHTML = (parseFloat(bids_data[i - total][0]).toFixed(2));
//      //             }
//      //         }else{
//      //             if (sellBuy[0].toUpperCase() == 'BTM'){
//      //                 if (bids_data[i-total]) {
//      //                     spans[1].innerHTML = (parseFloat(bids_data[i-total][0]).toFixed(8));
//      //                 }
//      //
//      //             }else{
//      //                 if (bids_data[i-total]){
//      //                     spans[1].innerHTML = (parseFloat(bids_data[i-total][0]).toFixed(6));
//      //                 }
//      //             }
//      //         }
//      //         if(bids_data[i-total]){
//      //             spans[2].innerHTML = (parseFloat(bids_data[i-total][1])).toFixed(4);
//      //             spans[3].innerHTML = (bids_data[i-total][0] * bids_data[i-total][1]).toFixed(4);
//      //         }
//      //     }
//      }
//  };
//  util.network({url: url, param: param, success: callback});
//
//  window.setTimeout(function () {
//      fetchRealTimeDepth();
//  }, 2000);
//}

//设置输入框
function inputListener() {
    $("#tradebuyprice").on('input',function (ev) {
        var val = $("#tradebuyprice").val();
        var cny =  parseFloat(calculateCNY(val,sellBuy[1])).toFixed(2);
        $("#tradebuyprice-cny").html("≈ "+cny+" CNY");
    });

    $("#tradesellprice").on('input',function (ev) {
        var val = $("#tradesellprice").val();
        var cny = parseFloat(calculateCNY(val,sellBuy[1])).toFixed(2);
        $("#tradesellprice-cny").html("≈ "+cny+" CNY");
    });
}

/**
 * 平台k线
 */
function fetchRealTimeKLine() {
    requestData();
}


$(function(){
    inputListener();
    fetchRealTimePrice();
    fetchRealTimeKLine();
    if (isPlatformTrade) {
        fetchRealTimeDepth();
        fetchRealTimeTrade();
    }
    return;
})
