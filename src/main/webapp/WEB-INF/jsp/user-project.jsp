<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add user to project</title>
</head>
<body>
<%@ include file="headers/logout.jsp"%>
<form action="${pageContext.request.contextPath}/user-project" method="post">
    <label for="user">
        <select name="user" id="user" required>
            <c:forEach var="user" items="${requestScope.users}">
                <option value="${user.id}">${user.email}</option>
            </c:forEach>
        </select></label><br>
    <label for="project">
        <select name="project" id="project" required>
            <c:forEach var="project" items="${requestScope.projects}">
                <option value="${project.id}">${project.name}</option>
            </c:forEach>
        </select></label><br>
    <button type="submit">Create</button>
</form>
</body>
</html>
