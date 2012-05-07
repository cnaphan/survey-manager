import surveymgr.*

class SessionTagLib {
	
	/**
	 * Displays a variety of different messages from flash.message, flash.error, flash.messages and request.errors
	 */
	def messages = {
		out << render(template: "/utils/messages")
		flash.message = null
		flash.messages = null
		flash.error = null
	}
	
	def isLoggedIn = { attrs, body ->
		if (session?.user) {
			out << body()
		}
	}
	
	/**
	 * Tests whether the current user is an admin or greater
	 */
	def isAdmin = { attrs, body ->
		if (session.user?.group >= User.Group.ADMIN) {
			out << body()
		}
	}
	
	/**
	 * Tests whether the current user is a manager or greater
	 **/
	def isManager = { attrs, body ->
		if (session.user?.group >= User.Group.MANAGER) {
			out << body()
		}
	}
	
	/**
	 * Tests whether the current user is the owner of the currently selected survey. In non-strict (default) mode, allows admins to see the content.
	 * @attr strict Survey owners and not admins can see the content
	 **/
	def isOwner = { attrs, body ->
		if ((session.user?.group >= User.Group.MANAGER && session.survey?.owner == session.user) || 
			(attrs.strict?.lower()?.equals("true") && session.user?.group >= User.Group.ADMIN)) {
			out << body()
		}
	}
}

