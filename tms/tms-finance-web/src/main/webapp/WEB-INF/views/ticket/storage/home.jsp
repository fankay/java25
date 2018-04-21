<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>TMS | 年票入库</title>
    <%@include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../../include/navhead.jsp"%>

    <!-- =============================================== -->

    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="ticket_storage"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                年票入库
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">入库列表</h3>
                    <div class="box-tools">
                        <a href="/ticket/storage/new" class="btn btn-sm btn-success"><i class="fa fa-plus"></i> 新增入库</a>
                    </div>
                </div>
                <div class="box-body">
                    <c:if test="${not empty message}">
                        <div class="alert alert-info">${message}</div>
                    </c:if>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>入库时间</th>
                                <th>内容</th>
                                <th>起始票号</th>
                                <th>截至票号</th>
                                <th>数量</th>
                                <th>入库人</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty pageInfo.list}">
                            <tr>
                                <td colspan="7">暂无记录</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${pageInfo.list}" var="record">
                            <tr>
                                <td><fmt:formatDate value="${record.createTime}"/></td>
                                <td>${record.content}</td>
                                <td>${record.beginTicketNum}</td>
                                <td>${record.endTicketNum}</td>
                                <td>${record.totalNum}</td>
                                <td>${record.accountName}</td>
                                <td>
                                    <a href="javascript:;" class="btn btn-sm del_link" rel="${record.id}"><i class="fa fa-trash text-danger"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${pageInfo.pages > 1}">
                        <ul id="pagination-demo" class="pagination pull-right"></ul>
                    </c:if>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<%@include file="../../include/js.jsp"%>
<script src="/static/plugins/page/jquery.twbsPagination.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {
        $('#pagination-demo').twbsPagination({
            totalPages: ${pageInfo.pages},
            visiblePages: 10,
            first:'首页',
            last:'末页',
            prev:'←',
            next:'→',
            href:"?p={{number}}"
        });

        $(".del_link").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除该入库记录?",function (index) {
                layer.close(index);
                window.location.href = "/ticket/storage/"+id+"/del";
            })
        });

    });
</script>
</body>
</html>
