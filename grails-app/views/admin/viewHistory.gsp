
<%@ page import="surveymgr.Survey" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'survey.label', default: 'Survey')}" />
		<title><g:message code="title" args="['View Survey History']"/></title>
		<g:javascript>
			$(function() {
				$(".buttons button").button().parent().buttonset()
			});
		</g:javascript>				
	</head>
	<body>
		<div id="body" class="narrow">
			<h3>View Survey History</h3>
			<g:messages/>
			<table>
				<thead>
					<tr>					
						<g:sortableColumn property="dateCreated" title="Timestamp" />
						<g:sortableColumn property="title" title="Title" />					
						<g:sortableColumn property="text" title="Text" />				
						<g:sortableColumn property="user.name" title="User" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${history}" status="i" var="h">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">					
						<td><g:formatDate formatName="default.date.format" date="${h.dateCreated}"/></td>					
						<td>${h.title}</td>					
						<td style="width: 50%;">${h.text}</td>					
						<td>${h.user.name}</td>					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${historyTotal}" />
			</div>
			<div class="buttons">
				<g:buttonLink class="cancel" action="dashboard" >Back</g:buttonLink>
			</div>
		</div>
	</body>
</html>
