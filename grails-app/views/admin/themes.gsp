<%@ page import="surveymgr.*" %>
<!doctype html>
<html>
	<head>
		<title><g:message code="title" args="['Survey Theme']"/></title>
		<meta name="layout" content="main">
		<g:javascript>
			$(function() {
				$(".buttons button").button({ icons: { primary: "ui-icon-disk" } }).next()
					.button({ icons: { primary: "ui-icon-cancel" } }).parent().buttonset()
			});
		</g:javascript>
	</head>
	<body>
		<div id="status">
			<h1>Overview</h1>
			<ul>
				<li>Survey: ${s.name }</li>
				<li>Current theme: ${s.theme ?: "none" }</li>				
			</ul>
		</div>
		<div id="body" class="indented">
			<h3>Survey Theme</h3>
			<g:messages/>
			<p>Themes allow you to change the appearance of the public survey. Use the form below to select a theme.</p>
			<g:form action="saveTheme">
				<table class="data" style="width: 20em;">
					<thead>
						<tr>
							<th width="1%">&nbsp;</th>
							<th>Theme</th>
							<th>&nbsp;</th>
							
						</tr>
					</thead>
					<tbody>
						<tr class="even">
							<td><g:radio name="theme" value="" id="theme-" checked="${ !s.theme }"/></td>
							<td><label for="theme-">None</label></td>
							<td>&nbsp;</td>
						</tr>
						<g:each in="${themes}" var="t" status="i">
							<tr class="${((i+1) % 2) == 0 ? 'even' : 'odd'}">
								<td><g:radio name="theme" value="${t}" id="theme-${t}" checked="${ t.equals(s.theme) }"/></td>
								<td><label for="theme-${t}">${t}</label></td>
								<td><g:link controller="home" action="testTheme" id="${t}" target="_new">View sample</g:link></td>
							</tr>
						</g:each>
					</tbody>					
				</table>					
				<div class="buttons">
					<button class="save" type="submit" icon="ui-icon-check">Save</button>
					<g:buttonLink class="cancel" action="dashboard" >Cancel</g:buttonLink>
				</div>
			</g:form>
		</div>
	</body>
</html>
