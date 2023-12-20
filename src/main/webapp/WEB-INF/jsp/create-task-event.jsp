<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create task event</title>
</head>
<body>
<%@ include file="headers/logout.jsp"%>
<h1>Create a new task event:</h1>
<br>
<form action="${pageContext.request.contextPath}/create-task-event" method="post">
    <label for="description">Description:
        <textarea id="description" name="description" rows="4" cols="50" required></textarea>
    </label>
    <input type="hidden" name="taskId" value="${requestScope.taskId}">
    <button type="submit">Create</button>
</form>
</body>
</html>