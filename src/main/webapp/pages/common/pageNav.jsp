<%--
  User: Key
  Date: 2021/09/15/19:53
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 分页条 --%>
<div id="page_nav">

    <%-- 如果当前页码大于1，即不是首页，才显示首页和上一页的按键 --%>
    <c:if test="${requestScope.pageOfBook.currentPage > 1}">
        <a href="${requestScope.pageOfBook.reqPath}&currentPage=1">首页</a>
        <a href="${requestScope.pageOfBook.reqPath}&currentPage=${requestScope.pageOfBook.currentPage - 1}">上一页</a>
    </c:if>

    <%-- 页码的显示 --%>
    <c:choose>
        <%-- 1. 总页码小于5，页码范围 [1,totalPage] --%>
        <c:when test="${requestScope.pageOfBook.totalPage < 5}">
            <%-- 直接修改循环遍历的起始和结束 --%>
            <c:set var="beginPage" value="1"></c:set>
            <c:set var="endPage" value="${requestScope.pageOfBook.totalPage}"></c:set>
        </c:when>

        <%-- 2. 总页码大于5，继续细分 --%>
        <c:otherwise>
            <c:choose>
                <%-- 2.1 当前页码为1、2、3，页码范围 [1,5] --%>
                <c:when test="${requestScope.pageOfBook.currentPage <= 3}">
                    <%-- 直接修改循环遍历的起始和结束 --%>
                    <c:set var="beginPage" value="1"></c:set>
                    <c:set var="endPage" value="5"></c:set>
                </c:when>

                <%-- 2.2 当前页码为totalPage-2、totalPage-1、totalPage，页码范围 [totalPage-4,totalPage] --%>
                <c:when test="${requestScope.pageOfBook.currentPage >= requestScope.pageOfBook.totalPage - 2}">
                    <%-- 直接修改循环遍历的起始和结束 --%>
                    <c:set var="beginPage" value="${requestScope.pageOfBook.totalPage - 4}"></c:set>
                    <c:set var="endPage" value="${requestScope.pageOfBook.totalPage}"></c:set>
                </c:when>

                <%-- 2.3 当前页码为其他情况，页码范围 [currPage-2,currPage+2] --%>
                <c:otherwise>
                    <%-- 直接修改循环遍历的起始和结束 --%>
                    <c:set var="beginPage" value="${requestScope.pageOfBook.currentPage - 2}"></c:set>
                    <c:set var="endPage" value="${requestScope.pageOfBook.currentPage + 2}"></c:set>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

    <%-- 将每种情况的页码显示都要循环的操作抽取出来 --%>
    <c:forEach begin="${beginPage}"
               end="${endPage}" var="i">
        <%-- 如果是当前页码，就标识出来，并不能点击 --%>
        <c:if test="${i == requestScope.pageOfBook.currentPage}">
            【${i}】
        </c:if>
        <%-- 如果不是当前页码，就设置为超链接，跳转到对应页码的页面 --%>
        <c:if test="${i != requestScope.pageOfBook.currentPage}">
            <a href="${requestScope.pageOfBook.reqPath}&currentPage=${i}">${i}</a>
        </c:if>
    </c:forEach>

    <%-- 如果当前页码小于总页码，才显示下一页和末页的按键 --%>
    <c:if test="${requestScope.pageOfBook.currentPage < requestScope.pageOfBook.totalPage}">
        <a href="${requestScope.pageOfBook.reqPath}&currentPage=${requestScope.pageOfBook.currentPage + 1}">下一页</a>
        <a href="${requestScope.pageOfBook.reqPath}&currentPage=${requestScope.pageOfBook.totalPage}">末页</a>
    </c:if>

    共${requestScope.pageOfBook.totalPage}页，${requestScope.pageOfBook.totalCount}条记录
    到第<input value="${requestScope.pageOfBook.currentPage}" name="pn" id="pn_input"/>页
    <input id="searchPageBth" type="button" value="确定">

    <%-- 利用JS代码给按键绑定单击事件，实现指定页码的跳转 --%>
    <script type="text/javascript">
        // 当页面加载完成后
        $(function () {
            // 绑定单机事件
            $("#searchPageBth").click(function () {
                // 先获取文本框内容，即需要跳转的页码
                let pageText = $("#pn_input").val();

                // 定义正则表达式
                let numPat = /[0-9]/;

                // 如果文本框内容必须是数字，不是数组就不合法，阻止页面跳转
                if (! numPat.test(pageText)) {
                    alert("输入页码不合法！");
                } else { // 如果输入内容是数字，就判断数字大小是否合法
                    // 获取总页码
                    let totalPage = ${requestScope.pageOfBook.totalPage};

                    // 判断文本框内容即页码是否合法，不合法就弹出警示框，并阻止页面跳转
                    if (pageText <= 0 || pageText > totalPage) {
                        alert("输入页码不合法！页码范围是：[1 , " + totalPage +"]");
                    } else {
                        // 通过location对象修改地址栏地址，实现页面跳转
                        location.href = "${pageScope.basePath}${requestScope.pageOfBook.reqPath}&currentPage=" + pageText;
                    }
                }
            })
        })
    </script>
</div>
