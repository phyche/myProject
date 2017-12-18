package com.test.handler;

import com.test.db.entity.AccountLoginDTO;

/**
 * 接口TOKEN可检查接口
 * @author twy
 *
 */
public interface TokenCheckable {

	public void setMember(AccountLoginDTO user);
	
}
