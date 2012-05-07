package surveymgr.questions

import static org.junit.Assert.*;
import grails.test.mixin.*
import org.junit.*
import surveymgr.*

@TestFor(Question)
@Mock([Choice, Survey])
class QuestionTests {

	/**
	 *  Tests the basic save and delete functionality
	 */
	void testSaves() {
		def s = new Survey(name: "S1").save(failOnError: true)
		
		def q1 = new Question(survey: s, questionId: "A1", type:"single", text: "What is your favourite sport?", choices: [new Choice(text:"Hockey"), new Choice(text:"Soccer")]).save(failOnError: true, flush: true)
		assert Question.count() == 1
		assert Choice.count() == 2
		
		def q2 = new Question(survey: s, questionId: "A2", type: "yesno", text: "Do you consider yourself to be atheletic?").save(flush: true)
		assert Question.count() == 2
		
		def q3 = new Question(survey: s, questionId: "A3", type: "multi", text: "Which sports have you played?", control: "list", choices: [new Choice(text:"Polo"), new Choice(text:"Lacrosse")]).save(flush: true)
		assert Question.count() == 3
		assert Choice.count() == 4
		
		assert Question.findByQuestionId("A1").questionId == "A1"
		assert Question.list().size() == 3
		
		assert Question.list(sort: "questionId", order: "desc")[0].questionId == "A3"
		
		q3.delete(flush: true, failOnError: true)
		assert Question.count() == 2
	}
	
	/**
	 *  Test the constraint that stops two questions in the same survey from having the same IDs
	 */
	void testMultiSurveySaves() {
		def s1 = new Survey(name: "S1").save(failOnError: true)
		def s2 = new Survey(name: "S2").save(failOnError: true)
		
		def q1 = new Question(survey: s1, questionId: "A1", type: "text", text: "X").save(failOnError: true, flush: true)
		def q2 = new Question(survey: s2, questionId: "A1", type: "text", text: "X").save(failOnError: true, flush: true)
		
		assert Survey.count() == 2
		assert Question.count() == 2
		
		def q3 = new Question(survey: s1, questionId: "A1", type: "text", text: "X")
		assert !q3.validate()
		assert q3.errors["questionId"] != null
		assert q3.errors["survey"] == null
		
		q3.questionId = "A2"
		assert q3.validate()
	}
	
	
}
