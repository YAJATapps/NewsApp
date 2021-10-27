package com.yajatkumar.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yajatkumar.newsapp.data.News

class SearchViewModel : ViewModel() {

    val allNews: MutableLiveData<List<News>> = MutableLiveData<List<News>>()

    fun setNews(news: List<News>) {
        allNews.value = news
    }

}

class SearchViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}