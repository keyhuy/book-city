<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>编辑图书</title>

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

		input {
			text-align: center;
		}
	</style>
</head>
<body>
	<%-- 头信息 --%>
	<div id="header">
		<img class="logo_img" alt="" src="../../static/img/logo.png" >
		<span class="wel_word">编辑图书</span>

		<%-- 使用jsp指令，引入管理员页面的菜单栏 --%>
		<%@ include file="/pages/common/managerMenu.jsp" %>
	</div>

	<%-- 引入图书信息表单 --%>
	<%@ include file="/pages/bookInfo/edit.jsp"%>

	<%-- 使用jsp指令，引入页脚 --%>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>