<%@ page import="surveymgr.*" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title><g:message code="title" args="['Home']"/></title>
	</head>
	<body>
		<div id="status">
			<h1>Manager Status</h1>
			<ul>
				<li>Version: <g:meta name="app.version"/></li>
				<li>Active surveys: ${ Survey.countByState(Survey.State.ACTIVE) }</li>
				<li>Inactive surveys: ${ Survey.countByStateNot(Survey.State.ACTIVE) }</li>
				<li>Users: ${ User.count() }</li>
			</ul>
			<h1>Controllers</h1>
			<ul>
				<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
					<li class="controller"><g:link controller="${c.logicalPropertyName}">${c.name}</g:link></li>
				</g:each>
			</ul>
		</div>
		<div id="body" class="indented">
			<h1>Welcome to Survey Manager</h1>
			<g:messages/>			
			<p>This might be a good place for some general user-centered dashboards.</p>

			
		</div>
	</body>
</html>
