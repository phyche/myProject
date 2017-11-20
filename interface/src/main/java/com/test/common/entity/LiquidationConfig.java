package com.test.common.entity;

public class LiquidationConfig {
    /**
     * 机构代码
     */
    private String companyCode;
    /**
     * 充值交易明细 每条长度
     */
    private String depositLengthPreRow;
    /**
     * 充值公交mcc码
     */
    private String depositMcc;
    /**
     * 充值交易 标识
     */
    private String liquidationDepositSign;
    /**
     * 入网机构自定义
     */
    private String selfCode;


    public String getLiquidationDepositSign() {
        return liquidationDepositSign;
    }

    public void setLiquidationDepositSign(String liquidationDepositSign) {
        this.liquidationDepositSign = liquidationDepositSign;
    }

    public String getSelfCode() {
        return selfCode;
    }

    public void setSelfCode(String selfCode) {
        this.selfCode = selfCode;
    }

    public String getDepositMcc() {
        return depositMcc;
    }

    public void setDepositMcc(String depositMcc) {
        this.depositMcc = depositMcc;
    }

    public String getDepositLengthPreRow() {
        return depositLengthPreRow;
    }

    public void setDepositLengthPreRow(String depositLengthPreRow) {
        this.depositLengthPreRow = depositLengthPreRow;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    public String toString() {
        return "LiquidationConfig{" +
                "companyCode='" + companyCode + '\'' +
                ", depositLengthPreRow='" + depositLengthPreRow + '\'' +
                ", depositMcc='" + depositMcc + '\'' +
                ", liquidationDepositSign='" + liquidationDepositSign + '\'' +
                ", selfCode='" + selfCode + '\'' +
                '}';
    }
}
