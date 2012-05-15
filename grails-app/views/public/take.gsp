<%@ page import="surveymgr.*" %>
<%@ page import="surveymgr.questions.*" %>
<!doctype html>
<html>
	<head>
		<title>${ s.name }</title>
		<meta name="layout" content="public">
		<g:themeStylesheets theme="${s.theme}"/>
	</head>
	<body>
		<div id="body">
			<g:form method="POST">
				<g:hiddenField name="pageNum" value="${currentPage}"/>
				<g:if test="${session.user}">
					<div id="user-div" style="">
						<h3>Operator Options</h3>
						Acting as ${session.respondent.surveyKey}
						<ul>
							<li><g:link controller="operator" action="pickRespondent">Switch respondent</g:link></li>
							<li><g:link controller="operator" action="quit">Back to dashboard</g:link></li>
						</ul>
					</div>
				</g:if>
				<div id="survey-header">
					<h1>${ s.name }</h1>
					<g:if test="${ s.description }"><div id="survey-description">${ s.description }</div></g:if>
				</div>
				<div id="survey-body">
					<g:if test="${ s.showPages }"><div id="current-page">Currently on page ${currentPage}/${totalPages}</div></g:if>
					<g:render template="/question/question" />
				</div>
				<div id="survey-footer">
					<g:if test="${ currentPage > 1 }">
						<g:actionSubmit action="previous" value="Previous Page" name="previous" id="previous"/>
					</g:if>
					<g:if test="${ currentPage < totalPages }">
						<g:actionSubmit action="next" value="Next Page" name="next" id="next"/>
					</g:if>			
				</div>
			</g:form>
		</div>
	</body>
</html>
