package com.test.common.service;

import com.test.ExcelHelper.vo.JgReconciliationVO;

import java.util.List;

public interface ExcelService {
    /**
     * 出门无忧对账3.0excel导出list
     * @param filename 本地文件名
     * @param sheetName excel中sheet名
     * @return
     * @throws Exception
     */
    public List<JgReconciliationVO> jgReconciliationExcelToList(String filename, String sheetName) throws Exception;
}
