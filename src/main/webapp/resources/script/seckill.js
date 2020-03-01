//存放主要的交互逻辑js代码
//javascript 模块化

//创建seckill对象
var seckill={
    //封装秒杀相关ajax的url
    URL:{
        now: function () {
            var seckillBox = $('#scekillbox');
            seckillBox.html('秒杀结束');
            return '/Seckill_war/seckill/time/now';
        },
        exposer :function(seckillid) {
            return '/Seckill_war/seckill/'+seckillid+'/exposer';
        },

         execution:function (seckillid,md5) {

            return  '/Seckill_war/seckill/'+seckillid+'/'+md5+'/execution';

        }

    },
    //验证手机号
    validdatePhone: function(phone){
        if(phone&&phone.length==11&&!isNaN(phone))
            return true;
        else
            return false;
    },
    handleSeckillkill: function(seckillid,seckillBox){
    //获取秒杀地址，控制显示逻辑，执行秒杀
        console.log('进入function');
        var node=$('#scekillbox');
        node.html('fuck').show();
        seckillBox.html('<button class="btn btn-info bt-sm" id="msaniu">开始秒杀</button>').show();
        //url都放在URL属性中
        $.post(seckill.URL.exposer(seckillid),{},function (result) {
            console.log('进入post');
            //在回掉函数中，执行交互流程
            if(result&&result['success']){
                var exposer=result['data'];
                if(exposer['exposed']){
                    //开启秒杀的话，控制node展示出来不要隐藏了
                    //1.获取秒杀地址
                    var cc5=exposer['md5'];
                    var killUrl=seckill.URL.execution(seckillid,cc5);
                    console.log('开启秒杀:'+killUrl);
                    //2.绑定秒杀操作one只绑定点击事件一次，click一直绑定
                    $('#msaniu').one('click',function() {
                        //绑定执行秒杀请求的操作
                        //1.先禁用按钮
                        console.log('进入按钮绑定事件');
                        //$(this).addClass('disabled');
                        //2.发送秒杀请求
                        $.post(killUrl,{},function (result) {
                            console.log('进入接口:'+killUrl);
                            if(result&&result['success']) {
                                var SeckillExecution = result['data'];
                                var sate=SeckillExecution['state'];
                                var stateinfo=SeckillExecution['stateinfo'];
                                //node结点其实就是countdown结点，用来展示秒杀结果
                                console.log('你好');
                                node.html('<span class="label label-success">'+stateinfo+'</span>')
                            }
                        });
                    });
                    //3.回调完后把node显示出来
                    node.show();
                    console.log('执行结束');
                }
                else{
                    //没有开启秒杀
                    console.log('没有执行秒杀');
                    var now=exposer['now'];
                    var start=exposer['starttime'];
                    var end=exposer['endtime'];
                    //重新计时，再重复一边countdown逻辑
                    seckill.countdown(seckillid,now,start,end);
                }

            }
            else{
                console.log('result:'+result);
            }
            
        });
    },
    //把时间判断封装成一个函数
    countdown: function(seckillid,nowtime,starttime,endtime){
        //拿到jsp中时间倒计时的那个结点
        var seckillBox=$('#scekillbox');
        //时间判断
        if(nowtime>endtime){
            //秒杀结束
            //seckillBox.html('秒杀结束');
            console.log('秒杀结束');
        }
        else if(nowtime<starttime){
            //秒杀未开始,倒计时事件绑定
            var killTime=new Date(starttime+1000);

            seckillBox.countdown(killTime,function (event) {
                var format=event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function () {
                //获取秒杀地址，控制显示逻辑，执行秒杀
                seckill.handleSeckillkill(seckillid,seckillBox);
            });
            console.log('秒杀倒计时');
        }
        else{
            console.log('执行秒杀');
            seckillBox.html('执行秒杀');
            seckill.handleSeckillkill(seckillid,seckillBox);
        }

    },
    detail:{
        innit:function(params){
            //手机验证和登陆，计时交互
            //规划我们的交互流程
           //在cookie中查找手机号
            var killPhone=$.cookie('killPhone');
            var starttime=params['starttime'];
            var endtime=params['endtime'];
            var seckillid=params['seckillid'];
            if(!seckill.validdatePhone(killPhone)){
                //当唯一输入手机号的时候就直接弹出登陆页面
                var killPhoneModal=$('#killPhoneModal');
                killPhoneModal.modal({
                    show:true,//显示弹出
                    backdrop:'static',//禁止位置关闭
                    keyboard:false//关闭键盘事件
                });
                $('#killPhoneBtn').click(function(){
                    var inputphone=$('#killPhoneKey').val();
                    if(seckill.validdatePhone(inputphone)){
                        $.cookie('killPhone',inputphone,{expires:7});
                        //刷新页面
                        window.location.reload();
                    }
                    else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });
            }
            // 已经登陆
            //记时交互
            //result是后端返回的结果 转为了json
            $.get(seckill.URL.now(),{},function (result) {
                console.log('进入了时间函数');
                if(result&&result['success']){
                    var nowtime=result['data'];
                    //时间判断
                    //其中三个参数是由detailpage.jsp页面传过来的，nowTime是在后端获取
                    seckill.countdown(seckillid,nowtime,starttime,endtime);
                    console.log('运行了countdown');
                }
                else{
                    console.log('result:'+result);
                }
            });
        }
    }

}