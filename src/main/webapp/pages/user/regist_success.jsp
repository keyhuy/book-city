<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>辉记书城会员注册页面</title>

	<%-- 使用jsp指令，引入base标签和其他资源 --%>
	<%@ include file="/pages/common/head.jsp"%>

	<style type="text/css">
		h1 {
			text-align: center;
			margin-top: 200px;
		}

		h1 a {
			color:red;
		}
	</style>
</head>
<body>
		<div id="header">
				<img class="logo_img" alt="" src="static/img/logo.png" >
				<span class="wel_word"></span>

			<%-- 使用jsp包含指令，登录成功的菜单栏 --%>
			<%@ include file="/pages/common/loginMenu.jsp"%>
		</div>
		
		<div id="main">
		
			<h1>注册成功! <a href="../../index.jsp">转到主页</a></h1>
	
		</div>

		<%-- 使用jsp指令，引入页脚 --%>
		<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>