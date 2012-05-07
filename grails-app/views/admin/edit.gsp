<%@ page import="surveymgr.*" %>
<!doctype html>
<html>
	<head>
		<title><g:message code="title" args="['Modify Survey']"/></title>
		<meta name="layout" content="main">
		<g:javascript>
			$(function() {
				$(".buttons button").button({ icons: { primary: "ui-icon-disk" } }).next()
					.button({ icons: { primary: "ui-icon-cancel" } }).parent().buttonset()
			});
		</g:javascript>
	</head>
	<body>
		<div id="body" class="narrow">
			<h1>Edit Survey</h1>
			<g:form action="updateSurvey">
				<fieldset class="form">
					<g:render template="/survey/form"/>
				</fieldset>
				<div class="buttons">
					<button class="save" type="submit">Save</button>
					<g:buttonLink class="cancel" action="dashboard" >Cancel</g:buttonLink>
				</div>
			</g:form>
		</div>
	</body>
</html>
