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
<title><g:layoutTitle default="Survey" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'basics.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'questions.css')}" type="text/css">
<g:javascript lib="jquery"/>
<r:require module="jquery-ui"/>
<g:layoutHead />
<r:layoutResources />
</head>
<body>
	<div id="header">
	</div>
	<g:layoutBody />
	<div id="footer">
	</div>
	<div id="spinner" class="spinner" style="display: none;">
		<g:message code="spinner.alt" default="Loading&hellip;" />
	</div>
	<g:javascript library="application" />
	<g:javascript library="questions"/>
	<r:layoutResources />
</body>
</html>