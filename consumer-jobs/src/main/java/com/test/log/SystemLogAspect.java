package com.test.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 切点类
 * 
 * @author MaQiang
 * @since 2016-05-24
 * @version 1.0
 */
@Aspect
public class SystemLogAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String requestPath = null; // 请求地址
	private String userName = null; // 用户名
	private Map<?, ?> inputParamMap = null; // 传入参数
	private Map<String, Object> outputParamMap = null; // 存放输出结果
	private long startTimeMillis = 0; // 开始时间
	private long endTimeMillis = 0; // 结束时间
	private String timestamp = null;// 执行时间
	private String methodName = null;// 类名.方法名
	private int userId = 0;

	@Pointcut("@annotation(com.cyber.log.CyberLog)")
	public void log() {
		//System.out.println("我是一个切入点");
	}

	/**
	 * 
	 * @Title：doBeforeInServiceLayer
	 * @Description: 方法调用前触发 记录开始时间
	 * @param joinPoint
	 */
	@Before("execution(* com.cyber.service..*.*(..))")
	public void doBeforeInServiceLayer(JoinPoint joinPoint) {
		startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		timestamp = df.format(new Date());// new Date()为获取当前系统时间
	}

	/**
	 * 
	 * @Title：doAfterInServiceLayer
	 * @Description: 方法调用后触发 记录结束时间
	 * @param joinPoint
	 */
	@After("execution(* com.cyber.service..*.*(..))")
	public void doAfterInServiceLayer(JoinPoint joinPoint) {
		endTimeMillis = System.currentTimeMillis(); // 记录方法执行完成的时间
		methodName = joinPoint.getTarget().getClass().getName() + "."
				+ joinPoint.getSignature().getName();
		
		this.saveLog();
	}

	/**
	 * 
	 * @Title：doAround
	 * @Description: 环绕触发
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* com.cyber.service..*.*(..))")
	public void doAround(ProceedingJoinPoint pjp) throws Throwable {
		/**
		 * 1.获取request信息 2.根据request获取session 3.从session中取出登录用户信息
		 */
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		// 从session中获取用户信息
/*		User user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER);
		if (user != null) {
			userId = user.getId();
			userName = user.getName();
		} else {
			userName = Constants.VISITOR;
		}*/
		// 获取输入参数
		inputParamMap = this.getParameterMap(request);
		// 获取请求地址
		requestPath = request.getRequestURI();

		// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
		/*
		 * outputParamMap = new HashMap<String, Object>(); Object result =
		 * pjp.proceed();// result的值就是被拦截方法的返回值 outputParamMap.put("result",
		 * result);
		 */
	}

	/**
	 * 
	 * @Title：saveLog
	 * @Description: 输出日志 日志写入数据库
	 */
	private void saveLog() {
/*		BusinessLog bl = new BusinessLog();
		bl.setMethodName(methodName);
		bl.setParams("");
		bl.setTimeGap(String.valueOf(endTimeMillis - startTimeMillis));
		bl.setTimestamp(timestamp);
		bl.setUrl(requestPath);
		bl.setUserId(userId);
		bl.setUserName(userName);
		logger.info(JsonUtil.objectToJson(bl));*/
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}
