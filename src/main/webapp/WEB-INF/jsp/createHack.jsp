<!DOCTYPE html>
<%-- 
    Document   : createHack.jsp
    Created on : 21/07/2011, 12:33:41 PM
    Author     : Wiley Fuller <wfuller@swin.edu.au>

    Copyright Swinburne University of Technology, 2011.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="/bbNG" prefix="bbNG"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://stripes.sourceforge.net/stripes.tld" prefix="stripes" %>  
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>



<fmt:message var="pluginPageStr" key="admin_plugin_manage.label" bundle="${bundles.navigation_item}"/>
<fmt:message var="title" key="jsh.createHackPage.title" />
<fmt:message var="pageHelp" key="jsh.createHackPage.pageHelp" />

<fmt:message var="stepHackDetailsText" key="jsh.createHackPage.step.hackDetails" />
<fmt:message var="labelNameText" key="jsh.createHackPage.label.name" />
<fmt:message var="labelDescriptionText" key="jsh.createHackPage.label.description" />
<fmt:message var="labelIdentifierText" key="jsh.createHackPage.label.identifier" />
<fmt:message var="labelVersionText" key="jsh.createHackPage.label.version" />
<fmt:message var="labelMinVersionText" key="jsh.createHackPage.label.minVersion" />
<fmt:message var="labelMaxVersionText" key="jsh.createHackPage.label.maxVersion" />
<fmt:message var="stepDeveloperText" key="jsh.createHackPage.step.developer" />
<fmt:message var="labelDeveloperNameText" key="jsh.createHackPage.label.developerName" />
<fmt:message var="labelDeveloperInstitutionText" key="jsh.createHackPage.label.developerInstitution" />
<fmt:message var="labelDeveloperURLText" key="jsh.createHackPage.label.developerURL" />
<fmt:message var="labelDeveloperEmailText" key="jsh.createHackPage.label.developerEmail" />
<fmt:message var="stepInjectedContentText" key="jsh.createHackPage.step.injectedContent" />
<fmt:message var="labelSnippetText" key="jsh.createHackPage.label.snippet" />
<fmt:message var="labelInjectionPointText" key="jsh.createHackPage.label.injectionPoint" />
<fmt:message var="labelResourcesText" key="jsh.createHackPage.label.resources" />
<fmt:message var="labelFileText" key="jsh.createHackPage.label.file" />
<fmt:message var="labelMimeTypeText" key="jsh.createHackPage.label.mimeType" />
<fmt:message var="labelExampleFileReferenceText" key="jsh.createHackPage.label.exampleFileReference" />
<fmt:message var="buttonRemoveResourceText" key="jsh.createHackPage.button.removeResource" />
<fmt:message var="buttonAddResourceText" key="jsh.createHackPage.button.addResource" />
<fmt:message var="stepRestrictionsText" key="jsh.createHackPage.step.restrictions" />
<fmt:message var="labelRestrictionListText" key="jsh.createHackPage.label.restrictionList" />
<fmt:message var="labelTypeText" key="jsh.createHackPage.label.type" />
<fmt:message var="labelInvertNoText" key="jsh.createHackPage.label.invertNo" />
<fmt:message var="labelInvertYesText" key="jsh.createHackPage.label.invertYes" />
<fmt:message var="labelValueText" key="jsh.createHackPage.label.value" />
<fmt:message var="buttonRemoveRestrictionText" key="jsh.createHackPage.button.removeRestriction" />
<fmt:message var="buttonAddRestrictionText" key="jsh.createHackPage.button.addRestriction" />
<fmt:message var="stepSaveText" key="jsh.createHackPage.step.save" />

<fmt:message var="injectionPointTopFrameText" key="jsh.injectionPoint.jsp.topFrame.start" />
<fmt:message var="injectionPointFramesetText" key="jsh.injectionPoint.jsp.frameset.start" />
<fmt:message var="injectionPointLearningSystemPageText" key="jsh.injectionPoint.tag.learningSystemPage.start" />
<fmt:message var="injectionPointEditModeButtonText" key="jsh.injectionPoint.tag.editModeViewToggle.start" />
<fmt:message var="injectionPointGlobalNavPageText" key="jsh.injectionPoint.tags.globalNavPage.start" />
<fmt:message var="injectionPointCourseBreadcrumbText" key="jsh.injectionPoint.tag.course.breadcrumb.control" />

