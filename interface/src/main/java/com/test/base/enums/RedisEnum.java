package com.test.base.enums;

public enum RedisEnum {
    //这一行作为例子，不是拼接使用，后面使用记得用FK_xxxxx就行了
//	PREFIX("FK_", "", "just后台接口前缀")
    LIQUIDATION_DEPOSIT_SOURCE("FK_LIQUIDATION_DEPOSIT_SOURCE", "", "充值对账数据源"),
    LIQUIDATION_DEPOSIT_DB_AMOUNT("FK_LIQUIDATION_DEPOSIT_DB_AMOUNT", "", "充值对账数据库包含金额"),
    LIQUIDATION_DEPOSIT_EXCEL_AMOUNT("FK_LIQUIDATION_DEPOSIT_EXCEL_AMOUNT", "", "充值对账excel包含金额"),
    LIQUIDATION_DEPOSIT_DB("FK_LIQUIDATION_DEPOSIT_DB", "", "充值对账数据库只看订单"),
    LIQUIDATION_DEPOSIT_EXCEL("FK_LIQUIDATION_DEPOSIT_EXCEL", "", "充值对账excel只看订单"),
    //对账结果
    LIQUIDATION_RES_DIFF_DEPOSIT_DB_AMOUNT("FK_LIQUIDATION_RES_DIFF_DEPOSIT_DB_AMOUNT", "", "对账结果数据库包含金额"),
    LIQUIDATION_RES_DIFF_DEPOSIT_EXCEL_AMOUNT("FK_LIQUIDATION_RES_DIFF_DEPOSIT_EXCEL_AMOUNT", "", "对账结果excel包含金额"),
    LIQUIDATION_RES_DIFF_DEPOSIT_DB("FK_LIQUIDATION_RES_DIFF_DEPOSIT_DB", "", "对账结果数据库只看订单"),
    LIQUIDATION_RES_DIFF_DEPOSIT_EXCEL("FK_LIQUIDATION_RES_DIFF_DEPOSIT_EXCEL", "", "对账结果excel只看订单"),

    LIQUIDATION_RES_INTERSECT_DEPOSIT_DB("FK_LIQUIDATION_RES_INTERSECT_DEPOSIT_DB", "", "对账结果数据库包"),
    LIQUIDATION_RES_INTERSECT_DEPOSIT_DB_AMOUNT("FK_LIQUIDATION_RES_INTERSECT_DEPOSIT_DB_AMOUNT", "", "对账结果数据库包含金额"),
    FK_TO_PAY_ORDER("FK_TO_PAY_ORDER", "", "支付订单信息的redis缓存key"),
    FK_PAY_ORDER_RESULT_FLAG("FK_PAY_ORDER_RESULT_FLAG", "", "支付订单结果的标识的redis缓存key"),
    FK_PAY_ORDER_MQ_FLAG("FK_PAY_ORDER_MQ_FLAG", "", "支付订单MQ的标识的redis缓存key"),
    FK_TO_PLACE_ORDER("FK_TO_PLACE_ORDER", "", "下单信息的redis缓存key");

    private RedisEnum(String code, String flag, String description) {
        this.code = code;
        this.flag = flag;
        this.description = description;
    }

    /**
     * seqences id
     */
    private String code;

    /**
     * 起始标志
     */
    private String flag;

    /**
     * 描述
     */
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static RedisEnum getEnumByCode(String code) {
        for (RedisEnum e : values()) {
            if (e.getCode().equals(code))
                return e;
        }
        return null;
    }

}
