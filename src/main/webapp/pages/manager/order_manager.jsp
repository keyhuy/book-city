<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单管理</title>
	<%-- 使用jsp指令，引入base标签和其他资源 --%>
	<%@ include file="/pages/common/head.jsp"%>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.png" >
			<span class="wel_word">订单管理系统</span>
		<%-- 使用jsp指令，引入管理员页面的菜单栏 --%>
		<%@ include file="/pages/common/managerMenu.jsp" %>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>订单号</td>
				<td>日期</td>
				<td>金额</td>
				<td>订单所属用户id</td>
				<td>详情</td>
				<td>发货</td>
			</tr>

			<%-- 如果还没有用户下过订单 --%>
			<c:if test="${empty requestScope.allOrders}">
				<tr>
					<td colspan="6">亲，当前未有用户下过订单~</td>
				</tr>
			</c:if>

			<%-- 有用户下过订单 --%>
			<c:if test="${not empty requestScope.allOrders}">
				<%-- 遍历订单信息 --%>
				<c:forEach items="${requestScope.allOrders}" var="order">
					<tr>
						<td>${order.orderId}</td>
						<td>${order.orderTime}</td>
						<td>${order.orderPrice}</td>
						<td>${order.userId}</td>
						<td><a href="orderServlet?action=checkOrderItemDetails&orderId=${order.orderId}">查看详情</a></td>

						<%-- 根据订单状态值显示对应信息 --%>
						<c:choose>
							<c:when test="${order.orderStatus == 0}">
								<td><a style="font-size: 17px" href="orderServlet?action=sendOrder&orderId=${order.orderId}">发货</a></td>
							</c:when>

							<c:when test="${order.orderStatus == 1}">
								<td>待签收</td>
							</c:when>

							<c:otherwise>
								<td>已签收</td>
							</c:otherwise>
						</c:choose>

					</tr>
				</c:forEach>
			</c:if>

		</table>
	</div>

	<%-- 使用jsp指令，引入页脚 --%>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>