
<%@ page import="surveymgr.Survey" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'survey.label', default: 'Survey')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="body" class="narrow">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:messages/>
			<ol class="property-list survey">
			
				<g:if test="${surveyInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="survey.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${surveyInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${surveyInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="survey.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${surveyInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${surveyInstance?.createdDate}">
				<li class="fieldcontain">
					<span id="createdDate-label" class="property-label"><g:message code="survey.createdDate.label" default="Created Date" /></span>
					
						<span class="property-value" aria-labelledby="createdDate-label"><g:formatDate date="${surveyInstance?.createdDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${surveyInstance?.expiryDate}">
				<li class="fieldcontain">
					<span id="expiryDate-label" class="property-label"><g:message code="survey.expiryDate.label" default="Expiry Date" /></span>
					
						<span class="property-value" aria-labelledby="expiryDate-label"><g:formatDate date="${surveyInstance?.expiryDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${surveyInstance?.owner}">
				<li class="fieldcontain">
					<span id="owner-label" class="property-label"><g:message code="survey.owner.label" default="Owner" /></span>
					
						<span class="property-value" aria-labelledby="owner-label"><g:link controller="user" action="show" id="${surveyInstance?.owner?.id}">${surveyInstance?.owner?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${surveyInstance?.attributes?.hasPublicView}">
				<li class="fieldcontain">
					<span id="hasPublicView-label" class="property-label"><g:message code="survey.hasPublicView.label" default="Has Public View" /></span>
					
						<span class="property-value" aria-labelledby="hasPublicView-label"><g:formatBoolean boolean="${surveyInstance?.attributes?.hasPublicView}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${surveyInstance?.operators}">
				<li class="fieldcontain">
					<span id="operators-label" class="property-label"><g:message code="survey.operators.label" default="Operators" /></span>
					
						<g:each in="${surveyInstance.operators}" var="o">
						<span class="property-value" aria-labelledby="operators-label"><g:link controller="user" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${surveyInstance?.questions}">
				<li class="fieldcontain">
					<span id="questions-label" class="property-label"><g:message code="survey.questions.label" default="Questions" /></span>
					
						<g:each in="${surveyInstance.questions}" var="q">
						<span class="property-value" aria-labelledby="questions-label"><g:link controller="question" action="show" id="${q.id}">${q?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${surveyInstance?.respondents}">
				<li class="fieldcontain">
					<span id="respondents-label" class="property-label"><g:message code="survey.respondents.label" default="Respondents" /></span>
					
						<g:each in="${surveyInstance.respondents}" var="r">
						<span class="property-value" aria-labelledby="respondents-label"><g:link controller="respondent" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${surveyInstance?.state}">
				<li class="fieldcontain">
					<span id="state-label" class="property-label"><g:message code="survey.state.label" default="State" /></span>
					
						<span class="property-value" aria-labelledby="state-label"><g:fieldValue bean="${surveyInstance}" field="state"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${surveyInstance?.id}" />
					<g:link class="edit" action="edit" id="${surveyInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
