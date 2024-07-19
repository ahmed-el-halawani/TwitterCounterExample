package com.elhalawany.twittercounterexample.domain.exceptions

import com.elhalawany.twittercounterexample.R
import com.elhalawany.twittercounterexample.core.Resource

class ExceptionHandler() {

    operator fun invoke(exception: Exception): Resource.Error {
        var messageRes: Int? = null
        var messageString: String? = null
        when (exception) {
            is GeneralServerException ->
                messageString = exception.message

            is UnhandledServerException ->
                messageRes = R.string.unknown_server_exception

            is ConnectivityException ->
                messageRes = R.string.internet_exception

            else -> messageRes = R.string.unknown_exception
        }

        return Resource.Error(messageString, messageRes, exception)
    }
}