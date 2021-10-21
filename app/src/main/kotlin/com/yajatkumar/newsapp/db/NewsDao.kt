package com.yajatkumar.newsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yajatkumar.newsapp.data.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the data has changed.
    @Query("SELECT * FROM news_table ORDER BY id ASC")
    fun getNews(): Flow<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: News)

    @Query("DELETE FROM news_table")
    suspend fun deleteAll()
}
