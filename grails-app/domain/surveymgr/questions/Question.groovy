package surveymgr.questions

import surveymgr.Survey

class Question {
	
	
	public enum Type {
		SINGLE("single"), MULTI("multi"), YESNO("yesno"), TEXT("text"), MATRIX("matrix")
		Type(String value) { this.value = value }
		private final String value
		public String toString() { this.value }
	}
	
	public enum Control {
		CHECKBOX("checkbox"),RADIO("radio"),SELECT("select"),LIST("list")
		Control(String value) { this.value = value }
		private final String value
		public String toString() { this.value }
	}
	
	
	static belongsTo = [survey: Survey]
	String questionId // A short string that uniquely identifies this question in the survey
	String text // The main text of the question
	Type type // Changes the type of question
	Control control // Changes the control used to render the question
	Integer sortOrder = 0 // Controls the order of questions within the survey
	Attributes attributes = new Attributes() // Additional properties that are rarely used	
		
	static hasMany = [answers: Answer,
					  choices: Choice, 
					  branches: Branch,
					  conditions: QuestionCondition]
	
	static constraints = {
		questionId(maxSize: 10, blank: false, unique: "survey")
		text(maxSize: 500)
		type(maxSize: 10)
		control(maxSize: 10, nullable: true)
		
    }
	
	static mapping = {
		//order index:'IX_ORDER' // Speeds up queries that order by "order" a lot
	}
	
	
	static embedded = ["attributes"]
}

public class Attributes {
	boolean forceBreak = false // Whether this question forces a new page
	String sectionHeaderText // Text that comes before the question to announce a new section
	// These properties only pertain to textfields
	String constraint // The type of constraint on the field, if it is text
	String constraintFormat  // The argument used to further constrain the text, where applicable
	String width // The width of the main element in CSS format (px, em, %)
	String height // The height of the main element, where applicable

	// These properties only pertain to matrices
	Integer matrixRange // The range of values that the matrix spans (1..5)
	String rangeLowText // The text at the low end
	String rangeHighText // The text at the high end
	boolean hasNa = false // Whether or not the matrix has a N/A field (mapped as null value in Answer)

	// These properties apply only to singles and multis
	boolean hasOther = false // Whether the question has an "other" field, if applicable
	String hasOtherText // Text for the "other" field, which overrides the default

		
	static constraints = {
		sectionHeaderText(maxSize: 2000, nullable: true)		
		constraint(maxSize: 20, nullable: true, inList: ["numeric","integer","oneword","regex"])
		constraintFormat(maxSize: 250, nullable: true)
		width(maxSize: 20, nullable: true)
		height(maxSize: 20, nullable: true)
		matrixRange(min: 1, max: 10, nullable: true)
		rangeLowText(maxSize: 50, nullable: true)
		rangeHighText(maxSize: 50, nullable: true)
		hasOtherText(maxSize: 100, nullable: true)
	}
}