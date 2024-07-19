package com.elhalawany.twittercounterexample.core

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object AndroidServicesHelper {
    fun copyToClipboard(context: Context, text: String, label: String? = "Copied") {
        val clipboard = context.getSystemService(ClipboardManager::class.java)
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }
}