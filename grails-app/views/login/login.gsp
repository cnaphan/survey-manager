<%@ page import="surveymgr.User"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="main">
<title><g:message code="title" args="['Login']" /></title>
</head>
<body>
	<div id="body" class="narrow">
		<h1>Login</h1>
		<g:messages/>
		<g:form method="POST">
			<g:hiddenField name="origUrl" value="${origUrl }"/>
			<fieldset class="form">
				<div class="fieldcontain required">
					<label for="name">
						Login Name:
					</label>
					<g:textField name="name" maxlength="25" required="" value="${ fieldValue(bean: userInstance, field: 'name') }"/>
				</div>
				<div class="fieldcontain required">
					<label for="password">
						Password:
					</label>
					<g:passwordField name="password" maxLength="25"/>
				</div>
				<div class="fieldcontain required">
					<label for="login">
					</label>
					<g:actionSubmit action="authenticate" value="Login" name="login" />
				</div>
			</fieldset>
		</g:form>
	</div>
</body>
</html>
