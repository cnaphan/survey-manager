package surveymgr

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
	
}
