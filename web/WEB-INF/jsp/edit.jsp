<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value=${resume.uuid}>
        <h3> Имя: </h3>
        <dl>
            <input type="text" name="fullName" size="55" value=${resume.fullName}>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" size="30" value=${resume.getContact(contactType)}>
                </dd>
            </dl>
        </c:forEach>
        <hr>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <jsp:useBean id="type"
                         type="ru.javawebinar.basejava.model.SectionType"/>
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
            <p>
            <h3>${type.titel}:</h3></p>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE'}">
                    <p><input type="text" name="${type.name()}" size="90"
                              value="<%=((TextSection) section).getContent()%>"></p>
                </c:when>

                <c:when test="${type == 'PERSONAL'}">
                    <p><textarea name='${type}' cols=90 rows=5><%=((TextSection) section).getContent()%></textarea>
                    </p>
                </c:when>

                <c:when test="${(type == 'ACHIEVEMENT') || (type == 'QUALIFICATIONS')}">
                    <p><textarea name='${type}' cols=90
                                 rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea></p>
                </c:when>

                <c:when test="${(type == 'EXPERIENCE') || (type == 'EDUCATION')}">
                    <c:forEach var="organization"
                               items="<%=((OrganizationSection) section).getOrganizations()%>" varStatus="counter">
                        <p><b><c:out value="Название:"/>
                            <input type="text" name="${type}" size="60"
                                   value="${organization.homePage.name}"></b></p>
                        <p><c:out value="Сайт:"/>
                            <input type="text" name="${type}url" size="65"
                                   value="${organization.homePage.url}"></p>

                        <div style="margin-left: 30px">
                            <c:forEach var="position" items="${organization.positions}">
                                <jsp:useBean id="position"
                                             type="ru.javawebinar.basejava.model.Organization.Position"/>

                                <p><c:out value="Начало:"/>
                                    <input type="text" name="${type}${counter.index}startData" size="8"
                                           value="${HtmlUtil.formatDate(position.startData)}" placeholder="yyyy/MM">
                                </p>
                                <p><c:out value="Окончание:"/>
                                    <input type="text" name="${type}${counter.index}endData" size="8"
                                           value="${HtmlUtil.formatDate(position.endData)}" placeholder="yyyy/MM">
                                </p>

                                <p><c:out value="Название:"/>
                                    <input type="text" name="${type}${counter.index}subTitel" size="60"
                                           value="${position.subTitel}"></p>
                                <p><c:out value="Описание:"/>
                                    <br>
                                    <textarea name="${type}${counter.index}description" cols=90
                                              rows=5>${position.description}</textarea></p>
                            </c:forEach>
                        </div>
                        <br>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
