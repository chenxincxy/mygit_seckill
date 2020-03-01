package cx.seckill.service.impl;

import cx.seckill.dto.Exposer;
import cx.seckill.dto.SeckillExecution;
import cx.seckill.entity.seckill;
import cx.seckill.exception.RepeatKillException;
import cx.seckill.exception.SeckillCloseException;
import cx.seckill.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;


import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit Spring配置文件的位置
@ContextConfiguration({"classpath:spring/Spring-dao.xml",
                        "classpath:spring/Spring-service.xml"})
//测试service的时候还要依赖dao的配置，所以两个配置文件的位置都要告诉
public class SeckillServiceimplTest {
    private final Logger logger= (Logger) LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillservice;
    @Test
    public void getSeckillList() {
        List<seckill> seckills=seckillservice.getSeckillList();
        logger.info("{seckillsList}"+seckills);
    }

    @Test
    public void getById() {
        long id=1000;
        seckill seckill=seckillservice.getById(id);
        logger.info("{seckill}"+seckill);
    }

    @Test
    public void exporteSeckillUrl() {
        long id=1003;
        long phone=18845613108L;
        String base=id+"/"+"saQ-=139ujqldsacnaKjJKDHd21o38`ex'D'23U`IO1WS";
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        Exposer exposer=seckillservice.ExporteSeckillUrl(id);
        logger.info("{exposer}"+exposer);
        if(exposer.isExposed()){
            try{
                SeckillExecution se=seckillservice.ExecuteSeckill(id,phone,md5);
                logger.info("{Successkilled}"+se.getSuccesskilled());
            }
            catch(Exception e){
                logger.error(e.getMessage());
            }

        }
    }
    @Test
    public void ExecuteSeckillTest(){
        long id=1003;
        long phone=18845613108L;
        Exposer exposer=seckillservice.ExporteSeckillUrl(id);
        if(exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution seckillExecution=seckillservice.ExecuteSeckill(id,phone,md5);
            logger.info("seckill:"+seckillExecution);
        }
    }

}