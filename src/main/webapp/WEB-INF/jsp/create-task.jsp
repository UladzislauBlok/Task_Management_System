<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create task</title>
</head>
<body>
<%@ include file="headers/logout.jsp"%>
<h1>Create a new task:</h1>
<br>
<form action="${pageContext.request.contextPath}/create-task" method="post">
    <label for="name">Name:
        <input type="text" name="name" id="name" required>
    </label><br>
    <label for="description">Description:
        <textarea id="description" name="description" rows="4" cols="50" required></textarea>
    </label>
    <input type="hidden" name="projectId" value="${requestScope.projectId}">
    <button type="submit">Create</button>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>
