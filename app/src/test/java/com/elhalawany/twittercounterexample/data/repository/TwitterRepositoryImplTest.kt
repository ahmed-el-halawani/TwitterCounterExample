package com.elhalawany.twittercounterexample.data.repository

import com.elhalawany.twittercounterexample.data.remote.entities.TwitterServices
import com.elhalawany.twittercounterexample.data.remote.entities.request.CreateTweetRequest
import com.elhalawany.twittercounterexample.data.remote.entities.response.CreatePostResponse
import com.elhalawany.twittercounterexample.data.remote.entities.response.Data
import com.elhalawany.twittercounterexample.domain.exceptions.ConnectivityException
import com.elhalawany.twittercounterexample.domain.exceptions.GeneralServerException
import com.elhalawany.twittercounterexample.domain.exceptions.UnhandledServerException
import com.elhalawany.twittercounterexample.domain.repository.TwitterRepository
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class TwitterRepositoryImplTest {

    private lateinit var twitterServices: TwitterServices
    private lateinit var twitterRepository: TwitterRepository

    @Before
    fun setUp() {
        twitterServices = mock(TwitterServices::class.java)
        twitterRepository = TwitterRepositoryImpl(twitterServices)
    }

    @Test
    fun testNoInternetConnection_expectThrowConnectivity() {
        runBlocking {
            //setup
            Mockito.`when`(twitterServices.createPost(CreateTweetRequest(""))).thenThrow(IOException())

            //act
            try {
                twitterRepository.createPost("")
            } catch (e: Exception) {
                //assert

                println(e)

                assert(
                    e is ConnectivityException
                )
            }
        }
    }

    @Test
    fun testServerIssue_expectThrowGeneralServerException() {
        runBlocking {
            //setup
            val httpException = mock(HttpException::class.java)
            val repository = mock(Response::class.java)
            val errorBody = mock(ResponseBody::class.java)
            Mockito.`when`(httpException.response()).thenReturn(repository)
            Mockito.`when`(repository.errorBody()).thenReturn(errorBody)
            Mockito.`when`(errorBody.string()).thenReturn(
                """
                {
                    "detail": "You are not allowed to create a Tweet with duplicate content.",
                    "type": "about:blank",
                    "title": "Forbidden",
                    "status": 403
                }
            """.trimIndent()
            )


            Mockito.`when`(twitterServices.createPost(CreateTweetRequest(""))).thenThrow(httpException)

            //act
            try {
                twitterRepository.createPost("")
            } catch (e: Exception) {
                //assert
                assert(
                    e is GeneralServerException && e.message == "You are not allowed to create a Tweet with duplicate content."
                )
            }
        }
    }

    @Test
    fun testServerIssueButWithoutErrorBody_expectThrowUnhandledServerException() {
        runBlocking {
            //setup
            val httpException = mock(HttpException::class.java)
            val repository = mock(Response::class.java)
            Mockito.`when`(httpException.response()).thenReturn(repository)
            Mockito.`when`(repository.errorBody()).thenReturn(null)

            Mockito.`when`(twitterServices.createPost(CreateTweetRequest(""))).thenThrow(httpException)

            //act
            try {
                twitterRepository.createPost("")
            } catch (e: Exception) {
                //assert
                assert(e is UnhandledServerException)
            }
        }
    }

    @Test
    fun testIsSuccessButWithEmptyBody_expectThrowGeneralServerException() {
        runBlocking {
            //setup
            Mockito.`when`(twitterServices.createPost(CreateTweetRequest(""))).thenReturn(
                CreatePostResponse(Data())
            )

            //act
            try {
                twitterRepository.createPost("")
            } catch (e: Exception) {
                //assert
                assert(e is GeneralServerException && e.message == "Tweet id is missing")
            }
        }
    }

}