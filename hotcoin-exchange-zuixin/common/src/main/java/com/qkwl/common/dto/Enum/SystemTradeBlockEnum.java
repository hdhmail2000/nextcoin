package com.qkwl.common.dto.Enum;

/**
 * 交易区域
 */
public enum SystemTradeBlockEnum {

	MAIN(1, "主板"),
	STARTUP(2, "创业板"),
	REVIVAL(3, "复活板");
	private Integer code;
	private Object value;

	SystemTradeBlockEnum(Integer code, Object value) {
		this.code = code;
		this.value = value;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public static String getValueByCode(Integer code) {
		for (SystemTradeBlockEnum coinType : SystemTradeBlockEnum.values()) {
			if (coinType.getCode().equals(code)) {
				return coinType.getValue().toString();
			}
		}
		return null;
	}
}
