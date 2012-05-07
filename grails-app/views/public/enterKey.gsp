<%@ page import="surveymgr.*" %>
<%@ page import="surveymgr.questions.*" %>
<!doctype html>
<html>
	<head>
		<title>Enter your Survey Key</title>
		<meta name="layout" content="public">
	</head>
	<body>
		<div id="body">
			<g:form method="POST">
				<g:hiddenField name="surveyId" value="${s.id}"/>
				<div id="survey-header">
					<h1>${s.name}</h1>
					<h2>Enter your Survey Key</h2>
					<g:messages/>
					<div>To start the survey, enter the key you were provided in the introductory email.</div>
				</div>
				<div id="survey-body">
					<div id="survey-key-div">
						<label for="">Survey Key:</label> <g:textField name="surveyKey" id="surveyKey" value="${surveyKey}"/>
					</div>
				</div>
				<div id="survey-footer">	
					<g:actionSubmit name="ok" value="Continue" action="useKey"/>
				</div>
			</g:form>
		</div>
	</body>
</html>
