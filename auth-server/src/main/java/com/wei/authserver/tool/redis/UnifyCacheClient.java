package com.wei.authserver.tool.redis;


import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class UnifyCacheClient {

    @Value("${spring.redis.host}")
    private static String host;

    @Value("${spring.redis.port}")
    private static int port;

    @Value("${spring.redis.timeout}")
    private static int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private static int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private  static long maxWaitMillis;

    @Value("${spring.redis.password}")
    private static String password;

    static JedisPool pool =null;
    /**
     * 构建redis连接池
     * @return JedisPool
     */
    public static synchronized JedisPool getPool() {


        if (pool == null) {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWait(maxWaitMillis);
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        }
        return pool;
    }

    /**
     * 返还到连接池
     * @param pool
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }

    /**
     * 根据key获取数据
     * @param key
     * @return
     */
    public static String get(String key){
        String value = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        }catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }

    /**
     * 根据key获取数据
     * @param key
     * @return
     */
    public static String get(String dir,String key){
        String value = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(genCacheKey(dir,key));
        }catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        }finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }

    /**
     * 向redis存入key和value,并释放连接资源
     * 如果key已经存在 则覆盖
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public static String put(String key, String value) {
        Jedis jedis = null;
        try {
            jedis =getPool().getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return "0";
        } finally {
            returnResource(pool, jedis);
        }
    }


    /**
     * 设置key value并制定这个键值
     * @param key
     * @param value
     * @return 成功返回OK 失败和异常返回null
     */
    public static Long setnx(String key, String value){
        Jedis jedis = null;
        Long result = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.setnx(key,value);
        } catch (Exception e){
            e.printStackTrace();
        } finally {



            return result;
        }
    }


    /**
     * 设置key value并制定这个键值的有效期
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return 成功返回OK 失败和异常返回null
     */
    public  static String put(String key,String value,int seconds){
        Jedis jedis = null;
        String res = null;
        JedisPool pool = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            res = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }


    /**
     * 设置key value并制定这个键值的有效期,带目录
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return 成功返回OK 失败和异常返回null
     */
    public  String put(String dir,String key,String value,int seconds){
        Jedis jedis = null;
        String res = null;
        JedisPool pool = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            res = jedis.setex(genCacheKey(dir,key), seconds, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.del(key);
        } catch (Exception e){
            e.printStackTrace();
        } finally {

            return result;
        }
    }

    public static String getSet(String key, String value){
        Jedis jedis = null;
        String result = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.getSet(key, value);
        } catch (Exception e){
            e.printStackTrace();
        } finally {

            return result;
        }
    }

    public  static String genCacheKey(String dir, String key){
        String _dir = dir;
        if(_dir == null){
            _dir ="";
        }else if(!_dir.endsWith("/")){
            _dir += "/";
        }

        return _dir + key;
    }



    /**
     * 删除指定的key,也可以传入一个包含key的数组
     * @param keys 一个key 也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public static Long remove(String... keys) {
        Jedis jedis = null;
        JedisPool pool = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return 0L;
        } finally {
            returnResource(pool, jedis);
        }
    }

    public  static String pput(String dir, Map<String, String> data, int seconds) {
        Jedis jedis = null;
        String res = null;
        JedisPool pool = null;
        if (data == null || data.isEmpty()){
            return null;
        }
        try {
            pool = getPool();
            jedis = pool.getResource();
            Pipeline pipe = jedis.pipelined();
            for (Iterator<String> iter = data.keySet().iterator(); iter.hasNext();) {
                String key = iter.next();
                pipe.psetex(genCacheKey(dir, key), seconds, data.get(key));
            }
            pipe.sync();
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    public static List<String> mget(String[] keys) {
        List<String> value = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.mget(keys);
        } catch (Exception e) {
            // 释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }


}