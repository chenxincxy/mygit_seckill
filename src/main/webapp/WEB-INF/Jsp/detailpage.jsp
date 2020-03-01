<%@page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="commen/head.jsp"%>

</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h1>${seckill.name}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <!--显示time图标-->
                <div class="container">
                <span class="glyphicon glyphicon-time"></span>
                <!--展示倒计时-->

                <span class="glyphicon" id="scekillbox"></span>
                </div>
            </h2>
        </div>
    </div>
</div>



<!--登陆弹出层，输入电话-->

<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                        placeholder="填手机号" class="form-control"/>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <!--验证信息-->
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>

        </div>
    </div>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<!--使用CDN 获取公共js  http://www/bootcdn.com/-->
<!--jQuery cookie操作插件-->
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>


<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>


<!--开始编写交互逻辑-->


<script src="/Seckill_war/resources/script/seckill.js" type="text/javascript"></script>
   <script type="text/javascript">

       $(function(){
        //使用EL表达式传递参数
        seckill.detail.innit({
            seckillid: ${seckill.seckillid},

            starttime:${seckill.starttime.time},

            endtime:${seckill.endtime.time}
        });

    });
</script>

</body>
</html>