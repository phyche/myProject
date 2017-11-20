package com.test.common.service.impl;

import com.test.ExcelHelper.ExcelException;
import com.test.ExcelHelper.ExcelUtil;
import com.test.ExcelHelper.vo.JgReconciliationVO;
import com.test.common.entity.FtpConfig;
import com.test.common.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by caiqi on 2016/11/23.
 */
@Service("excelService")
public class ExcelServiceImpl implements ExcelService {
    private Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);
    @Autowired
    private FtpConfig ftpConfig;
    public List<JgReconciliationVO> jgReconciliationExcelToList(String filename,String sheetName) throws Exception{
        List<JgReconciliationVO> list= null;
        try {
            InputStream in = new FileInputStream(ftpConfig.getFileLocalBasePath()+filename);
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
            fieldMap.put("完成时间","complateTime");
            fieldMap.put("交易类型","tradeType");
            fieldMap.put("收入","moneyIncome");
            fieldMap.put("支出","moneyExpense");
            fieldMap.put("手续费","moneyCharge");
            fieldMap.put("交易订单号","tradeBaseNo");
            fieldMap.put("商户订单号","orderId");
            String[] uniqueFields=new String[1];
            uniqueFields[0]= "交易订单号";
            list = ExcelUtil.excelToList(in,sheetName,JgReconciliationVO.class,fieldMap,uniqueFields);
        }catch (ExcelException e){
            logger.error("excel转list异常："+e.getLocalizedMessage());
            throw  new ExcelException(e);
        }catch (Exception e){
            logger.error("excel转list异常："+e.getLocalizedMessage());
            throw  new Exception(e);
        }
        return list;
    }

}
