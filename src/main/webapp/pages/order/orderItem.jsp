<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>

    <%-- 使用jsp指令，引入base标签和其他资源 --%>
    <%@ include file="/pages/common/head.jsp"%>
</head>
<body>

<div id="header">
    <span class="wel_word">订单详情</span>

    <%-- 用户访问 --%>
    <c:if test="${not empty param.flag}">
        <%-- 给注销按键绑定一个单击事件，提示是否要退出登录 --%>
        <script type="text/javascript">
            // 页面加载完成后
            $(function () {
                // 绑定单击事件
                $("a.logoutClass").click(function () {
                    // 弹出对话框，提示是否要退出登录
                    return confirm("您确定要退出登录吗？");
                })
            })
        </script>
        <div>
            <span>欢迎<span class="um_span">${sessionScope.loginUser.username}</span>光临辉记书城</span>
            <a class="logoutClass" href="userServlet?action=logout">退出登录</a>&nbsp;&nbsp;
            <a href="orderServlet?action=checkMyOrders">返回我的订单</a>
        </div>
    </c:if>

    <%-- 管理员访问 --%>
    <c:if test="${empty param.flag}">
        <div>
            <a href="orderServlet?action=checkAllOrders">返回订单管理</a>
        </div>
    </c:if>
</div>

<div id="main">
    <%-- 订单项列表 --%>
    <table>
        <tr>
            <td>商品名称</td>
            <td>数量</td>
            <td>单价</td>
            <td>金额</td>
        </tr>

        <%-- 遍历订单项集合 --%>
        <c:forEach items="${requestScope.orderItems}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>${item.count}</td>
                <td>${item.price}</td>
                <td>${item.totalPrice}</td>
            </tr>
        </c:forEach>
    </table>

    <%-- 订单确认收货信息，这里需判断是否是用户访问，是才显示 --%>
    <c:if test="${not empty param.flag}">
        <div class="cart_info">
            <%-- 根据订单状态显示对应信息 --%>
            <c:choose>
                <c:when test="${requestScope.status == 0}">
                    <span class="cart_span">订单未发货</span>
                </c:when>

                <c:when test="${requestScope.status == 1}">
                     <span class="cart_span">订单已发货，
                         <a style="font-size: 17px" href="orderServlet?action=signForOrder&orderId=${requestScope.orderId}">确认收货</a>
                     </span>
                </c:when>

                <c:otherwise>
                    <span class="cart_span">订单已签收</span>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>

</div>

    <%-- 使用jsp指令，引入页脚 --%>
    <%@ include file="/pages/common/footer.jsp" %>
</body>
</html>