package com.qkwl.common.dto.Enum;

public enum FinanceRecordOperationEnum {

    In(1, "充值"),
    Out(2, "提现"),
    Transfer(3,"转账"),
    Receive(4,"接受"),
    Other(6,"其他");
    
    private Integer code;

    private String value;

    FinanceRecordOperationEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByCode(Integer code) {
        for (FinanceRecordOperationEnum e : FinanceRecordOperationEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getValue().toString();
            }
        }
        return "";
    }

}
