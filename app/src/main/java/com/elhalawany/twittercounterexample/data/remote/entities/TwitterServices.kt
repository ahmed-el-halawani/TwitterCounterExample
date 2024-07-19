package com.elhalawany.twittercounterexample.data.remote.entities

import com.elhalawany.twittercounterexample.data.remote.entities.request.CreateTweetRequest
import com.elhalawany.twittercounterexample.data.remote.entities.response.CreatePostResponse
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException

interface TwitterServices {

    @POST("/2/tweets")
    @Throws(IOException::class, Exception::class)
    suspend fun createPost(@Body createTweetRequest: CreateTweetRequest): CreatePostResponse

}