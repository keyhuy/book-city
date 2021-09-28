<%--
  User: Key
  Date: 2021/09/15/20:13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- 给提交按键绑定单击事件 --%>
<script type="text/javascript">
    // 页面加载完全后
    $(function () {
        // 绑定单击事件
        $("#submitBtn").click(function () {
            // 获取每个文本框内容
            let titleTest = $("#title").val();
            let priceText = $("#price").val();
            let salesText = $("#sales").val();
            let stockText = $("#stock").val();
            let authorText = $("#author").val();

            // 判断每个文本内容是否为空
            if (titleTest === "" || priceText === "" || authorText === "" || salesText === "" || stockText === "") {
                alert("图书信息不能为空！");
                return false;
            }

            // 定义一个数字正则表达式
            let numPat = /[0-9]/;

            // 价格、销量、库存只能是数字
            if (numPat.test(priceText) && numPat.test(salesText) && numPat.test(stockText)) {
                // 价格、销量、库存不能为负数
                if (priceText < 0 || stockText < 0 || salesText < 0) {
                    alert("价格或销量或库存值不合法！");
                    return false;
                }
            } else {
                alert("价格或销量或库存值不合法！");
                return false;
            }

            // 书名和作者名不能是数字
            if (numPat.test(titleTest) || numPat.test(authorText)) {
                // 如果是数字就不合法
                alert("书名或作者名不合法！");

                return false;
            }
        })
    })
</script>

<%-- 表单列表 --%>
<div id="main">
    <form action="manager/bookServlet" method="post">
        <%-- 添加隐藏域，用于标识对应的功能方法 --%>
        <input type="hidden" name="updatePage" value="${param.updatePage}">
        <input type="hidden" name="action" value="${empty requestScope.book ? "addBook" : "updateBookInfo"}">
        <input type="hidden" name="id" value="${requestScope.book.id}">

        <table>
            <tr>
                <td>名称</td>
                <td>价格</td>
                <td>作者</td>
                <td>销量</td>
                <td>库存</td>
                <td colspan="2">操作</td>
            </tr>
            <tr>
                <td><input id="title" name="title" type="text" value="${requestScope.book.title}"/></td>
                <td><input id="price" name="price" type="text" value="${requestScope.book.price}"/></td>
                <td><input id="author" name="author" type="text" value="${requestScope.book.author}"/></td>
                <td><input id="sales" name="sales" type="text" value="${requestScope.book.sales}"/></td>
                <td><input id="stock" name="stock" type="text" value="${requestScope.book.stock}"/></td>
                <td><input id="submitBtn" type="submit" value="提交"/></td>
            </tr>
        </table>
    </form>
</div>
