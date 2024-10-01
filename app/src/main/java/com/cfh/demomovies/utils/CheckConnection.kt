package com.cfh.demomovies.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object CheckConnection {
    suspend fun isInternetAvailable(): Boolean = withContext(Dispatchers.IO) {
        var result = false
        val url = URL("https://clients3.google.com/generate_204")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        connection.connectTimeout = 1500
        connection.readTimeout = 1500
        try {
            val responseCode = connection.responseCode
            result = responseCode == HttpURLConnection.HTTP_NO_CONTENT
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.disconnect()
        }
        result
    }
}


