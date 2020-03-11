
function datafeed(){

    this.exchange = {
            "name":'usdt',
            "value":'hahah ',
            "desc":'xuxiix '
    };

    this.DatafeedSymbolType ={
            name:'USDT',
            value:'usdt',
    };

    this.request = false;

    this. ResolutionString = [1,15,30,60,240,"D","M"];

    this.configurationData={
            exchanges: this.exchange,
            supported_resolutions: this.ResolutionString,
            supports_marks: true, //是否支持显示刻度
            supports_time: true, //是否支持时间
            supports_timescale_marks:true,
            symbols_types: this.DatafeedSymbolType,
    };



    //onReady 执行
    this.onReady = function(callback){
        window.setTimeout(callback(this.configurationData),0);
    };

    this.searchSymbols = function(userInput,exchange,symbolType,onResult){
        onResult([]);
    };
    this.onHistoryCallback = null;
    this.resolveSymbol = function(symbolName, onSymbolResolvedCallback, onResolveErrorCallback){
        console.log(symbolName);
        window.setTimeout(onSymbolResolvedCallback(LibrarySymbolInfo),0);
    };
    this.getBars=function(symbolInfo, resolution, from, to, onHistoryCallback, onErrorCallback, firstDataRequest){
        console.log(from);
        console.log(to);
        ws.send(JSON.stringify({"req": "market.btcusdt.kline.1min","id": "id10"}));
        this.request = true;
        this.onHistoryCallback = onHistoryCallback;

    };

    this.subscribeBars = function(symbolInfo,resolution,onRealtimeCallback,subscriberUID, onResetCacheNeededCallback){

    };
    return this;
}


var LibrarySymbolInfo = {
    /**
     * Symbol Name
     */
    name:'USDT',
    full_name: '比特币',
    base_name: 'base_name',
    /**
     * Unique symbol id
     */
    ticker:'ticker',
    description: 'description',
    type: 'type',
    /**
     * @example "1700-0200"
     */
    session: "1700-0200",
    /**
     * Traded exchange
     * @example "NYSE"
     */
    exchange: "NYSE",
    listed_exchange: "NYSE",
    timezone: 'Asia/Shanghai',
    /**
     * Code (Tick)
     * @example 8/16/.../256 (1/8/100 1/16/100 ... 1/256/100) or 1/10/.../10000000 (1 0.1 ... 0.0000001)
     */
    pricescale: 1,
    /**
     * The number of units that make up one tick.
     * @example For example, U.S. equities are quotes in decimals, and tick in decimals, and can go up +/- .01. So the tick increment is 1. But the e-mini S&P futures contract, though quoted in decimals, goes up in .25 increments, so the tick increment is 25. (see also Tick Size)
     */
    minmov: '10',
    fractional: true,
    /**
     * @example Quarters of 1/32: pricescale=128, minmovement=1, minmovement2=4
     */
    minmove2: 4,
    /**
     * false if DWM only
     */
    has_intraday: true,
    /**
     * An array of resolutions which should be enabled in resolutions picker for this symbol.
     */
    supported_resolutions: ["1","15","30","60","240","D","M"],
    /**
     * @example (for ex.: "1,5,60") - only these resolutions will be requested, all others will be built using them if possible
     */
    intraday_multipliers:["1","15","30","60","240","D","M"],
    has_seconds: false,
    /**
     * It is an array containing seconds resolutions (in seconds without a postfix) the datafeed builds by itself.
     */
    // seconds_multipliers?: string[];
    // has_daily?: boolean;
    // has_weekly_and_monthly?: boolean;
    // has_empty_bars?: boolean;
    // force_session_rebuild?: boolean;
    // has_no_volume?: boolean;
    // /**
    //  * Integer showing typical volume value decimal places for this symbol
    //  */
    // volume_precision?: number;
    // data_status?: 'streaming' | 'endofday' | 'pulsed' | 'delayed_streaming';
    // /**
    //  * Boolean showing whether this symbol is expired futures contract or not.
    //  */
    // expired?: boolean;
    // /**
    //  * Unix timestamp of expiration date.
    //  */
    // expiration_date?: number;
    // sector?: string;
    // industry?: string;
    // currency_code?: string;
}
