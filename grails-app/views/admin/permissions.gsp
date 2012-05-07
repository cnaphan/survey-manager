<%@ page import="surveymgr.*" %>
<!doctype html>
<html>
	<head>
		<title><g:message code="title" args="['Survey Permissions']"/></title>
		<meta name="layout" content="main">
		<g:javascript>
			$(function() {
				$(".buttons button").button({ icons: { primary: "ui-icon-disk" } }).next()
					.button({ icons: { primary: "ui-icon-cancel" } }).parent().buttonset()
			});
		</g:javascript>
	</head>
	<body>
		<div id="status">
			<h1>Overview</h1>
			<ul>
				<li>Survey: ${s.name }</span></li>
				<li>Owner: ${session.user == s.owner ? "me" : s.owner.name }</li>
			</ul>
		</div>
		<div id="body" class="indented">
			<h1>Grant Survey Permissions</h1>
			<p>Granting permission to users gives them the ability to work on a particular survey. Users in the "Manager" group can modify (but not delete) the 
			survey, while users in the "Operator" group can only enter data.</p>
			<g:form action="savePermissions">
				<table class="data" style="width: 50%;">
					<thead>
						<tr>
							<th width="1%">&nbsp;<input type="hidden" name="operators"/></th>
							<th>Name</th>
							<th>Group</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${ userInstanceList }" var="u" status="i">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td>
									<g:hiddenField name="operatorIds" value="${u.id}"/>
									<g:checkBox name="operators" value="${u.id}" checked="${s.operators.contains(u)}" id="operators.${u.id}"/>
								</td>
								<td>${u.name }</td>
								<td><g:fieldValue bean="${u}" field="group"/></td>
							</tr>
						</g:each>
					</tbody>					
				</table>
				<div class="buttons">
					<button class="save" type="submit" icon="ui-icon-check">Save</button>
					<g:buttonLink class="cancel" action="dashboard" >Cancel</g:buttonLink>
				</div>
			</g:form>
		</div>
	</body>
</html>