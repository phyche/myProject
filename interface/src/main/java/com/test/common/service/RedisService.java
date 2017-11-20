package com.test.common.service;

import java.util.Map;
import java.util.Set;

public interface RedisService {

    public void set(String key, String value) throws Exception;
    public void set(final byte[] key, final byte[] value) throws Exception;
    /**
     * @param key
     * @param seconds 过期时间
     * @param value
     */
    public void setEX(final byte[] key, final long seconds, final byte[] value) throws Exception;
    /**
     * 插入时判断是否存在该key
     * @param key
     * @param value
     * @return
     */
    public boolean setNX(final byte[] key, final byte[] value) throws Exception;
    public void del(final byte[] key) throws Exception;
    public void delStrKey(String key)throws Exception;
    public String get(String key) throws Exception;
    public byte[] get(final byte[] key) throws Exception;
    public long dbsize() throws Exception;

    public boolean exists(final String key) throws Exception;
    public Set<byte[]> keys(final String pattern) throws Exception;
    public Set<byte[]> keys(final byte[] pattern) throws Exception;
    public void expire(final byte[] key, final long seconds) throws Exception;
    public void expire(String key, final long seconds) throws Exception;
    /**
     * 删除DB中所有数据
     */
    public void flushDb() throws Exception;

    /**
     * 给list的左边添加一个元素
     * @param key
     * @param value
     * @return
     */
    public Long lpush(String key, Object value) throws Exception;
    /**
     * 给list的右边添加一个元素
     * @param key
     * @param value
     * @return
     */
    public Long rpush(String key, Object value) throws Exception;
    /**
     * 从list的左边取出一个元素
     * @param key
     * @return
     */
    public Object lpop(String key) throws Exception;
    /**
     * 从list的右边取出一个元素
     * @param key
     * @return
     */
    public Object rpush(String key) throws Exception;
    /**
     * 获取list的长度
     * @param key
     * @return
     */
    public Long llen(String key) throws Exception;
    /**
     * 获取list中下标为index的元素
     * @param key
     * @param index
     * @return
     */
    public Object lindex(String key, long index) throws Exception;
    /**
     * 给set中添加一个元素
     * @param key
     * @param values
     * @return
     */
    public Long sadd(String key, Object... values) throws Exception;
    /**
     * 删除并返回set的任一成员
     * @param key
     * @return
     */
    public Object spop(String key) throws Exception;
    /**
     * 返回SET任一成员
     * @param key
     * @return
     */
    public Object srandomMember(String key) throws Exception;
    /**
     * 判断元素是否是set中的元素
     * @param key
     * @param o
     * @return
     */
    public Boolean isMember(String key, Object o) throws Exception;
    /**
     *比较key与otherKey的set集合，返回与otherKey集合不一样的set集合
     * @param key
     * @param otherkey
     * @return
     * @throws Exception
     */
    public Set<Object> sdifference(String key, String otherkey) throws Exception;
    /**
     * 比较key与otherKey的set集合，把集合不一样的set集合放入 resultKey结果redis中
     * @param key
     * @param otherkey
     * @param resultKey
     * @return
     * @throws Exception
     */
    public long sdifferenceAndStore(String key, String otherkey, String resultKey) throws Exception;
    /**
     * 比较key与otherKey的set集合，取出二者交集，返回set交集合
     * @param key
     * @param otherkey
     * @return
     * @throws Exception
     */
    public Set<Object> sintersect(String key, String otherkey) throws Exception;
    /**
     * 比较key与otherKey的set集合，取出二者交集，把set交集合放入resultKey结果redis中
     * @param key
     * @param otherkey
     * @param resultKey
     * @return
     * @throws Exception
     */
    public long sintersectAndStore(String key, String otherkey, String resultKey) throws Exception;
    /**
     * 获取size的元素总数
     * @param key
     * @return
     */
    public Long ssize(String key) throws Exception;
    /**
     * 获取set的所有元素
     * @param key
     * @return
     */
    public Set<Object> smembers(String key) throws Exception;
    /**
     * 给zset中添加一个带有score的元素
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean zadd(String key, Object value, double score) throws Exception;
    /**
     * 从zset中取出score在start和end之间的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> zrange(String key, long start, long end) throws Exception;
    /**
     * 计算zset的成员个数
     * @param key
     * @return
     */
    public Long zsize(String key) throws Exception;
    /**
     * 给hash中添加一个元素
     * @param key
     * @param hashKey
     * @param value
     */
    public void hput(String key, Object hashKey, Object value) throws Exception;
    /**
     * 把map的所有元素存入hash中
     * @param key
     * @param map
     */
    public void hputAll(String key, Map<Object, Object> map) throws Exception;
    /**
     * 获取hash中的一个元素
     * @param key
     * @param field
     * @return
     */
    public Object hget(String key, String field) throws Exception;
    /**
     * 获取hash中所有元素
     * @param key
     * @return
     */
//    public Map<byte[], byte[]> hgetAll(final String key) throws Exception;
    public Map<Object, Object> hgetAll(String key) throws Exception;
    /**
     * 删除hash中指定的元素
     * @param key
     * @param hashKeys
     * @return
     */
    public Long hdelete(String key, Object... hashKeys) throws Exception;
    /**
     * 获取hash中所有的fields
     * @param key
     * @return
     */
    public Set<Object> hkeys(String key) throws Exception;
    /**
     * 计算hash中元素个数
     * @param key
     * @return
     */
    public Long hsize(String key) throws Exception;
}
