<!DOCTYPE html>
<%-- 
    Document   : config.jsp
    Created on : 21/07/2011, 12:33:41 PM
    Author     : Wiley Fuller <wiley@alltheducks.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/bbNG" prefix="bbNG"%>
<%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>



<fmt:message var="pluginPageStr" key="admin_plugin_manage.label" bundle="${bundles.navigation_item}"/>
<fmt:message var="title" key="jsh.configPage.title" />
<fmt:message var="pageHelp" key="jsh.configPage.pageHelp" />
<fmt:message var="buttonCreateHackText" key="jsh.configPage.button.createHack" />
<fmt:message var="buttonUploadHackPackageText" key="jsh.configPage.button.uploadHackPackage" />
<fmt:message var="buttonForceReloadText" key="jsh.configPage.button.forceReload" />
<fmt:message var="buttonSuspendInjectionText" key="jsh.configPage.button.suspendInjection" />
<fmt:message var="buttonResumeInjectionText" key="jsh.configPage.button.resumeInjection" />
<fmt:message var="menuEditText" key="jsh.configPage.menuItem.edit" />
<fmt:message var="menuDeleteText" key="jsh.configPage.menuItem.delete" />
<fmt:message var="menuDownloadText" key="jsh.configPage.menuItem.download" />
<fmt:message var="menuEnableText" key="jsh.configPage.menuItem.enable" />
<fmt:message var="menuDisableText" key="jsh.configPage.menuItem.disable" />
<fmt:message var="imgaltEnabledText" key="jsh.configPage.imgalt.enabled" />
<fmt:message var="imgaltDisabledText" key="jsh.configPage.imgalt.disabled" />
<fmt:message var="labelHackIdText" key="jsh.configPage.label.hackId" />
<fmt:message var="labelVersionText" key="jsh.configPage.label.version" />
<fmt:message var="labelMinBbVersionText" key="jsh.configPage.label.minBbVersion" />
<fmt:message var="labelMaxBbVersionText" key="jsh.configPage.label.maxBbVersion" />
<fmt:message var="labelEnabledText" key="jsh.configPage.label.enabled" />
<fmt:message var="labelHookTypeText" key="jsh.configPage.label.hook" />

<jsp:useBean id="actionBean" class="org.oscelot.jshack.stripes.ConfigAction" scope="request" />

<bbNG:genericPage title="${title}" ctxId="ctx" >
  <bbNG:cssFile href="css/default.css"/>
  
  <bbNG:pageHeader instructions="${pageHelp}">
    <bbNG:breadcrumbBar environment="SYS_ADMIN" >
      <bbNG:breadcrumb title="${title}"/>
    </bbNG:breadcrumbBar>
    <bbNG:pageTitleBar title="${title}" />
  </bbNG:pageHeader>

  <bbNG:hierarchyList reorderable="false" >

    <bbNG:actionControlBar showWhenEmpty="true">
      <bbNG:actionButton title="${buttonCreateHackText}" url="CreateHack.action" primary="true"/>
      <bbNG:actionButton title="${buttonUploadHackPackageText}" url="UploadHackPackage.action" primary="true"/>
      <bbNG:actionButton title="${buttonForceReloadText}" url="ReloadHackPackages.action" primary="false"/>
      <c:choose>
        <c:when test="${actionBean.hackConfig.injectionSuspended}">
          <bbNG:actionButton title="${buttonResumeInjectionText}" url="SuspendInjection.action?resume" primary="false"/>
        </c:when>
        <c:otherwise>
          <bbNG:actionButton title="${buttonSuspendInjectionText}" url="SuspendInjection.action?suspend" primary="false"/>
        </c:otherwise>
      </c:choose>
    </bbNG:actionControlBar>
    <c:forEach items="${actionBean.hackPackages}" var="hack">
      <bbNG:hierarchyListItem title="${hack.name}">
        <bbNG:delegateContextMenu>
          <bbNG:contextMenuItem title="${menuEditText}" url="CreateHack.action?hackId=${hack.identifier}"/>
          <bbNG:contextMenuItem title="${menuDeleteText}" url="DeleteHack.action?hackId=${hack.identifier}"/>
          <bbNG:contextMenuItem title="${menuDownloadText}" url="DownloadHackPackage.action?hackId=${hack.identifier}"/>
          <c:choose>
            <c:when test="${hack.enabled}"><bbNG:contextMenuItem title="${menuDisableText}" url="SetHackStatus.action?disableHack&hackId=${hack.identifier}"/></c:when>
            <c:otherwise><bbNG:contextMenuItem title="${menuEnableText}" url="SetHackStatus.action?enableHack&hackId=${hack.identifier}"/></c:otherwise>
        </c:choose>
        </bbNG:delegateContextMenu>
          <c:if test="${not empty hack.developerName}">
            <p><em>${hack.developerName} | ${hack.developerInstitution} | <a href="${hack.developerURL}">${hack.developerURL}</a> | <a href="mailto:${hack.developerEmail}">${hack.developerEmail}</a></em></p>
          </c:if>
        <p><c:choose>
            <c:when test="${hack.enabled}"><span class="disabledIcon"><img src="/images/ci/icons/check.gif" alt="${imgaltEnabledText}" title="${imgaltEnabledText}"/></span></c:when>
            <c:otherwise><span class="disabledIcon"><img src="/images/ci/icons/x.gif"  alt="${imgaltDisabledText}" title="${imgaltDisabledText}"/></span></c:otherwise>
        </c:choose>
        ${hack.description}</p>
        <bbNG:itemDetail title="${labelHackIdText}" value="${hack.identifier}"/>
        <bbNG:itemDetail title="${labelVersionText}" value="${hack.version}"/>
        <bbNG:itemDetail title="${labelMinBbVersionText}" value="${hack.targetVersionMin}"/>
        <bbNG:itemDetail title="${labelMaxBbVersionText}" value="${hack.targetVersionMax}"/>
        <bbNG:itemDetail title="${labelEnabledText}" value="${hack.enabled}"/>
        <bbNG:itemDetail title="${labelHookTypeText}" value="${hack.hook}"/>
      </bbNG:hierarchyListItem>
    </c:forEach>
  </bbNG:hierarchyList>


</bbNG:genericPage>