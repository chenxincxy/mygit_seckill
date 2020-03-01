package cx.seckill.dao;

import cx.seckill.entity.seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

//创建测试类的快捷键 ctrl+shift+t
/*
    配置Spring和junit整合，目的:junit启动时加载初始化SpringIOC容器
    Spring-test,junit
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件的位置
@ContextConfiguration({"classpath:spring/Spring-dao.xml"})
public class SeckillDaoTest {
    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckilldao;
    @Test
    /*
    update seckill set number=number-1 where seckill_id=? and start_time <= ? and end_time>=? and number>0;
    更新结果：
    Updatecount=0
     */
    public void reduceNumber() {
        Date date=new Date();
        int Updatecount=seckilldao.reduceNumber(1000,date);
        System.out.println("Updatecount="+Updatecount);

    }

    @Test
    /*
    查询结果
    240元秒杀阿迪
    seckill{seckillid=1000, name='240元秒杀阿迪', number=100, starttime=Mon Nov 11 00:00:00 CST 2019, endtime=Tue Nov 12 00:00:00 CST 2019, createtime=Thu Feb 20 23:29:38 CST 2020}
     */
    public void queryById() {
        long id=1000;
        seckill seckill=seckilldao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    /*查询结果
    seckill{seckillid=1000, name='240元秒杀阿迪', number=100, starttime=Mon Nov 11 00:00:00 CST 2019, endtime=Tue Nov 12 00:00:00 CST 2019, createtime=Thu Feb 20 23:29:38 CST 2020}
    seckill{seckillid=1001, name='300元秒杀vans', number=200, starttime=Mon Nov 11 00:00:00 CST 2019, endtime=Tue Nov 12 00:00:00 CST 2019, createtime=Thu Feb 20 23:29:38 CST 2020}
    seckill{seckillid=1002, name='500元秒杀ipad3', number=300, starttime=Mon Nov 11 00:00:00 CST 2019, endtime=Tue Nov 12 00:00:00 CST 2019, createtime=Thu Feb 20 23:29:38 CST 2020}
    seckill{seckillid=1003, name='150元秒杀olay小白瓶', number=400, starttime=Mon Nov 11 00:00:00 CST 2019, endtime=Tue Nov 12 00:00:00 CST 2019, createtime=Thu Feb 20 23:29:38 CST 2020}
     */
    public void queryAll() {
        List<seckill> seckills=seckilldao.queryAll(0,100);
        for(seckill seckill:seckills){
            System.out.println(seckill);
        }
    }
}