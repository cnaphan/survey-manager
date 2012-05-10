<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><g:layoutTitle default="Survey Manager" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"
	type="text/css">-->
<g:javascript lib="jquery"/>
<r:require module="jquery-ui"/>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'basics.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'sparse.css')}" type="text/css">	
<g:layoutHead />
<r:layoutResources />
<g:javascript>
$(function() {
	$("#quick-links a").button().parent().buttonset();
});
</g:javascript>
</head>
<body>
	<div id="header">
		<a href="${ createLink(uri: '/') }" style="float: left;"><g:img file="survey_pencil.png" alt="Survey Manager"/></a>
		<div id="home" style="float: left;">			
			<h1><a href="${createLink(uri: '/') }">Survey Manager</a></h1>
		</div>
		<div id='login'>
			<g:if test="${request.getSession(false) && session.user }">
				${session.user.name } (<g:link controller="login" action="logout" params="${[origUrl:request.forwardURI-request.contextPath] }">logout</g:link>)
			</g:if>
			<g:else>
				 (<g:link controller="login" action="login" params="${[origUrl: request.forwardURI-request.contextPath] }">login</g:link>)
			</g:else>
		</div>
		<g:if test="${request.getSession(false) && session.user }">
			<div id='middle'>
				<div id="survey-info">
					Survey: 
					<g:if test="${request.getSession(false) && session.survey }">
						<strong>${ session.survey.name }</strong> (<g:link controller="home" action="pickSurvey" params="${[origUrl:request.forwardURI-request.contextPath ] }">change</g:link>)					
					</g:if>
					<g:else>
						<i>None</i> (<g:link controller="home" action="pickSurvey" params="${[origUrl:request.forwardURI-request.contextPath] }">pick</g:link>)
					</g:else>
				</div>
				<g:if test="${request.getSession(false) && session.survey }">
					<div id='quick-links'>
						<g:link controller="admin" action="dashboard">Dashboard</g:link>
						<g:if test="${session.survey.hasPublicView}">
							<g:link controller="public" uri="/~${surveymgr.SecurityUtils.encodeAsUrlFriendly(session.survey.name)}">Public View</g:link>
						</g:if>
					</div>
				</g:if>
			</div>
		</g:if>
		<div class="clearer" ></div>
	</div>
	<g:layoutBody />
	<div id="spinner" class="spinner" style="display: none;">
		<g:message code="spinner.alt" default="Loading&hellip;" />
	</div>
	<g:javascript library="application" />
	<r:layoutResources />
</body>
</html>