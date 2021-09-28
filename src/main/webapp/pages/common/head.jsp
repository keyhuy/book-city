<%--
  User: Key
  Date: 2021/09/11/19:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 动态获取base标签值 --%>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" +
            request.getServerPort() +
            request.getContextPath() + "/";

    // 把地址信息存储到pageContext域中
    pageContext.setAttribute("basePath", basePath);
%>

<!-- base标签，固定相对路径跳转的结果（固定路径的前面一部分） -->
<base href="<%=basePath%>">

<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<!-- 引入JQuery 文件 -->
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>
