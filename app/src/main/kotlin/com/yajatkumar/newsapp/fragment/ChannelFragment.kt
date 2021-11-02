package com.yajatkumar.newsapp.fragment

import android.util.Log
import android.widget.Toast
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.data.Source
import com.yajatkumar.newsapp.util.APIkey
import com.yajatkumar.newsapp.util.NewsAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChannelFragment : BaseCategoryFragment() {

    override fun loadItems() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: NewsAPI = retrofit.create(NewsAPI::class.java) ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Response from GET request
                val response = service.newsSources(APIkey.key(), "en") ?: return@launch
                var sources: List<Source>? = null

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val items = response.body()
                        if (items != null) {
                            if (items.status == "error") {
                                // Error
                                Log.e("error", response.code().toString())
                                newsFailedToast()
                            } else if (items.status == "ok") {
                                sources = items.sources
                            }
                        }
                    } else {
                        Log.e("error", response.code().toString())
                        newsFailedToast()
                    }

                    // Update the categories into the sourceViewModel
                    sources?.let { sourceViewModel.setCategories(it) }
                }
            } catch (e: Exception) {
                e.message?.let { Log.e("error", it) }
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    newsFailedToast()
                }
            }
        }
    }

    /**
     * Toast about failing to load news
     */
    private fun newsFailedToast() {
        if (context != null)
            Toast.makeText(context, resources.getString(R.string.failed_news), Toast.LENGTH_LONG)
                .show()
    }
}