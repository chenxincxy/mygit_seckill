package cx.seckill.entity;

import java.util.Date;

public class successkilled {
    private long seckillid;
    private long userphone;
    private short state;
    private Date createtime;

    //多对一，一个秒杀库存对应多个商品被秒杀成功
    private seckill seckill;
    public long getSeckillid() {
        return seckillid;
    }

    public cx.seckill.entity.seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(cx.seckill.entity.seckill seckill) {
        this.seckill = seckill;
    }

    public void setSeckillid(long seckillid) {
        this.seckillid = seckillid;
    }

    public long getUserphone() {
        return userphone;
    }

    public void setUserphone(long userphone) {
        this.userphone = userphone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "successkilled{" +
                "seckillid=" + seckillid +
                ", userphone=" + userphone +
                ", state=" + state +
                ", createtime=" + createtime +
                '}';
    }
}
