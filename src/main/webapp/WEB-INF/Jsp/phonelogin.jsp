<html>
<head>
    <title>HTML示例</title>
</head>
<body>
<form id="form1">
    <input type="text" name="phone"/>
    <br/>
    <input type="button" value="提交" onclick="form1();"/>
</form>

</body>
<script type="text/javascript">

    //实现提交的方法
    function form1(){
        //获取form
        var form1=document.getElementById("form1");
        //设置action
        form1.action="hello.html";
        //提交form表单
        form1.submit();
    }
</script>

</html>