/**
 *
 */

//
var USDT_CNY_RATE = 0;

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

$(function(){

    getExchangeRate();
});
