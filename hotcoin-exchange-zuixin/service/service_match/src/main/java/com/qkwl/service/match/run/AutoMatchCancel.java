package com.qkwl.service.match.run;

import com.qkwl.common.dto.Enum.SystemTradeStatusEnum;
import com.qkwl.common.dto.coin.SystemTradeType;
import com.qkwl.common.dto.entrust.FEntrust;
import com.qkwl.service.match.services.MatchServiceImpl;
import com.qkwl.service.match.utils.MatchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("autoMatchCancel")
public class AutoMatchCancel {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(AutoMatchCancel.class);
	
	@Autowired
	private MatchUtils matchUtils;
	@Autowired
	private MatchServiceImpl matchService;

	@PostConstruct
	public void init() {
		Thread thread = new Thread(new Work());
		thread.setName("AutoMatchCancel");
		thread.start();
	}

	class Work implements Runnable {
		public void run() {
			while (true) {
				// 获取币种列表
				List<SystemTradeType> systemTradeTypes = matchUtils.getAllTradeTypeList();
				if (systemTradeTypes == null) {
					continue;
				}
				// 遍历虚拟币列表
				for (SystemTradeType systemTradeType : systemTradeTypes) {
					if (systemTradeType == null) {
						continue;
					}
					// 火币撮合跳过
					if (systemTradeType.getStatus().equals(SystemTradeStatusEnum.HUOBI.getCode())) {
						continue;
					}
					// 币种ID
					int tradeId = systemTradeType.getId();

					//首先把GEC 和 HTEC 过滤掉
//					if ( 13 == tradeId || 21 == tradeId){
//						continue;
//					}

//					if ( 13 != tradeId && 21 != tradeId){
//                        continue;
//                    }


					// 强事务锁单更新
					List<FEntrust> fCancelEntrusts = matchService.getWaitCancelEntrust(tradeId);
					for (FEntrust fEntrust : fCancelEntrusts) {
						try {
							matchService.updateCancelMatch(fEntrust);
						} catch (Exception e) {
							logger.error("cancelMatch err : {}", fEntrust.getFid());
							e.printStackTrace();
							continue;
						}
					}
				}
			}
		}
	}
}
