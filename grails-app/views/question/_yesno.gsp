<%@ page import="surveymgr.questions.*" %>
<div class="choice-group yesno" id="question-${q.questionId}">
	<g:if test="${q.control == Question.Control.CHECKBOX }">
		<g:checkBox name="questions.${q.questionId }" checked="${a?.textValue?.equals("yes") }"/>
	</g:if>
	<g:else>
		<div class="choice-group">
			<g:radioGroup values="${['yes', 'no'] }" name="questions.${q.questionId}" labels="${['Yes','No'] }" value="${a?.textValue }">
				<div class="radio-item">${it.radio} <span class='radio-item-label'>${it.label }</span></div>
			</g:radioGroup>
		</div>
	</g:else>
</div>