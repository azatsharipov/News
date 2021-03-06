package com.example.news.data.repository

import com.example.news.BuildConfig
import com.example.news.data.News
import com.example.news.data.api.NewsApiRequest
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository() {
    var newsApiKey = BuildConfig.NEWS_API_KEY

    suspend fun getTopNews(page: Int, pageSize: Int = 15): MutableList<News>? {
        val news = Retrofit
            .Builder()
            .baseUrl("http://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(NewsApiRequest::class.java)
        val result = news
            .getTopNews("apple", pageSize, page, newsApiKey)
            .await()
        return result.body()?.articles
    }

    suspend fun getEverythingNews(page: Int, pageSize: Int = 15): MutableList<News>? {
        val news = Retrofit
            .Builder()
            .baseUrl("http://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(NewsApiRequest::class.java)
        val result = news
            .getEverythingNews("apple", pageSize, page, newsApiKey)
            .await()
        return result.body()?.articles
    }
}