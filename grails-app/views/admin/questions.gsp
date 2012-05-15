<%@ page import="surveymgr.*" %>
<!doctype html>
<html>
	<head>
		<title><g:message code="title" args="['Survey Permissions']"/></title>
		<meta name="layout" content="main">
		<g:javascript>
			$(function() {
				$(".buttons a").button().parent().buttonset()
			});
		</g:javascript>
	</head>
	<body>
		<div id="status">
			<h1>Overview</h1>
			<ul>
				<li>Survey: ${s.name }</span></li>
				<li>Questions: ${questions.size()}</li>
				<li>Pages: ${ totalPages }</li>
			</ul>
			<h1>Operations</h1>
			<ul>
				<li><g:link action="reorderQuestions">Re-order questions</g:link></li>
				<li><g:link action="uploadQuestions">Upload question script</g:link></li>
			</ul>
		</div>
		<div id="body" class="indented">
			<h3>Survey Questions</h3>						
			<table class="data" style="width: 50%;">
				<thead>
					<tr>		
						<th>#</th>
						<th>ID</th>
						<th>Text</th>							
					</tr>
				</thead>
				<tbody>
					<g:each in="${ questions }" var="q" status="i">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<td>
								${q.sortOrder}
							</td>
							<td>${ q.questionId }</td>
							<td><g:link </td>
						</tr>
					</g:each>
				</tbody>					
			</table>
			<div class="buttons">
				<g:buttonLink class="back" action="dashboard" >Back</g:buttonLink>
			</div>			
		</div>
	</body>
</html>
