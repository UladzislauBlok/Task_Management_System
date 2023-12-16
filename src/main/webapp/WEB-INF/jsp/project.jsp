<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Project</title>
</head>
<body>
<h1>${requestScope.project.name}</h1>
    <p>
        ${requestScope.project.description}
        <br>
        Start at: ${requestScope.project.startDate}
    </p>
    <c:forEach var="task" items="${requestScope.tasks}">
        <li>
            <a href="project?id=${task.id}">Name: ${task.name} | Status: ${task.status}</a>
            <br>
        </li>
    </c:forEach>
</body>
</html>
