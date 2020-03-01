package cx.seckill.dao;

import cx.seckill.entity.successkilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.awt.*;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件的位置
@ContextConfiguration({"classpath:spring/Spring-dao.xml"})
public class successkilledDaoTest {
    @Resource
    private successkilledDao successkilleddao;
    @Test
    /*
    插入结果影响行数：
    Insertcount=1
     */
    public void insertImfo() {
        long id=1000;
        long phone=12345678910L;
        int Insertcount=successkilleddao.insertImfo(id,phone);
        System.out.println("Insertcount="+Insertcount);
    }

    @Test
    /*
    successkilled{seckillid=1000, userphone=12345678910, state=0, createtime=Fri Feb 21 23:51:19 CST 2020}
    seckill{seckillid=1000, name='240元秒杀阿迪', number=100, starttime=Mon Nov 11 00:00:00 CST 2019, endtime=Tue Nov 12 00:00:00 CST 2019, createtime=Thu Feb 20 23:29:38 CST 2020}
     */
    public void queryByIdWithseckill() {
        Long id=1000L;
        Long phone=12345678910L;
        successkilled sk=new successkilled();
        sk=successkilleddao.queryByIdWithseckill(id,phone);
        System.out.println(sk);
        if(sk!=null)
        System.out.println(sk.getSeckill());
    }
}