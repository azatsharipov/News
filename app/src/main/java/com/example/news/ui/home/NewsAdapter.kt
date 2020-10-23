package com.example.news.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.News
import com.example.news.data.repository.NewsDbRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsAdapter(
    var news: MutableList<News>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_news_item_title)
        val tvTime: TextView = view.findViewById(R.id.tv_news_item_time)
        val ivAva: ImageView = view.findViewById(R.id.iv_news_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false) as View
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.tvTitle.setText(news[position].title)
        holder.tvTime.setText(news[position].publishedAt)
        Picasso.get().load(news[position].urlToImage).into(holder.ivAva)
        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("news", news[position])
            it.findNavController().navigate(R.id.detailsFragment, bundle)
        }
    }
}