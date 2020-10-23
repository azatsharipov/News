package com.example.news.ui.home

import com.example.news.data.News
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface HomeView : MvpView {
    fun startLoading()
    fun stopLoading()
    fun showNews(news: MutableList<News>)
}