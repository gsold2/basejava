<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
        <c:if test="${contactEntry.value.length() > 0}">
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
        <br/>
        </c:if>
        </c:forEach>
    <hr>

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
        <c:if test="<%=(sectionEntry.getKey() == SectionType.PERSONAL) || (sectionEntry.getKey() == SectionType.OBJECTIVE)%>">
            <h3><b><c:out value="${sectionEntry.key.titel}:"/></b></h3>
            <c:out value="<%=((TextSection) sectionEntry.getValue()).getContent()%>"/>
        </c:if>

        <c:if test="<%=(sectionEntry.getKey() == SectionType.ACHIEVEMENT) || (sectionEntry.getKey() == SectionType.QUALIFICATIONS)%>">
            <h3><b><c:out value="${sectionEntry.key.titel}:"/></b></h3>
            <ul>
                <c:forEach var="list" items="<%=((ListSection) sectionEntry.getValue()).getItems()%>">
                    <li><c:out value="${list}"/></li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="<%=(sectionEntry.getKey() == SectionType.EXPERIENCE) || (sectionEntry.getKey() == SectionType.EDUCATION)%>">
            <h3><b><c:out value="${sectionEntry.key.titel}:"/></b></h3>
            <c:forEach var="organization"
                       items="<%=((OrganizationSection) sectionEntry.getValue()).getOrganizations()%>">
                <c:if test="${organization.homePage.url == null}">
                    <b><c:out value="${organization.homePage.name}"/></b>
                </c:if>
                <c:if test="${organization.homePage.url != null}">
                    <b><a href=${organization.homePage.url}>${organization.homePage.name}</a></b>
                </c:if>

                <c:forEach var="position"
                           items="${organization.positions}">
                    <br>
                    <c:out value="Начало ${position.startData} окончание ${position.endData} "/>
                    <b><c:out value="${position.subTitel}"/></b>
                    <c:if test="${position.description.length() > 0}">
                        <br>
                        <c:out value="${position.description}"/>
                    </c:if>
                    <c:if test="${position.description.length() == 0}">
                        <br>
                    </c:if>
                </c:forEach>
                <br>
                <br>
            </c:forEach>
        </c:if>
    </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>