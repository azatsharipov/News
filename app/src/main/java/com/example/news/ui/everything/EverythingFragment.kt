package com.example.news.ui.everything


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.news.R
import com.example.news.data.News
import com.example.news.ui.home.NewsAdapter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class EverythingFragment : MvpAppCompatFragment(), EverythingView {

    lateinit var recyclerView: RecyclerView
    lateinit var newsAdapter: NewsAdapter
    lateinit var progressBar: ProgressBar
    lateinit var swipeRefresh: SwipeRefreshLayout
    var isLoading = false

    @InjectPresenter
    lateinit var presenter: EverythingPresenter

    val paginationListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 15
            val shouldPaginate = isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && !isLoading
            if(shouldPaginate) {
                presenter.loadNews()
//                isScrolling = false
            } else {
//                rvBreakingNews.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            /*
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }

             */
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_everything, container, false)

        recyclerView = view.findViewById(R.id.rv_everything)
        progressBar = view.findViewById(R.id.pb_everything)
        swipeRefresh = view.findViewById(R.id.swipe_everything)


        newsAdapter = NewsAdapter(mutableListOf())
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = newsAdapter
//            addOnScrollListener(paginationListener)
        }

        swipeRefresh.setOnRefreshListener {
            presenter.loadNews()
        }

        presenter.loadNews()

        return view
    }

    override fun startLoading() {
        isLoading = true
    }

    override fun stopLoading() {
        isLoading = false
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
    }

    override fun showNews(news: MutableList<News>) {
        newsAdapter.news = news
//        newsAdapter.news.addAll(news)
        newsAdapter.notifyDataSetChanged()
    }

}
