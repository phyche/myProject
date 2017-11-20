package com.test.common.service;

public interface LiquidationService {
    /**
     * 文件头
     * @param rowNum 记录行数
     * @param bodyLength 每行记录长度
     * @return
     * @throws Exception
     */
    public String liquidationHeader(String rowNum, String bodyLength) throws Exception;

    /**
     * 文件尾
     * @param body 文体
     * @param headerVerify 头部校验值
     * @return
     * @throws Exception
     */
    public String liquidationFooter(String body, String headerVerify) throws Exception;

    /**
     * 充值交易明细文件
     * @param contentJsonList list文件体json传
     * @return
     * @throws Exception
     */
    public String liquidationDepositBody(String contentJsonList) throws Exception;
}
