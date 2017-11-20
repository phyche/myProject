package com.myproject.util.excel;

import java.util.LinkedList;
import java.util.List;

public class TableColumn
{
  public static final int COLUMN_TYPE_STRING = 0;
  public static final int COLUMN_TYPE_FLOAT_2 = 1;
  public static final int COLUMN_TYPE_FLOAT_3 = 2;
  public static final int COLUMN_TYPE_INTEGER = 3;
  public static final int COLUMN_TYPE_FORMULA = 4;
  public static final int COLUMN_TYPE_RED_BG = 10;
  public static final int COLUMN_TYPE_YELLOW_BG = 11;
  public static final int COLUMN_TYPE_GREEN_BG = 12;
  private String id;
  private String display;
  private String dbField;
  private int columnType = 0;

  private boolean grouped = false;

  private boolean displaySummary = false;
  private String aggregateRule;
  private boolean isVisible = true;

  private boolean isComplex = false;
  private String common;
  private int width = 100;
  private int percentWidth;
  private String widthType = "P";
  protected int level;
  protected TableColumn parent = null;

  protected List<TableColumn> children = new LinkedList();

  public void addChild(TableColumn column) {
    this.children.add(column);
    column.parent = this;
    if (column.isVisible())
      this.isComplex = true;
  }

  public List<TableColumn> getChildren() {
    LinkedList ret = new LinkedList();
    for (TableColumn c : this.children) {
      if (c.isVisible())
        ret.add(c);
    }
    return ret;
  }

  public boolean isComplex() {
    return this.isComplex;
  }

  public int getLength() {
    int len = 0;
    if (isComplex()) {
      for (TableColumn col : getChildren())
        len += col.getLength();
    }
    else {
      len = 1;
    }
    return len;
  }

  public String getDisplay() {
    return this.display;
  }

  public void setDisplay(String display) {
    this.display = display;
  }

  public String getDbField() {
    return this.dbField;
  }

  public void setDbField(String dbField) {
    this.dbField = dbField;
  }

  public int getColumnType() {
    return this.columnType;
  }

  public String getColumnStringType(int columnType) {
    String stringType = "string";
    if (columnType == 3)
      stringType = "int";
    else if (columnType == 1)
      stringType = "float";
    else if (columnType == 2) {
      stringType = "float";
    }
    return stringType;
  }

  public void setColumnType(int columnType) {
    this.columnType = columnType;
  }

  public boolean isGrouped() {
    return this.grouped;
  }

  public void setGrouped(boolean grouped) {
    this.grouped = grouped;
  }

  public boolean isDisplaySummary() {
    return this.displaySummary;
  }

  public void setDisplaySummary(boolean displaySummary) {
    this.displaySummary = displaySummary;
  }

  public boolean isVisible() {
    return this.isVisible;
  }

  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  public String getAggregateRule() {
    return this.aggregateRule;
  }

  public void setAggregateRule(String aggregateRule) {
    this.aggregateRule = aggregateRule;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCommon() {
    return this.common;
  }

  public void setCommon(String common) {
    this.common = common;
  }

  public int getWidth() {
    return this.width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getPercentWidth() {
    return this.percentWidth;
  }

  public void setPercentWidth(int percentWidth) {
    this.percentWidth = percentWidth;
  }

  public String getWidthType() {
    return this.widthType;
  }

  public void setWidthType(String widthType) {
    this.widthType = widthType;
  }
}