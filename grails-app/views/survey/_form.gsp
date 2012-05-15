<%@ page import="surveymgr.Survey" %>
<g:javascript>
	$().ready(function() {
		$("#fieldcontain-questionsPerPage").css("display", $("#hasPublicView").is(":checked") ? "block" : "none") ;
		$("#hasPublicView").change(function() {
			$("#fieldcontain-questionsPerPage").css("display", $("#hasPublicView").is(":checked") ? "block" : "none") ;
			$("#fieldcontain-showPages").css("display", $("#hasPublicView").is(":checked") ? "block" : "none") ;
		});
	});
</g:javascript>
<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="survey.name.label" default="Name" />
	</label>
	<g:textField name="name" maxlength="50" value="${surveyInstance?.name}" style="width: 30em;"/>
</div>

<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="survey.description.label" default="Description" />
	</label>
	<g:textArea name="description" style="width: 40em; height: 5em;" maxlength="500" value="${surveyInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'createdDate', 'error')} required">
	<label for="createdDate">
		<g:message code="survey.createdDate.label" default="Created Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:formatDate date="${surveyInstance.createdDate }" formatName="format.longDate"/>
</div>

<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'expiryDate', 'error')} ">
	<label for="expiryDate">Expiry Date</label>
	<g:datePicker name="expiryDate" format="yyyy-MM-dd" precision="day"  value="${surveyInstance?.expiryDate}" default="none" noSelection="['': '']"  relativeYears="[0..1]"/>
</div>

<div class="fieldcontain">
	<label for="state">
		<g:message code="survey.state.label" default="State" />
	</label>
	${surveyInstance.state.toString()}
</div>

<fieldset class="subform">
	<legend>Survey Type</legend>
	<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'hasPublicView', 'error')} ">
		<label for="hasPublicView">Web Survey Mode</label>
	<g:checkBox name="hasPublicView" checked="${surveyInstance?.hasPublicView}" /> Whether a public web survey will be made available
	</div>
	<div id="fieldcontain-questionsPerPage" class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'questionsPerPage', 'error')} ">
		<label for="questionsPerPage">Questions per Page</label>
		<g:textField name="questionsPerPage" value="${surveyInstance?.questionsPerPage}" style="width: 2em;" maxlength="2"/>
	</div>	
	<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'showPages', 'error')} ">
		<label for="showPages">Show Page Count</label>
	<g:checkBox name="showPages" checked="${surveyInstance?.showPages}" />
	</div>
	<hr/>
	<div class="fieldcontain ${hasErrors(bean: surveyInstance, field: 'hasTelephoneMode', 'error')} ">
		<label for="hasTelephoneMode">Telephone Mode</label>
		<g:checkBox name="hasTelephoneMode" checked="${surveyInstance?.hasTelephoneMode}" /> Whether CATI features are enabled for this survey
	</div>	
</fieldset>
