<%@page contentType="text/html; charset=UTF-8" language="java" %>
<!--引入jstl-->

<%@include file="commen/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表页</title>
    <%@include file="commen/head.jsp"%>

</head>
<body>
    <!--页面显示部分-->
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>秒杀列表</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>库存量</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>创建时间</th>
                            <th>详情页</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <c:forEach var="sk" items="${list}">
                                <tr>
                                    <td>${sk.name}</td>
                                    <td>${sk.number}</td>
                                    <td>
                                    <fmt:formatDate value="${sk.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                    <fmt:formatDate value="${sk.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                     <td>
                                    <fmt:formatDate value="${sk.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <a class="btn btn-info" href="/Seckill_war/seckill/${sk.seckillid}/detailpage" target="_blank">link
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tr>
                    </tbody>

                </table>
            </div>
        </div>
    </div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>