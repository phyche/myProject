package com.test.common.service.impl;

import com.test.common.entity.LiquidationConfig;
import com.test.common.service.LiquidationService;
import com.test.common.utils.RandomUtils;
import com.myproject.json.JsonUtils;
import com.myproject.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by caiqi on 2016/11/23.
 */
@Service("liquidationService")
public class LiquidationServiceImpl implements LiquidationService {
    private Logger logger = LoggerFactory.getLogger(LiquidationServiceImpl.class);
    @Autowired
    private LiquidationConfig liquidationConfig;
    public String liquidationHeader(String rowNum,String bodyLength) throws Exception {
        StringBuffer sbHeader=new StringBuffer();
        try {
            //机构代码
            sbHeader.append(StringUtil.appendStringFixSpaceRight(liquidationConfig.getCompanyCode(),12));
            //记录条数
            sbHeader.append(StringUtil.appendStringFixSpaceRight(rowNum,3));
            //单条记录长度
            sbHeader.append(StringUtil.appendStringFixSpaceRight(bodyLength,4));
            //校验字节
            sbHeader.append(RandomUtils.randomHexString(1));
        } catch (Exception e) {
            logger.error("组合清算文件头异常："+e.getMessage());
            throw new Exception(e);
        }
        return sbHeader.toString();
    }
    public String liquidationFooter(String body,String headerVerify) throws Exception {
        byte[] verifyFoot = new byte[1];
        try {
//            处理一下body数据
            String[] bodyAry = body.split("\r\n");
            StringBuffer sbBodyContent = new StringBuffer();
            for (String bodytmp : bodyAry) {
                byte tmp = StringUtil.check(bodytmp.getBytes());
                verifyFoot[0] += tmp;
            }
            verifyFoot[0] +=headerVerify.getBytes()[0];
        } catch (Exception e) {
            logger.error("组合清算文件尾异常："+e.getMessage());
            throw new Exception(e);
        }
        return new String(verifyFoot);
    }
//   充值交易明细文件
    public String liquidationDepositBody(String contentJsonList) throws Exception {
        StringBuffer sbBody=new StringBuffer();
        try {
//            json转换list
            List<Map> list = JsonUtils.toObject(contentJsonList,List.class);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            list.forEach(o->{
                if(sbBody.length()>0)sbBody.append("\r\n");
                //            0	3	an	交易代码	000
                sbBody.append("000");
//            3	30	an	主账号	卡号、账户号，等，不足长度右补空格
                String loginName = (String)o.get("loginName");
                loginName = StringUtil.isEmpty(loginName)?"":loginName;
                sbBody.append(StringUtil.appendStringFixSpaceRight(loginName,30));
//            33	20	n	交易金额	正整数，单位：厘
                int amount = Integer.valueOf((String)o.get("amount"));
                sbBody.append(StringUtil.appendStringFix0Left(String.valueOf(amount*10),20));
//            53	3	an	交易货币代码	人民币：156
                sbBody.append("156");
//            56	14	n	交易时间	格式：yyyyMMddHHmmss
                String processingTime = (String)o.get("processingTime");
                sbBody.append(processingTime);
//            70	14	N	交易传输时间	格式：yyyyMMddHHmmss
                sbBody.append(processingTime);
//            84	6	an	系统跟踪号	不足长度右补空格
                sbBody.append(StringUtil.appendStringFixSpaceRight("",6));
//            90	12	an	发卡机构号	不足长度右补空格
                sbBody.append(StringUtil.appendStringFixSpaceRight("",12));
//            102	12	an	收单机构号	不足长度右补空格
                sbBody.append(StringUtil.appendStringFixSpaceRight("",12));
//            114	4	an	业务类型	MCC
                sbBody.append(liquidationConfig.getDepositMcc());
//            118	20	n	手续费金额	正整数，单位：厘
                sbBody.append(StringUtil.appendStringFix0Left("0",20));
//            138	20	ans	持卡人	汉字占2个长度，不足长度右补空格
                sbBody.append(StringUtil.appendStringFixSpaceRight("",20));
//            158	1	an	持卡人证件类型
                sbBody.append("0");
//            159	30	an	持卡人证件号码	不足长度右补空格
                sbBody.append(StringUtil.appendStringFixSpaceRight("",30));
//            189	2	an	持卡人类型	00
                sbBody.append("00");
//            191	20	n	交易前金额	正整数，单位：厘
                sbBody.append(StringUtil.appendStringFix0Left("0",20));
//            211	30	ans	保留	不足长度右补空格
                sbBody.append(StringUtil.appendStringFixSpaceRight("",30));
//            241	36	an	原系统参考号	通卡系统的订单号，不足长度右补空格
                String tradeBaseNo = (String)o.get("tradeBaseNo");
                sbBody.append(StringUtil.appendStringFixSpaceRight(tradeBaseNo,36));
//            277	8	An	终端号	不足长度右补空格
                sbBody.append(StringUtil.appendStringFixSpaceRight("",8));
//            285	2	N	终端类型
                sbBody.append(StringUtil.appendStringFixSpaceRight("",2));
//            287	36	an	终端流水号
                sbBody.append(StringUtil.appendStringFixSpaceRight("",36));
//            323	15	an	受理地通卡公司代码	收单机构号，不足长度右补空格
                sbBody.append(StringUtil.appendStringFixSpaceRight("",15));
//            338	36	an	受理机构流水号	订单系统，支付系统，不足长度右补空格
                sbBody.append(StringUtil.appendStringFixSpaceRight("",36));
//            374	1	n	交易状态	0：正常  1：异常
                sbBody.append("0");
            });
        } catch (Exception e) {
            logger.error("组合清算体--充值交易明细--异常："+e.getMessage());
            throw new Exception(e);
        }
        return sbBody.toString();
    }
}
