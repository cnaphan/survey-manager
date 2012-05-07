package surveymgr

import static org.junit.Assert.*;
import grails.test.mixin.*
import org.junit.*

import survey.questions.*


@TestFor(User)
class UserTests {

	void testSaves() {
		def users = [new User(name: "nikki", password: "pass", group: User.Group.ADMIN)]
		users*.save(flush: true)
		assert User.count() == 1
		
		User.get(1).delete()
		assert User.count() == 0
	}
	
	
	
}
