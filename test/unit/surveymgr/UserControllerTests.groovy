package surveymgr

import org.junit.*
import grails.test.mixin.*

@TestFor(UserController)
@Mock([User])
class UserControllerTests {

	void testAuthenticate() {
		mockDomain(User, [
			new User(name: "a", password:SecurityUtils.encodeAsSHA("pass"), group: User.Group.OPERATOR),
			new User(name: "b", password:SecurityUtils.encodeAsSHA("pass"), group: User.Group.OPERATOR)
		])

		assert User.count() == 2

		params.name = "a"
		params.password = "passxxx"
		controller.authenticate()
		assert !flash.message.contains("Welcome")
		assert response.redirectUrl == "/user/login"
		response.reset()
		
		params.name = "a"
		params.password = "pass"
		controller.authenticate()
		assert flash.message.contains("Welcome")
		assert response.redirectUrl != "/user/list"
	}
}
