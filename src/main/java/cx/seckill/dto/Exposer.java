package cx.seckill.dto;

import java.util.Date;

public class Exposer {
    //秒杀是否开启
    private boolean exposed;
    //机密措施
    private String md5;
    //秒杀对应库存商品号
    private long seckillId;
    //系统当前时间
    private long now;
    //秒杀开始时间
    private long starttime;
    //秒杀结束时间
    private long endtime;
    //秒杀开始
    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }
    //秒杀未开始
    public Exposer(boolean exposed,long seckillId,long now,long starttime,long endtime) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.starttime = starttime;
        this.endtime = endtime;
    }
    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                '}';
    }
}
