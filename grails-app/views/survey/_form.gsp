<%@ page import="surveymgr.Survey" %>

<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="survey.name.label" default="Name" />
		
	</label>
	<g:textField name="name" maxlength="50" value="${surveyInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="survey.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" style="width: 30em; height: 5em; maxlength="500" value="${surveyInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'createdDate', 'error')} required">
	<label for="createdDate">
		<g:message code="survey.createdDate.label" default="Created Date" />
		<span class="required-indicator">*</span>
	</label>
	${surveyInstance?.createdDate?.toString()}
</div>

<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'expiryDate', 'error')} ">
	<label for="expiryDate">Expiry Date</label>
	<g:datePicker name="expiryDate" format="yyyy-MM-dd" precision="day"  value="${surveyInstance?.expiryDate}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'state', 'error')} required">
	<label for="state">
		<g:message code="survey.state.label" default="State" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="state" from="${surveymgr.Survey$State?.values()}" keys="${surveymgr.Survey$State.values()*.name()}" required="" value="${surveyInstance?.state?.name()}"/>
</div>
<fieldset style="padding: 0.2em 0.5em; border: 1px solid #DDD; width: 50%; margin-left: 10%;">
	<legend >Survey Type</legend>
	<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'attributes.hasPublicView', 'error')} ">
		<label for="hasPublicView">Public View
		</label>
		<g:checkBox name="hasPublicView" value="${surveyInstance.attributes.hasPublicView}" />
	</div>
</fieldset>
