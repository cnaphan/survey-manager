class UrlMappings {

	static mappings = {
		
		"/$controller/$action?/$id?"{
			constraints {
				if ($controller == "public" && $action in "take") {
					return false
				}
			}
		}
		
		"/~$surveyName/$pageNum?" (controller: "public", action: "take") { }
				
		"/" (controller: "home", action: "index") {} 
		
		"500"(view:'/error')
	}
}
