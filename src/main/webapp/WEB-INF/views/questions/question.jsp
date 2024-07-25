<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <h2 class="mt-5">${article.title}</h2>
        <p class="text-muted">By
            <c:choose>
            <c:when test="${not empty user}">
                ${user.username}
            </c:when>
            <c:otherwise>
            익명
            </c:otherwise>
            </c:choose>
            on ${article.createdDate}
        <hr>
        <p>${article.content}</p>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>
        <a href="${pageContext.request.contextPath}/questions" class="btn btn-secondary">Back to Post List</a>
    </div>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

<!-- Bootstrap Bundle with Popper -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>
