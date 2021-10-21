package com.yajatkumar.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yajatkumar.newsapp.data.News
import kotlinx.coroutines.CoroutineScope

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): NewsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsRoomDatabase::class.java,
                    "news_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
