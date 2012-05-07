package surveymgr

import surveymgr.questions.Answer

/**
 * Represents a member of the public who can respond to a survey
 * @author Nikki
 * Bug Note: If you try to initialize a new object with a state (an enum), it will not be set. Seems to be a Grails bug.
 */
class Respondent {
	
	public enum State {
		UNSELECTED("Unselected"), UNCONTACTED("Uncontacted"), UNSTARTED("Unstarted"), STARTED("Started"), COMPLETED("Completed")
		State(String value) { this.value = value }
		private final String value
		public String toString() { this.value }
	}
	
	String name
	String email
	String telephoneNumber
	String surveyKey
	Integer emailAttempts = 0
	State state = State.UNSELECTED
	Community community

	static belongsTo = [survey: Survey]
	
	static hasMany = [answers: Answer]

    static constraints = {
    	name(nullable: true, maxSize: 150, blank: false)
		email(nullable: true, email: true, maxSize: 100, blank: false)
		telephoneNumber(nullable: true, maxSize: 30, blank: false)
		surveyKey(nullable: true, maxSize: 30, blank: false)
		community(nullable: true)
    }
}

