package com.example.news.data.repository

import com.example.news.data.News
import com.example.news.data.db.NewsDatabase

class NewsDbRepository(private val db: NewsDatabase) {
    suspend fun insert(news: News) = db.getNewsDao().insert(news)

    suspend fun delete(news: News) = db.getNewsDao().delete(news)

    suspend fun getAll() = db.getNewsDao().getAll()
}