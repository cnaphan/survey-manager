package surveymgr

import surveymgr.User;

class LoginController {

	static allowedMethods = [login: "GET", authenticate: "POST"]
	
	def login() {
		log.info("Loading login screen from ${params.origUrl}")
		[userInstance: new User(params), origUrl: params.origUrl]
	}

	def logout() {
		flash.message = "Goodbye, ${session?.user?.name}!"
		session.user = null
		session.respondent = null
		session.survey = null
		redirect(uri: "/")
	}

	def authenticate() {
		if (!params.name) {
			flash.error = "Please enter a user name."
			redirect(action: "login")
			return
		}
		assert User.count() > 0
		if (log.isDebugEnabled()) { log.debug("Authenticating ${params.name} with orig. URL = '${params.origUrl ? params.Url : 'none'}") }
		def user = User.findByNameAndPasswordAndActive(params.name, SecurityUtils.encodeAsSHA(params.password),true)
		if (user) {
			session.user = user
			log.info("Logged in as ${user.name}")
			flash.message = "Welcome, ${user.name}!"
			if (params.origUrl) {
				redirect(uri: params.origUrl)
			} else {
				redirect(controller: "survey", action: "pick")
			}
		} else {
			log.info("Attempt to log in as ${params.name} failed")
			flash.error = "Sorry, ${params.name}. Please try again."
			redirect(action:"login")
		}
	}
	
}
