package surveymgr

import surveymgr.questions.*

/**
 * A controller that contains actions for administering a survey. Not necessarily changing the survey object but working with its state and other 
 * features.
 */
class AdminController {

    def index() {
    	redirect(action: "dashboard")
    }
	
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
	
	def changeState() {
		if (!params.to) {
			flash.error = "Must supply state to change to"
			log.warn ("Attempt to change state without supplied state for survey ${s.name} by user ${session.user.name}")
			redirect(action: "dashboard")
			return
		}
		
		def s = Survey.get(session.survey.id)
		def oldState = s.state
		def newState = Survey.State.values().find { it.toString().equals(params.to) }
		assert oldState instanceof Survey.State
		assert newState instanceof Survey.State
		
		if (!newState) {
			flash.error = "State '${params.to}' was not recognized"
			log.warn ("Attempt to change to invalid state ${params.to} for survey ${s.name} by user ${session.user.name}")
			redirect(action: "dashboard")
			return
		} else if (newState == oldState) {
			flash.message = "State was not modified"
			log.warn("Attempt to change to same state ${params.to} for survey ${s.name} by user ${session.user.name}")
			redirect(action: "dashboard")
			return
		}
		
		s.state = newState
		s.addToHistory(new SurveyHistory(user: s.owner, title: "State Changed", text: "State was changed from ${oldState.toString()} to ${newState.toString()}"))

		if (s.save(flush: true)) {				
			flash.message = "The state of survey '${s.name}' has been changed from ${oldState.toString()} to ${s.state.toString()}"			
		}
		redirect(action: "dashboard")
	}
	
	def viewHistory() {
		if (!params.sort) { params.sort = "dateCreated" }
		if (!params.order) { params.order = "desc" }
		if (!params.max) { params.max = 25 }
		[history: SurveyHistory.findAllBySurvey(session.survey, params), historyTotal: SurveyHistory.countBySurvey(session.survey)] 
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
	
	def themes() {
		def s = Survey.get(session.survey.id) // Get a fresh copy of the survey
	    def themes = []
	    
	    def themeFolder = new File(servletContext.getRealPath("/css/themes/"))
	    themeFolder.eachDir { file -> themes << file.name }
	    
	    if (!(s.theme in themes)) {
	    	log.warn("Survey ${s.name}'s theme set to unknown theme ${s.theme}. Setting to null.")
	    	s.theme = null
	    	s.save()
	    	flash.message = "Survey theme set to None."
	    }
	    
		[themes: themes, s:s]
	}
	
	def saveTheme() {
		def s = Survey.get(session.survey.id) // Get a fresh copy of the survey
		def theme = params.theme ? params.theme : null
		
		if (s.theme != theme) {			
			s.theme = theme
			s.addToHistory(new SurveyHistory(user: s.owner, title: "Theme Changed", text: "Theme was changed to '${theme}'"))
		} else {
			redirect(action:"dashboard")
		}
		
		if (s.save()) {
			log.info("Theme for survey ${s.name} changed to ${theme ?: "none" }")
			flash.message = "Theme changed to '${theme ?: "none" }'"
			redirect(action:"dashboard")
		} else {			
			redirect(action:"themes")
		}
	}
	
}
