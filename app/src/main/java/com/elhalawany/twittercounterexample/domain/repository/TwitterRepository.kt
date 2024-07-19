package com.elhalawany.twittercounterexample.domain.repository

import com.elhalawany.twittercounterexample.domain.exceptions.ConnectivityException
import java.io.IOException

interface TwitterRepository {

    @Throws(IOException::class, ConnectivityException::class, Exception::class)
    suspend fun createPost(message: String): String
}