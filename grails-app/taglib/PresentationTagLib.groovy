class PresentationTagLib {
	
	/**
	 * Renders an input tag that acts as a link
	 * @attr url
	 */
	def buttonLink = { attrs, body ->
		out << "<button class='${attrs.class}' id='${attrs.id}' onclick='location.href = \"${createLink(attrs)}\"; return false;'>" << body() << "</button>"
	}
	
}

