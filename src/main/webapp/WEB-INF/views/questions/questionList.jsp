<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <h2 class="mt-5">Post List</h2>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
        </c:if>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="article" items="${articleList}">
                <tr onclick="location.href='${pageContext.request.contextPath}/questions/${article.id}'"
                    style="cursor: pointer;">
                    <td><c:out value="${article.title}"/></td>
                    <td>
                        <c:out value="${article.userName}"/>
                    </td>
                    <td><c:out value="${article.createdDate}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


        <!-- Pagination -->
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:set var="maxPages" value="5" />
                <c:set var="halfMaxPages" value="${maxPages / 2}" />
                <c:set var="startPage" value="${currentPage - halfMaxPages < 1 ? 1 : currentPage - halfMaxPages}" />
                <c:set var="endPage" value="${startPage + maxPages - 1}" />
                <c:if test="${endPage > totalPages}">
                    <c:set var="endPage" value="${totalPages}" />
                    <c:set var="startPage" value="${endPage - maxPages + 1 > 0 ? endPage - maxPages + 1 : 1}" />
                </c:if>

                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link" href="?page=1">처음</a>
                </li>
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage - 1}">&laquo;</a>
                </li>

                <c:forEach begin="${startPage}" end="${endPage}" var="i">
                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}">${i}</a>
                    </li>
                </c:forEach>

                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage + 1}">&raquo;</a>
                </li>
                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${totalPages}">마지막</a>
                </li>
            </ul>
        </nav>
    </div>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>