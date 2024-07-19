package com.elhalawany.twittercounterexample.core

import java.util.UUID

class OAuthHeaderGenerator(
    private val consumerKey: String,
    private val consumerSecret: String,
    private val token: String,
    private val tokenSecret: String,
) {

    fun generateAuthorizationHeader(requestMethod: String, requestUrl: String): String {
        val nonce = UUID.randomUUID().toString().replace("-", "")
        val timestamp = (System.currentTimeMillis() / 1000).toString()

        val signatureBaseString = createSignatureBaseString(
            method = requestMethod,
            url = requestUrl,
            nonce,
            timestamp
        )

        val signature = createSignature(signatureBaseString)

        val authorizationHeader = createAuthorizationHeader(nonce, timestamp, signature)
        return authorizationHeader
    }

    private fun createSignatureBaseString(
        method: String,
        url: String,
        nonce: String,
        timestamp: String
    ): String {
        val params = "oauth_consumer_key=$consumerKey&oauth_nonce=$nonce&oauth_signature_method=HMAC-SHA1" +
                "&oauth_timestamp=$timestamp&oauth_token=$token&oauth_version=1.0"
        return "$method&${encode(url)}&${encode(params)}"
    }

    private fun createSignature(baseString: String): String {
        val signingKey = "$consumerSecret&$tokenSecret"
        val mac = javax.crypto.Mac.getInstance("HmacSHA1")
        val keySpec = javax.crypto.spec.SecretKeySpec(signingKey.toByteArray(), "HmacSHA1")
        mac.init(keySpec)
        return android.util.Base64.encodeToString(mac.doFinal(baseString.toByteArray()), android.util.Base64.NO_WRAP)
    }

    private fun createAuthorizationHeader(nonce: String, timestamp: String, signature: String): String {
        return "OAuth " +
                "oauth_consumer_key=\"$consumerKey\", " +
                "oauth_nonce=\"$nonce\", " +
                "oauth_signature=\"${encode(signature)}\", " +
                "oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_timestamp=\"$timestamp\", " +
                "oauth_token=\"$token\", " +
                "oauth_version=\"1.0\""
    }

    private fun encode(value: String): String {
        return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20")
    }
}