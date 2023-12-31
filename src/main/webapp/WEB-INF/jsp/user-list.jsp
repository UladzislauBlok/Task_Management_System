<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<%@ include file="headers/logout.jsp"%>
<h1>List of users:</h1>
<ul>
    <c:forEach var="user" items="${requestScope.users}">
        <li>
            <a href="user?id=${user.id}">${user.firstName} ${user.lastName} ${user.email}</a>
            <br>
        </li>
    </c:forEach>
</ul>
<%@ include file="headers/create-user-button.jsp"%>
<br>
<%@ include file="headers/add-user-to-project-button.jsp"%>
</body>
</html>
