<%-- クラス一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>クラス一覧</title>

<style>

body{
    font-family:sans-serif;
    margin:20px;
}

table{
    border-collapse:collapse;
    width:500px;
}

th,td{
    border:1px solid #999;
    padding:10px;
    text-align:center;
}

th{
    background-color:#eeeeee;
}

.menu{
    margin-bottom:20px;
}

.message{
    color:green;
}

.error{
    color:red;
}

</style>

</head>
<body>

<h1>クラス管理</h1>

<div class="menu">

    <a href="ClassNumCreate.action">
        新規登録
    </a>

</div>

<c:if test="${not empty message}">
    <p class="message">
        ${message}
    </p>
</c:if>

<c:if test="${not empty error}">
    <p class="error">
        ${error}
    </p>
</c:if>

<table>

<tr>
    <th>クラス番号</th>
    <th>操作</th>
</tr>

<c:forEach var="c" items="${classList}">

<tr>

    <td>
        ${c.classNum}
    </td>

    <td>

        <a href="ClassNumDelete.action?classNum=${c.classNum}">
            削除
        </a>

    </td>

</tr>

</c:forEach>

</table>

</body>
</html>