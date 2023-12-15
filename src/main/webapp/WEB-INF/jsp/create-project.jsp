<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create project</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/create-project" method="post">
    <label for="name">Name:
        <input type="text" name="name" id="name" required>
    </label><br>
    <label for="project_description">Description:
    <textarea id="project_description" name="project_description" rows="4" cols="50" required></textarea>
    </label><br>
    <label for="project_start_date">Description:
        <input type="date" name="project_start_date" id="project_start_date" required>
    </label>
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
