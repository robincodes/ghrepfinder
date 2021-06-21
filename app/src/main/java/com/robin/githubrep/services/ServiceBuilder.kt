package com.robin.githubrep.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder{

    // Constant for base URL for the web service
    private const val URL = "https://api.github.com/search/"

    // Create OkHttp Client
    private val okHttp:OkHttpClient.Builder = OkHttpClient.Builder()

    //Create Retrofit Builder
    private val builder:Retrofit.Builder =  Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create()).client(okHttp.build())

    //Create Retrofit Instance
    private val retrofit:Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>):T{
        return retrofit.create(serviceType)
    }
}