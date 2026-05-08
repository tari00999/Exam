<%-- クラス更新JSP --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="bean.ClassNum" %>

<%
    ClassNum c =
        (ClassNum)request.getAttribute("class_data");
%>

<html>
<head>
    <title>クラス変更</title>
</head>
<body>

<h2>クラス変更</h2>

<form action="ClassUpdateExecute.action" method="post">

    <table border="1">

        <tr>
            <th>クラス番号</th>
            <td>

                <!-- 元の値を保持 -->
                <input type="hidden"
                       name="old_class_num"
                       value="<%= c.getClass_num() %>">

                <!-- 更新用 -->
                <input type="text"
                       name="class_num"
                       value="<%= c.getClass_num() %>">

            </td>
        </tr>

    </table>

    <br>

    <input type="submit" value="変更">

</form>

<br>

<a href="ClassList.action">一覧へ戻る</a>

</body>
</html>