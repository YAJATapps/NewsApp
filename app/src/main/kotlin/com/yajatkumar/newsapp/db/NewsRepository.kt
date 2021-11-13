package com.yajatkumar.newsapp.db

import androidx.annotation.WorkerThread
import com.yajatkumar.newsapp.data.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


/**
 * The News Repository to keep copy of the dao in room database
 */
class NewsRepository(private val newsDao: NewsDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allNews: Flow<List<News>> = newsDao.getNews()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(news: News) {
        newsDao.insert(news)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(news: List<News>) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDao.insertAll(*news.toTypedArray())
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        newsDao.deleteAll()
    }
}
