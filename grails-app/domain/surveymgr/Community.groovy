package surveymgr

class Community {

	String name
	String region
	
	static hasMany = [respondents: Respondent]
	
    static constraints = {
		name (maxSize: 50)
		region(maxSize: 50, nullable: true)
    }
}
