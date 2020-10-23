package com.example.news.ui.home

import com.example.news.data.News
import com.example.news.data.api.NewsApiFactory
import com.example.news.data.repository.NewsRepository
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope

@InjectViewState
class HomePresenter() : MvpPresenter<HomeView>() {

    var news: MutableList<News>? = null

    fun startCoroutineTimer(repeatMillis: Long) = presenterScope.launch(Dispatchers.Default) {
        while (true) {
            loadNews()
            delay(repeatMillis)
        }
    }

    suspend fun loadNews() {
        presenterScope.launch(Dispatchers.Main) {
            viewState.startLoading()
            withContext(Dispatchers.Default) {
                news = NewsRepository().getTopNews()

            }
            viewState.showNews(news ?: mutableListOf())
            viewState.stopLoading()
        }
    }
}