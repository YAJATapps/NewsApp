package com.yajatkumar.newsapp.db

import androidx.lifecycle.*
import com.yajatkumar.newsapp.data.News
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the news repository and
 * an up-to-date list of all news.
 */

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    // Using LiveData and caching what allNews returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNews: LiveData<List<News>> = repository.allNews.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(news: News) = viewModelScope.launch {
        repository.insert(news)
    }

    fun insertList(news: List<News>?) = viewModelScope.launch {
        if (news != null) {
            repository.deleteAll()
            repository.insertAll(news)
        }
    }
}

class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}