import surveymgr.*;
import surveymgr.questions.*

class BootStrap {

    def init = { servletContext ->
		
		if (!User.count()) {
			new User(name: "nikki", password: "stats", group: User.Group.ADMIN).save(failOnError: true)
			new User(name: "kelly", password: "stats", group: User.Group.MANAGER).save(failOnError: true)
			new User(name: "joe", password: "stats", group: User.Group.OPERATOR).save(failOnError: true, flush: true)
		}
		if (!Survey.count()) {
			def s = new Survey(name: "Employee Satisfaction 2012", state: Survey.State.ACTIVE, owner: User.findByName("nikki"), expiryDate: new GregorianCalendar(2012,12,25).time)
			s.hasPublicView = true
			s.questionsPerPage = 5
			s.hasTelephoneMode = true
			s.addToHistory(new SurveyHistory(user: s.owner, title: "Survey Created", text: "Survey '${s.name}' was created"))
			s.save(failOnError: true)
			
			def s2 = new Survey(name: "HR Survey 2012", owner: User.findByName("kelly"))
			s2.addToHistory(new SurveyHistory(user: s2.owner, title: "Survey Created", text: "Survey '${s2.name}' was created"))
			s2.save(failOnError: true)
			
			def q1 = new Question(survey: s, questionId: "A1", text: "What is your favourite sport?", type: Question.Type.SINGLE, sortOrder: 1)
				.addToChoices(new Choice(text: "Football"))
				.addToChoices(new Choice(text: "Soccer"))
			q1.sectionHeaderText = "<h1>Welcome to the Survey</h1>Please take your time. There are 100 questions. You have 5 minutes."
			q1.hasOther = true
			
			def q2 = new Question(survey: s, questionId: "A2", text: "What is your favourite sport?", type: Question.Type.SINGLE, control: Question.Control.SELECT, sortOrder: 2)
				.addToChoices(new Choice(text: "Football", sortOrder: 1))
				.addToChoices(new Choice(text: "Soccer", sortOrder: 2))

			q2.hasOther = true
			q2.hasOtherText = "If not those, then what?"
			def q3 = new Question(survey: s, questionId: "A3", text: "What is your name?", type: Question.Type.TEXT, sortOrder: 3)
			q3.width = "20em"
			def q4 = new Question(survey: s, questionId: "A4", text: "Describe yourself", type: Question.Type.TEXT, sortOrder: 4)
			q4.width = "30em"
			q4.height="10em"
			def q5 = new Question(survey: s, questionId: "A5", text: "Are you bored yet??", type:Question.Type.YESNO, control: Question.Control.RADIO, sortOrder: 5)
			
			def q6 = new Question(survey: s, questionId: "A6", text: "Which sports have you played?", type:Question.Type.MULTI, sortOrder: 6)
				.addToChoices(new Choice(text: "Football", sortOrder: 1))
				.addToChoices(new Choice(text: "Soccer", sortOrder: 2))
			q6.hasOther = true
			
			def q7 = new Question(survey: s, questionId: "A7", text: "What did you think of your experience here?", type: Question.Type.MATRIX, sortOrder: 7)
				.addToChoices(new Choice(text: "Cleanliness")).addToChoices(new Choice(text: "Price", sortOrder: 1))
			q7.matrixRange = 5
			q7.rangeLowText = "Very dissatisfied"
			q7.rangeHighText = "Very satisfied"
			q7.hasNa = true
			
			[q1,q2,q3,q4,q5,q6,q7]*.save(failOnError: true, flush: true)
			
			// create respondents
			def r1 = new Respondent(name: "Curtis Naphan", email: "cnaphan@gmail.com", surveyKey: "A123456", survey: s).save(failOnError: true, flush: true)
			def r2 = new Respondent(name: "Michael Naphan", email: "mnaphan@gmail.com", surveyKey: "A123456", survey: s).save(failOnError: true, flush: true)
			[
				new Answer(respondent: r1, question: q1).addToChoices(q1.choices.toArray()[1]),
				new Answer(respondent: r1, question: q2).addToChoices(q2.choices.toArray()[0]),
				new Answer(respondent: r1, question: q3, textValue: "Curtis Naphan"),
				new Answer(respondent: r1, question: q4, textValue: "I could tell you many things, young lady, but you may not like what you hear."),
				new Answer(respondent: r1, question: q5, textValue: "yes"),
				new Answer(respondent: r1, question: q6, textValue: "Darts").addToChoices(q6.choices.toArray()[0])
			]*.save(failOnError: true, flush: true)
			
		}	
    }
    def destroy = {
    }
}
