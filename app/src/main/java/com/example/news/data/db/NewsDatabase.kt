package com.example.news.data.db

import android.content.Context
import androidx.room.*
import com.example.news.data.News

@Database(
    entities = [News::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: NewsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDatabase(
                        context
                    ).also { instance = it }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                NewsDatabase::class.java, "my_newsDB.db").build()
    }
}