package cx.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import cx.seckill.entity.seckill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



//写了getSeckill和PutSeckill，分别是从Cahche中拿Seckill对象
//和放Seckill对象于缓存中
//记得要在Spring-dap.xml中写RedisDao的配置
public class RedisDao {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;
    public RedisDao(String ip,int port){
        jedisPool=new JedisPool(ip,port);

    }
    private RuntimeSchema<seckill> schema=RuntimeSchema.createFrom(seckill.class);

    public seckill getSeckill(long seckillid){
        //因为Redis内部并没实现序列化操作
        //get->byte[]->Object(Seckill)[一个节数组反序列化为一个对象]
        //把一个对象转化成字节数组 然后传到Redis中
        //采用自定义序列化
        //protostuff:pojo
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillid;//给jedis命名
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //空对象
                    seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    //通过schema扫描反射seckill.class,然后把bytes对象字节码的属性给seckill对应的属性
                    //seckill被反序列
                    return seckill;
                }
            }
            finally{
                jedis.close();
            }
        }
        catch(Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }


    public String putSeckill(seckill seckill){
        //拿到一个Object(seckill)对象->转化成对应字节码数组（序列化的过程）
        try{
            Jedis jedis=jedisPool.getResource();
            try{
                String key="seckill:"+seckill.getSeckillid();
                byte[] bytes=ProtostuffIOUtil.toByteArray(seckill,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //通过schema反射把seckill的属性转化成字节数组，LinkedBuffer.allocate是缓存器
                //超时缓存
                int timeout=60*60;//缓存一个小时，超时后会被清除
                String result=jedis.setex(key.getBytes(),timeout,bytes);
                return result;//成功返回的是"ok"
            }
            finally{
                jedis.close();
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

}
