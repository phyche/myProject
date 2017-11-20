package com.test.common.service.impl;

import com.test.common.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by caiqi on 2016/11/23.
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {
    private Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Autowired
    private RedisTemplate<String, Object>  redisTemplate;

    /**
     * 获取 RedisSerializer <br>
     * ------------------------------<br>
     */
    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }
    public void set(String key,String value) throws Exception{
//        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
//        opsForValue.set(key, value);
        this.set(key.getBytes(),value.getBytes());
    }
    public void set(final byte[] key,final byte[] value) throws Exception{
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.set(key, value);
                return null;
            }
        });
    }
    /**
     * @param key
     * @param seconds 过期时间 以秒为单位
     * @param value
     */
    public void setEX(final byte[] key,final long seconds,final byte[] value) throws Exception{
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.setEx(key, seconds, value);
                return null;
            }
        });
    }
    /**
     * 插入时判断是否存在该key
     * @param key
     * @param value
     * @return
     */
    public boolean setNX(final byte[] key,final byte[] value) throws Exception{
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.setNX(key, value);
            }
        });
    }
    public void del(final byte[] key) throws Exception{
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.del(key);
                return null;
            }
        });
    }

    public void delStrKey(String key) throws Exception{
        redisTemplate.delete(key);
    }

    public String get(String key) throws Exception{
//        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
//        return opsForValue.get(key);
        byte[] bytes = this.get(key.getBytes());
        String deserialize = getRedisSerializer().deserialize(bytes);
        return deserialize;
    }
    public byte[] get(final byte[] key) throws Exception{
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.get(key);
            }
        });
    }
    public long dbsize() throws Exception{
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    public boolean exists(final String key) throws Exception{
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }
    public Set<byte[]> keys(final String pattern) throws Exception{
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.keys(pattern.getBytes());
            }
        });
    }
    public Set<byte[]> keys(final byte[] pattern) throws Exception{
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.keys(pattern);
            }
        });
    }
    public void expire(final byte[] key,final long seconds) throws Exception{
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.expire(key, seconds);
                return null;
            }
        });
    }
    public void expire(String key,final long seconds) throws Exception{
        redisTemplate.expire(key,seconds, TimeUnit.MILLISECONDS);
    }
    /**
     * 删除DB中所有数据
     */
    public void flushDb() throws Exception{
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return null;
            }
        });
    }

    /**
     * 给list的左边添加一个元素
     * @param key
     * @param value
     * @return
     */
    public Long lpush(String key,Object value) throws Exception{
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.leftPush(key, value);
    }
    /**
     * 给list的右边添加一个元素
     * @param key
     * @param value
     * @return
     */
    public Long rpush(String key,Object value) throws Exception{
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.rightPush(key, value);
    }
    /**
     * 从list的左边取出一个元素
     * @param key
     * @return
     */
    public Object lpop(String key) throws Exception{
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.leftPop(key);
    }
    /**
     * 从list的右边取出一个元素
     * @param key
     * @return
     */
    public Object rpush(String key) throws Exception{
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.rightPop(key);
    }
    /**
     * 获取list的长度
     * @param key
     * @return
     */
    public Long llen(String key) throws Exception{
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.size(key);
    }
    /**
     * 获取list中下标为index的元素
     * @param key
     * @param index
     * @return
     */
    public Object lindex(String key,long index) throws Exception{
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        return opsForList.index(key, index);
    }

    /**
     * 给set中添加一个元素
     * @param key
     * @param values
     * @return
     */
    public Long sadd(String key,Object... values) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.add(key, values);
    }
    /**
     * 删除并返回set的任一成员
     * @param key
     * @return
     */
    public Object spop(String key) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.pop(key);
    }
    /**
     * 返回SET任一成员
     * @param key
     * @return
     */
    public Object srandomMember(String key) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.randomMember(key);
    }
    /**
     * 判断元素是否是set中的元素
     * @param key
     * @param o
     * @return
     */
    public Boolean isMember(String key,Object o) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.isMember(key, o);
    }
    /**
     * 获取size的元素总数
     * @param key
     * @return
     */
    public Long ssize(String key) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.size(key);
    }
    /**
     * 获取set的所有元素
     * @param key
     * @return
     */
    public Set<Object> smembers(String key) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.members(key);
    }
    /**
     *比较key与otherKey的set集合，返回与otherKey集合不一样的set集合
     * @param key
     * @param otherkey
     * @return
     * @throws Exception
     */
    public Set<Object> sdifference(String key,String otherkey) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.difference(key, otherkey);
    }

    /**
     * 比较key与otherKey的set集合，把集合不一样的set集合放入 resultKey结果redis中
     * @param key
     * @param otherkey
     * @param resultKey
     * @return
     * @throws Exception
     */
    public long sdifferenceAndStore(String key,String otherkey,String resultKey) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.differenceAndStore(key, otherkey,resultKey);
    }
    /**
     * 比较key与otherKey的set集合，取出二者交集，返回set交集合
     * @param key
     * @param otherkey
     * @return
     * @throws Exception
     */
    public Set<Object> sintersect(String key,String otherkey) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.intersect(key, otherkey);
    }

    /**
     * 比较key与otherKey的set集合，取出二者交集，把set交集合放入resultKey结果redis中
     * @param key
     * @param otherkey
     * @param resultKey
     * @return
     * @throws Exception
     */
    public long sintersectAndStore(String key,String otherkey,String resultKey) throws Exception{
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.intersectAndStore(key, otherkey,resultKey);
    }
    /**
     * 给zset中添加一个带有score的元素
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean zadd(String key,Object value,double score) throws Exception{
        ZSetOperations<String, Object> opsForZSet = redisTemplate.opsForZSet();
        return opsForZSet.add(key, value, score);
    }
    /**
     * 从zset中取出score在start和end之间的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> zrange(String key,long start,long end) throws Exception{
        ZSetOperations<String, Object> opsForZSet = redisTemplate.opsForZSet();
        return opsForZSet.range(key, start, end);
    }
    /**
     * 计算zset的成员个数
     * @param key
     * @return
     */
    public Long zsize(String key) throws Exception{
        ZSetOperations<String, Object> opsForZSet = redisTemplate.opsForZSet();
        return opsForZSet.size(key);
    }

    /**
     * 给hash中添加一个元素
     * @param key
     * @param hashKey
     * @param value
     */
    public void hput(String key,Object hashKey,Object value) throws Exception{
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        opsForHash.put(key, hashKey, value);
    }
    /**
     * 把map的所有元素存入hash中
     * @param key
     * @param map
     */
    public void hputAll(String key,Map<Object, Object> map) throws Exception{
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        opsForHash.putAll(key, map);
    }
    /**
     * 获取hash中的一个元素
     * @param key
     * @param field
     * @return
     */
    public Object hget(String key,String field) throws Exception{
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, field);
    }
    /**
     * 获取hash中所有元素
     * @param key
     * @return
     */
    public Map<Object, Object> hgetAll(String key) throws Exception{
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<Object, Object> entries = opsForHash.entries(key);
        return entries;
    }
/*    public Map<byte[], byte[]>  hgetAll(final String key) throws Exception{

        return redisTemplate.execute(new RedisCallback<Map<byte[], byte[]> >() {
            @Override
            public Map<byte[], byte[]>  doInRedis(RedisConnection connection)
                    throws DataAccessException {
//                return connection.hGetAll(new GenericToStringSerializer<String>(String.class).serialize(key));
                return connection.hGetAll(key.getBytes());
            }
        });
    }*/


    /**
     * 删除hash中指定的元素
     * @param key
     * @param hashKeys
     * @return
     */
    public Long hdelete(String key,Object... hashKeys) throws Exception{
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        return opsForHash.delete(key, hashKeys);
    }
    /**
     * 获取hash中所有的fields
     * @param key
     * @return
     */
    public Set<Object> hkeys(String key) throws Exception{
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        return opsForHash.keys(key);
    }
    /**
     * 计算hash中元素个数
     * @param key
     * @return
     */
    public Long hsize(String key) throws Exception{
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        return opsForHash.size(key);
    }
}
