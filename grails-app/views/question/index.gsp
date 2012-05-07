<%@ page import="surveymgr.questions.*" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<style>
			
		</style>
	</head>
	<body>
		<g:form action="showParams" method="POST">
			<g:render template="/question/question" model="${[questions: questions, answers: answers]}"/>
			<input type="submit"/>
		</g:form>
	</body>
</html>
