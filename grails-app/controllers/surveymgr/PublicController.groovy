package surveymgr

import surveymgr.questions.*

class PublicController {

	static allowedMethods = [take: "GET", selectKey: "GET", useKey: "POST"]
	
   
    def take() {
    	Integer pageNum
		try {
			pageNum = params.pageNum ? Integer.parseInt(params.pageNum) : 1
		} catch (java.lang.NumberFormatException e) {
			log.warn("Attempt to reach survey with invalid page number: ${params.pageNum}")
			render(status: 500)
			return
		}
		
		// If the given page number is out of bounsd, return an internal error (500)
		if (pageNum <= 0) {
			log.warn("Attempt to reach survey with page number in invalid range: ${pageNum}")
			render(status: 500)
			return			
		}
    	
		// Fetch the survey
		String surveyName = SecurityUtils.decodeAsUrlFriendly(params.surveyName)
		def s = Survey.findByName(surveyName)
		// Do error-checking on the survey before continuing
		if (s == null) {
			log.error("Attempt to reach survey with invalid name: '${surveyName}'")
			render(status: 404)
			return
		/*} else if (s.state < Survey.State.ACTIVE) {
			// This is not a legitimate case (survey should be unknown to the public at this point) so pretend the page doesn't exist
			log.warn("Attempt to reach dormant survey '${s.name}'. Responding with 404.")
			render(status: 404) // Return 404 Page Not Found
			return */
		} else if (s.state > Survey.State.ACTIVE || new Date().compareTo(s.expiryDate) >= 0) {
			// Attempt to reach an inactive or expired survey. 
			// This is a legitimate case (an old link) therefore it needs to be handled gracefully.
			log.warn("Attempt to reach non-active survey '${s.name}' with state ${s.state}")
			render(view: "inactive")
			return
		} else if (!s.hasPublicView) {
			log.warn("Attempt to reach survey '${s.name}' with no public view")
			render(status: 404) // Return 404 Page Not Found
			return
		}
		if (log.isDebugEnabled()) { log.debug("Fetched survey '${s.name}', page #${pageNum}") }
		
		// Prepare the questions for this page
		// Get all the questions for this survey
		def allQuestions = Question.createCriteria().list{
			eq("survey", s)
			order ("sortOrder", "asc")
			order ("questionId", "asc")			
		}
		if (log.isDebugEnabled()) { log.debug("Fetched ${allQuestions.size()} questions for survey ${s.name}") }
		
		def pagedQuestionResults = QuestionHelper.getPagedQuestions(allQuestions, pageNum, s.questionsPerPage)
		def questions = pagedQuestionResults.questions
		def totalPages = pagedQuestionResults.totalPages
		
		// If there are no questions on this page, return an error
		if (!questions) {
			log.warn("Attempt to reach page #${pageNum} of survey '${s.name}' which had no questions.")
			render(status: 404)
			return
		}
		
		def answers = [:]
		// Prepare the answers to these questions
		def r = Respondent.get(session.respondent.id)
		if (!r) {
			log.warn("Reached page #${pageNum} of survey '${s.name}' but could not get respondent. Likely cause is broken filter.")
			render (status: 500)
			return
		}
		
		if (log.isDebugEnabled()) { log.debug("Inspecting ${r.name}'s ${r.answers.size()} answers") }
		if (r.answers.size() > 0) {
			for (def q : questions) {
				for (def a: r.answers) {
					if (a.question.id == q.id) {
						answers[q.questionId] = a
						if (log.isDebugEnabled()) { log.debug("Found an answer for question ${q.questionId}. Adding to answers.") }		
						break
					}
				}
			}
		}

		if (log.isInfoEnabled()) { log.info("Using question list ${questions.collect{it.questionId}} and answer list ${answers.collect{it.value.question.questionId}}") }
		
    	[s:s, questions: questions, answers: answers, currentPage: pageNum, totalPages: totalPages]
    }
    
    def next() {
     	save(true)
    }
    
    def previous() {
    	if (Integer.parseInt(params.pageNum) <= 1) {
    		log.warn("Attempt on survey ${session.survey.name} for respondent #{session.respondent.id} to go previous on page 1")
    		render(status: 500)
    		return
    	}
    	save(false)
    }
    
    private void save(boolean goNext) {
     	log.info("Saving survey ${session.survey.name} for respondent #${session.respondent.id} and going to ${goNext ? 'next' : 'previous'}")
     	redirect (uri: "/~${SecurityUtils.encodeAsUrlFriendly(session.survey.name)}/2")
   }
    
    def enterKey() {
    	if (!params.origUrl) {
    		log.error ("Attempt to reach enterKey without survey in the query string")
    		render(status: 500)
    	}
    	String surveyName = SecurityUtils.decodeAsUrlFriendly(params.origUrl).replace("/~","")
    	if (surveyName.lastIndexOf("/") > 0) { surveyName = surveyName.substring(0, surveyName.lastIndexOf("/")) }
    	log.info("Using '${surveyName}' as the survey name for enterKey")
		def s = Survey.findByName(surveyName)
		if (s == null) {
    		log.error ("Attempt to reach enterKey without survey in the query string")
    		render(status: 500)			
		}
		
		[s: s, surveyKey: params.surveyKey]
    }
    
    def useKey() {
    	Integer surveyId = Integer.parseInt(params.surveyId)
    	Survey s = Survey.get(surveyId)
    	if (s == null) {
    		log.error ("Attempt to reach enterKey without bad survey ID #${surveyId}")
    		render(status: 500)
    		return
    	} else if (!params.surveyKey) {
    		flash.error = "Please enter a survey key"
    		render(view: "enterKey", model:[s:s, surveyKey: params.surveyKey])
    		return
    	}
    	log.info("Authenticating for survey #${params.surveyId} with key ${params.surveyKey}")
    	
    	Respondent r = Respondent.findBySurveyAndSurveyKey(s, params.surveyKey)
    	if (!r) {
       		flash.error = "The survey key ${params.surveyKey} was invalid"
    		render(view: "enterKey", model:[s:s, surveyKey: params.surveyKey])
    		return
    	} else if (r.state == Respondent.State.COMPLETED) {
    		// TODO Probably want a better way to handle this
       		flash.error = "You have already completed the survey"
    		render(view: "enterKey", model:[s:s, surveyKey: params.surveyKey])
    		return    		
    	} else {
    		log.info("Respondent entered ${params.surveyKey} and is using respodnent ${r.toString()}")
    		session.respondent = r
    		redirect(uri: "/~${SecurityUtils.encodeAsUrlFriendly(s.name)}")
    	}
    }
    
    private List getStylesheetsForTheme(String theme) {
    	def themeFolder = new File(servletContext.getRealPath("/css/themes/${theme}"))
    	def stylesheets = []
    	themeFolder.eachFile { file ->
    		if (file.name.endsWith(".css")) {
    			stylesheets << "${servletContext.getRealPath('/css/themes/${theme}')}/${file.name}"
    		}
    	}
    	return stylesheets
    }
    
    

 
}
