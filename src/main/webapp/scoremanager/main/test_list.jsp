# 成績管理処理 JSP ベース

## test_list.jsp（成績一覧画面）

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test_list</title>
<style>
body {
    font-family: sans-serif;
    margin: 20px;
}

table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    border: 1px solid #999;
    padding: 8px;
    text-align: center;
}

th {
    background-color: #f0f0f0;
}

.menu {
    margin-bottom: 20px;
}

.message {
    color: green;
}

.error {
    color: red;
}
</style>
</head>
<body>

<h1>成績一覧</h1>

<div class="menu">
    <a href="ScoreCreate.action">新規登録</a>
</div>

<form action="ScoreList.action" method="get">
    入学年度：
    <select name="entYear">
        <option value="">--------</option>
        <c:forEach var="year" items="${yearList}">
            <option value="${year}">${year}</option>
        </c:forEach>
    </select>

    クラス：
    <select name="classNum">
        <option value="">--------</option>
        <c:forEach var="classNum" items="${classList}">
            <option value="${classNum}">${classNum}</option>
        </c:forEach>
    </select>

    科目：
    <select name="subjectCd">
        <option value="">--------</option>
        <c:forEach var="subject" items="${subjectList}">
            <option value="${subject.cd}">${subject.name}</option>
        </c:forEach>
    </select>

    <input type="submit" value="検索">
</form>

<hr>

<c:if test="${not empty message}">
    <p class="message">${message}</p>
</c:if>

<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<table>
    <tr>
        <th>学生番号</th>
        <th>氏名</th>
        <th>科目</th>
        <th>回数</th>
        <th>点数</th>
        <th>操作</th>
    </tr>

    <c:forEach var="score" items="${scoreList}">
        <tr>
            <td>${score.student.no}</td>
            <td>${score.student.name}</td>
            <td>${score.subject.name}</td>
            <td>${score.no}</td>
            <td>${score.point}</td>
            <td>
                <a href="ScoreUpdate.action?studentNo=${score.student.no}&subjectCd=${score.subject.cd}&no=${score.no}">
                    変更
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
```

---

## score_create.jsp（成績登録画面）

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績登録</title>
</head>
<body>

<h1>成績登録</h1>

<form action="ScoreCreateExecute.action" method="post">

    <p>
        学生：
        <select name="studentNo" required>
            <option value="">--------</option>
            <c:forEach var="student" items="${studentList}">
                <option value="${student.no}">
                    ${student.name}
                </option>
            </c:forEach>
        </select>
    </p>

    <p>
        科目：
        <select name="subjectCd" required>
            <option value="">--------</option>
            <c:forEach var="subject" items="${subjectList}">
                <option value="${subject.cd}">
                    ${subject.name}
                </option>
            </c:forEach>
        </select>
    </p>

    <p>
        回数：
        <input type="number" name="no" required>
    </p>

    <p>
        点数：
        <input type="number" name="point" min="0" max="100" required>
    </p>

    <input type="submit" value="登録">

</form>

<p>
    <a href="ScoreList.action">戻る</a>
</p>

</body>
</html>
```

---

## score_update.jsp（成績変更画面）

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績変更</title>
</head>
<body>

<h1>成績変更</h1>

<form action="ScoreUpdateExecute.action" method="post">

    <input type="hidden" name="studentNo"
        value="${score.student.no}">

    <input type="hidden" name="subjectCd"
        value="${score.subject.cd}">

    <input type="hidden" name="no"
        value="${score.no}">

    <p>
        学生番号：${score.student.no}
    </p>

    <p>
        氏名：${score.student.name}
    </p>

    <p>
        科目：${score.subject.name}
    </p>

    <p>
        回数：${score.no}
    </p>

    <p>
        点数：
        <input type="number" name="point"
            value="${score.point}"
            min="0" max="100" required>
    </p>

    <input type="submit" value="変更">

</form>

<p>
    <a href="ScoreList.action">戻る</a>
</p>

</body>
</html>
```

---

## score_delete.jsp（削除確認画面）

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績削除</title>
</head>
<body>

<h1>成績削除</h1>

<p>以下の成績を削除しますか？</p>

<table border="1">
    <tr>
        <th>学生番号</th>
        <td>${score.student.no}</td>
    </tr>
    <tr>
        <th>氏名</th>
        <td>${score.student.name}</td>
    </tr>
    <tr>
        <th>科目</th>
        <td>${score.subject.name}</td>
    </tr>
    <tr>
        <th>回数</th>
        <td>${score.no}</td>
    </tr>
    <tr>
        <th>点数</th>
        <td>${score.point}</td>
    </tr>
</table>

<form action="ScoreDeleteExecute.action" method="post">

    <input type="hidden" name="studentNo"
        value="${score.student.no}">

    <input type="hidden" name="subjectCd"
        value="${score.subject.cd}">

    <input type="hidden" name="no"
        value="${score.no}">

    <input type="submit" value="削除">

</form>

<p>
    <a href="ScoreList.action">戻る</a>
</p>

</body>
</html>
```

---

## 想定しているBean

```java
Score
 ├ Student student
 ├ Subject subject
 ├ int no
 └ int point
```

```java
Student
 ├ String no
 └ String name
```

```java
Subject
 ├ String cd
 └ String name
```

---

## Action側で渡す主な属性

```java
request.setAttribute("scoreList", scoreList);
request.setAttribute("studentList", studentList);
request.setAttribute("subjectList", subjectList);
request.setAttribute("classList", classList);
request.setAttribute("yearList", yearList);
request.setAttribute("score", score);
```
