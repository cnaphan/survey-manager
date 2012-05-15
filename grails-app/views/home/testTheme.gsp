<%@ page import="surveymgr.*" %>
<%@ page import="surveymgr.questions.*" %>
<!doctype html>
<html>
	<head>
		<title>Sample Survey</title>
		<meta name="layout" content="public">
		<g:themeStylesheets theme="${theme}"/>
	</head>
	<body>
		<div id="body">				
			<div id="survey-header">
				<h1>Sample Survey Title</h1>
				<div id="survey-description">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</div>
			</div>
			<div id="survey-body">				
				<div id="current-page">Page 1/2</div>
				<g:render template="/question/question" />
			</div>
			<div id="survey-footer">
				<input type="button" value="Previous Page" name="previous" id="previous"/>
				<input type="button" value="Next Page" name="next" id="next"/>
			</div>
		</div>
	</body>
</html>
