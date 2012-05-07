package surveymgr.questions

class QuestionCondition {
	
	String ifVariable
	String ifOperation
	String ifValue
	String thenOperation
	String thenParameter1
	String thenParameter2
	
	static belongsTo = [question: Question]

    static constraints = {
		ifVariable(maxSize: 50)
		ifOperation(maxSize: 50)
		ifValue(maxSize: 50)
		thenOperation(maxSize: 50)
		thenParameter1(maxSize: 50, nullable: true)
		thenParameter2(maxSize: 50, nullable: true)
    }
}
