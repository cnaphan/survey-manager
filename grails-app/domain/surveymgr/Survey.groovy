package surveymgr

import surveymgr.questions.*

class Survey {
	
	public enum State {
		DORMANT ("Dormant"), ACTIVE("Active"), INACTIVE("Inactive")
		State(String value) { this.value = value }
		private final String value
		public String toString() { this.value }
	}

	String name
	String description
	User owner
	Date createdDate = new Date()
	Date expiryDate
	State state = State.DORMANT
	Attributes attributes = new Attributes() // Additional properties that are rarely used	
	
	static hasMany = [respondents: Respondent, questions: Question, operators: User]
	static mappedBy = [operators: "assignedSurveys", owner: "ownedSurveys"]
	
	static mapping = {
		operators joinTable: "operator_survey"
	}
	
    static constraints = {
		name(maxSize: 50)
		description(maxSize: 500, nullable: true)
		createdDate(editable: false, format: 'yyyy-MM-dd')
		expiryDate(nullable: true, , format: 'yyyy-MM-dd')
		owner(editable: false)
    }
	static embedded = ["attributes"]
}

public class Attributes {
	boolean hasPublicView = false
	boolean hasTelephoneMode = false
	Integer questionsPerPage = 4
	
	static constraints = {
		questionsPerPage(min: 0, max: 99)
	}
}

