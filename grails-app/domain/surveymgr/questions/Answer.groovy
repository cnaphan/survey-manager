package surveymgr.questions

import surveymgr.Respondent

/**
 * Represents an answer to a particular question for a particular respondent. Answers can be solitary or they can be linked
 * to collections of choices or matrix rows, when there are multiple answers within one answer. Answers can also have "other" text. When "other"
 * is non-null, it is assumed to be selected.
 * 
 * Note: There is a many-to-many relationship between answer and choice, but it is not modeled in reverse (i.e. there is no belongsTo or hasMany mapping
 * on the Choice side). This could lead to issues and it should be addressed by manually mapping the join table. (i.e. another domain class AnswerChoice)
 * 
 * @author Nikki
 *
 */
class Answer {

	// An answer is dependent on both the respondent and the question (i.e. if either is deleted, delete the answer, too)
	static belongsTo = [respondent : Respondent, question : Question]
	
	// Used for text and yes/no questions
	String textValue
	
	// Answers have relations with 0+ choices (singles, multis) and 0+ row answers (matrices)
	static hasMany = [choices : Choice, rows: MatrixRow]
	
    static constraints = {
		question (unique: "respondent")
		textValue (maxSize: 5000, nullable: true)
    }
}
