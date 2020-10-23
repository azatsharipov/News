package com.example.news.ui.everything


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
    var isScrolling = false

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
            val shouldPaginate = isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && !isLoading && isScrolling
            if (shouldPaginate) {
                presenter.loadNews()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
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
            addOnScrollListener(paginationListener)
        }

        swipeRefresh.setOnRefreshListener {
            if (hasInternet())
                presenter.loadNews(isSwipe = true)
        }

        if (hasInternet())
            presenter.loadNews()

        return view
    }

    fun hasInternet(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo!=null && networkInfo.isConnected
            }
        }
        return false
    }


    override fun startLoading() {
        isLoading = true
    }

    override fun stopLoading() {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        isLoading = false
    }

    override fun showNews(news: MutableList<News>, isSwipe: Boolean) {
        if (isSwipe)
            newsAdapter.news = news
        newsAdapter.news.addAll(news)
        newsAdapter.notifyDataSetChanged()
    }

}
