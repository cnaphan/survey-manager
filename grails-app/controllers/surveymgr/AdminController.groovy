package surveymgr

class AdminController {

    def index() { }
	
	def dashboard() {
		def s = Survey.get(session.survey.id)
		def statistics = [RespondentCount: Respondent.countBySurvey(s)]
		
		Respondent.State.values().each {
			statistics[it.toString() + "Count"] = Respondent.countBySurveyAndState(s, it)
			if (statistics.RespondentCount > 0) { 
				statistics[it.toString() + "Percent"] = (Double) Math.round(100 * ((Double) statistics[it.toString() + "Count"] / (Double)statistics.RespondentCount))
			} else {
				statistics[it.toString() + "Percent"] = 0
			}
		}
		if (statistics.RespondentCount == 0) { statistics.UnselectedPercent = 100 }
		[s:s, statistics: statistics]
	}
	
	def edit () {
		[surveyInstance: Survey.get(session.survey.id)]
	}
		
	
	def permissions() {
		def survey = Survey.get(session.survey.id)
		[s: survey, userInstanceList: User.findAllByIdNotAndActive(survey.owner.id, true, [sort: "name", order: "asc,"])]
	}
	
	def savePermissions() {
		log.debug("Starting saveOperators: params=${params}")
		def s = Survey.get(session.survey.id) // Get a fresh copy of the survey
		def surveyOperatorIds = s.operators.collect{ it.id }
		Integer changesMade = 0
		flash.messages = []
		params.operatorIds.each { operatorId -> // Walk through the operators
			if (params.operators.contains(operatorId) && !surveyOperatorIds.contains(Long.parseLong(operatorId))) { // This operator is selected and it's not in the survey
				def u = User.get(operatorId)
				s.addToOperators(u)
				flash.messages << ["info", "${u.name} has been granted permission"]
				changesMade++
			} else if (!params.operators.contains(operatorId) && surveyOperatorIds.contains(Long.parseLong(operatorId))) { // This operator is not selected and the survey has it
				def u = User.get(operatorId)
				s.removeFromOperators(u)
				flash.messages << ["info", "${u.name}'s permission has been revoked"]
				changesMade++
			}
		}
		if (!s.save()) {
			flash.messages = null
			flash.error = "Could not save. Survey permissions were not updated."
			log.error("An error occurred while saving survey permissions")
			redirect(action: "permissions")
			return;
		}
		if (changesMade) {
			log.info("Permissions updated with ${changesMade} changes")
		} else {
			log.debug("Permissions updated with no changes")
		}
		redirect(action:"dashboard")
	}
	
}
