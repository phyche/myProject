package com.test.common.utils;

import com.test.common.enums.ReportEnum;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportUtil {

	public static Map<String,String> fixBeginEndTime(String type,String begin,String end) throws Exception {
		Calendar beginCal = Calendar.getInstance();
//		begin.set(2015,11,25);
		Calendar endCal = Calendar.getInstance();
		if(ReportEnum.QUERY_DATE_TYPE_DAY.getCode().equals(type)){
			Date beginDate = DateUtil.toDate(begin,"yyyy-MM-dd");
			Date endDate = DateUtil.toDate(end,"yyyy-MM-dd");
			beginCal.setTime(beginDate);
			endCal.setTime(endDate);
		}else if(ReportEnum.QUERY_DATE_TYPE_MONTH.getCode().equals(type)){
			Date beginDate = DateUtil.toDate(begin,"yyyy-MM");
			Date endDate = DateUtil.toDate(end,"yyyy-MM");
			beginCal.setTime(beginDate);
			beginCal.set(Calendar.DAY_OF_MONTH,1);
			endCal.setTime(endDate);
//				获取最后一天
			endCal.add(Calendar.MONTH,1);
			endCal.set(Calendar.DAY_OF_MONTH,1);
			endCal.add(Calendar.DATE,-1);
		}else if(ReportEnum.QUERY_DATE_TYPE_YEAR.getCode().equals(type)){
			Date beginDate = DateUtil.toDate(begin,"yyyy");
			Date endDate = DateUtil.toDate(end,"yyyy");
			beginCal.setTime(beginDate);
			beginCal.set(Calendar.DAY_OF_YEAR,1);
			beginCal.set(Calendar.DAY_OF_MONTH,1);
			endCal.setTime(endDate);
			endCal.add(Calendar.YEAR,1);
			endCal.set(Calendar.DAY_OF_MONTH,1);
			endCal.add(Calendar.DATE,-1);
		}
		Map<String,String> res = new HashMap<>();
		res.put("begin",DateUtil.praseDate(beginCal.getTime(),"yyyy-MM-dd"));
		res.put("end",DateUtil.praseDate(endCal.getTime(),"yyyy-MM-dd"));
		return res;
	}


	public static void main(String[] args) {

	}
}
