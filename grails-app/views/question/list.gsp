<%@ page import="surveymgr.*" %>
<!doctype html>
<html>
	<head>
		<title><g:message code="title" args="['Survey Permissions']"/></title>
		<meta name="layout" content="main">
		<g:javascript>
			$(function() {
				$(".buttons button").button().parent().buttonset()
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
				<li><g:link action="edit">Append new question</g:link></li>
				<li><g:link action="uploadQuestions">Upload question script</g:link></li>
			</ul>
		</div>
		<div id="body" class="indented">
			<h3>Survey Questions</h3>	
			<g:messages/>
			<table class="data" style="width: 75%;">
				<thead>
					<tr>		
						<th style="width: 2em; text-align: center;">#</th>
						<th style="width: 3em;">ID</th>
						<th>Text</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${ questions }" var="q" status="i">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<td>
								${q.sortOrder}
							</td>
							<td>${ q.questionId }</td>
							<td><g:link action="edit" id="${q.id}">${ q.text }</g:link></td>
							<td>
								<g:if test="${ q.sectionHeaderText }">&sect; </g:if>
								<g:if test="${ q.forceBreak }">&crarr; </g:if>
								<g:if test="${ q.hasOther }">&and; </g:if>
							</td>
							<td>
								<g:if test="${ q.sortOrder > 1 }"><g:link action="reorder" id="${q.id}" params="${[dir:"up"]}">Up</g:link></g:if>
								<g:if test="${ q.sortOrder > 1 && q.sortOrder < questions.size()}"> | </g:if> 
								<g:if test="${ q.sortOrder < questions.size()}"><g:link action="reorder" id="${q.id}" params="${[dir:"down"]}">Down</g:link></g:if>
							</td>
						</tr>
					</g:each>
				</tbody>					
			</table>
			<div class="buttons">
				<g:buttonLink class="back" controller="admin" action="dashboard" >Back</g:buttonLink>
			</div>			
		</div>
	</body>
</html>
