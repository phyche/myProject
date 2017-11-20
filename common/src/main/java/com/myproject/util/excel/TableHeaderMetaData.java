package com.myproject.util.excel;

import java.util.LinkedList;
import java.util.List;

public class TableHeaderMetaData
{
  private LinkedList<TableColumn> columns;
  private LinkedList<TableColumn> leafs;
  private String common;
  public int maxlevel = 0;

  public TableHeaderMetaData() {
    this.columns = new LinkedList();
    this.leafs = new LinkedList();
  }

  public void addColumn(TableColumn col)
  {
    setLevel(col, 1);
    this.columns.add(col);
    addLeafColumn(col);
  }

  public void refresh()
  {
    this.maxlevel = 1;
    for (TableColumn col : this.columns)
      if (col.isVisible()) {
        col.level = 1;
        int level = refreshChildren(col);
        if (level > this.maxlevel)
          this.maxlevel = level;
      }
  }

  private int refreshChildren(TableColumn parent)
  {
    if (parent.children.size() != 0) {
      int max = parent.level;
      for (TableColumn col : parent.children) {
        if (col.isVisible()) {
          col.parent = parent;
          parent.level += 1;
          int level = refreshChildren(col);
          if (level > max)
            max = level;
        }
      }
      return max;
    }
    return parent.level;
  }

  private void setLevel(TableColumn col, int level)
  {
    col.level = level;
    if ((col.isVisible()) && (level > this.maxlevel))
      this.maxlevel = level;
  }

  private void addLeafColumn(TableColumn col)
  {
    if (col.parent != null)
      setLevel(col, col.parent.level + 1);
    if (col.isComplex()) {
      for (TableColumn c : col.getChildren())
        addLeafColumn(c);
    }
    else
      this.leafs.add(col);
  }

  public List<TableColumn> getColumns()
  {
    return this.leafs;
  }

  public List<TableColumn> getOriginColumns()
  {
    LinkedList ret = new LinkedList();
    for (TableColumn c : this.columns) {
      if (c.isVisible())
        ret.add(c);
    }
    return ret;
  }

  public TableColumn getColumnAt(int index)
  {
    return (TableColumn)this.leafs.get(index);
  }

  public int getColumnCount()
  {
    return this.leafs.size();
  }

  public String getCommon() {
    return this.common;
  }

  public void setCommon(String common) {
    this.common = common;
  }
}