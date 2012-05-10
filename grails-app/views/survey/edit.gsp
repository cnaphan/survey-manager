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
			<h3>Edit Survey</h3>
			<g:form action="update">
				<g:hiddenField name="id" value="${surveyInstance?.id}" />
				<g:hiddenField name="version" value="${surveyInstance?.version}" />			
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
