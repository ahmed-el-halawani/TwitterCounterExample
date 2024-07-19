package com.elhalawany.twittercounterexample.data.remote.entities

import com.elhalawany.twittercounterexample.BuildConfig
import com.elhalawany.twittercounterexample.core.OAuthHeaderGenerator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory


object ServicesProvider {
    private val retrofit = retrofit2.Retrofit.Builder()

    fun getTwitterService(): TwitterServices {
        val client = OkHttpClient.Builder()
            .addInterceptor(oAuthInterceptor())
            .addInterceptor(loggingInterceptor())
            .build()

        return retrofit
            .baseUrl("https://api.twitter.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TwitterServices::class.java)
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }


    private fun oAuthInterceptor() = Interceptor {
        val consumerKey = BuildConfig.API_KEY
        val consumerSecret = BuildConfig.API_SECRET
        val token = BuildConfig.ACCESS_TOKEN
        val tokenSecret = BuildConfig.ACCESS_TOKEN_SECRET


        val originalRequest = it.request()
        val authorizationHeader = OAuthHeaderGenerator(
            consumerKey,
            consumerSecret,
            token,
            tokenSecret
        )
            .generateAuthorizationHeader(
                originalRequest.method,
                originalRequest.url.toString()
            )

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", authorizationHeader)
            .build()

        it.proceed(newRequest)
    }


}