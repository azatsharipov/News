package com.example.news.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "my_news")
data class News(
    val title: String,
    val description: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    var isMarked: Boolean = false,
    @PrimaryKey
    var source: Source
) : Serializable