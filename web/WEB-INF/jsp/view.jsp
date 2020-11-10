<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Pезюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <p>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
        <c:if test="${contactEntry.value.length() > 0}">
            ${contactEntry.key.toHtml(contactEntry.value)}
            <br/>
        </c:if>
    </c:forEach>
    <hr>

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section"
                     type="ru.javawebinar.basejava.model.AbstractSection"/>
        <h2><c:out value="${type.titel}:"/></h2>

        <c:choose>
            <c:when test="${(type == 'PERSONAL') || (type == 'OBJECTIVE')}">
                <c:out value="<%=((TextSection) section).getContent()%>"/>
            </c:when>

            <c:when test="${(type == 'ACHIEVEMENT') || (type == 'QUALIFICATIONS')}">
                <ul>
                    <c:forEach var="list" items="<%=((ListSection) section).getItems()%>">
                        <li><c:out value="${list}"/></li>
                    </c:forEach>
                </ul>
            </c:when>

            <c:when test="${(type == 'EXPERIENCE') || (type == 'EDUCATION')}">
                <c:forEach var="organization"
                           items="<%=((OrganizationSection) section).getOrganizations()%>">
                    <c:if test="${organization.homePage.url == null}">
                        <h4><c:out value="${organization.homePage.name}"/></h4>
                    </c:if>
                    <c:if test="${organization.homePage.url != null}">
                        <h4><a href=${organization.homePage.url}>${organization.homePage.name}</a></h4>
                    </c:if>

                    <c:forEach var="position"
                               items="${organization.positions}">
                        <jsp:useBean id="position"
                                     type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <br>
                        <c:out value="${HtmlUtil.formatDate(position.startDate)}"/>
                        <c:out value="-"/>
                        <c:out value="${HtmlUtil.formatDate(position.endDate)}"/>
                        <b><c:out value="${position.subTitel}"/></b>
                        <c:if test="${position.description.length() > 0}">
                            <br>
                            <c:out value="${position.description}"/>
                        </c:if>
                    </c:forEach>

                    <br>
                    <br>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>