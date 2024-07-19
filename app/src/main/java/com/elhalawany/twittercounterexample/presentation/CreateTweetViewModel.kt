package com.elhalawany.twittercounterexample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhalawany.twittercounterexample.core.DialogResource
import com.elhalawany.twittercounterexample.core.Resource
import com.elhalawany.twittercounterexample.data.remote.entities.ServicesProvider
import com.elhalawany.twittercounterexample.data.repository.TwitterRepositoryImpl
import com.elhalawany.twittercounterexample.domain.exceptions.ExceptionHandler
import com.elhalawany.twittercounterexample.domain.usecases.CreateTweetUseCase
import kotlinx.coroutines.launch

class CreateTweetViewModel : ViewModel() {
    private val createTweetUseCase by lazy {
        CreateTweetUseCase(TwitterRepositoryImpl(ServicesProvider.getTwitterService()), ExceptionHandler())
    }

    private val createTweetMutableLiveData = MutableLiveData<DialogResource?>()
    val createTweetLiveData: LiveData<DialogResource?> = createTweetMutableLiveData;


    fun createTweet(tweet: String) = viewModelScope.launch {
        createTweetUseCase(tweet).collect {
            when (it) {
                is Resource.Loading -> {
                    createTweetMutableLiveData.postValue(DialogResource.Loading())
                }

                is Resource.Error -> {
                    createTweetMutableLiveData.postValue(DialogResource.Error(message = it.message, messageRes = it.messageRes))
                }

                is Resource.Success -> {
                    createTweetMutableLiveData.postValue(DialogResource.Success(it.data))
                }
            }
        }
    }

    fun hideDialog() {
        createTweetMutableLiveData.value = null
    }


}