/**   
* @Title: RedisSyncAspect.java 
* @Package com.cyber.channel.aop 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年6月18日 下午4:42:30 
* @version V1.0   
*/
package com.test.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * @ClassName: RedisSyncAspect 
 * @Description: mysql同步redis 尽量不要和业务代码耦合在一起
 * @author cssuger@163.com 
 * @date 2016年6月18日 下午4:42:30 
 *  
 */
@Aspect
public class RedisSyncAspect {

	private  static Logger logger = LoggerFactory.getLogger(RedisSyncAspect.class);

	@Autowired
	private RedisTemplate redisTemplate;
	
	public void doAfter(JoinPoint jp) {
		// 获取当前线程绑定的连接
//		RedisConnection conn = RedisConnectionUtils.bindConnection(redisTemplate.getConnectionFactory(), true);
//		//返还给连接池
//		conn.close();
//		TransactionSynchronizationManager.unbindResource(redisTemplate.getConnectionFactory());
		//System.out.println("log Ending method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
	}

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable  {
		//String className = 
		String methodName = pjp.getSignature().getName();
		long time = System.currentTimeMillis();
		Object retVal = null;
		retVal = pjp.proceed();
		Object [] args = pjp.getArgs();
/*		if(null != args && args.length> 0){
			for(Object ob : args){
				if(ob instanceof BankDetail){
					BankDetail  bankdetail = (BankDetail)ob;
					buliderRedisOperate(methodName,bankdetail);
				}
			}
		}*/
		
		time = System.currentTimeMillis() - time;
		logger.info("process time: " + time + " ms"+":retVal"+retVal);
		return retVal;
	}

/*	private void addRedis(BankDetail  bankdetail){
		Map<Object,Object> bankMap = Maps.newHashMap();
		bankMap.put(bankdetail.getOrgCode(), bankdetail.getOrgName());
		redisDaoImpl.hputAll(Constants.BANK_KEY, bankMap);
	}*/
	
/*	private void buliderRedisOperate(String methodName,BankDetail  bankdetail){
		//增加操作
		if(methodName.equals("removeBankDetail")){
			
			redisDaoImpl.hdelete(Constants.BANK_KEY, new Object[]{bankdetail.getOrgCode()});
		}else if(methodName.equals("addBankDetail")){
			addRedis(bankdetail);
		}else if(methodName.equals("updateBankDetail")){
			addRedis(bankdetail);
		}
	}*/
	
	public void doBefore(JoinPoint jp) {
		//System.out.println("log Begining method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
	}

	public void doThrowing(JoinPoint jp, Throwable ex) {
		
	}


	
}
