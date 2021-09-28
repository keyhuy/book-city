<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>

	<%-- 使用jsp指令，引入base标签和其他资源 --%>
	<%@ include file="/pages/common/head.jsp"%>

	<%-- 绑定单击事件 --%>
	<script type="text/javascript">
		// 页面加载完成后
		$(function () {
			// 给删除购物车商品项按键绑定单击事件
			$("a.delItemClass").click(function () {
				// 弹出对话框，提示用户是否确认删除
				return confirm("您确认要将" + $(this).parent().parent().find("td:first").text() + "移出购物车吗？");
			});

			// 给清空购物车绑定单击事件
			$("#clearId").click(function () {
				// 弹出对话框，询问用户是否确认清空
				return confirm("您确认要清空购物车所有商品吗？");
			});

			// 给商品项数量文本框绑定监听内容修改事件
			$(".updItemCountClass").change(function () {

				// 获取更新后的商品数量
				let updatedCount = this.value;

				// 先判断用户输入的商品数量值是否合法
				// 定义一个数字正则表达式
				let numPat = /[0-9]/;

				// 先判断输入值是否是数字
				if (! numPat.test(updatedCount)) {
					// 输入值不是数字，弹出警告框，并阻止提交
					alert("请输入合法的商品数量值！");
					// 回显原来的值
					this.value = this.defaultValue;
					return false;
				}

				// 输入值是数字，就判断是否是正数
				if (updatedCount <= 0) {
					// 输入值不是正数，弹出警告框，并阻止提交
					alert("请输入合法的商品数量值！");
					// 回显原来的值
					this.value = this.defaultValue;
					return false;
				}

				/* 输入值合法后，就弹出对话框 */

				// 获取商品项名称
				let itemName = $(this).parent().parent().find("td:first").text();

				// 获取对应的商品id值
				let bookId = $(this).attr("bookId");

				// 弹出对话框，判断用户是否确认修改
				if (! confirm("您确认要将" + itemName + "商品的数量修改成：" + updatedCount + "吗？")) {
					// 用户点击取消，则显示修改前的数据
					this.value = this.defaultValue;
				} else {
					// 用户点击确认，跳转到后台，进行修改的具体逻辑
					location.href = "${pageScope.basePath}cartServlet?action=updateItemCount&id=" +
							bookId + "&updatedCount=" + updatedCount;
				}
			});
		})
	</script>
</head>
<body>
	
	<div id="header">
			<span class="wel_word">购物车</span>

		<%-- 使用jsp包含指令，登录成功的菜单栏 --%>
		<%@ include file="/pages/common/loginMenu.jsp"%>
	</div>
	
	<div id="main">
		<%-- 购物车商品项列表 --%>
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>		

			<%-- 如果购物车中还没有添加过商品，就显示提示信息，并引导用户去首页 --%>
			<c:if test="${empty sessionScope.cart.items}">
				<tr>
					<td colspan="5">亲，当前购物车为空，<a style="font-size: 16px" href="index.jsp">快去首页浏览商品吧</a></td>
				</tr>
			</c:if>

			<%-- 如果当前购物车中已有添加过商品，就显示商品列表 --%>
			<c:if test="${not empty sessionScope.cart.items}">
				<%-- 使用jstl标签遍历session域中的购物车对象中的商品项集合 --%>
				<c:forEach items="${sessionScope.cart.items}" var="entry">
					<tr>
						<td>${entry.value.name}</td>
						<td>
							<input type="text"
									style="width: 50px"
									class="updItemCountClass"
									bookId="${entry.value.id}"
									value="${entry.value.count}">
						</td>
						<td>${entry.value.price}</td>
						<td>${entry.value.totalPrice}</td>
						<td><a class="delItemClass" href="cartServlet?action=deleteItem&id=${entry.value.id}">删除</a></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>

		<%-- 如果当前购物车没有商品，就不显示具体信息 --%>
		<c:if test="${not empty sessionScope.cart.items}">
			<div class="cart_info">
				<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.itemTotalCount}</span>件商品</span>
				<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.itemTotalPrice}</span>元</span>
				<span class="cart_span"><a id="clearId" href="cartServlet?action=clearCart">清空购物车</a></span>
				<span class="cart_span"><a href="orderServlet?action=createOrder">去结账</a></span>
			</div>
		</c:if>
	</div>

	<%-- 使用jsp指令，引入页脚 --%>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>