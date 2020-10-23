package com.example.news.ui.details

import android.os.Bundle
import com.example.news.data.News
import com.example.news.data.repository.NewsDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope

@InjectViewState
class DetailsPresenter : MvpPresenter<DetailsView>() {

    lateinit var news: News

    fun setupNews(bundle: Bundle?) {
        news = bundle?.getSerializable("news") as News
        viewState.showNews(news)
    }

    fun insertNews(repository: NewsDbRepository) {
        presenterScope.launch(Dispatchers.Main) {
            news.isMarked = true
            repository.insert(news)
        }
    }

    fun deleteNews(repository: NewsDbRepository) {
        presenterScope.launch(Dispatchers.Main) {
            news.isMarked = false
            repository.delete(news)
        }
    }

}