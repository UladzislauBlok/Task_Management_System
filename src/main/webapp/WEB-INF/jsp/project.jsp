<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Project</title>
</head>
<body>
<%@ include file="headers/logout.jsp"%>
<h1>${requestScope.project.name}</h1>
    <p>
        ${requestScope.project.description}
        <br>
        Start at: ${requestScope.project.startDate}
        <br>
            <c:if test="${(sessionScope.user.role eq 'PM' or sessionScope.user.role eq 'TL')
            and sessionScope.user.project.id eq requestScope.project.id}">
                <form action="${pageContext.request.contextPath}/create-task" method="get">
                <input type="hidden" name="projectId" value="${requestScope.project.id}">
                <button type="submit">Create task</button>
                </form>
            </c:if>
    </p>
    <c:forEach var="task" items="${requestScope.tasks}">
        <li>
            <a href="task?id=${task.id}">Name: ${task.name} | Status: ${task.status}</a>
            <br>
        </li>
    </c:forEach>
</body>
</html>
