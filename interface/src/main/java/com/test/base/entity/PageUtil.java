package com.test.base.entity;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.test.common.utils.StringUtil;
import org.apache.ibatis.session.RowBounds;

import javax.servlet.http.HttpServletResponse;

public class PageUtil
{
  public static final int FIRST_PAGE = 1;
  public static final int DEFAULT_PAGE_SIZE = 10;

  public static RowBounds get(Integer pageNo, Integer pageSize)
  {
    if ((pageNo == null) || (pageNo.intValue() < 1)) {
      pageNo = Integer.valueOf(1);
    }
    if ((pageSize == null) || (pageSize.intValue() < 10)) {
      pageSize = Integer.valueOf(10);
    }
    int first = getFirst(pageNo.intValue(), pageSize.intValue());
    int last = getLast(first, pageSize.intValue());
    return new RowBounds(first, last);
  }

  private static int getFirst(int pageNo, int pageSize) {
    return (pageNo - 1) * pageSize;
  }

  private static int getLast(int first, int pageSize) {
    return first + pageSize;
  }

  public static int begin(int pageNo, int pageSize) {
    return getFirst(pageNo, pageSize);
  }

  public static int end(int pageNo, int pageSize) {
    return getLast(getFirst(pageNo, pageSize), pageSize);
  }

  public static void printJsonToPage(Page<?> pageModel, HttpServletResponse response)
    throws Exception
  {
    SerializeWriter out = new SerializeWriter();
    JSONSerializer serializer = new JSONSerializer(out);
    serializer.write(pageModel);
    out.writeTo(response.getWriter());
  }

  private static int getTotalPage(int totalPage, int perPage)
  {
    if (perPage != 0) {
      if (totalPage % perPage != 0) {
        return totalPage / perPage + 1;
      }
      return totalPage / perPage;
    }

    return 0;
  }

  public static int validateRequestPage(String requestPage)
  {
    int currentPage = 1;
    if (!StringUtil.isEmpty(requestPage)) {
      try {
        currentPage = Integer.parseInt(requestPage);
      } catch (Exception e) {
        currentPage = 1;
      }
    }
    return currentPage;
  }

  private static int validateTotalPage(int currentPage, int totalPage)
  {
    if (currentPage > totalPage) {
      currentPage = totalPage;
    }
    if (currentPage < 1) {
      currentPage = 1;
    }

    return currentPage;
  }

  public static PageObject makePageObject(String myCurrentPage, int totalRow, int perPage)
  {
    int currentPage = validateRequestPage(myCurrentPage);

    int totalPage = getTotalPage(totalRow, perPage);

    currentPage = validateTotalPage(currentPage, totalPage);
    PageObject pageObject = new PageObject();
    pageObject.setCurrentPage(currentPage);
    pageObject.setTotalPage(totalPage);
    pageObject.setPerPage(perPage);
    pageObject.setTotalRow(totalRow);
    return pageObject;
  }
}