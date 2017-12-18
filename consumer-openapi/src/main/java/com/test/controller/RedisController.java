package com.test.controller;

import com.google.gson.Gson;
import com.myproject.json.JsonUtils;
import com.myproject.util.EncryptUtils;
import com.myproject.util.excel.ExcelUtils;
import com.myproject.util.excel.JsGridReportBase;
import com.myproject.util.excel.TableData;
import com.test.db.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/redis")
public class RedisController {

	static final Logger logger =  LoggerFactory.getLogger(RedisController.class);
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private MyService myService;

	/**
	 * 图片上传请求路径
	 */
	@Value("#{configProperties['requestPath']}")
	public String  imageRequestPathStr;

	public RedisController() {
	}
	@RequestMapping(value = "/backdoor", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView backdoor(ModelAndView modelAndView) {
		modelAndView.setViewName("/backdoor/redisBackDoor");
		modelAndView.addObject("imageRequestPathStr",imageRequestPathStr);
		return modelAndView;
	}
	@RequestMapping(value = "/setredis", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,String> setredis(String key, String val) {
		Map<String,String> res = new HashMap<>();
		res.put("msg","设置完毕");
//		redisDaoImpl.set(key,val);
		redisTemplate.opsForValue().set(key,val);
		return res;
	}

	@RequestMapping(value = "/setHashredis", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,String> setHashredis(String primaryKey, String vicekey,String val) {
		Map<String,String> res = new HashMap<>();
		res.put("msg","设置完毕");
		redisTemplate.opsForHash().put(primaryKey,vicekey, val);
		return res;
	}

	@RequestMapping(value = "/getredis", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,String> getredis(String key) {
		Map<String,String> res = new HashMap<>();
//		Object o = redisDaoImpl.get(key);
		Object o = redisTemplate.opsForValue().get(key);
		res.put("data", JsonUtils.toJson(o));
		return res;
	}

	@RequestMapping(value = "/getHashredis", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,String> getHashredis(String primaryKey,String vicekey) {
		Map<String,String> res = new HashMap<>();
//		Object o = redisDaoImpl.get(key);
		Object o = redisTemplate.opsForHash().get(primaryKey,vicekey);
		res.put("data", JsonUtils.toJson(o));
		return res;
	}

	@RequestMapping(value = "/delredis", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,String> delredis(String key) {
		Map<String,String> res = new HashMap<>();
//		redisDaoImpl.del(key.getBytes());
		redisTemplate.delete(key);
		res.put("msg","删除完毕");
		return res;
	}

	@RequestMapping(value = "/delHashredis", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String,String> delHashredis(String primaryKey,String vicekey) {
		Map<String,String> res = new HashMap<>();
		redisTemplate.opsForHash().delete(primaryKey,vicekey);
		res.put("msg","删除完毕");
		return res;
	}

	@RequestMapping(value = "/qsql", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void qsql(HttpServletResponse res, String qsql) {
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/json");

		List list = new ArrayList<>();
		try {
			list = getListBysql(qsql);
			Gson gson = new Gson();
			res.getWriter().write(gson.toJson(list));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@RequestMapping({"/reportBySql"})
	public void reportBySql(String qsql, HttpServletRequest request, HttpServletResponse response)
	{
		List list = new ArrayList();
		try {
			list = getListBysql(qsql);
		}catch (Exception e){
		}
		String title = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Map obj = null;
		if(list.size()>0){
			obj = (LinkedHashMap)list.get(0);
		}else{

		}
		if(obj == null){
			return;
		}
		Iterator it=obj.entrySet().iterator();
		int countcol = obj==null?0:obj.entrySet().size(),index = 0;
		String[] hearders = new String[countcol];
		String[] fields = new String[countcol];
		if(obj!=null){
			while (it.hasNext()){
				Map.Entry e=(Map.Entry)it.next();
				String key = (String)e.getKey();
				hearders[index] = key;
				fields[index] = key;
				index++;
			}
		}

//		String[] hearders = { "用户ID", "登录名称", "创建时间", "最后修改时间", "所属角色名称" };
//		String[] fields = { "id", "name", "createdatetime", "modifydatetime", "roleNames" };
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		try {
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(title, "admin", td);
		}
		catch (Exception e)
		{
		}
	}

	private List getListBysql(String qsql) throws Exception{
		if (qsql!=null) {
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(qsql);
			qsql = m.replaceAll("");
		}
		List list = myService.query(qsql);
		for(Object data:list){
			Map dataMap = (Map)data;
			Iterator ittmp=dataMap.entrySet().iterator();
			while (ittmp.hasNext()){
				Map.Entry e=(Map.Entry)ittmp.next();
				String key = (String)e.getKey();
				if(key.lastIndexOf("_fuyou")>-1 || key.lastIndexOf("_FUYOU")>-1){
					String val = (String)e.getValue();
					val = EncryptUtils.aesDecrypt(val,"fuyou");
					dataMap.put(key,val);
				}
			}
		}
		return list;
	}

}
