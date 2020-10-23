package com.example.news.data.api

import com.example.news.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsApiFactory{

    private val authInterceptor = Interceptor {chain->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.NEWS_API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val apiClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()



    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(apiClient)
        .baseUrl("http://newsapi.org/v2/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val newsApi : NewsApiRequest = retrofit().create(NewsApiRequest::class.java)

}