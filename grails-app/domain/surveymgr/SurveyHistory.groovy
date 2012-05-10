package surveymgr

class SurveyHistory {

	static belongsTo = [survey: Survey]
	
	Date dateCreated
	User user
	String title
	String text
	
	static constraints = {
	  	user(nullable: true)
		title(blank:false, maxSize: 30)
		text(blank:true, maxSize: 500)
    }

}
