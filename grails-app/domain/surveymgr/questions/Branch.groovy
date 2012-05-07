package surveymgr.questions

class Branch {
	
	String ifValue
	String gotoQuestionId
	
	static belongsTo = [question: Question]

    static constraints = {
		ifValue(maxSize: 50)
		gotoQuestionId(maxSize: 20)
    }
}
