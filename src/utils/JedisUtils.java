package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {

	private static JedisPool pool = null;

	static {
		//1.创立连接池
		JedisPoolConfig config = new JedisPoolConfig();
		/*
		//配置信息可以通过配置文件来配置，不用写里面
		
		//和c3p0一样，都是个数
		config.setMaxIdle(30);//最大闲置个数
		config.setMinIdle(10);//最小闲置个数
		config.setMaxTotal(50);//最大总数
*/		
		InputStream in = JedisUtils.class.getClassLoader().getResourceAsStream("redis.properties");
		Properties pro = new Properties();
	
		try {
			pro.load(in);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		config.setMaxIdle(Integer.parseInt(pro.getProperty("redis.maxIdle").toString()));//最大闲置个数
		config.setMinIdle(Integer.parseInt(pro.getProperty("redis.minIdle").toString()));//最小闲置个数
		config.setMaxTotal(Integer.parseInt(pro.getProperty("redis.maxTotal").toString()));//最大总数
		
		 pool = new JedisPool(config,
				 			pro.getProperty("redis.url").toString(),
				 			Integer.parseInt(pro.getProperty("redis.port").toString()));//一般经常用 poolconfig host port
	}
	public static Jedis getJedis() {
		return pool.getResource();
	}
	
	
	//resourceboundle深度理解
}
