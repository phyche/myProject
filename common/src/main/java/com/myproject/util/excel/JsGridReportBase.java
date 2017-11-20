package com.myproject.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class JsGridReportBase
{
  public SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private HttpServletResponse response;
  private HttpSession session;
  private ServletOutputStream out;
  private InputStream downloadFile;
  public static int EXCEL_MAX_CNT = 100000;
  public static int SHEET_MAX_CNT = 20000;

  public JsGridReportBase()
  {
  }

  public JsGridReportBase(HttpServletRequest request, HttpServletResponse response) throws Exception {
    this.response = response;
    this.session = request.getSession();
    init(this.session);
  }

  private void init(HttpSession session) throws Exception {
    this.out = this.response.getOutputStream();
  }

  public void outDataToBrowser(TableData tableData)
  {
    StringBuffer outData = new StringBuffer();

    outData.append("{pageInfo: {totalRowNum: " + tableData.getTotalRows() + "},");

    outData.append("data: [");
    boolean isFirst = true;

    TableHeaderMetaData headerMetaData = tableData.getTableHeader();
    List<TableDataRow> dataRows = tableData.getRows();
    try {
      for (TableDataRow dataRow : dataRows) {
        List dataCells = dataRow.getCells();
        int size = dataCells.size();
        if (!isFirst) {
          outData.append(",{");
          for (int i = 0; i < size; i++) {
            outData.append(headerMetaData.getColumnAt(i).getId() + ": '" + ((TableDataCell)dataCells.get(i)).getValue() + "',");
          }

          int index = outData.lastIndexOf(",");
          outData.deleteCharAt(index);
          outData.append("}");
        } else {
          outData.append("{");
          for (int i = 0; i < size; i++) {
            outData.append(headerMetaData.getColumnAt(i).getId() + ": '" + ((TableDataCell)dataCells.get(i)).getValue() + "',");
          }

          int index = outData.lastIndexOf(",");
          outData.deleteCharAt(index);
          outData.append("}");
          isFirst = false;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    outData.append("]");
    outData.append("}");
    try
    {
      this.out.print(outData.toString());
      this.out.flush();
      this.out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void stopGrouping(HSSFSheet sheet, HashMap<Integer, String> word, HashMap<Integer, Integer> counter, int i, int size, int rownum, HSSFCellStyle style)
  {
    String w = (String)word.get(Integer.valueOf(i));
    if (w != null) {
      int len = ((Integer)counter.get(Integer.valueOf(i))).intValue();
      CellRangeAddress address = new CellRangeAddress(rownum - len, rownum - 1, i, i);

      sheet.addMergedRegion(address);
      fillMergedRegion(sheet, address, style);
      word.remove(Integer.valueOf(i));
      counter.remove(Integer.valueOf(i));
    }
    if (i + 1 < size)
      stopGrouping(sheet, word, counter, i + 1, size, rownum, style);
  }

  private void generateColumn(HSSFSheet sheet, TableColumn tc, int maxlevel, int rownum, int colnum, HSSFCellStyle headerstyle)
  {
    HSSFRow row = sheet.getRow(rownum);
    if (row == null) {
      row = sheet.createRow(rownum);
    }
    HSSFCell cell = row.createCell(colnum);
    cell.setCellValue(tc.getDisplay());

    if (headerstyle != null)
      cell.setCellStyle(headerstyle);
    if (tc.isComplex()) {
      CellRangeAddress address = new CellRangeAddress(rownum, rownum, colnum, colnum + tc.getLength() - 1);

      sheet.addMergedRegion(address);
      fillMergedRegion(sheet, address, headerstyle);

      int cn = colnum;
      for (int i = 0; i < tc.getChildren().size(); i++) {
        if (i != 0) {
          cn += ((TableColumn)tc.getChildren().get(i - 1)).getLength();
        }
        generateColumn(sheet, (TableColumn)tc.getChildren().get(i), maxlevel, rownum + 1, cn, headerstyle);
      }
    }
    else {
      CellRangeAddress address = new CellRangeAddress(rownum, rownum + maxlevel - tc.level, colnum, colnum);

      sheet.addMergedRegion(address);
      fillMergedRegion(sheet, address, headerstyle);
    }
    sheet.autoSizeColumn(colnum, true);
  }

  private void fillMergedRegion(HSSFSheet sheet, CellRangeAddress address, HSSFCellStyle style)
  {
    for (int i = address.getFirstRow(); i <= address.getLastRow(); i++) {
      HSSFRow row = sheet.getRow(i);
      if (row == null)
        row = sheet.createRow(i);
      for (int j = address.getFirstColumn(); j <= address.getLastColumn(); j++) {
        HSSFCell cell = row.getCell(j);
        if (cell == null) {
          cell = row.createCell(j);
          if (style != null)
            cell.setCellStyle(style);
        }
      }
    }
  }

  public HSSFWorkbook writeSheet(HSSFWorkbook wb, String title, HashMap<String, HSSFCellStyle> styles, String creator, TableData tableData)
    throws Exception
  {
    TableHeaderMetaData headerMetaData = tableData.getTableHeader();

    SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
    String create_time = formater.format(new Date());

    HSSFSheet sheet = wb.createSheet(title);
    sheet.setDisplayGridlines(false);

    HSSFRow row = sheet.createRow(0);
    HSSFCell cell = row.createCell(0);
    int rownum = 0;
    cell.setCellValue(new HSSFRichTextString(title));
    HSSFCellStyle style = (HSSFCellStyle)styles.get("TITLE");
    if (style != null)
      cell.setCellStyle(style);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerMetaData.getColumnCount() - 1));

    row = sheet.createRow(1);
    cell = row.createCell(0);
    cell.setCellValue(new HSSFRichTextString("创建人:"));
    style = (HSSFCellStyle)styles.get("SUB_TITLE");
    if (style != null) {
      cell.setCellStyle(style);
    }
    cell = row.createCell(1);
    cell.setCellValue(new HSSFRichTextString(creator));
    style = (HSSFCellStyle)styles.get("SUB_TITLE2");
    if (style != null) {
      cell.setCellStyle(style);
    }
    cell = row.createCell(2);
    cell.setCellValue(new HSSFRichTextString("创建时间:"));
    style = (HSSFCellStyle)styles.get("SUB_TITLE");
    if (style != null) {
      cell.setCellStyle(style);
    }
    cell = row.createCell(3);
    style = (HSSFCellStyle)styles.get("SUB_TITLE2");
    cell.setCellValue(new HSSFRichTextString(create_time));
    if (style != null) {
      cell.setCellStyle(style);
    }
    rownum = 3;

    HSSFCellStyle headerstyle = (HSSFCellStyle)styles.get("TABLE_HEADER");

    int colnum = 0;
    for (int i = 0; i < headerMetaData.getOriginColumns().size(); i++) {
      TableColumn tc = (TableColumn)headerMetaData.getOriginColumns().get(i);
      if (i != 0) {
        colnum += ((TableColumn)headerMetaData.getOriginColumns().get(i - 1)).getLength();
      }

      generateColumn(sheet, tc, headerMetaData.maxlevel, rownum, colnum, headerstyle);
    }

    rownum += headerMetaData.maxlevel;

    List<TableDataRow> dataRows = tableData.getRows();

    HashMap counter = new HashMap();
    HashMap word = new HashMap();
    int index = 0;
    for (TableDataRow dataRow : dataRows) {
      row = sheet.createRow(rownum);

      List dataCells = dataRow.getCells();
      int size = headerMetaData.getColumns().size();
      index = -1;
      for (int i = 0; i < size; i++) {
        TableColumn tc = (TableColumn)headerMetaData.getColumns().get(i);
        if (tc.isVisible())
        {
          index++;

          String value = ((TableDataCell)dataCells.get(i)).getValue();
          if (tc.isGrouped()) {
            String w = (String)word.get(Integer.valueOf(index));
            if (w == null) {
              word.put(Integer.valueOf(index), value);
              counter.put(Integer.valueOf(index), Integer.valueOf(1));
              createCell(row, tc, dataCells, i, index, styles);
            }
            else if (w.equals(value)) {
              counter.put(Integer.valueOf(index), Integer.valueOf(((Integer)counter.get(Integer.valueOf(index))).intValue() + 1));
            } else {
              stopGrouping(sheet, word, counter, index, size, rownum, (HSSFCellStyle)styles.get("STRING"));

              word.put(Integer.valueOf(index), value);
              counter.put(Integer.valueOf(index), Integer.valueOf(1));
              createCell(row, tc, dataCells, i, index, styles);
            }
          }
          else {
            createCell(row, tc, dataCells, i, index, styles);
          }
        }
      }
      rownum++;
    }

    stopGrouping(sheet, word, counter, 0, index, rownum, (HSSFCellStyle)styles.get("STRING"));

    for (int c = 0; c < headerMetaData.getColumns().size(); c++) {
      sheet.autoSizeColumn((short)c);
      String t = ((TableColumn)headerMetaData.getColumns().get(c)).getDisplay();
      if (sheet.getColumnWidth(c) < t.length() * 256 * 3)
        sheet.setColumnWidth(c, t.length() * 256 * 3);
    }
    sheet.setGridsPrinted(true);

    return wb;
  }

  public HSSFWorkbook writeSheet(HSSFWorkbook wb, HashMap<String, HSSFCellStyle> styles, String creator, List<TableData> tableDataLst)
    throws Exception
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
    String create_time = formater.format(new Date());

    int cnt = 1;
    for (TableData tableData : tableDataLst) {
      String sheetTitle = tableData.getSheetTitle();
      sheetTitle = (sheetTitle == null) || (sheetTitle.equals("")) ? "sheet" + cnt : sheetTitle;
      cnt++;

      TableHeaderMetaData headerMetaData = tableData.getTableHeader();
      HSSFSheet sheet = wb.createSheet(sheetTitle);
      sheet.setDisplayGridlines(false);
      wb.cloneSheet(0);

      HSSFRow row = sheet.createRow(0);
      HSSFCell cell = row.createCell(0);
      int rownum = 0;
      cell.setCellValue(new HSSFRichTextString(sheetTitle));
      HSSFCellStyle style = (HSSFCellStyle)styles.get("TITLE");
      if (style != null)
        cell.setCellStyle(style);
      sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerMetaData.getColumnCount() - 1));

      row = sheet.createRow(1);
      cell = row.createCell(0);
      cell.setCellValue(new HSSFRichTextString("创建人:"));
      style = (HSSFCellStyle)styles.get("SUB_TITLE");
      if (style != null) {
        cell.setCellStyle(style);
      }
      cell = row.createCell(1);
      cell.setCellValue(new HSSFRichTextString(creator));
      style = (HSSFCellStyle)styles.get("SUB_TITLE2");
      if (style != null) {
        cell.setCellStyle(style);
      }
      cell = row.createCell(2);
      cell.setCellValue(new HSSFRichTextString("创建时间:"));
      style = (HSSFCellStyle)styles.get("SUB_TITLE");
      if (style != null) {
        cell.setCellStyle(style);
      }
      cell = row.createCell(3);
      style = (HSSFCellStyle)styles.get("SUB_TITLE2");
      cell.setCellValue(new HSSFRichTextString(create_time));
      if (style != null) {
        cell.setCellStyle(style);
      }
      rownum = 3;

      HSSFCellStyle headerstyle = (HSSFCellStyle)styles.get("TABLE_HEADER");

      int colnum = 0;
      for (int i = 0; i < headerMetaData.getOriginColumns().size(); i++) {
        TableColumn tc = (TableColumn)headerMetaData.getOriginColumns().get(i);
        if (i != 0) {
          colnum += ((TableColumn)headerMetaData.getOriginColumns().get(i - 1)).getLength();
        }
        generateColumn(sheet, tc, headerMetaData.maxlevel, rownum, colnum, headerstyle);
      }
      rownum += headerMetaData.maxlevel;

      List<TableDataRow> dataRows = tableData.getRows();

      int index = 0;
      for (TableDataRow dataRow : dataRows) {
        row = sheet.createRow(rownum);

        List dataCells = dataRow.getCells();
        int size = headerMetaData.getColumns().size();
        index = -1;
        for (int i = 0; i < size; i++) {
          TableColumn tc = (TableColumn)headerMetaData.getColumns().get(i);
          if (tc.isVisible())
          {
            index++;

            createCell(row, tc, dataCells, i, index, styles);
          }
        }
        rownum++;
      }

      for (int c = 0; c < headerMetaData.getColumns().size(); c++) {
        sheet.autoSizeColumn((short)c);
        String t = ((TableColumn)headerMetaData.getColumns().get(c)).getDisplay();
        if (sheet.getColumnWidth(c) < t.length() * 256 * 3)
          sheet.setColumnWidth(c, t.length() * 256 * 3);
      }
      sheet.setGridsPrinted(true);
    }

    return wb;
  }

  public void exportToExcel(String title, String creator, TableData tableData)
    throws Exception
  {
    HSSFWorkbook wb = new HSSFWorkbook();

    HashMap styles = initStyles(wb);

    wb = writeSheet(wb, title, styles, creator, tableData);

    String sFileName = "Excel报表.xls";
    this.response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));

    this.response.setHeader("Connection", "close");
    this.response.setHeader("Content-Type", "application/vnd.ms-excel");

    wb.write(this.response.getOutputStream());
  }

  public void exportToExcel(String title, String creator, List<TableData> tableDataLst)
    throws Exception
  {
    HSSFWorkbook wb = new HSSFWorkbook();
    HashMap styles = initStyles(wb);

    int i = 1;
    for (TableData tableData : tableDataLst) {
      String sheetTitle = tableData.getSheetTitle();
      sheetTitle = (sheetTitle == null) || (sheetTitle.equals("")) ? "sheet" + i : sheetTitle;
      wb = writeSheet(wb, tableData.getSheetTitle(), styles, creator, tableData);
      i++;
    }

    String sFileName = title + ".xls";
    this.response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));

    this.response.setHeader("Connection", "close");
    this.response.setHeader("Content-Type", "application/vnd.ms-excel");

    wb.write(this.response.getOutputStream());
  }

  public void exportToExcel(ZipOutputStream zout, ExcelBean bean)
    throws Exception
  {
    List list = bean.getList();

    HSSFWorkbook wb = new HSSFWorkbook();
    HashMap styles = initStyles(wb);

    int excel_num = list.size() / EXCEL_MAX_CNT;
    if (list.size() % EXCEL_MAX_CNT > 0) {
      excel_num++;
    }
    int start = 0; int end = 0;
    int _start = 0; int _end = 0;
    for (int i = 1; i <= excel_num; i++) {
      start = (i - 1) * EXCEL_MAX_CNT;
      end = i * EXCEL_MAX_CNT - 1;
      end = end > list.size() ? list.size() : end;
      List sublist = list.subList(start, end);
      System.out.println("正在导出第 " + i + " 个文件！");
      int sheet_num = sublist.size() / SHEET_MAX_CNT;
      if (sublist.size() % SHEET_MAX_CNT > 0) {
        sheet_num++;
      }

      List tds = new ArrayList();
      TableData td = null;
      for (int j = 1; j <= sheet_num; j++) {
        _start = (j - 1) * SHEET_MAX_CNT;
        _end = j * SHEET_MAX_CNT - 1;
        _end = _end > sublist.size() ? sublist.size() : _end;
        System.out.println("正在导出第" + j + "个sheet：第" + (start + _start + 1) + "~" + (start + _start + _end + 1) + "条记录");
        List sheetLst = sublist.subList(_start, _end);
        if ((bean.getChildren() != null) && (bean.getChildren().length > 0))
          td = ExcelUtils.createTableData(sheetLst, ExcelUtils.createTableHeader(bean.getHearders(), bean.getChildren()), bean.getFields());
        else
          td = ExcelUtils.createTableData(sheetLst, ExcelUtils.createTableHeader(bean.getHearders()), bean.getFields());
        td.setSheetTitle("第" + (start + _start + 1) + "~" + (start + _start + _end + 1) + "条记录");
        tds.add(td);
      }
      wb = writeSheet(wb, styles, bean.getCreator(), tds);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      wb.write(baos);
      baos.flush();
      byte[] aa = baos.toByteArray();

      String sFileName = bean.getTitle() + "(" + (start + 1) + "~" + (end + 1) + ")" + ".xls";

      this.downloadFile = new ByteArrayInputStream(aa, 0, aa.length);
      ZipEntry entry = new ZipEntry(sFileName);
      zout.putNextEntry(entry);
      while (this.downloadFile.read(aa) > 0) {
        zout.write(aa);
      }
      baos.close();
      this.downloadFile.close();
    }
  }

  private void createCell(HSSFRow row, TableColumn tc, List<TableDataCell> data, int i, int index, HashMap<String, HSSFCellStyle> styles)
  {
    TableDataCell dc = (TableDataCell)data.get(i);
    HSSFCell cell = row.createCell(index);
    HSSFCellStyle style;
    switch (tc.getColumnType()) {
    case 3:
      cell.setCellValue(dc.getIntValue());
      style = (HSSFCellStyle)styles.get("INT");
      if (row.getRowNum() % 2 != 0)
        style = (HSSFCellStyle)styles.get("INT_C");
      if (style != null)
        cell.setCellStyle(style); break;
    case 1:
      cell.setCellValue(dc.getDoubleValue());
      style = (HSSFCellStyle)styles.get("D2");
      if (row.getRowNum() % 2 != 0)
        style = (HSSFCellStyle)styles.get("D2_C");
      if (style != null)
        cell.setCellStyle(style); break;
    case 2:
      cell.setCellValue(dc.getDoubleValue());
      style = (HSSFCellStyle)styles.get("D3");
      if (row.getRowNum() % 2 != 0)
        style = (HSSFCellStyle)styles.get("D3_C");
      if (style != null)
        cell.setCellStyle(style); break;
    case 10:
      cell.setCellValue(dc.getValue());
      style = (HSSFCellStyle)styles.get("RED_BG");
      if (style != null)
        cell.setCellStyle(style); break;
    case 11:
      cell.setCellValue(dc.getValue());
      style = (HSSFCellStyle)styles.get("YELLOW_BG");
      if (style != null)
        cell.setCellStyle(style); break;
    case 12:
      cell.setCellValue(dc.getValue());
      style = (HSSFCellStyle)styles.get("GREEN_BG");
      if (style != null)
        cell.setCellStyle(style); break;
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    default:
      if (dc.getValue().equalsIgnoreCase("&nbsp;"))
        cell.setCellValue("");
      else
        cell.setCellValue(dc.getValue());
      style = (HSSFCellStyle)styles.get("STRING");
      if (row.getRowNum() % 2 != 0)
        style = (HSSFCellStyle)styles.get("STRING_C");
      if (style != null)
        cell.setCellStyle(style);
      break;
    }
  }

  private HashMap<String, HSSFCellStyle> initStyles(HSSFWorkbook wb)
  {
    HashMap ret = new HashMap();
    try {
      ClassPathResource res = new ClassPathResource("module.xls", getClass());
      InputStream is = res.getInputStream();
      POIFSFileSystem fs = new POIFSFileSystem(is);

      HSSFWorkbook src = new HSSFWorkbook(fs);
      HSSFSheet sheet = src.getSheetAt(0);

      buildStyle(wb, src, sheet, 0, ret, "TITLE");
      buildStyle(wb, src, sheet, 1, ret, "SUB_TITLE");
      buildStyle(wb, src, sheet, 2, ret, "SUB_TITLE2");

      buildStyle(wb, src, sheet, 4, ret, "TABLE_HEADER");
      buildStyle(wb, src, sheet, 5, ret, "STRING");
      buildStyle(wb, src, sheet, 6, ret, "INT");
      buildStyle(wb, src, sheet, 7, ret, "D2");
      buildStyle(wb, src, sheet, 8, ret, "D3");

      buildStyle(wb, src, sheet, 10, ret, "STRING_C");
      buildStyle(wb, src, sheet, 11, ret, "INT_C");
      buildStyle(wb, src, sheet, 12, ret, "D2_C");
      buildStyle(wb, src, sheet, 13, ret, "D3_C");

      buildStyle(wb, src, sheet, 15, ret, "RED_BG");
      buildStyle(wb, src, sheet, 16, ret, "YELLOW_BG");
      buildStyle(wb, src, sheet, 17, ret, "GREEN_BG");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }

  private void buildStyle(HSSFWorkbook wb, HSSFWorkbook src, HSSFSheet sheet, int index, HashMap<String, HSSFCellStyle> ret, String key)
  {
    HSSFRow row = sheet.getRow(index);
    HSSFCell cell = row.getCell(1);
    HSSFCellStyle nstyle = wb.createCellStyle();
    ExcelUtils.copyCellStyle(wb, nstyle, src, cell.getCellStyle());
    ret.put(key, nstyle);
  }

  protected String getUTF8String(String string)
  {
    if (string == null)
      return null;
    try
    {
      return new String(string.getBytes("ISO8859-1"), "UTF-8");
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }return string;
  }

  protected String getGBKString(String string)
  {
    if (string == null)
      return null;
    try
    {
      return new String(string.getBytes("ISO8859-1"), "GBK");
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }return string;
  }

  public String fieldRender(String value)
  {
    if (value == null) {
      return "&nbsp;";
    }
    return value;
  }
}