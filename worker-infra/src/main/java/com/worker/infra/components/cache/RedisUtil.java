package com.worker.infra.components.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类，基于spring和redis的redisTemplate工具类
 * 针对所有的hash,都是以h开头的方法
 * 针对所有的Set,都是以s开头的方法
 * 针对所有的List,都是以l开头的方法
 * 针对所有的bitmap,都是以b开头的方法
 *  @author
 * @date: 2023/11/4 2:19
 */
@Component
@Slf4j
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    public RedisTemplate<String, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    private String prefix(String key) {
        return "worker" + ":" + key;
    }

    // ============================common=============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return 是否成功 True：成功 FALSE：失败
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(this.prefix(key), time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtil Exception, key is [{}]", key, e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(this.prefix(key), TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 是否成功 True：成功 FALSE：失败
     */
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(this.prefix(key));
        } catch (Exception e) {
            log.error("RedisUtil hasKey Exception, key is [{}]", key, e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(this.prefix(key[0]));
            } else {
                String[] newKeys = new String[key.length];
                for (int i = 0; i < key.length; i++) {
                    newKeys[i] = this.prefix(key[i]);
                }

                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(newKeys));
            }
        }
    }

    /**
     * 删除某类缓存(谨慎使用 使用场景 key不多的情况)
     *
     * @param keyPrefix 某类key前缀
     */
    public void delByKeyPrefix(String keyPrefix) {
        Set<String> keys = redisTemplate.keys(this.prefix(keyPrefix + "*"));
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        redisTemplate.delete(keys);
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Serializable get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(this.prefix(key));
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return 是否成功 True：成功 FALSE：失败
     */
    public boolean set(String key, Serializable value) {
        try {
            redisTemplate.opsForValue().set(this.prefix(key), value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil set Exception, key is [{}]", key, e);
            return false;
        }

    }

    /**
     * 数值缓存自增
     *
     * @param key 键
     */
    public Long incr(String key) {
        try {
            return redisTemplate.opsForValue().increment(this.prefix(key));
        } catch (Exception e) {
            log.error("RedisUtil incr Exception, key is [{}]", key, e);
            return null;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return 是否成功 True：成功 FALSE：失败
     */
    public boolean set(String key, Serializable value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(this.prefix(key), value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtil Exception, key is [{}]", key, e);
            return false;
        }
    }

    /**
     * 缓存放入
     *
     * @param key   缓存Key
     * @param value 缓存值
     * @param time  毫秒
     * @return 是否成功 True：成功 FALSE：失败
     */
    public Boolean setIfAbsent(String key, Serializable value, long time) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(this.prefix(key), value, time, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("RedisUtil setIfAbsent() Exception, key is [{}]", key, e);
            return false;
        }

    }
    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(this.prefix(key), item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(this.prefix(key));
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Serializable> map) {
        try {
            redisTemplate.opsForHash().putAll(this.prefix(key), map);
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Serializable> map, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().putAll(this.prefix(key), map);
            if (time > 0) {
                expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Serializable value) {
        try {
            redisTemplate.opsForHash().put(this.prefix(key), item, value);
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Serializable value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().put(this.prefix(key), item, value);
            if (time > 0) {
                expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error(key, e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, String... item) {
        redisTemplate.opsForHash().delete(this.prefix(key), item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(this.prefix(key), item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(this.prefix(key), item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(this.prefix(key), item, -by);
    }


    // ===============================list=================================

    /**
     * 获取list缓存的内容 取出来的元素 总数 end-start+1
     *
     * @param key   键
     * @param start 开始 0 是第一个元素
     * @param end   结束 -1代表所有值
     * @return
     */
    public List<Serializable> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(this.prefix(key), start, end);
        } catch (Exception e) {
            log.error("RedisUtil lGet() Exception, key is [{}]", key, e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return 缓存的长度
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(this.prefix(key));
        } catch (Exception e) {
            log.error("RedisUtil lGetListSize() Exception, key is [{}]", key, e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 值
     */
    public Serializable lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(this.prefix(key), index);
        } catch (Exception e) {
            log.error("RedisUtil lGetIndex() Exception, key is [{}]", key, e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public boolean lSet(String key, Serializable value) {
        try {
            redisTemplate.opsForList().rightPush(this.prefix(key), value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil lSet() Exception, key is [{}]", key, e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 是否成功
     */
    public boolean lSet(String key, Serializable value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForList().rightPush(this.prefix(key), value);
            if (time > 0) {
                expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtil lSet() Exception, key is [{}]", key, e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public boolean lSet(String key, List<Serializable> value) {
        try {
            redisTemplate.opsForList().rightPushAll(this.prefix(key), value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil lSet() Exception, key is [{}]", key, e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 是否成功
     */
    public boolean lSet(String key, List<Serializable> value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForList().rightPushAll(this.prefix(key), value);
            if (time > 0) {
                expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error("RedisUtil lSet() Exception, key is [{}]", key, e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return 是否成功
     */
    public boolean lUpdateIndex(String key, long index, Serializable value) {
        try {
            redisTemplate.opsForList().set(this.prefix(key), index, value);
            return true;
        } catch (Exception e) {
            log.error("RedisUtil lUpdateIndex() Exception, key is [{}]", key, e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Serializable value) {
        try {
            return redisTemplate.opsForList().remove(this.prefix(key), count, value);
        } catch (Exception e) {
            log.error("RedisUtil lRemove() Exception, key is [{}]", key, e);
            return 0;
        }
    }

    /**
     * 执行脚本
     *
     * @param script 脚本
     * @param keys   key列表
     * @param args   参数
     * @param <T>    范型
     * @return
     */
    public <T> T execute(RedisScript<T> script, List<String> keys, Object... args) {
        try {
            List<String> wrapKeys = new ArrayList<>();
            for (String key : keys) {
                wrapKeys.add(this.prefix(key));
            }

            return redisTemplate.execute(script, wrapKeys, args);

        } catch (Exception e) {
            log.error("RedisUtil bGet() Exception, script is [{}]，keys is [{}]，args is [{}]", script, keys, args, e);
        }

        return null;
    }

    /**
     * 生成redis key
     *
     * @param biz
     * @param separator
     * @param key
     * @return
     */
    public String generateRedisKey(String biz, String separator, Object key) {
        StringBuilder keyBuilder = new StringBuilder(biz);
        return keyBuilder.append(separator).append(key.toString()).toString();
    }
}
