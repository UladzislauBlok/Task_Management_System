<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<%@ include file="headers/logout.jsp"%>
    <div>
        <span><a href="${pageContext.request.contextPath}/project">Project list</a></span>
        <span><a href="${pageContext.request.contextPath}/user">User list</a></span>
    </div>
</body>
</html>
