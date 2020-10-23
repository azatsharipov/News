package com.example.news.ui.details


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

import com.example.news.R
import com.example.news.data.News
import com.example.news.data.db.NewsDatabase
import com.example.news.data.repository.NewsDbRepository
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class DetailsFragment : MvpAppCompatFragment(), DetailsView {

    lateinit var tvTitle: TextView
    lateinit var tvContent: TextView
    lateinit var ivAva: ImageView
    lateinit var cbMark: CheckBox

    @InjectPresenter
    lateinit var presenter: DetailsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        tvTitle = view.findViewById(R.id.tv_details_title)
        tvContent = view.findViewById(R.id.tv_details_content)
        ivAva = view.findViewById(R.id.iv_details)
        cbMark = view.findViewById(R.id.cb_details)

        cbMark.setOnCheckedChangeListener { compoundButton, b ->
            val database = NewsDatabase(activity as Context)
            val repository = NewsDbRepository(database)
            if (b) {
                presenter.insertNews(repository)
            } else {
                presenter.deleteNews(repository)
            }
        }

        presenter.setupNews(arguments)

        return view
    }

    override fun showNews(news: News) {
        tvTitle.setText(news.title)
        tvContent.setText(news.content)
        cbMark.isChecked = news.isMarked
        Picasso.get().load(news.urlToImage).into(ivAva)
    }


}
