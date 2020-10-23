package com.example.news.ui.marks


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView

import com.example.news.R
import com.example.news.data.News
import com.example.news.data.db.NewsDatabase
import com.example.news.data.repository.NewsDbRepository
import com.example.news.ui.home.NewsAdapter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class MarksFragment : MvpAppCompatFragment(), MarksView {

    lateinit var recyclerView: RecyclerView
    lateinit var newsAdapter: NewsAdapter
    lateinit var progressBar: ProgressBar

    @InjectPresenter
    lateinit var presenter: MarksPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_marks, container, false)

        recyclerView = view.findViewById(R.id.rv_marks)
        progressBar = view.findViewById(R.id.pb_marks)

        newsAdapter = NewsAdapter(mutableListOf())
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        val database = NewsDatabase(activity as Context)
        val repository = NewsDbRepository(database)
        presenter.loadNewsFromDb(repository)

        return view
    }

    override fun startLoading() {

    }

    override fun stopLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showNews(news: MutableList<News>) {
        newsAdapter.news = news
        newsAdapter.notifyDataSetChanged()
    }

}
