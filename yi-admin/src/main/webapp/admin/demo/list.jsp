<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by Cai on 2015/5/14 10:11.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>demo list</title>
</head>
<body>
<h1>demo list</h1>
<c:forEach var="demo" items="${demoList}">
    <div>姓名：${demo.name}</div>
    <div>年龄：${demo.age}</div>
    <div>
        性别：
        <c:if test="${demo.sex == 0}">女</c:if>
        <c:if test="${demo.sex == 1}">男</c:if>
    </div>
</c:forEach>
</body>
</html>
