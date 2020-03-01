package cx.seckill.web;


import com.sun.org.apache.xpath.internal.operations.Mod;
import cx.seckill.SeckillEnum.SeckillStateEnum;
import cx.seckill.dto.Exposer;
import cx.seckill.dto.SeckillExecution;
import cx.seckill.dto.SeckillResult;
import cx.seckill.entity.seckill;
import cx.seckill.exception.RepeatKillException;
import cx.seckill.exception.SeckillCloseException;
import cx.seckill.service.SeckillService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    //列表页
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String getlist(Model model){
        List<seckill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
        //"/WEB-INF/Jsp/list.jsp"
    }
    //详情页
    @RequestMapping(value="/{seckillid}/detailpage",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillid") Long seckillid, Model model){
        if(seckillid==null)
            return "redirect:/seckill/list";
        seckill seckill=seckillService.getById(seckillid);
        if(seckill==null){
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detailpage";
    }
    //ajax,json
    //实现Result接口，返回结果数据封装成类
    @RequestMapping(value="/{seckillid}/exposer",method =RequestMethod.POST,
    produces ={"application/json;charset=UTF-8"})
    @ResponseBody//当SpringMvc看到这个注解，会把返回结果转成json
    public SeckillResult<Exposer> expsor(@PathVariable("seckillid") Long seckillid){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.ExporteSeckillUrl(seckillid);
            result = new SeckillResult<Exposer>(true, exposer);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            result=new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }
    //执行秒杀
    @RequestMapping(value="/{seckillid}/{md5}/execution",method =RequestMethod.POST,
            produces ={"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillid") Long seckillid,
                                          @PathVariable("md5") String md5,
                                          @CookieValue(value="killPhone",required=false) Long killPhone){
                        //required=false,证明这个参数可为空，否则当前端没有传入phone的时候程序会报错
        if(killPhone==null){
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        SeckillExecution ske;
        try {
            ske = seckillService.ExecuteSeckill(seckillid, killPhone, md5);
            result = new SeckillResult<SeckillExecution>(true, ske);
        }
        catch (SeckillCloseException e1){
            logger.error(e1.getMessage(),e1);
            ske=new SeckillExecution(seckillid,SeckillStateEnum.END);
            return  new SeckillResult<SeckillExecution>(false,ske);
        }
        catch (RepeatKillException e2){
            logger.error(e2.getMessage(),e2);
            ske=new SeckillExecution(seckillid,SeckillStateEnum.REPEAT_KILL);
            return  new SeckillResult<SeckillExecution>(false, ske);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            ske=new SeckillExecution(seckillid,SeckillStateEnum.INNER_ERROR);
            return  new SeckillResult<SeckillExecution>(false, ske);
        }
        return result;
    }
    //获取系统当前时间

    @RequestMapping(value="/time/now",method=RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now=new Date();
        return new SeckillResult<Long> (true,now.getTime());
    }


}
