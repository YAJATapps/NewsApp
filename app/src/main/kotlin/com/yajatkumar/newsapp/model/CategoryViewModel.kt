package com.yajatkumar.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yajatkumar.newsapp.data.Category

class CategoryViewModel : ViewModel() {

    val allCategories: MutableLiveData<List<Category>> = MutableLiveData<List<Category>>()

    fun setCategories(news: List<Category>) {
        allCategories.value = news
    }

}

class CategoryViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}