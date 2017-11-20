package com.myproject.util.excel;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class TableData
{
  private static DecimalFormat format = new DecimalFormat("0.##");
  public static final int STYLE_TYPE_STRING = 0;
  public static final int STYLE_TYPE_FLOAT_2 = 1;
  public static final int STYLE_TYPE_FLOAT_3 = 2;
  public static final int STYLE_TYPE_INTEGER = 3;
  public static final int STYLE_TYPE_RED_BG = 10;
  public static final int STYLE_TYPE_YELLOW_BG = 11;
  public static final int STYLE_TYPE_GREEN_BG = 12;
  private String sheetTitle;
  private TableHeaderMetaData header;
  private LinkedList<TableDataRow> rows;
  private int totalRows;

  public TableData()
  {
  }

  public TableData(TableHeaderMetaData header)
  {
    this.header = header;
    this.rows = new LinkedList();
  }

  public void compute()
  {
    for (int i = 0; i < this.header.getColumnCount(); i++) {
      TableColumn tc = this.header.getColumnAt(i);
      if ((tc.isVisible()) && (tc.isGrouped()) && (tc.isDisplaySummary()))
        buildSummary(i);
    }
  }

  private TableDataRow buildNewRow(String key, int index)
  {
    TableDataRow nrow = new TableDataRow(this);
    for (int i = 0; i < index; i++) {
      nrow.addCell("");
    }
    nrow.addCell("(" + key + ")小计");
    for (int i = index + 1; i < this.header.getColumnCount(); i++) {
      TableColumn thc = this.header.getColumnAt(i);
      if (thc.getAggregateRule() != null)
        nrow.addCell(Integer.valueOf(0));
      else {
        nrow.addCell("");
      }
    }
    return nrow;
  }

  private void buildSummary(int index) {
    LinkedList lst = new LinkedList();
    String okey = null; SummaryData sd = null;
    for (int j = 0; j < this.rows.size(); j++) {
      TableDataRow row = (TableDataRow)this.rows.get(j);
      String key = row.getCellAt(index).getValue();

      if (okey == null) {
        okey = key;
        sd = new SummaryData();
        sd.key = key;
        sd.count = 0;
        sd.row = buildNewRow(key, index);
      }

      if ((okey != null) && (!okey.equals(key))) {
        for (int i = index; i < this.header.getColumnCount(); i++) {
          TableColumn thc = this.header.getColumnAt(i);
          if (thc.getAggregateRule() != null) {
            String value = sd.row.getCellAt(i).getValue();
            if (thc.getAggregateRule().equalsIgnoreCase("avg")) {
              if (sd.count > 0)
                sd.row.getCellAt(i).setValue("平均：" + format.format(Double.parseDouble(value) / sd.count));
              else
                sd.row.getCellAt(i).setValue("平均：0");
            }
            else if (thc.getAggregateRule().equalsIgnoreCase("max"))
              sd.row.getCellAt(i).setValue("最大值：" + value);
            else if (thc.getAggregateRule().equalsIgnoreCase("min"))
              sd.row.getCellAt(i).setValue("最小值：" + value);
            else if (thc.getAggregateRule().equalsIgnoreCase("sum"))
              sd.row.getCellAt(i).setValue("求和：" + value);
            else if (thc.getAggregateRule().equalsIgnoreCase("count")) {
              sd.row.getCellAt(i).setValue("共" + value + "行");
            }
          }
        }
        sd.index = j;

        FormulaProcessor.getInstance().fillValue(sd.row);
        lst.add(sd);

        okey = key;
        sd = new SummaryData();
        sd.key = key;
        sd.count = 0;
        sd.row = buildNewRow(key, index);
      }
      sd.count += 1;
      for (int i = index + 1; i < this.header.getColumnCount(); i++) {
        TableColumn thc = this.header.getColumnAt(i);
        if ((thc.getColumnType() != 4) && (thc.getAggregateRule() != null)) {
          Double value = Double.valueOf(Double.parseDouble(sd.row.getCellAt(i).getValue()));
          Double cellValue = null;
          Double e=null;
          try {
            cellValue = Double.valueOf(row.getCellAt(i).getValue());
          } catch (NumberFormatException ex) {
            cellValue = null;
          }
          if (cellValue != null)
          {
            if (thc.getAggregateRule().equalsIgnoreCase("max")) {
              if (value.doubleValue() < cellValue.doubleValue())
                value = cellValue;
            }
            else if (thc.getAggregateRule().equalsIgnoreCase("min")) {
              if (value.doubleValue() > cellValue.doubleValue())
                value = cellValue;
            }
            else if (thc.getAggregateRule().equalsIgnoreCase("count")) {
              e = value; Double localDouble1 = value = Double.valueOf(value.doubleValue() + 1.0D);
            } else if (thc.getAggregateRule().equalsIgnoreCase("sum")) {
              value = Double.valueOf(value.doubleValue() + cellValue.doubleValue());
            } else if (thc.getAggregateRule().equalsIgnoreCase("avg")) {
              value = Double.valueOf(value.doubleValue() + cellValue.doubleValue());
            }
            sd.row.getCellAt(i).setValue(format.format(value));
          }
        }
      }
    }
    if (sd != null) {
      for (int i = index; i < this.header.getColumnCount(); i++) {
        TableColumn thc = this.header.getColumnAt(i);
        if (thc.getAggregateRule() != null) {
          String value = sd.row.getCellAt(i).getValue();
          if (thc.getAggregateRule().equalsIgnoreCase("avg")) {
            if (sd.count > 0)
              sd.row.getCellAt(i).setValue("平均：" + format.format(Double.parseDouble(value) / sd.count));
            else
              sd.row.getCellAt(i).setValue("平均：0");
          }
          else if (thc.getAggregateRule().equalsIgnoreCase("max"))
            sd.row.getCellAt(i).setValue("最大值：" + value);
          else if (thc.getAggregateRule().equalsIgnoreCase("min"))
            sd.row.getCellAt(i).setValue("最小值：" + value);
          else if (thc.getAggregateRule().equalsIgnoreCase("sum"))
            sd.row.getCellAt(i).setValue("求和：" + value);
          else if (thc.getAggregateRule().equalsIgnoreCase("count")) {
            sd.row.getCellAt(i).setValue("共" + value + "行");
          }
        }
      }
      FormulaProcessor.getInstance().fillValue(sd.row);
      lst.add(sd);
      sd.index = this.rows.size();
    }
    for (int i = 0; i < lst.size(); i++) {
      SummaryData sda = (SummaryData)lst.get(i);
      this.rows.add(sda.index + i, sda.row);
    }
  }

  public TableHeaderMetaData getTableHeader()
  {
    return this.header;
  }

  public void addRow(TableDataRow row)
  {
    this.rows.add(row);
  }

  public List<TableDataRow> getRows() {
    return this.rows;
  }

  public TableDataRow getRowAt(int index)
  {
    return (TableDataRow)this.rows.get(index);
  }

  public int getRowCount()
  {
    return this.rows.size();
  }

  public int getTotalRows() {
    return this.totalRows;
  }

  public void setTotalRows(int totalRows) {
    this.totalRows = totalRows;
  }

  public void setHeader(TableHeaderMetaData header) {
    this.header = header;
  }

  public void setRows(LinkedList<TableDataRow> rows) {
    this.rows = rows;
  }

  public String getSheetTitle() {
    return this.sheetTitle;
  }

  public void setSheetTitle(String sheetTitle) {
    this.sheetTitle = sheetTitle;
  }

  class SummaryData
  {
    String key;
    int index;
    int count;
    TableDataRow row;

    SummaryData()
    {
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer();
      sb.append(this.key).append("\t").append(this.index).append("\t").append(this.count).append("\t");

      for (TableDataCell cell : this.row.getCells()) {
        sb.append(cell.getValue()).append("\t");
      }
      return sb.toString();
    }
  }
}