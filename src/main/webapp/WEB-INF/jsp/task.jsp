<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Task</title>
</head>
<body>
<%@ include file="headers/logout.jsp"%>
    <h1>${requestScope.task.name}</h1>
    <br>
    <p>Description: ${requestScope.task.description}</p>
    <p>Status: ${requestScope.task.status}</p>
    <a href="${pageContext.request.contextPath}/project?id=${requestScope.task.projectId}">Project</a>
    <br>
    <c:if test="${sessionScope.user.project.id eq requestScope.task.projectId}">
        <form action="${pageContext.request.contextPath}/create-task-event" method="get">
            <input type="hidden" name="taskId" value="${requestScope.task.id}">
            <button type="submit">Create task event</button>
        </form>
    </c:if>
    <ol>
    <c:forEach var="taskEvent" items="${requestScope.task.taskEventList}">
        <li>
            <p>${taskEvent.description}</p>
            <p>${taskEvent.eventUserName}</p>
        </li>
    </c:forEach>
    </ol>
</body>
</html>
