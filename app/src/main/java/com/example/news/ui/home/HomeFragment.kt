package com.example.news.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView

import com.example.news.R
import com.example.news.data.News
import com.example.news.data.api.NewsApiFactory
import com.example.news.data.api.NewsApiRequest
import com.example.news.data.repository.NewsRepository
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HomeFragment : MvpAppCompatFragment(), HomeView {

    lateinit var recyclerView: RecyclerView
    lateinit var newsAdapter: NewsAdapter
    lateinit var progressBar: ProgressBar

    @InjectPresenter
    lateinit var presenter: HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.rv_home)
        progressBar = view.findViewById(R.id.pb_home)

        newsAdapter = NewsAdapter(mutableListOf())
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = newsAdapter
        }
        presenter.startCoroutineTimer(5000)
        return view
    }

    override fun showNews(news: MutableList<News>) {
        newsAdapter.news = news
        newsAdapter.notifyDataSetChanged()
    }

    override fun startLoading() {
//        progressBar.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        progressBar.visibility = View.GONE
    }



}
