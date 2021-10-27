package com.yajatkumar.newsapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.databinding.RecyclerViewBinding
import com.yajatkumar.newsapp.db.NewsRepository
import com.yajatkumar.newsapp.db.NewsRoomDatabase
import com.yajatkumar.newsapp.model.NewsViewModel
import com.yajatkumar.newsapp.model.NewsViewModelFactory
import com.yajatkumar.newsapp.util.APIkey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : BaseFragment() {

    private lateinit var binding: RecyclerViewBinding

    private val newsViewModel: NewsViewModel by viewModels {
        val database = NewsRoomDatabase.getDatabase(requireContext())
        val repository by lazy { NewsRepository(database.newsDao()) }
        NewsViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecyclerViewBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        try {
            loadNews()
        } catch (e: Exception) {
            e.message?.let { Log.e("loadNews failed", it) }
            Toast.makeText(rootView.context, "Failed to load news", Toast.LENGTH_LONG).show()
        }
    }

    override fun setViewModel() {
        // Add an observer on the LiveData returned by getNews.
        // The onChanged() method fires when the observed data changes and the activity is in the foreground.
        newsViewModel.allNews.observe(viewLifecycleOwner) { news ->
            // Update the cached copy of the news in the adapter.
            newsAdapter.setNews(news)
        }
    }

    override fun setRecyclerView() {
        mainRecycler = binding.mainRecycler
    }

    /**
     * Update the news from the API
     */
    private fun loadNews() {
        val service = retrofitService()

        CoroutineScope(Dispatchers.IO).launch {

            // Response from GET request
            val response = service.newsList(APIkey.key(), "us")
            var newsList: List<News>? = null

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        if (items.status == "error") {
                            if (items.code == "apiKeyInvalid") {
                                // Handle the case when api key is not valid
                            } else {
                                // Other error
                            }
                        } else if (items.status == "ok") {
                            newsList = items.articles
                        }
                    }
                } else {
                    Log.e("error", response.code().toString())
                }

                // Update the news into the newsViewModel
                newsViewModel.insertList(newsList)
            }
        }
    }

}