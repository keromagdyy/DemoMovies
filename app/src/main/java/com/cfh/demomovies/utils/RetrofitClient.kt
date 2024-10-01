package com.cfh.demomovies.utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun <T> getInstance(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(ConstantLinks.BASE_URL)
            .client(SetupHttpClient().getOkHttpClient())
            .build()

        return retrofit.create(service)
    }

}
