package com.example.news.ui.everything

import com.example.news.data.News
import com.example.news.data.repository.NewsRepository
import com.example.news.ui.home.HomeView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope

@InjectViewState
class EverythingPresenter() : MvpPresenter<EverythingView>() {
    var news: MutableList<News>? = null
    var page = 0

    fun loadNews() {
        presenterScope.launch(Dispatchers.Main) {
            viewState.startLoading()
            withContext(Dispatchers.Default) {
                page++
                news = NewsRepository().getEverythingNews(page)
            }
            viewState.showNews(news ?: mutableListOf())
            viewState.stopLoading()
        }
    }
}