<%@ page import="surveymgr.Respondent" %>
<!doctype html>
<html>
	<head>
		<title><g:message code="title" args="['Select Respondent']"/></title>
		<meta name="layout" content="main">
		<g:javascript>
			$(function() {
				$("#pick").button({
            		icons: { primary: "ui-icon-check" }
                });
			});
		</g:javascript>
	</head>
	<body>
		<div id="body" class='narrow'>
			<h3>Select Respondent</h3>
			<g:if test="${respondents }">
			<p>Which respondent will you act as?</p>
			<g:form action="selectRespondent">
				<table class="data">
					<thead>
						<tr>
							<th width="1%">&nbsp;</th>
							<g:sortableColumn property="surveyKey" title="Key"/>
							<g:sortableColumn property="name" title="Name"/>
							<g:sortableColumn property="email" title="Email"/>
							<g:sortableColumn property="telephoneNumber" title="Telephone"/>
							<g:sortableColumn property="community" title="Community"/>
							<g:sortableColumn property="state" title="State"/>
						</tr>
					</thead>
					<tbody>
						<g:each in="${ respondents }" var="r" status="i">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td><g:radio name="respondentId" value="${r.id}" checked="${i == 0 }" id="respondentId.${r.id}"/></td>
								<td><label for="respondentId.${r.id}">${r.surveyKey}</label></td>
								<td>${r.name }</td>
								<td>${r.email }</td>
								<td>${r.telephoneNumber }</td>
								<td>${r.community ? r.community.name : "" }</td>
								<td>${r.state.toString()}
							</tr>
						</g:each>
					</tbody>					
				</table>
				<div class="pagination">
					<g:paginate total="${respondentTotal}" />
				</div>
				<div class="buttons">
					<button id="pick" type="submit">Select Respondent</button>
				</div>
			</g:form>
			</g:if>
			<g:else>
				<p>There are currently no respondents for this survey.</p>
			</g:else>
		</div>
	</body>
</html>
