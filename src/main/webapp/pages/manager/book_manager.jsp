<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书管理</title>
	<%-- 使用jsp指令，引入base标签和其他资源 --%>
	<%@ include file="/pages/common/head.jsp"%>

</head>
<body>
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.png" >
			<span class="wel_word">图书管理系统</span>
		<%-- 使用jsp指令，引入管理员页面的菜单栏 --%>
		<%@ include file="/pages/common/managerMenu.jsp" %>
	</div>

	<%-- 列表 --%>
	<div id="main">
		<%-- 引入图书列表 --%>
		<%@ include file="/pages/bookInfo/listForManager.jsp"%>

		<%-- 引入分页条 --%>
		<%@ include file="/pages/common/pageNav.jsp"%>
	</div>

	<%-- 使用jsp指令，引入页脚 --%>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>