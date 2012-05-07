
<%@ page import="surveymgr.Survey" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'survey.label', default: 'Survey')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="body">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:messages/>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'survey.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'survey.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="createdDate" title="${message(code: 'survey.createdDate.label', default: 'Created Date')}" />
					
						<g:sortableColumn property="expiryDate" title="${message(code: 'survey.expiryDate.label', default: 'Expiry Date')}" />
					
						<th><g:message code="survey.owner.label" default="Owner" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${surveyInstanceList}" status="i" var="surveyInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${surveyInstance.id}">${fieldValue(bean: surveyInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: surveyInstance, field: "description")}</td>
					
						<td><g:formatDate date="${surveyInstance.createdDate}" /></td>
					
						<td><g:formatDate date="${surveyInstance.expiryDate}" /></td>
					
						<td>${fieldValue(bean: surveyInstance, field: "owner")}</td>
					
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${surveyInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
