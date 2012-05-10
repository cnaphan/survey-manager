<%@ page import="surveymgr.Survey" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'survey.label', default: 'Survey')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<g:javascript>
			$(function() {
				$(".buttons button").button({ icons: { primary: "ui-icon-disk" } }).next()
					.button({ icons: { primary: "ui-icon-cancel" } }).parent().buttonset()
			});
		</g:javascript>
	</head>
	<body>
		<div id="body" class="narrow">
			<h3><g:message code="default.create.label" args="[entityName]" /></h3>
			<g:messages/>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<div class="buttons">
					<button class="save" type="submit">Save</button>
					<g:buttonLink class="cancel" controller="admin" action="dashboard" >Cancel</g:buttonLink>
				</div>
			</g:form>
		</div>
	</body>
</html>
