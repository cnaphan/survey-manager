
class AdminFilters {

	def filters = {
	
		 loggedInOnly(controller:"*", action:"*", controllerExclude:"public|login") {
			before = {
				if (!session.user) {
					log.info("Attempt to reach ${request.forwardURI} without user. Redirecting to login")
					flash.message = "Please login before continuing"
					redirect(controller:"login", action:"login", params: [origUrl:request.forwardURI-request.contextPath])
					return false
				}
			}
		}
		
		mustBeAdmin(controller: "user") {
			before = {
				if (!session.user || session.user.group < surveymgr.User.Group.ADMIN) {
					flash.error = "You must be an administrator to access that page"
					redirect(controller:"login", action:"login", params: [origUrl:request.forwardURI-request.contextPath])
					return false
				}
			}
		}
		 
		mustHaveSurvey(controller: "question|admin|respondent|operator") {
			before = {
				if (!session.survey) {
					log.info("Attempt to reach ${request.forwardURI} without survey. Redirecting to survey/pick")
					flash.message = "You must pick a survey before continuing to that page"					
					redirect(controller: "home", action:"pickSurvey", params: [origUrl:request.forwardURI-request.contextPath])
					return false
				}
			}
		}
		
		mustHaveRespondent(controller: "public", action: "take|save") {
			before = {
				if (!session.respondent) {
					// If the requester is not associated with a respondent and tries to access the public pages, redirect to an appropriate page
					if (session.user) {
						if (session.survey) {
							// If the requester is a logged-in user working on a survey, redirect to the "Select Respondent" page
							log.info("User had no respondent and tried to reach ${actionName}, so redirecting to pickRespondent")
							redirect(controller: "operator", action:"pickRespondent")
						} else {
							log.info("Attempt to reach ${request.forwardURI} without survey. Redirecting to survey/pick")
							flash.message = "You must pick a survey before continuing to that page"					
							redirect(controller: "home", action:"pickSurvey", params: [origUrl:request.forwardURI-request.contextPath])
						}
						return false
					} else {
						// If the requester is not loggged in, redirect to the "Enter Survey Key" page
						log.info("Public requester had no respondent and tried to reach ${actionName}, so redirecting to enterKey")
						redirect(controller: "public", action:"enterKey", params: [origUrl:request.forwardURI-request.contextPath])
						return false
					}
				}
			}
		}
	}

}
