package com.elhalawany.twittercounterexample.data.repository

import com.elhalawany.twittercounterexample.data.remote.TwitterServices
import com.elhalawany.twittercounterexample.data.remote.entities.request.CreateTweetRequest
import com.elhalawany.twittercounterexample.data.remote.entities.response.ErrorResponse
import com.elhalawany.twittercounterexample.domain.exceptions.ConnectivityException
import com.elhalawany.twittercounterexample.domain.exceptions.GeneralServerException
import com.elhalawany.twittercounterexample.domain.exceptions.UnhandledServerException
import com.elhalawany.twittercounterexample.domain.repository.TwitterRepository
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class TwitterRepositoryImpl(private val twitterServices: TwitterServices) : TwitterRepository {

    override suspend fun createPost(message: String): String {
        try {
            val response = twitterServices.createPost(CreateTweetRequest(message))
            return response.data?.id ?: throw GeneralServerException("Tweet id is missing")
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string() ?: throw UnhandledServerException(e)
            val response = Gson().fromJson(errorBody, ErrorResponse::class.java)
            throw GeneralServerException(response.detail, e)
        } catch (e: IOException) {
            throw ConnectivityException()
        }
    }

}