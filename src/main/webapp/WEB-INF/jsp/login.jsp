<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="email">Email:
            <input type="text" name="email" id="email" required>
        </label>
        <label for="password">Password:
            <input type="password" name="password" id="password" required>
        </label>
        <button type="submit">Login</button>
    </form>
</body>
</html>
