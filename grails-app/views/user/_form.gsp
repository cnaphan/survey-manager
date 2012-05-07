<%@ page import="surveymgr.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="user.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="50" required="" value="${userInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="user.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="password" name="password" required="" value="${userInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'group', 'error')} required">
	<label for="group">
		<g:message code="user.group.label" default="Group" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="group" name="group.id" from="${survey.UserGroup.list()}" optionKey="id" required="" value="${userInstance?.group?.id}" class="many-to-one"/>
</div>

