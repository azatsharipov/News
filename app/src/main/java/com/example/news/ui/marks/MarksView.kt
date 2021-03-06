package com.example.news.ui.marks

import com.example.news.data.News
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface MarksView : MvpView {
    fun startLoading()
    fun stopLoading()
    fun showNews(news: MutableList<News>)

}