package com.qkwl.service.match.run;

import java.util.List;

import com.qkwl.common.dto.Enum.EntrustSourceEnum;
import com.qkwl.common.dto.Enum.SystemTradeStatusEnum;
import com.qkwl.common.dto.coin.SystemTradeType;
import com.qkwl.service.match.utils.MatchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qkwl.common.dto.entrust.FEntrust;
import com.qkwl.service.match.services.MatchServiceImpl;

import javax.annotation.PostConstruct;

@Component("autoMatch")
public class AutoMatch {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(AutoMatch.class);

    @Autowired
    private MatchUtils matchUtils;
    @Autowired
    private MatchServiceImpl matchService;

    @PostConstruct
    public void init() {
        Thread thread = new Thread(new Work());
        thread.setName("AutoMatch");
        thread.start();
    }

    class Work implements Runnable {
        //int count = 0;
        //boolean isNoTrade = true;
        public void run() {
            while (true) {
                //isNoTrade = true;
                // 获取币种列表
                List<SystemTradeType> systemTradeTypes = matchUtils.getTradeTypeList();
                if (systemTradeTypes == null) {
                    continue;
                }
                // 遍历虚拟币列表
                for (SystemTradeType systemTradeType : systemTradeTypes) {
                    if (systemTradeType == null) {
                        continue;
                    }
                    // 非平台撮合 跳过
                    if (!systemTradeType.getStatus().equals(SystemTradeStatusEnum.NORMAL.getCode())) {
                        continue;
                    }
                    // 币种ID
                    int tradeId = systemTradeType.getId();
                    //首先把GEC 和 HTEC 过滤掉
//                    if ( 13 == tradeId || 21 == tradeId){
//                        continue;
//                    }

//                    if ( 13 != tradeId && 21 != tradeId){
//                        continue;
//                    }

                    // 获取排序买卖单
                    List<FEntrust> buyEntrusts = matchService.getBuyEntrusts(tradeId);
                    List<FEntrust> sellEntrusts = matchService.getSellEntrusts(tradeId);
                    if (buyEntrusts == null || buyEntrusts.size() <= 0
                            || sellEntrusts == null || sellEntrusts.size() <= 0) {
                        continue;
                    }

                    //如果不是10723和10719就需要比较价格是否满足撮合条件
//                    if (!((buyEntrusts.get(0).getFuid() == 10723 && sellEntrusts.get(0).getFuid() == 10719)
//                            || (buyEntrusts.get(0).getFuid() == 10719 && sellEntrusts.get(0).getFuid() == 10723))){
//                        if (buyEntrusts.get(0).getFprize().compareTo(sellEntrusts.get(0).getFprize()) < 0) {
//                            continue;
//                        }
//                    }

                    if (buyEntrusts.get(0).getFprize().compareTo(sellEntrusts.get(0).getFprize()) < 0) {
                        continue;
                    }

                    // 遍历判断价格
                    for (FEntrust buyEntrustWait : buyEntrusts) {
                        Integer fuid = buyEntrustWait.getFuid();
                        for (FEntrust sellEntrustWait : sellEntrusts) {
                            // null 判断
                            //logger.info("uid = "+fuid+" , sellUID = "+sellEntrustWait.getFuid());
//                            if (buyEntrustWait == null || sellEntrustWait == null
//                                    || (fuid == 10723 && sellEntrustWait.getFuid() != 10719) //10723只能和10719成交
//                                    || (sellEntrustWait.getFuid() == 10723 && fuid != 10719)) {
//                                continue;
//                            }

                            if (buyEntrustWait == null || sellEntrustWait == null) {
                                continue;
                            }

                            // 意向达成,开始撮合
                            try {
                                matchService.updateMatch(systemTradeType, buyEntrustWait, sellEntrustWait);
                               // logger.info("buyPrice = "+buyEntrustWait.getFprize() +" , sellPrice = "+sellEntrustWait.getFprize() + ",result = "+result);
                            } catch (Exception e) {
                                logger.error("math err : {}_{}", buyEntrustWait.getFid(), sellEntrustWait.getFid());
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                }

                //如果没有交易就休息1秒
//                if (isNoTrade) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    //撮合了50笔交易之后休息0.5秒
//                    if (count >= 50) {
//                        count = 0;
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
            }
        }
    }
}
