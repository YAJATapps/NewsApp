package com.yajatkumar.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.adapter.NewsAdapter
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.databinding.ActivityMainBinding
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit
import com.yajatkumar.newsapp.db.NewsRepository
import com.yajatkumar.newsapp.db.NewsRoomDatabase
import com.yajatkumar.newsapp.model.NewsViewModel
import com.yajatkumar.newsapp.model.NewsViewModelFactory
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainRecycler: RecyclerView

    private val newsViewModel: NewsViewModel by viewModels {
        val database = NewsRoomDatabase.getDatabase(this)
        val repository by lazy { NewsRepository(database.newsDao()) }
        NewsViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainRecycler = binding.mainRecycler

        val newsAdapter = NewsAdapter(this)
        mainRecycler.adapter = newsAdapter
        mainRecycler.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getNews.
        // The onChanged() method fires when the observed data changes and the activity is in the foreground.
        newsViewModel.allNews.observe(this) { news ->
            // Update the cached copy of the news in the adapter.
            newsAdapter.setNews(news)
        }

        loadNews()
    }

    private fun loadNews() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Retrofit Service
        val service = retrofit.create(NewsAPI::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            // Response from GET request
            val response = service.newsList(key(), "us")
            var newsList: List<News>? = null

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        newsList = items.articles
                    }
                } else {
                    Log.e("error", response.code().toString())
                }

                // Update the news into the newsViewModel
                newsViewModel.insertList(newsList)
            }
        }

    }

    // The API key for https://newsapi.org
    private fun key(): String {
        val key = "NWYzODlhYmEzYzcwNDhhZjk2ZDVhZDM0MDhkNWJlOGI=";
        return String(Base64.decode(key.toByteArray(), Base64.DEFAULT))
    }

}