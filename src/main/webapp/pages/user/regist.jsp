<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员注册页面</title>
	<%-- 使用jsp指令，引入base标签和其他资源 --%>
	<%@ include file="/pages/common/head.jsp"%>

	<!-- js实现表单校验的功能 -->
	<script type="text/javascript">
		// 页面加载完成之后
		$(function () {

			// 给注册按钮绑定点击事件
			$("#sub_btn").click(function () {
				// 验证用户名：必须由字母、数字、下划线组成，长度5-12位
				// 1. 获取表单内容
				let nameText = $("#username").val();

				// 2. 创建正则表达式对象
				let namePat = /^\w{3,12}/;

				// 3. 用test() 方法验证
				if (! namePat.test(nameText)) {
					// 4. 如果信息不合法，提示信息
					$("span.errorMsg").text("用户名不合法！");

					// 必须要加上，才能阻止表单的提交
					return false;
				}

				// 验证密码：必须由字母、数字、下划线组成，长度5-12位
				// 1. 获取表单内容
				let pwdText = $("#password").val();

				// 2. 创建正则表达式对象
				let pwdPat = /^\w{5,12}/;

				// 3. 用test() 方法验证
				if (! pwdPat.test(pwdText)) {
					// 4. 如果信息不合法，提示信息
					$("span.errorMsg").text("密码格式不合法！");

					// 必须要加上，才能阻止表单的提交
					return false;
				}

				// 验证确认密码：确认密码必须与密码一样
				// 1. 获取确认密码的内容
				let rePwdText = $("#repwd").val();

				// 2. 与密码进行比较
				if (rePwdText !== pwdText) {
					// 提示错误信息
					$("span.errorMsg").text("确认密码与密码不一致！");

					return false;
				}

				// 验证邮箱：根据正则表达式验证即可
				// 1. 获取表单内容
				let emailText = $("#email").val();

				// 2. 创建正则表达式对象
				let emailPat = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;

				// 3. 与正则表达式比较
				if (! emailPat.test(emailText)) {
					// 4. 如果信息不合法，提示信息
					$("span.errorMsg").text("邮箱格式不合法！");

					// 必须要加上，才能阻止表单的提交
					return false;
				}

				// 验证验证码：暂时先根据空字符串和null来判断
				let codeText = $("#code").val();

				if (codeText == null || codeText === "") {
					// 4. 如果信息不合法，提示信息
					$("span.errorMsg").text("验证码不正确！");

					// 必须要加上，才能阻止表单的提交
					return false;
				}

				// 最后如果没有不合法信息，就把提示信息去掉
				$("span.errorMsg").text("");
			});

			// 给验证码图片绑定单击事件
			$("#vCodeId").click(function () {
				// 更新<img>标签中的src属性值，后面带上一个变化的参数值，用时间戳保证每次值都不一样
				this.src  ="verifiedCode.jpg?d=" + new Date();
			});
		})

	</script>

<style type="text/css">
	.login_form{
		height:420px;
		margin-top: 25px;
	}
	
</style>
</head>
<body>
	<div id="login_header">
		<img class="logo_img" alt="" src="static/img/logo.png" >
	</div>
		
	<div class="login_banner">

		<div id="l_content">
			<span class="login_word">欢迎注册</span>
		</div>

		<div id="content">
			<div class="login_form">
				<div class="login_box">
					<div class="tit">
						<h1>注册尚硅谷会员</h1>
						<%-- 显示错误信息 --%>
						<span class="errorMsg">
							${requestScope.msg}
						</span>
					</div>
					<div class="form">
						<!-- 表单提交的资源路径为registerServlet -->
						<form action="userServlet" method="post">
							<%-- 添加隐藏域 --%>
							<input type="hidden" name="action" value="register">

							<label>用户名称：</label>
							<input class="itxt" type="text" placeholder="请输入用户名"
								   autocomplete="off" tabindex="1" name="username" id="username"
								   value="${requestScope.username}" />
							<br />
							<br />
							<label>用户密码：</label>
							<input class="itxt" type="password" placeholder="请输入密码"
								   autocomplete="off" tabindex="1" name="password" id="password" />
							<br />
							<br />
							<label>确认密码：</label>
							<input class="itxt" type="password" placeholder="确认密码"
								   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
							<br />
							<br />
							<label>电子邮件：</label>
							<input class="itxt" type="text" placeholder="请输入邮箱地址"
								   autocomplete="off" tabindex="1" name="email" id="email"
								   value="${requestScope.email}"/>
							<br />
							<br />
							<label>验证码：</label>
							<input class="itxt" type="text" name="code" style="width: 100px; height: 10px" id="code"/>
							<img id="vCodeId" alt="" src="verifiedCode.jpg" style="float: right; margin-right: 40px; width: 110px; height: 30px;">
							<br />
							<br />
							<input type="submit" value="注册" id="sub_btn" />

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