package com.example.news.ui.marks

import com.example.news.data.News
import com.example.news.data.repository.NewsDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope

@InjectViewState
class MarksPresenter : MvpPresenter<MarksView>() {

    var news: MutableList<News>? = null

    fun loadNewsFromDb(repository: NewsDbRepository) {
        presenterScope.launch(Dispatchers.Main) {
            viewState.startLoading()
            withContext(Dispatchers.Default) {
                news = repository.getAll() as MutableList<News>?
            }
            viewState.showNews(news ?: mutableListOf())
            viewState.stopLoading()
        }
    }

}