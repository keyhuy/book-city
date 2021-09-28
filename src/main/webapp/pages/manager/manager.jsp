<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>后台管理</title>
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
			<span class="wel_word">后台管理系统</span>
		<%-- 使用jsp指令，引入管理员页面的菜单栏 --%>
		<%@ include file="/pages/common/managerMenu.jsp" %>
	</div>
	
	<div id="main">
		<h1>欢迎管理员进入后台管理系统</h1>
	</div>

	<%-- 使用jsp指令，引入页脚 --%>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>