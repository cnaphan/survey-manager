<%@ page import="surveymgr.questions.*" %>
<!doctype html>
<html>
	<head>
		<title><g:message code="title" args="['Modify Survey']"/></title>
		<meta name="layout" content="main">
		<g:javascript>
			$(function() {
				$(".buttons button").button({ icons: { primary: "ui-icon-disk" } }).next()
					.button({ icons: { primary: "ui-icon-disk" } }).next()
					.button({ icons: { primary: "ui-icon-cancel" } }).parent().buttonset()
				$("#saveAndNew").click(function() {
					$("#andNew").val("true");
				});
			});
		</g:javascript>
	</head>
	<body>
		<div id="body" class="narrow">
			<h3>Edit Survey Question</h3>
			<g:form action="save">
				<g:hiddenField name="id" value="${q?.id}" />
				<g:hiddenField name="version" value="${q?.version}" />
				<g:hiddenField name="andNew" value="false" />	
				<fieldset class="form">					
					<div class="fieldcontain">
						<label for="sortOrder">#</label>
						${q.sortOrder}
					</div>
					<div class="fieldcontain ${hasErrors(bean: q, field: 'questionId', 'error')} ">
						<label for="questionId">ID</label>
						<g:textField name="questionId" maxlength="5" value="${q?.questionId}" style="width: 5em;"/>
					</div>					
					<div class="fieldcontain ${hasErrors(bean: q, field: 'text', 'error')} ">
						<label for="text">Text</label>
						<g:textArea name="text" style="width: 40em; height: 5em;" maxlength="500" value="${q?.text}"/>
					</div>
					<div class="fieldcontain ${hasErrors(bean: q, field: 'type', 'error')} ">
						<label for="type">Type</label>
						<g:select name="type" from="${Question.Type.values().collect { message(code:"Question.Type.${it.toString()}.label") }}" keys="${Question.Type.values()}" value="${q.type}"/>
						<label for="control">Control</label>
						<g:select name="control" from="${Question.Control.values().collect { message(code:"Question.Control.${it.toString()}.label") }}" keys="${Question.Control.values()}"  value="${q.control}"/>
					</div>
					
				</fieldset>
				<div class="buttons">
					<button id="save" class="save" type="submit">Save</button>
					<button id="saveAndNew" class="save" type="submit">Save &amp; New</button>
					<g:buttonLink class="cancel" action="list" >Cancel</g:buttonLink>
				</div>
			</g:form>
		</div>
	</body>
</html>
