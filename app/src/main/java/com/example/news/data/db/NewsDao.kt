package com.example.news.data.db

import androidx.room.*
import com.example.news.data.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM News")
    suspend fun getAll(): List<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: News)

    @Delete
    suspend fun delete(news: News)
}