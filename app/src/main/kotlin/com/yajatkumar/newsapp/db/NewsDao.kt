package com.yajatkumar.newsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yajatkumar.newsapp.data.News
import kotlinx.coroutines.flow.Flow

// The Dao for the News
@Dao
interface NewsDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the data has changed.
    @Query("SELECT * FROM news_table ORDER BY id ASC")
    fun getNews(): Flow<List<News>>

    // Insert a news items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: News)

    // Insert all the items in the vararg
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: News)

    // Delete all items from table
    @Query("DELETE FROM news_table")
    suspend fun deleteAll()
}
