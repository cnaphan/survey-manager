package surveymgr

import surveymgr.questions.*

class OperatorController {

	static allowedMethods = [pickRespondent: "GET", selectRespondent: "POST", quit: "GET"]
    
    
    /**
     * Sets up the page for selecting a respondent. Used by operators to enter survey data for people.
     */
    def pickRespondent() {
    	assert session.survey
    	assert session.user
    	def s = session.survey    	
    	params.max = 20
    	def respondents = Respondent.findAllBySurvey(s, params)
    	def respondentTotal = Respondent.countBySurvey(s)
    	if (log.isInfoEnabled()) { log.info("User ${session.user.name} selecting respondent for survey ${s.name} out of ${respondentTotal} respondents") } 
    	render(view: "/public/pickRespondent", model: [respondents: respondents, respondentTotal: respondentTotal])
    }
    
     /**
     * Selects a survey
     */
   def selectRespondent() {
    	assert session.survey
    	assert session.user
    	def s = session.survey
    	def r = Respondent.findBySurvey(s)
    	
    	if (!r) {
    		log.warn("User ${session.user.name} attempted to select a respondent that did not exist. Page might be stale.")
    		flash.error = "Respondent no longer exists"
    		redirect(action: "pickRespondent")
    	} else {
    		session.respondent = r
    		if (log.isInfoEnabled()) { log.info("User ${session.user.name} selected respondent #${r.id} for survey '${s.name}'") }
    		redirect(uri: "/~${SecurityUtils.encodeAsUrlFriendly(s.name)}")
    	}
    }
    
    /**
     * Stops impersonating a respondent and goes back to the dashboard
     */
    def quit() {
    	session.respondent = null
    	redirect(controller: "admin", action: "dashboard")
    }
}
