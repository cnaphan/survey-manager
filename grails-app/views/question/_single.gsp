<%@ page import="surveymgr.questions.*" %>
<g:set var="value" value="${a?.choices ? a.choices.toArray()[0].id : ""}"/>
<g:set var="sortedChoices" value="${q.choices.sort {a,b -> a.sortOrder.compareTo(b.sortOrder)}}"/>
<div class="choice-group single" id="question-${q.questionId}">
	<g:if test="${q.control == Question.Control.SELECT }">
		<g:select from="${sortedChoices}" noSelection="${[0:'--Select one--']}" keys="${sortedChoices.collect{ it.id ? it.id : 0} }" optionValue="text" name="questions.${q.questionId}" value="${value}"/>
		<g:if test="${q.attributes.hasOther }">
		<div class="other-item">
			<label for="others.${q.questionId}">${q.attributes.hasOtherText ? q.attributes.hasOtherText : message(code:"default.hasOther.label") }</label>
			<g:textField name="others.${q.questionId}" value="${a?.textValue }"/>
		</div>				
		</g:if>
	</g:if>
	<g:else test="${q.control == Question.Control.RADIO }">
		<g:radioGroup values="${sortedChoices.collect { it.id }}" name="questions.${q.questionId}" labels="${sortedChoices.collect { it.text } }" value="${value}">
			<div class="radio-item">${it.radio} <span class='radio-item-label'>${it.label }</span></div>
		</g:radioGroup>
		<g:if test="${q.attributes.hasOther }">
		<div class="other-item">
			<g:radio name="questions.${q.questionId }" value="0" checked="${a?.textValue ? true : false}" id="otherRadio.${q.questionId }"/> 
			<label for="otherRadio.${q.questionId }" class='radio-item-label radio-other-item-label'>${q.attributes.hasOtherText ? q.attributes.hasOtherText : message(code:"default.hasOther.label") }</label> <g:textField name="others.${q.questionId}"  value="${a?.textValue }"/>
		</div>
		</g:if>				
	</g:else>
</div>	