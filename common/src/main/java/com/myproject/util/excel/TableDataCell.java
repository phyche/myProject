package com.myproject.util.excel;

import java.text.DecimalFormat;

public class TableDataCell
{
  private String value;
  private int intValue;
  private double doubleValue;
  private TableDataRow row;
  private int columnIndex;
  private static DecimalFormat format2 = new DecimalFormat("0.##");

  private static DecimalFormat format3 = new DecimalFormat("0.###");

  private int cellStyle = 0;

  public TableDataCell(TableDataRow row) {
    this(-1, row);
  }

  public TableDataCell(int index, TableDataRow row) {
    this.row = row;
    if (index == -1)
      index = row.getCells().size();
    else {
      this.columnIndex = index;
    }
    this.value = "";
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setValue(int value) {
    this.value = String.valueOf(value);
    this.intValue = value;
  }

  public void setValue(double value) {
    int type = this.row.getTable().getTableHeader().getColumnAt(this.columnIndex).getColumnType();

    if (type == 1)
      this.value = format2.format(value);
    else if (type == 2) {
      this.value = format3.format(value);
    }
    this.doubleValue = value;
    this.value = String.valueOf(value);
  }

  public String getValue() {
    return this.value;
  }

  public TableDataRow getRow() {
    return this.row;
  }

  public int getColumnIndex() {
    return this.columnIndex;
  }

  public int getIntValue() {
    return this.intValue;
  }

  public double getDoubleValue() {
    return this.doubleValue;
  }

  public void setCellStyle(int cellStyle) {
    this.cellStyle = cellStyle;
  }

  public int getCellStyle() {
    return this.cellStyle;
  }
}