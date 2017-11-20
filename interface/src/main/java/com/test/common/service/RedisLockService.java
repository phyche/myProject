package com.test.common.service;

/**
 * Created by ll on 2017-9-27.
 */
public interface RedisLockService {
    public boolean lock(String lockKey, Integer timeoutMsecs, Integer expireMsecs) throws InterruptedException;
    public void unlock(String lockKey);
}
