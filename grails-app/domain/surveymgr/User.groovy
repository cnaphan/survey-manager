package surveymgr

class User {

	public enum Group {
		OPERATOR("Operator"), MANAGER("Manager"), ADMIN("Admin")
		Group(String value) { this.value = value }
		private final String value
		public String toString() { this.value }
	}
	
	String name
	String password
	Group group
	boolean active = true
	
	static hasMany = [ownedSurveys: Survey, assignedSurveys: Survey]
	static belongsTo = [Survey]
	
	static mapping = {
		table "cati_user"
		group column: "user_group" // Can't name a column 'group' because it's a SQL key word
	}
	
    static constraints = {
		name blank: false, size: 1..50
		password blank: false, password: true
		group maxSize: 20
    }
	
	def beforeInsert = {
		password = SecurityUtils.encodeAsSHA(password)
	}
	

	String toString() { "User[name=${this.name}, group=${this.group.toString()}]" }
	
	boolean equals(Object b) {
		return b.id == this.id 
	}
}
