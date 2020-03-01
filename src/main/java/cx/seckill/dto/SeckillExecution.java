package cx.seckill.dto;

import cx.seckill.SeckillEnum.SeckillStateEnum;
import cx.seckill.entity.successkilled;

public class SeckillExecution {
    private long seckillId;
    //秒杀执行状态结果
    private int state;
    //状态的信息
    private String stateinfo;
    //成功后返回的秒杀成功明细（秒杀成功对象）
    private successkilled successkilled;

    //秒杀成功
    public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum, successkilled successkilled) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateinfo = seckillStateEnum.getStateinfo();
        this.successkilled = successkilled;
    }
    //秒杀失败
    public SeckillExecution(long seckillId,SeckillStateEnum seckillStateEnum) {
        this.seckillId = seckillId;
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateinfo =seckillStateEnum.getStateinfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateinfo() {
        return stateinfo;
    }

    public void setStateinfo(String stateinfo) {
        this.stateinfo = stateinfo;
    }

    public cx.seckill.entity.successkilled getSuccesskilled() {
        return successkilled;
    }

    public void setSuccesskilled(cx.seckill.entity.successkilled successkilled) {
        this.successkilled = successkilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", stateinfo='" + stateinfo + '\'' +
                '}';
    }
}
