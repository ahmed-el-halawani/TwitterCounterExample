package com.elhalawany.twittercounterexample.data.remote.entities.response

data class CreatePostResponse(
	val data: Data? = null
)

data class Data(
	val editHistoryTweetIds: List<String?>? = null,
	val id: String? = null,
	val text: String? = null
)

