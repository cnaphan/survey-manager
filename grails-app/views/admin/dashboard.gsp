<%@ page import="surveymgr.*" %>
<!doctype html>
<html>
	<head>
		<title><g:message code="title" args="['Survey Dashboard']"/></title>
		<meta name="layout" content="main">
		<g:javascript library="jquery"/>
		<script type='text/javascript' src='https://www.google.com/jsapi'></script>
		<g:javascript>
			google.load('visualization', '1', {packages:['gauge','corechart']});
			google.setOnLoadCallback(drawChart);
			function drawChart() {
			  var data1 = google.visualization.arrayToDataTable([
			    ['Label', 'Value'],
			    ['Responses', ${ statistics.CompletedPercent }],
			  ]);
			
			  var options1 = {
			    width: 120, height: 120,
			    redFrom: 0, redTo: 33,
			    yellowFrom: 34, yellowTo: 66,
			    greenFrom: 64, greenTo:100,
			    minorTicks: 10,
			    majorTicks: 10
			  };
			
			  var chart1 = new google.visualization.Gauge(document.getElementById('response_gauge'));
			  chart1.draw(data1, options1);

			  
			  var data2 = google.visualization.arrayToDataTable([
			                                                    ['State', 'Response %'],
			                                                    <g:each in="${surveymgr.Respondent.State.values()}" var="it">['${it.toString()}', ${ statistics[it.toString() + 'Percent']}],
			                                                    </g:each>			                                                    
			                                                  ]);

               var options2 = {              
                 colors: ["#444", "#AAA", "red", "goldenrod", "green"],
                 chartArea: {top: 15, left: 5, width: 110, height: 110},
             
                 legend: {position: "none"},
                 fontName: "Calibri",
                 fontSize: 14,
               };

               var chart2 = new google.visualization.PieChart(document.getElementById('responsePie'));
               chart2.draw(data2, options2);
			}
		</g:javascript>		
	</head>
	<body>
		<div id="status">
			<h1>Overview</h1>
			<ul>
				<li>Survey is <strong>${ s.state.toString().toLowerCase() }</strong></li>
				<g:if test="${ s.hasPublicView }"><li>Theme: ${ s.theme ?: "none"}</li></g:if>
				<li>${ statistics.StartedCount }/${statistics.RespondentCount } responses started</li>
				<li>${ statistics.CompletedCount }/${statistics.RespondentCount } responses complete</li>
			</ul>
			<h1>Response</h1>
			<ul>
				<li>Response Rate:  ${ statistics.CompletedPercent }%
					<div id="response_gauge"></div></li>
				<li>Responses by State
					<div id="responsePie"></div></li>
			</ul>
		</div>
		<div id="body" class='indented'>
			<h3>Survey Dashboard</h3>
			<g:messages/>
			<ol class='property-list' style="width: 75%; padding-bottom: 0; ">
				<li class="fieldcontain">
					<span class="property-label">Name:</span>
					<span class="property-value">${s.name }
				</li>
				<g:if test="${ s.expiryDate}"><li class="fieldcontain"><span class="property-label">Expires:</span><span class="property-value"><g:formatDate date="${s.expiryDate }" formatName="format.longDate"/></span></li></g:if>
				<li class="fieldcontain"><span class="property-label">Assigned:</span>
					<span class="property-value">
						${session.user == s.owner ? "me" : s.owner.name } (owner)<g:each in="${s.operators}" var="u">, ${u.name}</g:each>
					</span>
				</li>
				<g:if test="${ s.hasPublicView }"><li class="fieldcontain"><span class="property-label">Public URL:</span> <span class="property-value"><input id="publicUrl" type="text" value="${ grailsApplication.config.grails.serverURL }/~${ SecurityUtils.encodeAsUrlFriendly(s.name) }" style="width: 30em;"  onclick="this.select();" readonly/></span></li></g:if>
			</ol>
			<hr/>
			<div style="width: 33%; float: left;">
				<h3>Survey Operations</h3>
				<ul>
					<g:if test="${ s.state < Survey.State.ACTIVE }">
						<li><em>The survey is no yet active so operations are not allowed.</em></li>
					</g:if>
					<g:elseif test="${ s.state == Survey.State.ACTIVE }">
						<li><g:link controller="operator" action="pickRespondent">Fill in survey as respondent</g:link></li>
					</g:elseif>
					<g:elseif test="${ s.state > Survey.State.ACTIVE }">
						<li><em>The survey is no longer active so operations are not allowed.</em></li>
					</g:elseif>
					<li><g:link action="viewHistory">View survey history</g:link></li>
				</ul>
			</div>
			<g:isManager>
				<div style="width: 33%; float: left;">
					<h3>Survey Management</h3>
					<ul>
						<g:if test="${ s.state < Survey.State.ACTIVE }">
							<li><g:link action="changeState" params="${[to:Survey.State.ACTIVE]}">Activate survey</g:link></li>
							<li><g:link controller="survey" action="edit" id="${s.id}">Modify survey</g:link>
							<li><g:link controller="question" action="list">Modify survey questions</g:link></li>
							<g:if test="${ s.hasPublicView }"><li><g:link action="themes">Modify survey theme</g:link></g:if> 
						</g:if>
						<g:elseif test="${ s.state == Survey.State.ACTIVE }">
							<li><g:link action="changeState" params="${[to:Survey.State.DORMANT]}">Make survey dormant again</g:link> (survey is not ready yet)</li>
							<li><g:link action="changeState" params="${[to:Survey.State.INACTIVE]}">De-activate survey</g:link> (survey is complete)</li>
						</g:elseif>
						<g:elseif test="${ s.state > Survey.State.ACTIVE }">
							<li><g:link action="changeState" params="${[to:Survey.State.ACTIVE]}">Re-activate survey</g:link></li>
						</g:elseif>
						<li><g:link action="permissions">Grant survey permissions</g:link></li>
					</ul>
					<h3>Response Management</h3>
					<ul>
						<li><g:link>Manage survey respondents</g:link></li>
						<li><g:link>Contact survey respondents</g:link></li>
						<li><g:link>View survey responses</g:link></li>
						<li><g:link>Export survey responses</g:link></li>
					</ul>						
				</div>
			</g:isManager>
			<g:isAdmin>
				<div style="width: 33%; float: left;">
					<h3>Survey Administration</h3>
					<ul>
						<li><g:link action="clearResponses">Clear survey responses</g:link></li>
						<li><g:link action="clearRespondents">Clear survey respondents</g:link></li>
						<li><g:link action="delete">Delete survey</g:link></li>
					</ul>
				</div>
			</g:isAdmin>
		</div>
	</body>
</html>
