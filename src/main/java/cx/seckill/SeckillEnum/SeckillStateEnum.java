package cx.seckill.SeckillEnum;

public enum SeckillStateEnum {
    SUCCESS(1,"恭喜您,秒杀成功"),
    END(-1,"秒杀结束"),
    REPEAT_KILL(-2,"请勿重复秒杀"),
    INNER_ERROR(-3,"系统异常"),
    DATE_REWRITE(-4,"数据被篡改");
    private int state;
    private String stateinfo;

    SeckillStateEnum(int state, String stateinfo) {
        this.state = state;
        this.stateinfo = stateinfo;
    }


    public int getState() {
        return state;
    }

    public String getStateinfo() {
        return stateinfo;
    }

    public static SeckillStateEnum stateOf(int index){
        for(SeckillStateEnum state:values()){
            if(state.getState()==index)
            return state;
        }
        return null;
    }

}
