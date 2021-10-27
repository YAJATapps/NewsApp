package com.yajatkumar.newsapp.fragment

import android.util.Log
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
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(NewsAPI::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            // Response from GET request
            val response = service.newsSources(APIkey.key())
            var sources: List<Source>? = null

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        if (items.status == "error") {
                            // Error
                        } else if (items.status == "ok") {
                            sources = items.sources
                        }
                    }
                } else {
                    Log.e("error", response.code().toString())
                }

                // Update the categories into the sourceViewModel
                sources?.let { sourceViewModel.setCategories(it) }
            }
        }


    }

}