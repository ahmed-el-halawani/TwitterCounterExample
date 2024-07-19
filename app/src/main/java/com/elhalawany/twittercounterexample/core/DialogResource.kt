package com.elhalawany.twittercounterexample.core

import android.content.Context
import androidx.annotation.StringRes

sealed class DialogResource(
    private val titleString: String? = null,
    private val messageString: String? = null,
    @StringRes val titleRes: Int? = null,
    @StringRes val messageRes: Int? = null,
) {

    fun getTitle(context: Context) = titleString ?: titleRes?.let { context.getString(titleRes) } ?: ""

    fun getMessage(context: Context) =
        messageString ?: messageRes?.let { context.getString(messageRes) } ?: ""

    class Loading(
        title: String? = null,
        message: String? = null,
        titleRes: Int? = null,
        messageRes: Int? = null,
    ) : DialogResource(title, message, titleRes, messageRes)

    class Success(
        title: String? = null,
        message: String? = null,
        @StringRes titleRes: Int? = null,
        @StringRes messageRes: Int? = null,
    ) : DialogResource(title, message, titleRes, messageRes)

    class Error(
        title: String? = null,
        message: String? = null,
        @StringRes titleRes: Int? = null,
        @StringRes messageRes: Int? = null,
    ) : DialogResource(title, message, titleRes, messageRes)

}