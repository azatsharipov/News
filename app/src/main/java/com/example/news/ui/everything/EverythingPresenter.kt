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

    fun loadNews(isSwipe: Boolean = false) {
        presenterScope.launch(Dispatchers.Main) {
            viewState.startLoading()
            withContext(Dispatchers.Default) {
                if (isSwipe)
                    page = 0
                page++
                news = NewsRepository().getEverythingNews(page)
            }
            viewState.showNews(news ?: mutableListOf(), isSwipe)
            viewState.stopLoading()
        }
    }
}