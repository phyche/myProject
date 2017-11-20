package com.myproject.util.excel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TableDataRow
{
  private LinkedList<TableDataCell> cells;
  private TableData table;
  private int rowStyle = 0;

  public void addCell(TableDataCell cell) {
    this.cells.add(cell);
  }

  public void addCell(String value) {
    TableDataCell cell = new TableDataCell(this);
    cell.setValue(value);
    cell.setCellStyle(this.rowStyle);
    addCell(cell);
  }

  public void addCell(Integer value) {
    TableDataCell cell = new TableDataCell(this);
    cell.setValue(value.intValue());
    cell.setCellStyle(this.rowStyle);
    addCell(cell);
  }

  public void addCell(Double value) {
    TableDataCell cell = new TableDataCell(this);
    cell.setValue(value.doubleValue());
    cell.setCellStyle(this.rowStyle);
    addCell(cell);
  }

  public void addCell(Object value) {
    if ((value instanceof String))
      addCell((String)value);
    else if ((value instanceof Integer))
      addCell((Integer)value);
    else if ((value instanceof Double))
      addCell((Double)value);
    else if ((value instanceof BigDecimal))
      addCell(value.toString());
    else if ((value instanceof Long))
      addCell(value.toString());
    else if ((value instanceof Date))
      addCell(((Date)value).toLocaleString());
    else if ((value instanceof Timestamp))
      addCell(((Timestamp)value).toLocaleString());
    else if (value == null)
      addCell("");
    else
      addCell(value + "");
  }

  public TableDataCell getCellAt(int index)
  {
    return (TableDataCell)this.cells.get(index);
  }

  public List<TableDataCell> getCells() {
    return this.cells;
  }

  public TableData getTable() {
    return this.table;
  }

  public TableDataRow(TableData table) {
    this.cells = new LinkedList();
    this.table = table;
  }

  public void setRowStyle(int rowStyle) {
    this.rowStyle = rowStyle;
  }

  public int getRowStyle() {
    return this.rowStyle;
  }
}