<%-- 検索機能JSP --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.ClassNum" %>

<%
    List<ClassNum> list =
        (List<ClassNum>)request.getAttribute("class_list");

    String keyword =
        (String)request.getAttribute("keyword");

    if (keyword == null) {
        keyword = "";
    }
%>

<html>
<head>
    <title>クラス検索</title>
</head>
<body>

<h2>クラス検索</h2>

<form action="ClassSearch.action" method="post">

    検索：
    <input type="text" name="keyword" value="<%= keyword %>">

    <input type="submit" value="検索">

</form>

<hr>

<table border="1">

    <tr>
        <th>クラス番号</th>
    </tr>

<%
    if (list != null) {

        for (ClassNum c : list) {
%>

    <tr>
        <td><%= c.getClass_num() %></td>
    </tr>

<%
        }
    }
%>

</table>

</body>
</html>