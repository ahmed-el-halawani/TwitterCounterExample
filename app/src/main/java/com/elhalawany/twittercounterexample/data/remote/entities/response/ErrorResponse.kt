package com.elhalawany.twittercounterexample.data.remote.entities.response

data class ErrorResponse(
	val detail: String? = null,
	val type: String? = null,
	val title: String? = null,
	val status: Int? = null
)

