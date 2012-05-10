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
				<li>Dormant surveys: ${ Survey.countByState(Survey.State.DORMANT) }</li>
				<li>Active surveys: ${ Survey.countByState(Survey.State.ACTIVE) }</li>
				<li>Inactive surveys: ${ Survey.countByState(Survey.State.INACTIVE) }</li>
				<li>Users: ${ User.count() }</li>
			</ul>
		</div>
		<div id="body" class="indented">
			<h1>Welcome to Survey Manager</h1>
			<g:messages/>			
			<g:if test="${mySurveys}">
				<div style="width: 33%; float: left;">
				<h3>My Surveys</h3>
				<ul>
				<g:each in="${ mySurveys}" var="s">
					<li><g:link action="setSurvey" params="${[surveyId:s.id]}">${s.name}</g:link></li>
				</g:each>
				</ul>
				</div>
			</g:if>
			
			<div style="width: 33%; float: left;">
				<h3>Manager Actions</h3>
				<ul>
					<li><g:link controller="survey" action="create">Create a new survey</g:link></li>
				</ul>
			</div>

			
		</div>
	</body>
</html>
