<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>
	<%-- 使用jsp指令，引入base标签和其他资源 --%>
	<%@ include file="/pages/common/head.jsp"%>
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
</style>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.png" >
			<span class="wel_word">我的订单</span>
		<%-- 使用jsp包含指令，登录成功的菜单栏 --%>
		<%@ include file="/pages/common/loginMenu.jsp"%>
	</div>

	<%-- 显示订单信息 --%>
	<div id="main">
		<table>
			<tr>
				<td>订单号</td>
				<td>日期</td>
				<td>金额</td>
				<td>状态</td>
				<td>详情</td>
			</tr>

			<%-- 如果没有订单信息 --%>
			<c:if test="${empty requestScope.myOrders}">
				<tr>
					<td colspan="5">亲，当前无订单信息，<a style="font-size: 16px" href="pages/cart/cart.jsp">快去购物车结账吧</a></td>
				</tr>
			</c:if>

			<%-- 有订单信息就显示 --%>
			<c:if test="${not empty requestScope.myOrders}">
				<%-- 遍历订单信息 --%>
				<c:forEach items="${requestScope.myOrders}" var="myOrder">
					<tr>
						<td>${myOrder.orderId}</td>
						<td>${myOrder.orderTime}</td>
						<td>${myOrder.orderPrice}</td>

							<%-- 根据订单状态值显示对应的状态 --%>
						<c:choose>
							<c:when test="${myOrder.orderStatus == 0}">
								<td>未发货</td>
							</c:when>

							<c:when test="${myOrder.orderStatus == 1}">
								<td>已发货</td>
							</c:when>

							<c:otherwise>
								<td>已签收</td>
							</c:otherwise>
						</c:choose>

						<td><a href="orderServlet?action=checkOrderItemDetails&orderId=${myOrder.orderId}&flag=1">查看详情</a></td>
					</tr>
				</c:forEach>
			</c:if>

		</table>
	</div>

	<%-- 使用jsp指令，引入页脚 --%>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>