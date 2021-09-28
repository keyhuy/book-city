<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>书城首页</title>

	<%-- 使用jsp指令，引入base标签和其他资源 --%>
	<%@ include file="/pages/common/head.jsp"%>
	
	<%-- 给查询价格区间按键绑定单击事件 --%>
	<script type="text/javascript">
		// 页面加载完成后
		$(function () {
			// 价格输入框绑定单击事件
			$("#searchPriceBtn").click(function () {
				// 获取文本框内容
				let minText = $("#min").val();
				let maxText = $("#max").val();

				// 如果有任意一个文本框为空就弹出警示框，并阻止提交
				if (maxText === "" || minText === "") {
					alert("输入价格不合法！");
					return false;
				}

				// 设置数字正则表达式
				let numPat = /[0-9]/;
				// 如果价格输入框都不为空，就判断是否都是数字
				if (! numPat.test(maxText) || ! numPat.test(minText)) {
					alert("输入价格不合法！");
					return false;
				}

				// 如果价格都为数字，就比较最小价格和最大价格，如果最小价格比最大价格还大，就阻止提交
				// 必须先把文本内容转成int型，才能比较
				if (parseInt(maxText) < parseInt(minText)) {
					alert("输入价格不合法！");
					return false;
				}

				// 如果存在负数的价格，也阻止提交
				if (minText < 0 || maxText < 0) {
					alert("输入价格不合法！");
					return false;
				}
			});

			// 给退出登录按键绑定单击事件
			$("a.logoutClass").click(function () {
				// 弹出对话框，提示是否要退出登录
				return confirm("您确定要退出登录吗？");
			});

			// 给加入购物车绑定单击事件，点击后将商品（图书）id值传给后台
			$("button.addItemClass").click(function () {
				// 获取自定义属性bookId的值（图书的id值）
				let bookId = $(this).attr("bookId");

				// 通过location对象，改变地址栏路径，跳转时将id传给后台
				location.href = "${pageScope.basePath}cartServlet?action=addItem&id=" + bookId;
			});
		})
	</script>
</head>
<body>

	<%-- 头部菜单栏 --%>
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.png" >
			<div>
				<%-- 如果用户未登录，就显示登录和注册的按键 --%>
				<c:if test="${empty sessionScope.loginUser.username}">
					<a href="pages/user/login.jsp">登录</a> |
					<a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp;
				</c:if>
				<%-- 如果用户已经登录，就不显示登陆注册的字样，并显示用户名 --%>
				<c:if test="${not empty sessionScope.loginUser.username}">
					<span>欢迎<span class="um_span">${sessionScope.loginUser.username}</span>光临辉记书城</span>
					<a href="orderServlet?action=checkMyOrders">我的订单</a>
					<a class="logoutClass" href="userServlet?action=logout">退出登录</a>&nbsp;&nbsp;
				</c:if>

				<a href="pages/cart/cart.jsp">购物车</a>
				<a href="pages/manager/manager.jsp">后台管理</a>
			</div>
	</div>

	<%-- 主要展示信息 --%>
	<div id="main">
		<div id="book">
			<%-- 加购图书、搜索图书价格 --%>
			<div class="book_cond">
				<form action="client/cBookServlet" method="get">
					<%-- 添加一个隐藏域，标识功能方法 --%>
					<input type="hidden" name="action" value="pageCliBooksByPrice">
					价格：<input id="min" type="text" name="min" value="${param.min}"> 元 -
						<input id="max" type="text" name="max" value="${param.max}"> 元
						<input id="searchPriceBtn" type="submit" value="查询" />
				</form>
			</div>
			<div style="text-align: center">

				<%-- 如果购物车不为空 --%>
				<c:if test="${not empty sessionScope.cart.items}">
					<span>您的购物车中有 ${sessionScope.cart.itemTotalCount} 件商品</span>
					<div>
						您刚刚将<span style="color: red">${sessionScope.nameOfLastAddedItem}</span>加入到了购物车中
					</div>
				</c:if>

				<%-- 如果购物车为空 --%>
				<c:if test="${empty sessionScope.cart.items}">
					<span></span>
					<div>
						<span style="color: deepskyblue">    当前购物车为空~</span>
					</div>
				</c:if>
			</div>

			<%-- 图书信息列表，用jstl标签循环遍历 --%>
			<c:forEach items="${requestScope.pageOfBook.infoPerPage}" var="book">
				<div class="b_list">
					<div class="img_div">
						<img class="book_img" alt="" src="${book.imgPath}" />
					</div>
					<div class="book_info">
						<div class="book_name">
							<span class="sp1">书名:</span>
							<span class="sp2">${book.title}</span>
						</div>
						<div class="book_author">
							<span class="sp1">作者:</span>
							<span class="sp2">${book.author}</span>
						</div>
						<div class="book_price">
							<span class="sp1">价格:</span>
							<span class="sp2">￥${book.price}</span>
						</div>
						<div class="book_sales">
							<span class="sp1">销量:</span>
							<span class="sp2">${book.sales}</span>
						</div>
						<div class="book_amount">
							<span class="sp1">库存:</span>
							<span class="sp2">${book.stock}</span>
						</div>
						<div class="book_add">
							<button bookId="${book.id}" class="addItemClass">加入购物车</button>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<%-- 引入分页条 --%>
		<%@ include file="/pages/common/pageNav.jsp"%>
	</div>
	
	<%-- 页脚 --%>
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>