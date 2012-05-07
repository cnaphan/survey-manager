package surveymgr

import surveymgr.questions.*

import org.junit.*
import grails.test.mixin.*

@TestFor(SurveyController)
@Mock([Survey, User, Question, Respondent])
class SurveyControllerTests {
	
	void testQueries() {
		def u1 = new User(name: "U1", password: "pass", group: User.Group.MANAGER).save(failOnError: true)
		def s = new Survey(name: "S1", owner: u1).save(failOnError: true)
		assert Survey.count() == 1
		
		new Respondent(email: "suzy@gmail.com", survey: s).save(failOnError: true)
		new Respondent(email: "bob@gmail.com", survey: s).save(failOnError: true)
		new Respondent(email: "george@gmail.com", survey: s).save(failOnError: true)
		
		new Question(survey: s, questionId: "Q1", text: "What is your name?", type: Question.Type.TEXT).save(failOnError: true)
		new Question(survey: s, questionId: "Q2", text: "Are you happy?", type: Question.Type.YESNO).save(failOnError: true)

		s = Survey.get(1)
		assert s != null
		assert s.questions?.size() == 2
		assert s.respondents?.size() == 3
		
		def r = Respondent.get(3)
		r.state = Respondent.State.UNSTARTED
		r.save(failOnError: true)
		
		assert s.respondents.find { it.email.startsWith("george") }.state == Respondent.State.UNSTARTED
		assert Respondent.countBySurveyAndState(s, Respondent.State.UNSELECTED) == 2
		
		
		
	 }

}
