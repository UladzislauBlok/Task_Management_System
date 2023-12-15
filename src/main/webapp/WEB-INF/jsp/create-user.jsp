<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create user</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/create-user" method="post" enctype="multipart/form-data">
    <label for="first_name">First name:
        <input type="text" name="first_name" id="first_name" required>
    </label><br>
    <label for="last_name">Last name:
        <input type="text" name="last_name" id="last_name" required>
    </label><br>
    <label for="image">Image:
        <input type="file" name="image" id="image" required>
    </label><br>
    <label for="email">Email:
        <input type="text" name="email" id="email" required>
    </label><br>
    <label for="password">Password:
        <input type="password" name="password" id="password" required>
    </label><br>
    <label for="role">
        <select name="role" id="role">
            <c:forEach var="role" items="${requestScope.roles}">
                <option value="${role}">${role}</option>
            </c:forEach>
        </select></label><br>
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
