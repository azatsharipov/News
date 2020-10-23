package com.example.news.ui.home


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.news.R
import com.example.news.data.News
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HomeFragment : MvpAppCompatFragment(), HomeView {

    lateinit var recyclerView: RecyclerView
    lateinit var newsAdapter: NewsAdapter
    lateinit var progressBar: ProgressBar
    var isLoading: Boolean = false
    var isScrolling: Boolean = false

    @InjectPresenter
    lateinit var presenter: HomePresenter

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
                presenter.loadNews(isPagination = true)
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.rv_home)
        progressBar = view.findViewById(R.id.pb_home)

        newsAdapter = NewsAdapter(mutableListOf())
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = newsAdapter
            addOnScrollListener(paginationListener)
        }
        if (hasInternet())
            presenter.startCoroutineTimer(5000)

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

    fun updateNews(news: MutableList<News>) {
        news.forEach{
            var exist = false
            for (n in newsAdapter.news) {
                if (it.source.id == n.source.id)
                    exist = true
            }
            if (!exist)
                newsAdapter.news.add(it)
        }
    }

    override fun showNews(news: MutableList<News>, isPagination: Boolean) {
        if (isPagination)
            newsAdapter.news.addAll(news)
        else
            updateNews(news)
        newsAdapter.notifyDataSetChanged()
    }

    override fun startLoading() {
        isLoading = true
//        progressBar.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        isLoading = false
        progressBar.visibility = View.GONE
    }



}
