<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>TMS | 年票下发</title>
    <%@include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../../include/navhead.jsp"%>

    <!-- =============================================== -->

    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="ticket_out"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                年票下发
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">下发列表</h3>
                    <div class="box-tools">
                        <a href="/ticket/out/new" class="btn btn-sm btn-success"><i class="fa fa-plus"> 新增下发</i></a>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>下发时间</th>
                                <th>下发网点</th>
                                <th>内容</th>
                                <th>起始票号</th>
                                <th>截至票号</th>
                                <th>数量</th>
                                <th>单价</th>
                                <th>总价</th>
                                <th>支付方式</th>
                                <th>下发人</th>
                                <th>状态</th>
                                <th>收款人</th>
                                <th></th>
                            </tr>
                        </thead>
                    </table>
                    
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<%@include file="../../include/js.jsp"%>
</body>
</html>
