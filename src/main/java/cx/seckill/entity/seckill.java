package cx.seckill.entity;

import java.util.Date;

public class seckill {
    private long seckillid;
    private String name;
    private int number;
    private Date starttime;
    private Date endtime;
    private Date createtime;

    public long getSeckillid() {
        return seckillid;
    }

    public void setSeckillid(long seckillid) {
        this.seckillid = seckillid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "seckill{" +
                "seckillid=" + seckillid +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", createtime=" + createtime +
                '}';
    }
}
