package surveymgr

import surveymgr.questions.*

class QuestionController {
	
	def list() {
		def s = Survey.get(session.survey.id) // Get a fresh copy of the survey		
		def questions = Question.findAllBySurvey(s, [sort: "sortOrder", order: "asc"])
		def pagedQuestionResults = QuestionHelper.getPagedQuestions(questions, 1, s.questionsPerPage)
		[s:s, questions: questions, totalPages: pagedQuestionResults.totalPages]		
	}
	
	def reorder() {
		def id = params.id
		if (!id) {
			render(status: 500)
		}
		def dir = (params.dir == "down") ? 1 : -1;
		
		def s = Survey.get(session.survey.id) // Get a fresh copy of the survey		
		def q = Question.get(id.toLong())
		def oldSortOrder = q.sortOrder
		def newSortOrder = oldSortOrder + dir
		
		if (newSortOrder < 1 || newSortOrder > s.questions.size()) {
			log.warn("Tried to reorder when new sort order is out of range")
			flash.error = "Could not reorder. New order was out of range."
			redirect(action: "list")
			return			
		}
		
		def q2 = Question.findBySurveyAndSortOrder(s, newSortOrder)
		if (!q2) {
			log.warn("Tried to reorder and could not find adjacent question at #${newSortOrder}")
			flash.error = "Could not reorder. Can't find adjacent question at #${newSortOrder}"
			redirect(action: "list")
			return			
		}
		
		q.sortOrder = newSortOrder
		q2.sortOrder = oldSortOrder
		
		if (!q.save() || !q2.save()) {
			flash.error = "Could not reorder. There was an error while saving."
		} 
		
		redirect(action: "list")		
	}
	
	def edit () {
		def q
		if (!params.id) {
			q = new Question(params)
			
			def s = Survey.get(session.survey.id) // Get a fresh copy of the survey		
			def lastQuestion = Question.findBySurvey(s, [sort:"sortOrder", order:"desc", max:1])			
			q.sortOrder = lastQuestion.sortOrder + 1
			
		} else {
			q = Question.get(params.id.toLong())
		}		
		[q:q]		
	}
	
	def save () {
		Long id = params.id ? params.id.toLong() : null;
		boolean andNew = params.andNew?.equals("true") // Whether to move immediately to a new question on success
		render(text: params)
	}
}
