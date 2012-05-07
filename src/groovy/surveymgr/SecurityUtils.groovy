package surveymgr

import java.security.MessageDigest

class SecurityUtils {
	
	static public String encodeAsSHA(String plainText) {
		MessageDigest md = MessageDigest.getInstance('SHA')
		md.update(plainText.getBytes('UTF-8'))
		return new String(md.digest())
	}
	
	static public String encodeAsUrlFriendly(String plainText) {
		return plainText.replace(" ", "_").encodeAsURL()
	}
	static public String decodeAsUrlFriendly(String plainText) {
		return plainText.decodeURL().replace("_", " ")
	}
}
