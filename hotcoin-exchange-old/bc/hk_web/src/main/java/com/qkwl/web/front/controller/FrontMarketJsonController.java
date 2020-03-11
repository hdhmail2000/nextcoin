package com.qkwl.web.front.controller;

import com.alibaba.druid.util.StringUtils;
import com.qkwl.common.dto.Enum.SystemTradeBlockEnum;
import com.qkwl.common.dto.Enum.SystemTradeStatusEnum;
import com.qkwl.common.dto.Enum.SystemTradeTypeEnum;
import com.qkwl.common.dto.coin.SystemTradeType;
import com.qkwl.common.dto.coin.SystemTradeTypeVO;
import com.qkwl.common.dto.news.FArticle;
import com.qkwl.common.dto.user.FUser;
import com.qkwl.common.framework.redis.RedisHelper;
import com.qkwl.common.util.ModelMapperUtils;
import com.qkwl.web.front.comm.AutoMarket;
import com.qkwl.web.front.controller.base.JsonBaseController;
import com.qkwl.web.utils.WebConstant;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qkwl.common.util.ReturnResult;
import com.qkwl.common.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
public class FrontMarketJsonController extends JsonBaseController {

    @Autowired
    private AutoMarket autoMarket;
    @Autowired
    private RedisHelper redisHelper;


    //获取K线数据
    @ResponseBody
    @RequestMapping("/kline/fullperiod")
    public String fullperiod(
            @RequestParam(required = true) int step,
            @RequestParam(required = true) int symbol
    ) throws Exception {
        JSONArray result = autoMarket.getKlineJson(symbol, step / 60);
        if (result != null) {
            return result.clone().toString();
        }
        return "[[]]";
    }

    @ResponseBody
    @RequestMapping("/kline/fulldepth")
    public ReturnResult fulldepth(
            @RequestParam(required = false, defaultValue = "0") int step,
            @RequestParam(required = false, defaultValue = "0") int symbol
    ) throws Exception {
        JSONObject jsonObject = new JSONObject();
        JSONObject returnObject = new JSONObject();
        if (symbol == 0) {
            return ReturnResult.FAILUER("币种ID错误！");
        }
        jsonObject.put("bids", autoMarket.getBuyDepthJson(symbol));
        jsonObject.put("asks", autoMarket.getSellDepthJson(symbol));
        jsonObject.put("date", Utils.getTimestamp().getTime() / 1000);
        returnObject.put("depth", jsonObject);
        if (step > 0) {
            JSONObject periodobject = new JSONObject();
            periodobject.put("marketFrom", symbol);
            periodobject.put("coinVol", symbol);
            periodobject.put("type", step);
            periodobject.put("data", autoMarket.getLastKlineJson(symbol, step / 60));
            returnObject.put("period", periodobject);
        }
        return ReturnResult.SUCCESS(returnObject);
    }

    @ResponseBody
    @RequestMapping("/kline/lastperiod")
    public ReturnResult lastPeriod(
            @RequestParam(required = false, defaultValue = "0") int step,
            @RequestParam(required = false, defaultValue = "0") int symbol
    ) throws Exception {
        if (symbol == 0) {
            return ReturnResult.FAILUER("币种ID错误！");
        }
        return ReturnResult.SUCCESS(autoMarket.getLastKlineJson(symbol, step / 60));
    }


    @ResponseBody
    @RequestMapping("/market/rate")
    public ReturnResult exchangeRate() throws Exception {
        Map<String, Object> systemArgsList = redisHelper.getSystemArgsList();
        JSONObject resultJson = new JSONObject();
        resultJson.put("CNY", systemArgsList.get("rechargeUSDTPrice"));
        return ReturnResult.SUCCESS(resultJson);
    }

