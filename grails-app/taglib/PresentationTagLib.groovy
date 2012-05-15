class PresentationTagLib {
	
	/**
	 * Renders an input tag that acts as a link
	 * @attr url
	 */
	def buttonLink = { attrs, body ->
		out << "<button class='${attrs.class}' id='${attrs.id}' onclick='location.href = \"${createLink(attrs)}\"; return false;'>" << body() << "</button>"
	}
	
	def themeStylesheets = { attrs, body ->		
		if (attrs.theme) {
			def themeFolderName = servletContext.getRealPath("/css/themes/${attrs.theme}")
			def themeFolder = new File(themeFolderName)
			if (themeFolder.exists()) {
				themeFolder.eachFile { file ->
					if (file.name.endsWith(".css")) {						
						def cssPath = resource(dir: "css/themes/" + attrs.theme, file: file.name)
						out << "<link rel='stylesheet' href='${cssPath}' type='text/css'/>"
					}
				}
			} else {
				log.warn("Attempt to load stylesheets from unknown theme: ${attrs.theme}")
			}
		}
	}
	
}

