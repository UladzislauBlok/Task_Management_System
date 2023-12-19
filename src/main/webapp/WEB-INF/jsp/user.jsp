<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User</title>
</head>
<body>
    <h1>User ${requestScope.user.firstName} ${requestScope.user.lastName}</h1>
    <img src="${pageContext.request.contextPath}/images/users?id=${requestScope.user.id}&image=${requestScope.user.image}" alt="user img">
    <br>
    <p>Email: ${requestScope.user.email}</p>
    <p>Role: ${requestScope.user.role}</p>
    <br>
    <p>Project: <a href="project?id=${requestScope.user.project.id}">${requestScope.user.project.name}</a>
    </p>
</body>
</html>