    /**
     * 交易
     * @param symbol        交易对id
     * @param tradeType     交易区id SystemTradeTypeEnum
     * @param sellBuy       交易对描述 btc_usdt
     * @param limit
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/trademarket_json")
    public ReturnResult tradeMarket(
            @RequestParam(value = "symbol", required = false, defaultValue = "0") Integer symbol,
            @RequestParam(value = "type", required = false, defaultValue = "1") Integer tradeType,
            @RequestParam(value = "sb", required = false, defaultValue = "btc_usdt") String sellBuy,
            @RequestParam(value = "limit", required = false, defaultValue = "0") Integer limit
    ) throws Exception {
        limit = limit < 0 ? 0 : limit;
        limit = limit > 1 ? 1 : limit;
        JSONObject jsonObject = new JSONObject();
        SystemTradeType systemTradeType = redisHelper.getTradeType(symbol, WebConstant.BCAgentId);
        if (systemTradeType == null || systemTradeType.getStatus().equals(SystemTradeStatusEnum.ABNORMAL.getCode()) || !systemTradeType.getIsShare()) {
            systemTradeType = redisHelper.getTradeTypeFirst(tradeType, WebConstant.BCAgentId);
            if (systemTradeType == null) {
                return ReturnResult.FAILUER("");
            }
            symbol = systemTradeType.getId();
        }
        // 小数位处理(默认价格2位，数量4位)
        String digit = StringUtils.isEmpty(systemTradeType.getDigit()) ? "2#4" : systemTradeType.getDigit();
        String[] digits = digit.split("#");
        jsonObject.put("cnyDigit", Integer.valueOf(digits[0]));
        jsonObject.put("coinDigit", Integer.valueOf(digits[1]));
        FUser fuser = getCurrentUserInfoByToken();
        if (fuser != null) {
            jsonObject.put("isTelephoneBind", fuser.getFistelephonebind());
            jsonObject.put("tradePassword", fuser.getFtradepassword() == null);
            jsonObject.put("needTradePasswd", redisHelper.getNeedTradePassword(fuser.getFid()));
            jsonObject.put("login", true);
        } else {
            jsonObject.put("login", false);
        }

        //现有交易板块
        Map<Integer, Object> blockMap = new LinkedHashMap<>();

        //现有的交易区
        Map<Integer, Object> typeMap = new LinkedHashMap<>();



        //交易区对应的交易对
        Map<Integer, Map<Integer, List<SystemTradeTypeVO>>> tradeBlockListMap = new LinkedHashMap<>();

        //所有交易对
        List<SystemTradeType> tradeTypeList = redisHelper.getTradeTypeList(WebConstant.BCAgentId);

        for (SystemTradeBlockEnum blockEnum : SystemTradeBlockEnum.values()) {
            blockMap.put(blockEnum.getCode(),blockEnum.getValue());
            //交易区对应的交易对
            Map<Integer, List<SystemTradeTypeVO>> tradeTypeListMap = new LinkedHashMap<>();
            for (SystemTradeTypeEnum typeEnum : SystemTradeTypeEnum.values()) {
                typeMap.put(typeEnum.getCode(), typeEnum.getSymbol());
                List<SystemTradeType> tempTradeTypeList = new ArrayList<>();
                for (SystemTradeType stt : tradeTypeList) {
                    if (stt.getType() == typeEnum.getCode() && stt.getTradeBlock() == blockEnum.getCode()) {
                        stt.setBuyShortName(stt.getBuyShortName().toLowerCase());
                        stt.setSellShortName(stt.getSellShortName().toLowerCase());
                        tempTradeTypeList.add(stt);
                    }
                }
                tradeTypeListMap.put(typeEnum.getCode(), ModelMapperUtils.mapper(tempTradeTypeList, SystemTradeTypeVO.class));
            }
            tradeBlockListMap.put(blockEnum.getCode(),tradeTypeListMap);
        }

        //List<SystemTradeTypeVO> mapper = ModelMapperUtils.mapper(redisHelper.getTradeTypeList(WebConstant.BCAgentId), SystemTradeTypeVO.class);
        redisHelper.getCoinInfo(systemTradeType.getSellCoinId(),super.getLanEnum().getName());

        //英文的交易名称
        sellBuy = sellBuy.toLowerCase();
        //交易 卖买
        jsonObject.put("sb", sellBuy);
        //交易 卖方
        jsonObject.put("sell", sellBuy.split("_")[0]);
        //交易 买方
        jsonObject.put("buy", sellBuy.split("_")[1]);
        jsonObject.put("typeMap", typeMap);
        jsonObject.put("blockMap", blockMap);
        jsonObject.put("type", tradeType);
        jsonObject.put("tradeType", ModelMapperUtils.mapper(systemTradeType, SystemTradeTypeVO.class));
        List<FArticle> farticles = redisHelper.getArticles(2, 2, 1, WebConstant.BCAgentId);
        jsonObject.put("article", farticles);    //公告

        jsonObject.put("symbol", symbol);
        jsonObject.put("tradeTypeListMap", tradeBlockListMap);
        jsonObject.put("isPlatformStatus", systemTradeType.getStatus() == SystemTradeStatusEnum.NORMAL.getCode());
        jsonObject.put("limit", limit);
        jsonObject.put("coinInfo",redisHelper.getCoinInfo(systemTradeType.getSellCoinId(),super.getLanEnum().getCode()+""));
        return ReturnResult.SUCCESS(jsonObject);

    }


}
