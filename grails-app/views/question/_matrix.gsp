<%@ page import="surveymgr.questions.*" %>
<g:set var="sortedChoices" value="${q.choices.sort {a,b -> a.sortOrder.compareTo(b.sortOrder)}}"/>
<div class="choice-group matrix" id="question-${q.questionId}">
	<table id="question-${q.questionId}" class="matrix-table">
		<thead>
			<tr>
				<th class="matrix-first-column">&nbsp;</th>
				<g:each in="${ 1..q.matrixRange }" var="col" status="colNum">
					<th class="matrix-column">
						<g:if test="${ q.rangeLowText && (colNum == 0) }">
							<div class="matrix-range-text">${q.rangeLowText }</div>
						</g:if>
						<g:elseif test="${ q.rangeHighText && (colNum == (1..q.matrixRange).size() - 1) }">
							<div class="matrix-range-text">${q.rangeHighText }</div>
						</g:elseif>
						${col}
					</th>
				</g:each>
				<g:if test="${ q.hasNa }">
					<th class="matrix-column"><g:message code="default.na.label"/></th>
				</g:if>
			</tr>
		</thead>
		<tbody>
			<g:each in="${sortedChoices }" var="c" status="j">
			<tr class="${(j % 2) == 0 ? 'even' : 'odd'}">
				<td class="matrix-first-column">${c.text }</td>
				<g:each in="${ 1..q.matrixRange }" var="col" status="colNum">
					<td class="matrix-column">
						<g:radio name="questions.${q.questionId}.${col}" value="on" checked="${a?.rows?.contains { (it.choice == c) && (it.value == col)} }"/>
					</td>
				</g:each>
				<g:if test="${q.hasNa }">
					<td class="matrix-column">
						<g:radio name="questions.${q.questionId }.0" value="on" checked="${a?.rows?.contains { (it.choice == c) && (it.value == 0)} }"/>
					</td>
				</g:if>
			</tr>
			</g:each>
		</tbody>
	</table>
</div>		