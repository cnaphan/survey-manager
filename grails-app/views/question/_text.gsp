<g:if test="${q.height }">
	<div class='text-area-item'>
		<g:textArea name="questions.${q.questionId }" style="width: ${q.width ? q.width : '50em'}; height: ${q.height ? q.height : '20em'}" 
			class="text-area-item">${ a?.textValue }</g:textArea>
	</div>
</g:if>
<g:else>
	<div class='text-field-item'>
		<g:textField name="questions.${q.questionId }" style="width: ${q.width ? q.width : '50em'};" 
			class="text-field-item" value="${ a?.textValue }"/>
	</div>						
</g:else>