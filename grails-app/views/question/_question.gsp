<%@ page import="surveymgr.questions.*" %>
<g:each in="${questions}" var="q" status="i">
	<g:if test="${q.attributes.sectionHeaderText }">
		<div class="section-header-block">
			${q.attributes.sectionHeaderText }
		</div>
	</g:if>
	<div class="question-block" class="${ i % 2 == 0 ? 'even' : 'odd' }">
		<span class='text-line'><span class='text-num'>${q.sortOrder}.</span> <span class='text-item'>${q.text }</span></span>
		<g:render template="/question/${q.type.toString()}" model="${[q:q, a:answers[q.questionId]] }"/>			
	</div>
</g:each>