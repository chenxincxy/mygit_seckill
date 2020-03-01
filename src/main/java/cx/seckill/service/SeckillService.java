package cx.seckill.service;

import cx.seckill.dto.Exposer;
import cx.seckill.dto.SeckillExecution;
import cx.seckill.entity.seckill;
import cx.seckill.exception.RepeatKillException;
import cx.seckill.exception.SeckillCloseException;
import cx.seckill.exception.SeckillException;

import java.util.List;

public interface SeckillService {
    /*
    查询到整个秒杀商品的列表信息
     */
    List<seckill> getSeckillList();

    /*
    查询单个秒杀商品信息
     */
    seckill getById(long seckillId);

    /*
    秒杀接口暴露
    秒杀开启时，输出秒杀接口的地址
    否则，输出秒杀开始和结束的时间
     */
    Exposer ExporteSeckillUrl(long seckillId);

    /*
    执行秒杀（还要抛出秒杀异常）
     */
    SeckillExecution ExecuteSeckill(long seckillId, long userphone, String md5);
}