<fmt:message var="restrictionUrlText" key="jsh.restriction.url" />
<fmt:message var="restrictionEntitlementText" key="jsh.restriction.entitlement" />
<fmt:message var="restrictionAdvancedText" key="jsh.restriction.advanced" />
<fmt:message var="restrictionCourseRoleText" key="jsh.restriction.courseRole" />
<fmt:message var="restrictionSystemRoleText" key="jsh.restriction.systemRole" />
<fmt:message var="restrictionPortalRoleText" key="jsh.restriction.portalRole" />
<fmt:message var="restrictionCourseAvailabilityText" key="jsh.restriction.courseAvailability" />
<fmt:message var="restrictionRequestParameterText" key="jsh.restriction.requestParameter" />


<bbNG:genericPage title="${title}" ctxId="ctx" >
  <bbNG:cssFile href="css/default.css?2"/>
  <script type="text/javascript" src="js/ace/ace.js"></script>
  <bbNG:jsFile href="js/mustache.js"/>
  <bbNG:jsFile href="js/jshack.js?1"/>
  <bbNG:jsFile href="/javascript/scriptaculous/version_pinned_scriptaculous.js"/>
  <bbNG:pageHeader instructions="${pageHelp}">
    <bbNG:breadcrumbBar environment="SYS_ADMIN" >
      <bbNG:breadcrumb href="../blackboard/admin/manage_plugins.jsp" title="${pluginPageStr}" />
      <bbNG:breadcrumb title="${title}"/>
    </bbNG:breadcrumbBar>
    <bbNG:pageTitleBar title="${title}" />
  </bbNG:pageHeader>
  <stripes:form beanclass="org.oscelot.jshack.stripes.CreateHackAction" enctype="multipart/form-data" method="POST">
    <stripes:hidden name="saveHackPackage" />
    <bbNG:dataCollection>
      <bbNG:step title="${stepHackDetailsText}">
        <bbNG:dataElement label="${labelNameText}" isRequired="true">
          <stripes:text name="hack.name" style="width:35em;" />
        </bbNG:dataElement>      
        <bbNG:dataElement label="${labelDescriptionText}" isRequired="true">
          <stripes:text name="hack.description" style="width:35em;" />
        </bbNG:dataElement>      
        <bbNG:dataElement label="${labelIdentifierText}" isRequired="true">
          <stripes:text name="hack.identifier"  disabled="${!empty actionBean.hack.identifier}" maxlength="20" size="20" />
          <c:if test="${!empty actionBean.hack.identifier}">
            <stripes:hidden name="hack.identifier" />
          </c:if>
        </bbNG:dataElement>      
        <bbNG:dataElement label="${labelVersionText}">
          <stripes:text name="hack.version" />
        </bbNG:dataElement>
        <bbNG:dataElement label="${labelMinVersionText}">
          <stripes:text name="hack.targetVersionMin" />
        </bbNG:dataElement>      
        <bbNG:dataElement label="${labelMaxVersionText}">
          <stripes:text name="hack.targetVersionMax" />
        </bbNG:dataElement>    
      </bbNG:step>
      
    <bbNG:step title="${stepDeveloperText}">
        <bbNG:dataElement label="${labelDeveloperNameText}">
          <stripes:text name="hack.developerName" />
        </bbNG:dataElement>
        <bbNG:dataElement label="${labelDeveloperInstitutionText}">
          <stripes:text name="hack.developerInstitution" />
        </bbNG:dataElement>
        <bbNG:dataElement label="${labelDeveloperURLText}">
          <stripes:text name="hack.developerURL" />
        </bbNG:dataElement>    
        <bbNG:dataElement label="${labelDeveloperEmailText}">
          <stripes:text name="hack.developerEmail" />
        </bbNG:dataElement>      
    </bbNG:step>

      <bbNG:step title="${stepInjectedContentText}">
        <bbNG:dataElement label="${labelSnippetText}">
          <div id="editorContainer">
            <div id="snippetEditor"><c:out value="${actionBean.hack.snippet}" escapeXml="true" /></div>
          </div>
          <input type="hidden" name="hack.snippet" id="snippetInput"/>
        </bbNG:dataElement>      
        <bbNG:dataElement label="${labelInjectionPointText}">
          <stripes:select name="hack.hook">
            <stripes:option value="jsp.topFrame.start">${injectionPointTopFrameText}</stripes:option>
            <stripes:option value="jsp.frameset.start">${injectionPointFramesetText}</stripes:option>
            <stripes:option value="tag.learningSystemPage.start">${injectionPointLearningSystemPageText}</stripes:option>
            <stripes:option value="tag.editModeViewToggle.start">${injectionPointEditModeButtonText}</stripes:option>
            <stripes:option value="tags.globalNavPage.start">${injectionPointGlobalNavPageText}</stripes:option>
            <stripes:option value="tag.course.breadcrumb.control">${injectionPointCourseBreadcrumbText}</stripes:option>
          </stripes:select>
        </bbNG:dataElement>      
        <bbNG:dataElement label="${labelResourcesText}" >
          <div id="jsh-resources"></div>
          <button id="jsh_addResourceButton">${buttonAddResourceText}</button>
        </bbNG:dataElement>      
      </bbNG:step>

      <bbNG:step title="${stepRestrictionsText}">
        <bbNG:dataElement label="${labelRestrictionListText}">
          <div id="jsh-restrictions"></div>
          <button id="jsh_addRestrictionButton">${buttonAddRestrictionText}</button>
        </bbNG:dataElement>
      </bbNG:step>

      <bbNG:stepSubmit cancelUrl="Config.action">
        <bbNG:stepSubmitButton label="${stepSaveText}" id="submitHackButton"/>
      </bbNG:stepSubmit>
    </bbNG:dataCollection>

  </stripes:form>


  <script id="jsh-resourceTemplate" type="text/template">
    <div class="jsh-resourceDiv">
      <table style="width:100%">
        <tr class="fileRow"><th><label>${labelFileText} </label>
          </th><td>
            {{^path}}<input name="resourceFiles" type="File"></input><input type="hidden" name="path"/>{{/path}}
            {{#path}}{{path}}<input type="hidden" name="path" value="{{path}}"/>{{/path}}
            {{#tempFile}}<input type="hidden" name="tempFiles" value="{{tempFile}}"/>{{/tempFile}}
          </td></tr>
        <tr class="mimeRow"><th><label>${labelMimeTypeText}</label></th><td><input type="text" name="mime" value="{{mime}}"></input></td></tr>
        <tr title="Copy and paste this code into the snippet editor."><th>${labelExampleFileReferenceText}</th>
          <td class="exampleRef">{{#path}} ${'${'}resourcePath}/{{path}} {{/path}}</td></tr>
        <tr><td colspan="2"><span style="float:right;"><a href="#" class="removeResourceLink">${buttonRemoveResourceText}</a></span></td></tr>
      </table>
    </div>
  </script>

  <script id="jsh-restrictionTemplate" type="text/template">
    <div class="jsh-restrictionDiv">
      ${labelTypeText} <select name="type">
        {{#types}}
        <option value="{{value}}"{{#selected}} selected="true"{{/selected}}>{{name}}</option>
        {{/types}}
      </select>
      <select name="inverse"><option value="false" {{^inverse}} selected=true {{/inverse}}>${labelInvertNoText}</option><option value="true" {{#inverse}} selected=true {{/inverse}}>${labelInvertYesText}</option></select> ${labelValueText} 
      <input type="text" name="value" style="width:35em;" value="{{value}}">
      <a href="#" class="removeRestrictionLink">${buttonRemoveRestrictionText}</a>
    </div>
  </script>


  <script>
    var resources = <json:array name="resources" var="currRes" items="${actionBean.hack.resources}">
      <json:object>
        <json:property name="path" value="${currRes.path}" escapeXml="false"/>
        <json:property name="mime" value="${currRes.mime}"/>
        <json:property name="embed" value="${currRes.embed}"/>
      </json:object>
    </json:array>
      var tempFiles = <json:array name="tempFiles" var="currFile" items="${actionBean.tempFiles}">
      <json:object>
        <json:property name="fileName" value="${currFile}"/>
      </json:object>
    </json:array>

      var restrictions = <json:array name="restrictions" var="currRestr" items="${actionBean.hack.restrictions}">
      <json:object>
        <json:property name="type" value="${currRestr.type}"/>
        <json:property name="value" value="${currRestr.value}" escapeXml="false"/>
        <json:property name="inverse" value="${currRestr.inverse}"/>
      </json:object>
    </json:array>

      var restrictionTypes = [{'name': '${restrictionUrlText}', 'value': 'URL'},
        {'name': '${restrictionEntitlementText}', 'value': 'ENTITLEMENT'},
        {'name': '${restrictionAdvancedText}', 'value': 'ADVANCED'}, {'name': '${restrictionCourseRoleText}', 'value': 'COURSE_ROLE'},
        {'name': '${restrictionSystemRoleText}', 'value': 'SYSTEM_ROLE'}, {'name': '${restrictionPortalRoleText}', 'value': 'PORTAL_ROLE'},
        {'name': '${restrictionCourseAvailabilityText}', 'value': 'COURSE_AVAILABILITY'}, 
        {'name': '${restrictionRequestParameterText}', 'value': 'REQUEST_PARAMETER'}];
      var editor;
      var resTmpl;
      var restrTmpl;

      Event.observe(document, 'dom:loaded', function() {
        editor = ace.edit('snippetEditor');
        editor.getSession().setMode('ace/mode/html');

        resTmpl = $('jsh-resourceTemplate').innerHTML;
        restrTmpl = $('jsh-restrictionTemplate').innerHTML;

        Event.observe($('jsh_addResourceButton'), 'click', function(e) {
          Event.stop(e);
          var resContainer = $('jsh-resources');
          resContainer.insert({'bottom': createResourceDiv({'path': '', 'mime': 'text/html'})});
          return false;
        });

        Event.observe($('jsh_addRestrictionButton'), 'click', function(e) {
          Event.stop(e);
          var restrContainer = $('jsh-restrictions');
          restrContainer.insert({'bottom': createRestrictionDiv(decorateRestrictionForPresentation({}))});
          return false;
        });

        var bottomSubmitBtn = $('bottom_submitHackButton');
        if(bottomSubmitBtn) {
          Event.observe(bottomSubmitBtn, 'click', submitHandler);
        }

        var topSubmitBtn = $('top_submitHackButton');
        if(topSubmitBtn) {
          Event.observe(topSubmitBtn, 'click', submitHandler);
        }

        var resContainer = $('jsh-resources');
        if (resources.length === 0) {
          resContainer.insert({'bottom': createResourceDiv({'mime': 'text/html'})});
        } else {
          for (var i = 0; i < resources.length; i++) {
            if (resources[i].path && resources[i].path.trim() === '') {
              resources[i].path = null;
            }
            if (tempFiles[i]) {
              resources[i].tempFile = tempFiles[i].fileName;
            }
            resContainer.insert({'bottom': createResourceDiv(resources[i])});
          }
        }

        var restrContainer = $('jsh-restrictions');
        if (restrictions.length === 0) {
          restrContainer.insert({'bottom': createRestrictionDiv(decorateRestrictionForPresentation({}))});
        } else {
          for (var i = 0; i < restrictions.length; i++) {
            restrContainer.insert({'bottom': createRestrictionDiv(decorateRestrictionForPresentation(restrictions[i]))});
          }
        }

      });


      /**
 * Function for modifying the form and form data before submission
 */
      function submitHandler(e) {
        $('snippetInput').setValue(Base64.encode(editor.getValue()));

        var formValid = validateForm();
        if (!formValid) {
          Event.stop(e);
          return false;
        }

        try {
          var restrictionDivs = $("jsh-restrictions").select('.jsh-restrictionDiv');
          for (var i = 0; i < restrictionDivs.length; i++) {
            var typeInput = restrictionDivs[i].select('[name="type"]')[0];
            var inverseInput = restrictionDivs[i].select('[name="inverse"]')[0];
            var valueInput = restrictionDivs[i].select('[name="value"]')[0];
            var hackPrefix = 'hack.restrictions[' + i + ']';
            Element.writeAttribute(typeInput, 'name', hackPrefix + '.type');
            Element.writeAttribute(inverseInput, 'name', hackPrefix + '.inverse');
            Element.writeAttribute(valueInput, 'name', hackPrefix + '.value');
          }

          var resourceDivs = $("jsh-resources").select('.jsh-resourceDiv');
          for (var i = 0; i < resourceDivs.length; i++) {
            var pathInput = resourceDivs[i].select('[name="path"]')[0];
            var mimeInput = resourceDivs[i].select('[name="mime"]')[0];
            $(mimeInput).value = Base64.encode(mimeInput.value);
            var resourceFileInput = resourceDivs[i].select('[name="resourceFiles"]')[0];
            var tempFileInput = resourceDivs[i].select('[name="tempFiles"]')[0];
            var hackPrefix = 'hack.resources[' + i + ']';
            Element.writeAttribute(pathInput, 'name', hackPrefix + '.path');
            Element.writeAttribute(mimeInput, 'name', hackPrefix + '.mime');
            if (resourceFileInput) {
              Element.writeAttribute(resourceFileInput, 'name', 'resourceFiles[' + i + ']');
            }
            if (tempFileInput) {
              Element.writeAttribute(tempFileInput, 'name', 'tempFiles[' + i + ']');
            }
          }
        } catch (ex) {
          Event.stop(e);
          throw(ex);
        }
      }


      function decorateRestrictionForPresentation(restriction) {
        restriction.types = new Array();
        for (var i = 0; i < restrictionTypes.length; i++) {
          var currType = restrictionTypes[i];
          var displayType = {'name': currType.name, 'value': currType.value};
          if (restriction.type && restriction.type === currType.value) {
            displayType.selected = true;
          }
          restriction.types.push(displayType);
        }
        return restriction;
      }


      function createRestrictionDiv(restriction) {
        var html = Mustache.render(restrTmpl, restriction);
        var frag = document.createDocumentFragment();
        var div = document.createElement('div');
        frag.appendChild(div);
        Element.insert(div, {'bottom': html});
        var restrictionDiv = $(div).select('.jsh-restrictionDiv')[0];
        var removeRestrictionLink = $(div).select('.removeRestrictionLink')[0];
        Event.observe($(removeRestrictionLink), 'click', function(resDiv) {
          return function(e) {
            Event.stop(e);
            Effect.BlindUp(resDiv, {'afterFinish': function() {
                $(resDiv).remove()
              }, 'duration': 0.5});
          };
        }(restrictionDiv));
        return restrictionDiv;
      }



      function createResourceDiv(resource) {
        var html = Mustache.render(resTmpl, resource);
        var frag = document.createDocumentFragment();
        var div = document.createElement('div');
        frag.appendChild(div);
        Element.insert(div, {'bottom': html});
        var resourceDiv = $(div).select('.jsh-resourceDiv')[0];
        var removeRestrictionLink = $(div).select('.removeResourceLink')[0];
        Event.observe($(removeRestrictionLink), 'click', function(resDiv) {
          return function(e) {
            Event.stop(e);
            Effect.BlindUp(resDiv, {'afterFinish': function() {
                $(resDiv).remove()
              }, 'duration': 0.5});
          };
        }(resourceDiv));

        return resourceDiv;
      }



      function validateForm() {
        return true;
      }


  </script>
</bbNG:genericPage>