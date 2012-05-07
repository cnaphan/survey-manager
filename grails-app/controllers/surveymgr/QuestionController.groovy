package surveymgr

import surveymgr.questions.*

class QuestionController {

	def index() {
		def r = Respondent.get(1)
		def q = Question.list()
		def answerList = [new Answer(respondent: r, question: q[0]).addToChoices(q[0].choices.toArray()[1]),
							new Answer(respondent: r, question: q[1]).addToChoices(q[1].choices.toArray()[0]),
							new Answer(respondent: r, question: q[2], textValue: "Curtis Naphan"),
							new Answer(respondent: r, question: q[3], textValue: "I could tell you many things, young lady, but you may not like what you hear."),
							new Answer(respondent: r, question: q[4], textValue: "yes"),
							new Answer(respondent: r, question: q[5], choices: [q[5].choices], textValue: "Darts")]
		def answers = answerList.collectEntries { a -> [a.question.questionId, a] }		
		[questions: q, answers: answers]	
	}
	
	def showParams() {
		render(text: params.questions.sort { a,b -> a.key.compareTo(b.key) }.toString())
	}
}
