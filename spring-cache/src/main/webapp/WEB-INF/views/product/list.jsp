<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <title></title>
</head>
<body>
    <div class="container">
        <h3>商品列表</h3>
        <div class="well-sm">
            <form class="form-inline">
                <input type="text" name="productName" class="form-control" placeholder="商品名称" value="${param.productName}">
                <input type="text" name="place" class="form-control" placeholder="产地" value="${param.place}">
                <input type="text" name="minPrice" class="form-control" placeholder="最低价格" value="${param.minPrice}">
                <input type="text" name="maxPrice" class="form-control" placeholder="最高价格" value="${param.maxPrice}">
                <select name="typeId" class="form-control">
                    <option value="">所属分类</option>
                    <c:forEach items="${typeList}" var="type">
                        <option value="${type.id}" ${param.typeId == type.id ? 'selected' : ''}>${type.typeName}</option>
                    </c:forEach>
                </select>
                <button class="btn btn-default">搜索</button>
            </form>
        </div>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <a href="/product/new" class="btn btn-primary pull-right">添加商品</a>
        <table class="table">
            <thead>
                <tr>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>市场价</th>
                    <th>产地</th>
                    <th>所属分类</th>
                    <th>#</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pageInfo.list}" var="product">
                    <tr>
                        <td><a href="/product/${product.id}">${product.productName}</a></td>
                        <td>${product.price}</td>
                        <td>${product.marketPrice}</td>
                        <td>${product.place}</td>
                        <td>${product.productType.typeName}</td>
                        <td>
                            <a href="/product/${product.id}/edit">编辑</a>
                            <a href="javascript:;" class="delLink" rel="${product.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        共${pageInfo.total}条数据
        <ul id="pagination-demo" class="pagination pull-right"></ul>
    </div>
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/jquery.twbsPagination.js"></script>
    <script>
        $(function () {
            //console.log(encodeURIComponent('${param.productName}'));
            //分页
            $('#pagination-demo').twbsPagination({
                totalPages: ${pageInfo.pages},
                visiblePages: 10,
                first:'首页',
                last:'末页',
                prev:'←',
                next:'→',
                href:"?productName="+encodeURIComponent('${param.productName}')
                     +"&place="+encodeURIComponent('${param.place}')+"&minPrice=${param.minPrice}&maxPrice=${param.maxPrice}&typeId=${param.typeId}&p={{number}}"
            });

            //删除
            $(".delLink").click(function () {
                var id = $(this).attr("rel");
                if(confirm("确定要删除吗")) {
                    window.location.href = "/product/"+id+"/del";
                }
            });
        });
    </script>

</body>
</html>