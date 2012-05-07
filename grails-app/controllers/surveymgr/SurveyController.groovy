package surveymgr

import org.springframework.dao.DataIntegrityViolationException

class SurveyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	 static scaffold = true
	 
	 def pick() {
	 	 def surveys
	 	 if (!params.includeInactive?.equals("true")) {
	 	 	 surveys = Survey.findAllByStateInList([Survey.State.DORMANT, Survey.State.ACTIVE], [sort: "createdDate", order: "desc"])
	 	 } else {
	 	 	 surveys = Survey.list(sort: "createdDate", order: "desc")
	 	 }
		 [surveyInstanceList: surveys]
	 }
	 
	 def set() {
		 if (!params.surveyId) {
			 log.warn("Tried to pick survey but no ID was passed")
			 flash.messages =  [["warn", "You must pick a survey"]]
			 redirect(action: "pick")
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
			 redirect(action: "pick")
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
	
/*
    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [surveyInstanceList: Survey.list(params), surveyInstanceTotal: Survey.count()]
    }

    def create() {
        [surveyInstance: new Survey(params)]
    }

    def save() {
        def surveyInstance = new Survey(params)
        if (!surveyInstance.save(flush: true)) {
            render(view: "create", model: [surveyInstance: surveyInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'survey.label', default: 'Survey'), surveyInstance.id])
        redirect(action: "show", id: surveyInstance.id)
    }

    def show() {
        def surveyInstance = Survey.get(params.id)
        if (!surveyInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'survey.label', default: 'Survey'), params.id])
            redirect(action: "list")
            return
        }

        [surveyInstance: surveyInstance]
    }
*/
    def edit() {
		if (!params.id && session.survey) {
			log.debug("No survey ID passed so using session survey ID")
			params.id = session.survey.id
		}
		
        def surveyInstance = Survey.get(params.id)
		
        if (!surveyInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'survey.label', default: 'Survey'), params.id])
            redirect(action: "list")
            return
        }

        [surveyInstance: surveyInstance, operatorInstanceList: User.findAllByIdNot(surveyInstance.owner.id, [sort: "name", order: "asc"])]
    }
/*
    def update() {
        def surveyInstance = Survey.get(params.id)
        if (!surveyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'survey.label', default: 'Survey'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (surveyInstance.version > version) {
                surveyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'survey.label', default: 'Survey')] as Object[],
                          "Another user has updated this Survey while you were editing")
                render(view: "edit", model: [surveyInstance: surveyInstance])
                return
            }
        }

        surveyInstance.properties = params

        if (!surveyInstance.save(flush: true)) {
            render(view: "edit", model: [surveyInstance: surveyInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'survey.label', default: 'Survey'), surveyInstance.id])
        redirect(action: "show", id: surveyInstance.id)
    }

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
}
