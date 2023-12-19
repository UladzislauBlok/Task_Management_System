<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Task</title>
</head>
<body>
    <h1>${requestScope.task.name}</h1>
    <br>
    <p>Description: ${requestScope.task.description}</p>
    <p>Status: ${requestScope.task.status}</p>
    <a href="${pageContext.request.contextPath}/project?id=${requestScope.task.projectId}">Project</a>
    <br>
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
