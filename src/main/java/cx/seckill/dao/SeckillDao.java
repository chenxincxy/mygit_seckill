package cx.seckill.dao;

import cx.seckill.entity.seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SeckillDao {
    /*减库存
    返回int类型结果，表示更新影响行数
    * */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime")Date killTime);

    //查询:根据seckillid查询秒杀对象
    seckill queryById(long seckillId);
    /*
    根据偏移量查询秒杀商品列表
     */
    List<seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    //使用存储过程执行秒杀
    void KillByprocedure(Map<String,Object> paramMap);
}
