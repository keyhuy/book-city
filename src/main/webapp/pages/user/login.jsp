<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>辉记书城会员登录页面</title>
	<%-- 使用jsp指令，引入base标签和其他资源 --%>
	<%@ include file="/pages/common/head.jsp"%>

	<%-- 给提交按键绑定单击事件 --%>
	<script type="text/javascript">
		// 当页面加载完成后
		$(function () {
			// 给按键绑定单击事件
			$("#sub_btn").click(function () {
				// 获取文本框内容
				let uNameText = $("#uName").val();
				let pwdText = $("#pwd").val();

				// 判断文本内容是否为空
				if (uNameText === "" || pwdText === "") {
					// 提示信息
					$("span.errorMsg").text("用户名和密码不能为空！");
					return false;
				}

				// 最后把提示信息清空
				$("span.errorMsg").text("");
			})
		})
	</script>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.png" >
		</div>
		
		<div class="login_banner">

			<div id="l_content">
				<span class="login_word">欢迎登录</span>
			</div>

			<div id="content">
				<div class="login_form">
					<div class="login_box">
						<div class="tit">
							<h1>辉记书城会员</h1>
							<a href="pages/user/regist.jsp">立即注册</a>
						</div>
						<div class="msg_cont">
							<b></b>
							<%-- 获取request域中内容并显示 --%>
							<span class="errorMsg">
								${empty requestScope.msg ? "请输入用户名和密码" : requestScope.msg}
							</span>
						</div>
						<div class="form">
							<!-- 表单提交到资源路径 loginServlet -->
							<form action="userServlet" method="post">
								<%-- 添加隐藏域 --%>
								<input type="hidden" name="action" value="login">

								<label>用户名称：</label>
								<input class="itxt" id="uName" type="text" placeholder="请输入用户名"
									   autocomplete="off" tabindex="1" name="username"
									   value="${requestScope.username}" />
								<br />
								<br />
								<label>用户密码：</label>
								<input class="itxt" id="pwd" type="password" placeholder="请输入密码"
									   autocomplete="off" tabindex="1" name="password" />
								<br />
								<br />
								<input type="submit" value="登录" id="sub_btn" />
							</form>
						</div>

					</div>
				</div>
			</div>
		</div>

	<%-- 使用jsp指令，引入页脚 --%>
	<%@ include file="/pages/common/footer.jsp" %>
</body>
</html>