package com.wyt.study;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis操作工具类
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**************************************** 通用操作 **************************************/

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据key删除缓存，key可传多个
     *
     * @param keys
     */
    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            redisTemplate.delete(Arrays.asList(keys));
        }
    }

    /**
     * 获取缓存的过期时间(单位秒)
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存的过期时间(单位秒)
     *
     * @param key
     * @param expire
     * @return
     */
    public boolean setExpire(String key, long expire) {
        try {
            return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**************************************** 普通类型(String)操作 **************************************/

    /**
     * 根据key获取普通类型(String)缓存的值
     *
     * @param key
     * @return 值
     */
    public Object get(String key) {
        if (key == null) return null;
        //opsForValue 为普通类型缓存，即String类型的先骨干操作
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置普通类型(String)缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置普通类型(String)缓存
     *
     * @param key
     * @param value
     * @param expire 过期时间(单位为秒)，如果小于等于0则表示不过期
     * @return
     */
    public boolean set(String key, String value, long expire) {
        try {
            if (expire > 0) {
                redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 自增
     *
     * @param key
     * @return 自增后的值
     */
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 按步长增长
     *
     * @param key
     * @param delta
     * @return 增长后的值
     */
    public Long incrBy(String key, long delta) {
        if (delta == 0) return incr(key);
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 自减
     *
     * @param key
     * @return 自减后的值
     */
    public Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 按步长自减
     *
     * @param key
     * @param delta
     * @return 自减后的值
     */
    public Long decrBy(String key, long delta) {
        if (delta == 0) return redisTemplate.opsForValue().decrement(key);
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**************************************** Hash类型操作 **************************************/

    /**
     * 判断hash key中是否有该项的值
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 删除hash key中的项(可多个)的值
     *
     * @param key
     * @param items
     * @return 删除的项的个数
     */
    public long hdel(String key, String... items) {
        return redisTemplate.opsForHash().delete(key, items);
    }

    /**
     * 获取hash key中某项的值
     *
     * @param key  缓存的key
     * @param item hash中的属性名称
     * @return
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 设置hash key中某项的值
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取hash key中多项的值
     *
     * @param key
     * @param items
     * @return
     */
    public List<Object> hmget(String key, String... items) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(items));
    }

    /**
     * 批量设置hash key中项的值
     *
     * @param key
     * @param itemVaues
     * @return
     */
    public boolean hmset(String key, Map<String, Object> itemVaues) {
        try {
            redisTemplate.opsForHash().putAll(key, itemVaues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取hash Key对应的所有键值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hash增长
     *
     * @param key
     * @param item
     * @param delta 步长
     * @return 增加后的值
     */
    public Long hincr(String key, String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * hash增长
     *
     * @param key
     * @param item
     * @param delta 步长
     * @return 增加后的值
     */
    public Double hincr(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * hash减少
     *
     * @param key
     * @param item
     * @param delta 步长
     * @return 增加后的值
     */
    public Long hdecr(String key, String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**
     * hash减少
     *
     * @param key
     * @param item
     * @param delta 步长
     * @return 增加后的值
     */
    public Double hdecr(String key, String item, double delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    /**************************************** List类型操作 **************************************/

    /**
     * 获取list缓存的长度
     *
     * @param key
     * @return
     */
    public long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 删除list中count个值为value的元素
     *
     * @param key
     * @param count
     * @param value
     * @return 删除元素的个数
     */
    public long lrem(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 设置list中索引为index的元素的值
     *
     * @param key
     * @param index
     * @param value
     */
    public void lset(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key
     * @param start 起始索引
     * @param end   结束索引 0 到 -1代表获取所有值
     * @return
     */
    public List<Object> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key
     * @param start 起始索引
     * @param end   结束索引 0 到 -1代表获取所有值
     * @return
     */
    public Object lindex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 只保留list指定区间内的元素，不在指定区间之内的元素都将被删除。
     *
     * @param key
     * @param start 起始索引
     * @param end   结束索引
     * @return
     */
    public void ltrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 将一个或多个值插入到list头部
     *
     * @param key
     * @param value
     * @return
     */
    public long lpush(String key, String... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 将一个或多个值插入到list
     *
     * @param key
     * @param value
     * @return
     */
    public long rpush(String key, String... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 移出并获取list的头部元素
     * @param key
     * @return
     */
    public Object lpop(String key){
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取list的尾部元素
     * @param key
     * @return
     */
    public Object rpop(String key){
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移出并获取list的头部元素，如果list没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param key
     * @param timeout 单位秒，必须大于0，如果等于0会一直阻塞
     * @return
     */
    public Object lpopB(String key, long timeout){
        return redisTemplate.opsForList().leftPop(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 移出并获取list的尾部元素，如果list没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param key
     * @param timeout 单位秒，必须大于0，如果等于0会一直阻塞
     * @return
     */
    public Object rpopB(String key, long timeout){
        return redisTemplate.opsForList().rightPop(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 移除源list的最后一个元素，并将该元素添加到目标list并返回
     * @param sourcekey 源list key
     * @param destinationkey 目标list key
     * @return 弹出并添加到目标list的元素的值
     */
    public Object rpoplpush(String sourcekey, String destinationkey){
        return redisTemplate.opsForList().rightPopAndLeftPush(sourcekey, destinationkey);
    }

    /**
     * 移除源list的最后一个元素，并将该元素添加到目标list并返回, 如果源list没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param sourcekey 源list key
     * @param destinationkey 目标list key
     * @param timeout 单位秒，必须大于0，如果等于0会一直阻塞
     * @return 弹出并添加到目标list的元素的值
     */
    public Object rpoplpushB(String sourcekey, String destinationkey, long timeout){
        return redisTemplate.opsForList().rightPopAndLeftPush(sourcekey, destinationkey, timeout, TimeUnit.SECONDS);
    }

}
