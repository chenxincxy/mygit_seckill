package cx.seckill.service.impl;

import cx.seckill.SeckillEnum.SeckillStateEnum;
import cx.seckill.dao.SeckillDao;
import cx.seckill.dao.cache.RedisDao;
import cx.seckill.dao.successkilledDao;
import cx.seckill.dto.Exposer;
import cx.seckill.dto.SeckillExecution;
import cx.seckill.entity.seckill;
import cx.seckill.entity.successkilled;
import cx.seckill.exception.RepeatKillException;
import cx.seckill.exception.SeckillCloseException;
import cx.seckill.exception.SeckillException;
import cx.seckill.service.SeckillService;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.beans.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceimpl implements SeckillService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckilldao;
    @Autowired
    private successkilledDao  successkilleddao;
    @Autowired
    private RedisDao redisDao;
    private String salt="saQ-=139ujqldsacnaKjJKDHd21o38`ex'D'23U`IO1WS";




    @Override
    public List<seckill> getSeckillList() {
        return seckilldao.queryAll(0,4);
    }

    @Override
    public seckill getById(long seckillId) {
        return seckilldao.queryById(seckillId);
    }


    //优化该接口,优化点:超时缓存的基础上来维护一致性
    //做缓存降低了Tomcat与数据库的访问量，
    @Override
    public Exposer ExporteSeckillUrl(long seckillId) {
        //缓存优化
        seckill seckill;
        //缓存中拿
        seckill=redisDao.getSeckill(seckillId);
        if(seckill==null){
            //缓存中没有，数据库里面拿
            seckill=seckilldao.queryById(seckillId);
            //数据库没有返回false
            if(seckill==null){
                return new Exposer(false,seckillId);
            }
            else {
                //数据库中有那么往缓存中放
                redisDao.putSeckill(seckill);
            }
        }
        Date now=new Date();
        Date startdate=seckill.getStarttime();
        Date enddate=seckill.getEndtime();
        if(now.getTime()<startdate.getTime()||now.getTime()>enddate.getTime())
            return new Exposer(false,seckillId,now.getTime(),startdate.getTime(),enddate.getTime());
        String md5=getMd5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMd5(long seckillId){
           String base=seckillId+"/"+salt;
           String md5= DigestUtils.md5DigestAsHex(base.getBytes());
           return md5;
    }



    @Override
    @Transactional
    /*
    使用注解控制事务方法的优点：
    1.开发团队达成一致的约定，明确标注事务方法的编程风格
    2.要保证事务方法的执行时间尽量短，不要穿插网络操作，如RPC/HTTP请求，如果有写在其它方法中
    3.不是所有方法都需要事务，如果只有一条修改操作，不需要并发控制，只读操作不需要事务控制
     */
    public SeckillExecution ExecuteSeckill(long seckillId, long userphone, String md5) {
        if(md5==null||!md5.equals(getMd5(seckillId)))
            throw new SeckillException("数据串改");
        Date killTime = new Date();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("seckillid",seckillId);
        map.put("userphone",userphone);
        map.put("killTime",killTime);
        map.put("result",null);
        //执行存储过程
        try {
            seckilldao.KillByprocedure(map);
            //获取result
            //使用它前先在pom.xml中加入依赖
            //      <groupId>commons-collections</groupId>
            //      <artifactId>commons-collections</artifactId>
            //      <version>3.2</version>
            int result=MapUtils.getInteger(map,"result",-3);
            //秒杀成功
            if(result==1){
               successkilled sk=successkilleddao.queryByIdWithseckill(seckillId,userphone);
               return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,sk);
            }
            else{
                return new SeckillExecution(seckillId,SeckillStateEnum.stateOf(result));
            }
        }
        //执行秒杀的时候:减库存+记录购买成功的行为
       catch(Exception e){
            logger.error(e.getMessage());
            return new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
       }
    }
}
/*
 try {
            int InsertCount = successkilleddao.insertImfo(seckillId, userphone);//通过主键唯一表示购买行为记录
            if (InsertCount <= 0) {
                throw new RepeatKillException("重复秒杀");
            } else {
                int UpdateCount = seckilldao.reduceNumber(seckillId, nowkillTime);
                if (UpdateCount <= 0) {
                    throw new SeckillCloseException("秒杀结束:库存为0或者不在秒杀时间范围内");
                } else {
                    //秒杀成功
                    successkilled successkilled = successkilleddao.queryByIdWithseckill(seckillId, userphone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successkilled);
                //commit
                }
            }
        }
        //捕捉到异常后，进行处理，事务回滚rollback，保证了减库存+记录购买行为的事务性。
        catch (RepeatKillException e1){
              throw e1;
        }
        catch (SeckillCloseException e2){
              throw e2;
        }
        //编译时期的异常转化为运行时异常
        catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new SeckillException("秒杀业务异常");
        }
 */