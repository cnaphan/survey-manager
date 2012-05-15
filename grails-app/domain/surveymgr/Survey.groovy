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
	String theme
	
	boolean hasPublicView = false
	boolean hasTelephoneMode = false
	Integer questionsPerPage = 4
	boolean showPages = false
	
	
	static hasMany = [respondents: Respondent, 
					  questions: Question,
					  operators: User,
					  history: SurveyHistory]
	static mappedBy = [operators: "assignedSurveys", owner: "ownedSurveys"]
	
	static mapping = {
		operators joinTable: "operator_survey"
	}
	
    static constraints = {
		name(maxSize: 50)
		description(maxSize: 500, nullable: true)
		createdDate(editable: false, format: 'yyyy-MM-dd')
		expiryDate(nullable: true, , format: 'yyyy-MM-dd')
		theme (maxSize: 25, nullable: true)
		
		questionsPerPage(nullable: true, min: 1, max: 99)
    }
	
}

