package com.example.news.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "News")
data class News(
    val author: String,
    val title: String,
    val description: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String,
    var isMarked: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
) : Serializable