package com.example.news.ui.details

import com.example.news.data.News
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface DetailsView : MvpView {
    fun showNews(news: News)
}