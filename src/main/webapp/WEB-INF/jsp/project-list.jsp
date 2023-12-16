<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Project list</title>
</head>
<body>
<h1>List of projects:</h1>
    <ul>
        <c:forEach var="project" items="${requestScope.projects}">
            <li>
                <a href="project?id=${project.id}">${project.name}</a>
                <br>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
