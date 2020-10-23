package com.example.news.data.api

import com.example.news.data.News

data class NewsApiResponse(
    val articles: MutableList<News>
)