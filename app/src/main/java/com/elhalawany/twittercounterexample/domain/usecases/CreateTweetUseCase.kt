package com.elhalawany.twittercounterexample.domain.usecases

import com.elhalawany.twittercounterexample.core.Resource
import com.elhalawany.twittercounterexample.domain.exceptions.ExceptionHandler
import com.elhalawany.twittercounterexample.domain.repository.TwitterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CreateTweetUseCase(private val twitterRepository: TwitterRepository, val exceptionHandler: ExceptionHandler) {


    suspend operator fun invoke(message: String) = flow {
        emit(Resource.Loading)

        try {
            val postId = twitterRepository.createPost(message)
            emit(Resource.Success(postId))
        } catch (e: Exception) {
            emit(exceptionHandler(e))
        }
    }
}