package com.example.news.ui.everything

import com.example.news.data.News
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface EverythingView : MvpView {
    fun startLoading()
    fun stopLoading()
    fun showNews(news: MutableList<News>)
}