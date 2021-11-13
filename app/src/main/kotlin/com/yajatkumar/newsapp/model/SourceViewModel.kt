package com.yajatkumar.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yajatkumar.newsapp.data.Source


/**
 * View Model to an up-to-date list of all source items.
 */
class SourceViewModel : ViewModel() {

    val allCategories: MutableLiveData<List<Source>> = MutableLiveData<List<Source>>()

    fun setCategories(news: List<Source>) {
        allCategories.value = news
    }

}

class SourceViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SourceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SourceViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}