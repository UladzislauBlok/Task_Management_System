<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="email">Email:
            <input type="text" name="email" id="email" value="${param.email}" required>
        </label>
        <label for="password">Password:
            <input type="password" name="password" id="password" required>
        </label>
        <button type="submit">Login</button>
    </form>
    <c:if test="${param.error != null}">
        <div style="color: red">
            <span>Email or password is not correct</span>
        </div>
    </c:if>
</body>
</html>
