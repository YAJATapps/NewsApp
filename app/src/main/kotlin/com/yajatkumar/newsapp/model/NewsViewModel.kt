package com.yajatkumar.newsapp.model

import androidx.lifecycle.*
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.db.NewsRepository
import kotlinx.coroutines.launch

/**
 * View Model to an up-to-date list of all news.
 * It keeps a reference to the news repository if its not null or it will not use Room database
 */
class NewsViewModel(private val repository: NewsRepository?) : ViewModel() {

    // Using LiveData and caching what allNews returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNews: LiveData<List<News>> =
        repository?.allNews?.asLiveData() ?: MutableLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertList(news: List<News>?) = viewModelScope.launch {
        if (repository == null) {
            // If repo is null the allNews is mutable
            (allNews as MutableLiveData).value = news
        } else {
            if (news != null) {
                repository.deleteAll()
                repository.insertAll(news)
            }
        }
    }
}

class NewsViewModelFactory(private val repository: NewsRepository?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}