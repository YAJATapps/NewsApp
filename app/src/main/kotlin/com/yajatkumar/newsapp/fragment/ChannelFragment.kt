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


/**
 * The channel fragment that shows different channels for news
 */
class ChannelFragment : BaseCategoryFragment() {

    /**
     * Load the items for news channels by getting the sources from news API
     */
    override fun loadItems() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: NewsAPI = retrofit.create(NewsAPI::class.java) ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Response from GET request
                val response = service.newsSources(APIkey.key(requireContext()), "en") ?: return@launch
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
                // Log the exception and show error toast
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