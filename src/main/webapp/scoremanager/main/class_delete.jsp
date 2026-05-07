<%-- 削除機能JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>クラス削除</title>

<style>

body{
    font-family:sans-serif;
    margin:20px;
}

table{
    border-collapse:collapse;
    width:400px;
}

th,td{
    border:1px solid #999;
    padding:10px;
}

th{
    background-color:#eeeeee;
    width:150px;
}

.button{
    margin-top:20px;
}

</style>

</head>
<body>

<h1>クラス削除</h1>

<p>
    以下のクラスを削除しますか？
</p>

<table>

<tr>
    <th>クラス番号</th>
    <td>${classNum.classNum}</td>
</tr>

</table>

<form action="ClassNumDeleteExecute.action"
      method="post">

    <input type="hidden"
           name="classNum"
           value="${classNum.classNum}">

    <div class="button">

        <input type="submit"
               value="削除">

        <a href="ClassNumList.action">
            戻る
        </a>

    </div>

</form>

</body>
</html>