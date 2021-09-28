<%--
  User: Key
  Date: 2021/09/15/20:05
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<table>
    <tr>
        <td>名称</td>
        <td>价格</td>
        <td>作者</td>
        <td>销量</td>
        <td>库存</td>
        <td colspan="2">操作</td>
    </tr>

    <%-- 使用JSTL标签中的forEach，遍历request域中的list信息 --%>
    <c:forEach items="${requestScope.pageOfBook.infoPerPage}" var="book">
        <tr>
            <td>${book.title}</td>
            <td>${book.price}</td>
            <td>${book.author}</td>
            <td>${book.sales}</td>
            <td>${book.stock}</td>
            <td><a href="manager/bookServlet?action=getBookInfo&id=${book.id}&updatePage=${requestScope.pageOfBook.currentPage}">
                修改
            </a></td>
            <td><a class="delClass" href="manager/bookServlet?action=deleteBook&id=${book.id}&updatePage=${requestScope.pageOfBook.currentPage}">
                删除
            </a></td>
        </tr>
    </c:forEach>

    <%-- 添加按钮 --%>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><a href="pages/manager/book_edit.jsp?updatePage=${requestScope.pageOfBook.totalPage}">添加图书</a></td>
    </tr>
</table>

<%-- 通过JS代码向删除按键绑定单击事件 --%>
<script type="text/javascript">
    // 页面加载完成后
    $(function () {

        // 给删除按钮绑定单机事件，弹出提示框
        $("a.delClass").click(function () {

            // 直接返回，如果点击确认返回true，提交信息到servlet，如果点击取消返回false，不提交
            return confirm("您确定要删除["+ $(this).parent().parent().find("td:first").text() +"]的全部信息吗？");
        })
    })
</script>
