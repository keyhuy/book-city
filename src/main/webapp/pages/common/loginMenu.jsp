<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: Key
  Date: 2021/09/11/19:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <%-- 判断用户是否已登录再显示 --%>
    <c:if test="${not empty sessionScope.loginUser}">
        <span>欢迎<span class="um_span">${sessionScope.loginUser.username}</span>光临辉记书城</span>
        <a href="orderServlet?action=checkMyOrders">我的订单</a>
        <a class="logoutClass" href="userServlet?action=logout">退出登录</a>&nbsp;&nbsp;
    </c:if>
    <a href="index.jsp">返回首页</a>
</div>
