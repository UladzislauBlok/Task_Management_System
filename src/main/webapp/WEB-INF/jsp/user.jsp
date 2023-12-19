<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h1>List of projects:</h1>
<ul>
    <c:forEach var="user" items="${requestScope.users}">
        <li>
            <a href="user?id=${user.id}">${user.firstName} ${user.lastName} ${user.email}</a>
            <br>
        </li>
    </c:forEach>
</ul>
</body>
</html>
