package com.test.common.service;


import com.test.common.enums.SnEnum;

public interface SnService {
	/**
	 * 查询下一个自增长ID
	 */
	public long nextSeq();

	/**
	 * 根据seqId查询下一个自增长ID
	 * 
	 * @param sn
	 * @return
	 */
	public long nextSeqById(SnEnum sn);
	
	/**
	 * 获取业务code
	 * @param sn
	 * @return
	 */
	public String getSnCode(SnEnum sn);
	/**
	 * 根据seqId查询下一个自增长ID
	 *
	 * @param seqId
	 * @return
	 */
	public long nextSeqById(String seqId);
	/**
	 * 获取业务code
	 * @param str
	 * @return
	 */
	public String getSnCode(String str, String seqId);
}
