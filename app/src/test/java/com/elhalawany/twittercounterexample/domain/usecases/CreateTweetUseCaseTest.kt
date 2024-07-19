package com.elhalawany.twittercounterexample.domain.usecases

import com.elhalawany.twittercounterexample.R
import com.elhalawany.twittercounterexample.core.Resource
import com.elhalawany.twittercounterexample.domain.exceptions.ConnectivityException
import com.elhalawany.twittercounterexample.domain.exceptions.ExceptionHandler
import com.elhalawany.twittercounterexample.domain.exceptions.GeneralServerException
import com.elhalawany.twittercounterexample.domain.repository.TwitterRepository
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class CreateTweetUseCaseTest {

    private lateinit var twitterRepository: TwitterRepository
    private lateinit var exceptionHandler: ExceptionHandler
    private lateinit var useCase: CreateTweetUseCase


    @Before
    fun setUp() {
        twitterRepository = mock(TwitterRepository::class.java)
        exceptionHandler = ExceptionHandler()
        useCase = CreateTweetUseCase(twitterRepository, exceptionHandler)

    }


    @Test
    fun testNoInternetConnection_expectEmitConnectivityErrorMessage() {
        runBlocking {
            //setup
            Mockito.`when`(twitterRepository.createPost("")).thenThrow(ConnectivityException())

            //act
            val result = useCase.invoke("").last()

            //assert
            assert(
                result is Resource.Error &&
                result.exception is ConnectivityException &&
                result.messageRes == R.string.internet_exception
            )
        }
    }

    @Test
    fun testServerIssue_expectEmitGeneralServerErrorMessage() {
        runBlocking {
            //setup
            Mockito.`when`(twitterRepository.createPost("")).thenThrow(GeneralServerException(message = "server issue"))

            //act
            val result = useCase.invoke("").last()

            //assert
            assert(
                result is Resource.Error &&
                result.exception is GeneralServerException &&
                result.message == "server issue"
            )
        }
    }


    @Test
    fun testUnhandledException_expectEmitUnKnownExceptionErrorMessage() {
        runBlocking {
            //setup
            Mockito.`when`(twitterRepository.createPost("")).thenThrow(Exception())

            //act
            val result = useCase.invoke("").last()

            //assert
            assert(
                result is Resource.Error &&
                result.exception is Exception &&
                result.messageRes == R.string.unknown_exception
            )
        }
    }

}