<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
        <form action="${pageContext.request.contextPath}/create-user" method="get">
            <button type="submit">Create user</button>
        </form>
    </c:if>
</div>
