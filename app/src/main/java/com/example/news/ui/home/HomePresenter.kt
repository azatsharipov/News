package com.example.news.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import com.example.news.data.News
import com.example.news.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope


@InjectViewState
class HomePresenter() : MvpPresenter<HomeView>() {

    var news: MutableList<News>? = null
    var page = 0

    fun startCoroutineTimer(repeatMillis: Long) = presenterScope.launch(Dispatchers.Default) {
        while (true) {
            loadNews()
            delay(repeatMillis)
        }
    }

    fun loadNews(isPagination: Boolean = false) {
        presenterScope.launch(Dispatchers.Main) {
            if (isPagination)
                viewState.startLoading()
            withContext(Dispatchers.Default) {
                if (isPagination) {
                    page++
                    news = NewsRepository().getTopNews(page)
                } else {
                    news = NewsRepository().getTopNews(1)
                }
            }
            viewState.showNews(news ?: mutableListOf(), isPagination)
            viewState.stopLoading()
        }
    }
}