package surveymgr.questions

/**
 * Represents a row of an answer to a matrix-type question
 * @author Nikki
 *
 */
class MatrixRow {
	
	Integer value
	Choice choice
	
	static belongsTo = [answer: Answer]

    static constraints = {
    }
	
}
