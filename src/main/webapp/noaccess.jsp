<%-- 
    Document   : noaccess
    Created on : 21/07/2011, 11:36:53 AM
    Author     : Wiley Fuller <wiley@alltheducks.com>
  --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/bbNG" prefix="bbNG"%>
<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<fmt:message var="title" key="jsh.noAccessPage.title" />
<fmt:message var="noAccessMessage" key="jsh.noAccessPage.noAccessMessage" />

<bbNG:learningSystemPage title="${title}" ctxId="ctx" > 
    <bbNG:pageHeader >
        <bbNG:breadcrumbBar >
            <bbNG:breadcrumb title="${title}"/>
        </bbNG:breadcrumbBar>
        <bbNG:pageTitleBar title="${title}" />
    </bbNG:pageHeader>
    ${noAccessMessage}
</bbNG:learningSystemPage>