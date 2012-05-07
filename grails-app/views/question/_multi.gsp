<%@ page import="surveymgr.questions.*" %>
<g:set var="sortedChoices" value="${q.choices.sort {a,b -> a.sortOrder.compareTo(b.sortOrder)}}"/>
<div class="choice-group check-group multi" id="question-${q.questionId}">
	<g:each in="${sortedChoices }" var="c">
		<div class='check-item'>
			<g:checkBox name="questions.${q.questionId }" id="questions.${q.questionId }.${c.id }" value="${c.id }" checked="${ a?.choices?.contains { it.id == c.id }}"/>
			<label for="questions.${q.questionId }.${c.id }">${c.text }</label>
		</div>
	</g:each>
	<g:if test="${q.attributes.hasOther }">
		<div class="other-item">
			<g:checkBox name="questions.${q.questionId }" id="questions.${q.questionId }.0" value="0" checked="${a?.textValue ? true : false }"/> 
			<label class='check-item-label check-other-item-label'>${q.attributes.hasOtherText ? q.attributes.hasOtherText : message(code:"default.hasOther.label") }</label>
			<g:textField name="others.${q.questionId}" value="${a?.textValue }"/>
		</div>
	</g:if>
</div>