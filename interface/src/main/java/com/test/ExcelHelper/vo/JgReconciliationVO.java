package com.test.ExcelHelper.vo;

import java.io.Serializable;

/**
 * Created by ll on 2017-10-16.
 */
public class JgReconciliationVO implements Serializable {
//    完成时间
    private String complateTime;
//    交易类型
    private String tradeType;
//    收入
    private String moneyIncome;
//    支出
    private String moneyExpense;
//    手续费
    private String moneyCharge;
//    交易订单号
    private String tradeBaseNo;
//    商户订单号
    private String orderId;

    public String getComplateTime() {
        return complateTime;
    }

    public void setComplateTime(String complateTime) {
        this.complateTime = complateTime;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getMoneyIncome() {
        return moneyIncome;
    }

    public void setMoneyIncome(String moneyIncome) {
        this.moneyIncome = moneyIncome;
    }

    public String getMoneyExpense() {
        return moneyExpense;
    }

    public void setMoneyExpense(String moneyExpense) {
        this.moneyExpense = moneyExpense;
    }

    public String getMoneyCharge() {
        return moneyCharge;
    }

    public void setMoneyCharge(String moneyCharge) {
        this.moneyCharge = moneyCharge;
    }

    public String getTradeBaseNo() {
        return tradeBaseNo;
    }

    public void setTradeBaseNo(String tradeBaseNo) {
        this.tradeBaseNo = tradeBaseNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
