package surveymgr

import surveymgr.questions.*
import org.apache.log4j.Logger

class QuestionHelper {
	
	private final static Logger log = Logger.getLogger(getClass())

	public static Map getPagedQuestions(List allQuestions, Integer pageNum, Integer questionsPerPage) {
		Integer currentPage = 1
		Integer questionsConsideredThisPage = 0
		def questions = [] // Start a new list of questions that will be shown
		for (def q : allQuestions) {
			// Check for new page conditions
			if ((questionsConsideredThisPage >= questionsPerPage)  ||
				(questions.size() > 0 & q.forceBreak)) {
				// If this isn't the first question on this page and this question forces a page break, increment the page counter
				currentPage = currentPage + 1 // Increment the page number
				questionsConsideredThisPage = 0 // Reset the # of questions considered this page
				if (log.isDebugEnabled()) { log.debug("Breaking page. Going to page #${currentPage}. Looking for page #${pageNum}.") }
			}
			if (log.isDebugEnabled()) { log.debug("Considering question ${q.questionId} for page #${currentPage}") }
			if (currentPage == pageNum) {
				// If we're on the page we want, add the question to the current list
				questions << q
				if (log.isDebugEnabled()) { log.debug("Adding question ${q.questionId} as question #${questions.size()} of the question list") }
			}
			questionsConsideredThisPage++			
		}
		return [questions: questions, totalPages: currentPage]
	}

}
