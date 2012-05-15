package surveymgr

import org.springframework.dao.DataIntegrityViolationException

class SurveyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "GET"]
        
    def create() {
    	def s = new Survey(params)
    	if (!checkPermissions(s)) { return }
    	s.owner = session.user
        [surveyInstance: s]
    }	
    
    def save() {
        def s = new Survey(params)
    	if (!checkPermissions(s)) { return }
    	s.owner = session.user
    	s.properties = params
        if (!s.save(flush: true)) {
            render(view: "create", model: [surveyInstance: s])
            return
        }

		flash.message = "Survey '${s.name}' has been created"
		session.survey = s
        redirect(controller: "admin", action: "dashboard")
    }

	def edit () {
		def s = Survey.get(params.id)
    	if (!checkPermissions(s)) { return }
		[surveyInstance: s, origUrl: params.origUrl]
	}
	
    def update() {
        def s = Survey.get(params.id)
    	if (!checkPermissions(s)) { return }
        
        if (params.version) {
            def version = params.version.toLong()
            if (s.version > version) {
                s.errors.rejectValue("version", "default.optimistic.locking.failure",
                          ["Survey"] as Object[],
                          "Another user has updated the survey while you were editing")
                render(view: "edit", model: [surveyInstance: s])
                return
            }
        }

        s.properties = params
 		s.addToHistory(new SurveyHistory(user: s.owner, title: "Survey Modified", text: "Survey was modified"))
       

        if (!s.save(flush: true)) {
            render(view: "edit", model: [surveyInstance: s])
            return
        }
        
		flash.message = "The survey '${s.name}' has been modified"
        session.survey = s
        
        if (s.equals(session.survey)) {
        	redirect(controller: "admin", action: "dashboard")	
        } else {
        	redirect(uri: "/")
        }        
    }
    

    
    
   /*
    def delete() {
        def surveyInstance = Survey.get(params.id)
        if (!surveyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'survey.label', default: 'Survey'), params.id])
            redirect(action: "list")
            return
        }

        try {
            surveyInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'survey.label', default: 'Survey'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'survey.label', default: 'Survey'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
    
    */
    
    /**
     * Ensures the user has sufficient privileges to work with the survey. Works for both new and existing surveys.
     */
    private boolean checkPermissions(Survey s) {
    	if (!s) {
			flash.error = message(code: 'default.not.found.message', args: ["Survey", params.id])
			log.warn("Attempt to access survey #${params.id} which did not exists")
		} else if (s.id && session.user != s.owner && session.user.group < User.Group.ADMIN) {
			// Ensure the user has sufficient privilege to edit the survey
			flash.error = message(code: 'default.insufficient.authority.message')
			log.warn("Attempt to edit survey ${s.name} by user ${session.user.name} without sufficient authority")
		} else if (!s.id && session.user.group < User.Group.MANAGER) {
			// Ensure user has sufficinet privilege to create the survey
			flash.error = message(code: 'default.insufficient.authority.message')
			log.warn("Attempt to create survey by user ${session.user.name} without sufficient authority")			
		} else if (s.state >= Survey.State.ACTIVE) {
			// Ensure the survey is in the dormant state
			flash.error = "Cannot modify non-dormant survey"
			log.warn("Attempt to modify non-dormant survey ${s.name} by user ${session.user.name}")
		} else {
			return true
		}
        if (s.equals(session.survey)) {
        	redirect(controller: "admin", action: "dashboard")	
        } else {
        	redirect(uri: "/")
        }        
		return false
    }
}
