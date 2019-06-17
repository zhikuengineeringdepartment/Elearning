<%--
  Created by IntelliJ IDEA.
  User: 鲍伟
  Date: 2019/3/13
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${courseView.courseName}</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <c:if test="${courseView != null}">
        <ul class="nav nav-pills">
        <c:forEach items="${courseView.sections}" var="section">

            <li <c:if test="${sectionView.sectionName.equals(section.sectionName)}">class="active"</c:if>>
                <a href="/Elearning/section/getSection?sid=${section.sid}">${section.sectionName}</a>
            </li>
        </c:forEach>
        </ul>
    </c:if>
    <c:forEach items="${sectionView.knowledgeViews}" var="knowledge">
        <h4>${knowledge.knowledgeName}</h4>
        <ul class="list-group">
            <c:forEach items="${knowledge.paragraphs}" var="paragraph">
                <li class="list-group-item">${paragraph.paragraphContent},${paragraph.paragraphType}</li>
            </c:forEach>
        </ul>
    </c:forEach>
</body>
</html>