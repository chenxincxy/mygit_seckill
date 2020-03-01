package cx.seckill.dao;

import cx.seckill.entity.successkilled;
import org.apache.ibatis.annotations.Param;

public interface successkilledDao {
    /*插入购买明细
    返回int类型，表示插入操作影响行数
    */
    int insertImfo(@Param("seckillId") long seckillId, @Param("userphone") long userphone);

    //根据id查询successkilled,并携带seckill对象实体

    successkilled queryByIdWithseckill(@Param("seckillId") long seckillId, @Param("userphone") long userphone);
}
