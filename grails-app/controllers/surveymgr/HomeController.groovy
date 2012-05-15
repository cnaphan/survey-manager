package surveymgr

import surveymgr.questions.*

/**
 * A controller for actions that require login but don't work on any particular area, like the home dashboard.
 */
class HomeController {
 	 
	def index() {
		
		def allSurveys = Survey.findAllByStateNot(Survey.State.INACTIVE, [sort:"name", order:"asc"])
		
		def mySurveys = allSurveys.findAll { s -> s.owner == session.user || s.operators.contains(session.user) || session.user.group == User.Group.ADMIN }
		for (def s : allSurveys) {
			if (true) {
				
				
			}
		}
		[mySurveys: mySurveys]
	}
	
	def pickSurvey() {
	 	 def surveys
	 	 if (!params.includeInactive?.equals("true")) {
	 	 	 surveys = Survey.findAllByStateInList([Survey.State.DORMANT, Survey.State.ACTIVE], [sort: "createdDate", order: "desc"])
	 	 } else {
	 	 	 surveys = Survey.list(sort: "createdDate", order: "desc")
	 	 }
		 [surveyInstanceList: surveys]
	 }
	 
	 def setSurvey() {
		 if (!params.surveyId) {
			 log.warn("Tried to pick survey but no ID was passed")
			 flash.messages =  [["warn", "You must pick a survey"]]
			 redirect(action: "pickSurvey")
			 return;			 
		 }
		 if (session.survey && session.survey.id.toString().equals(params.surveyId)) {
			 log.info("User picked the same survey as before")
			 if (params.origUrl) {
				 redirect(uri: params.origUrl)
			 } else {
				 redirect(controller: "admin", action: "dashboard")
			 }
			 return;
		 }
		 def survey = Survey.get(params.surveyId)
		 if (!survey) {
			 log.warn("Tried to pick survey ${params.surveyId} but could not find it")
			 flash.messages = [["warn", "Could not find survey with ID=${params.surveyId}"]]
			 redirect(action: "pickSurvey")
		 } else {
		 	log.info("User ${session.user.name} picked survey ${survey.name}")
			flash.message = "Now using survey '${survey.name}'"
		 	session.survey = survey
			// Tap the associations to ensure they don't need to be lazily initialized later on
			if (params.origUrl) {
				redirect(uri: params.origUrl)
			} else {
				redirect(controller: "admin", action: "dashboard")
			}			 
		 }
	 }

	 
	def testTheme() {
		def theme = params.id		
		if (!theme) {
			flash.error = "Cannot load theme test without theme"
			redirect(uri:"/")
			return
		}
		
		def q1 = new Question(questionId: "A1", text: "What is your favourite sport?", type: Question.Type.SINGLE, sortOrder: 1)
			.addToChoices(new Choice(text: "Football"))
			.addToChoices(new Choice(text: "Soccer"))
		q1.sectionHeaderText = "<h1>Welcome to the Survey</h1>Please take your time. There are 100 questions. You have 5 minutes."
		q1.hasOther = true
		
		def q2 = new Question(questionId: "A2", text: "What is your favourite sport?", type: Question.Type.SINGLE, control: Question.Control.SELECT, sortOrder: 2)
			.addToChoices(new Choice(text: "Football", sortOrder: 1))
			.addToChoices(new Choice(text: "Soccer", sortOrder: 2))

		q2.hasOther = true
		q2.hasOtherText = "If not those, then what?"
		def q3 = new Question(questionId: "A3", text: "What is your name?", type: Question.Type.TEXT, sortOrder: 3)
		q3.width = "20em"
		def q4 = new Question(questionId: "A4", text: "Describe yourself", type: Question.Type.TEXT, sortOrder: 4)
		q4.width = "30em"
		q4.height="10em"
		def q5 = new Question(questionId: "A5", text: "Are you bored yet??", type:Question.Type.YESNO, control: Question.Control.RADIO, sortOrder: 5)
		
		def q6 = new Question(questionId: "A6", text: "Which sports have you played?", type:Question.Type.MULTI, sortOrder: 6)
			.addToChoices(new Choice(text: "Football", sortOrder: 1))
			.addToChoices(new Choice(text: "Soccer", sortOrder: 2))
		q6.hasOther = true
		
		def q7 = new Question(questionId: "A7", text: "What did you think of your experience here?", type: Question.Type.MATRIX, sortOrder: 7)
			.addToChoices(new Choice(text: "Cleanliness")).addToChoices(new Choice(text: "Price", sortOrder: 1))
		q7.matrixRange = 5
		q7.rangeLowText = "Very dissatisfied"
		q7.rangeHighText = "Very satisfied"
		q7.hasNa = true
		
		def questions = [q1,q2,q3,q4,q5,q6,q7]

		[questions: questions, answers: [:], theme: theme]
	}
	 
}
