package surveymgr.questions

class Choice {
	
	String text
	Integer sortOrder = 0
	
	static belongsTo = [question: Question]
	//static hasMany = [answers: Answer]

    static constraints = {
		text(size: 1..250)
    }
	
	String toString() { text }
}
