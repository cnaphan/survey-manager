<%@ page import="surveymgr.Survey" %>
<!doctype html>
<html>
	<head>
		<title><g:message code="title" args="['Pick a Survey']"/></title>
		<meta name="layout" content="main">
		<g:javascript>
			$(function() {
				$("#pick").button({
            		icons: { primary: "ui-icon-check" }
                });
                $("#includeInactiveSurveys").change(function(){
                	var chk = ($(this).attr("checked") == "checked") ? "true" : "false";
                	location.href = "${createLink(action: "pick")}?${params.origUrl ? "origUrl=${params.origUrl}&" : ""}includeInactive=" + chk;
                });
			});
		</g:javascript>
	</head>
	<body>
		<div id="body" class='narrow'>
			<h1>Pick a Survey</h1>
			<g:if test="${surveyInstanceList }">
			<div style="float: right;"><g:checkBox name="includeInactiveSurveys" checked="${params.includeInactive}"/> <label for="includeInactiveSurveys">Include inactive surveys</label></div>
			<p>Which survey would you like to work on?</p>
			<g:form action="set">
				<g:hiddenField name="origUrl" value="${params.origUrl }"/>
				<table class="data">
					<thead>
						<tr>
							<th width="1%">&nbsp;</th>
							<th width="50%">Name</th>
							<th>State</th>
							<th>Created On</th>
							<th>Expires On</th>
							<th>Owner</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${ surveyInstanceList }" var="s" status="i">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td><g:radio name="surveyId" value="${s.id}" id="surveyId.${s.id}" checked="${(!session?.survey && i == 0) || session?.survey?.id == s.id }"/></td>
								<td><label for="surveyId.${s.id}">${s.name }</label></td>
								<td>${s.state == Survey.State.ACTIVE ? "<span style='color: green;'>${s.state}</span>" : "<span style='color: grey;'>${s.state}</span>" }
								<td><g:formatDate date="${s.createdDate }" formatName="format.longDate"/></td>
								<td><g:if test="${s.expiryDate}"><g:formatDate date="${s.expiryDate }" formatName="format.longDate"/></g:if><g:else>Never</g:else></td>
								<td><g:link controller="user" action="show" id="${s.owner.id }">${s.owner.name }</g:link></td>
							</tr>
						</g:each>
					</tbody>					
				</table>
				<div class="buttons">
					<button id="pick" type="submit">Use Survey</button>
				</div>
			</g:form>
			</g:if>
			<g:else>
				<p>There are currently no surveys. <g:link action="create">Create a new one.</g:link>
			</g:else>
		</div>
	</body>
</html>
