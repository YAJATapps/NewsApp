package com.yajatkumar.newsapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


/**
 * The home fragment that is used to show the default page of the news with general news
 * It is loaded by default on start
 */
class HomeFragment : BaseNewsFragment() {

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
            newsFailedToast()
        }
    }

    /**
     * Set The view model to observe the news and set it in adapter
     */
    override fun setViewModel() {
        // Add an observer on the LiveData returned by getNews.
        // The onChanged() method fires when the observed data changes and the activity is in the foreground.
        newsViewModel.allNews.observe(viewLifecycleOwner) { news ->
            // Update the cached copy of the news in the adapter.
            newsAdapter.setNews(news)
        }
    }

    /**
     * Set the recyclerView from binding
     */
    override fun setRecyclerView() {
        mainRecycler = binding.mainRecycler
    }

    /**
     * Update the news from the API
     */
    override fun loadNews() {
        val service = retrofitService() ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Response from GET request
                val response = service.newsList(APIkey.key(requireContext()), "us") ?: return@launch
                var newsList: List<News>? = null

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val items = response.body()
                        if (items != null) {
                            if (items.status == "error") {
                                if (items.code == "apiKeyInvalid") {
                                    // Handle the case when api key is not valid
                                    Log.e("errorInvalidAPI", response.code().toString())
                                    newsFailedToast()
                                } else {
                                    // Other error
                                    Log.e("error", response.code().toString())
                                    newsFailedToast()
                                }
                            } else if (items.status == "ok") {
                                newsList = items.articles
                            }
                        }
                    } else {
                        Log.e("error", response.code().toString())
                        newsFailedToast()
                    }

                    // Update the news into the newsViewModel
                    newsViewModel.insertList(newsList)
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

}