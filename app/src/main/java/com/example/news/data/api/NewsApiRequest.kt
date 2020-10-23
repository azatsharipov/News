package com.example.news.data.api

import com.example.news.data.News
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiRequest {

    @GET("top-headlines")
    fun getTopNews(@Query("q") q: String, @Query("apiKey") key: String): Deferred<Response<NewsApiResponse>>

    @GET("everything")
    fun getEverythingNews(@Query("q") q: String, @Query("pageSize") pageSize: Int, @Query("page") page: Int, @Query("apiKey") key: String): Deferred<Response<NewsApiResponse>>

}