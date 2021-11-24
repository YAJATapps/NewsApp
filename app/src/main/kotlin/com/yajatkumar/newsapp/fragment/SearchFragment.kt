package com.yajatkumar.newsapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yajatkumar.newsapp.databinding.SearchFragmentBinding
import com.yajatkumar.newsapp.model.SearchViewModel
import com.yajatkumar.newsapp.model.SearchViewModelFactory
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.util.APIkey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * The fragment that contains the searchbar and the list to show the results
 */
class SearchFragment : BaseNewsFragment() {

    private lateinit var binding: SearchFragmentBinding

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory()
    }

    // The query that is currently there in searchbar
    private var query: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        // Listen to changes in searchbar editText
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) {
                    // Set the editText value to query
                    query = s.toString()

                    // Load news by searching that query
                    loadNews()
                } else {
                    // Query is null when searchbar is empty
                    query = null

                    // List should be empty when searchbar is empty
                    searchViewModel.allNews.value = null
                }
            }
        })

    }

    /**
     * Set The view model to observe the news and set it in adapter
     */
    override fun setViewModel() {
        // Add an observer on the LiveData returned by allNews.
        // The onChanged() method fires when the observed data changes and the activity is in the foreground.
        searchViewModel.allNews.observe(viewLifecycleOwner) { news ->
            // Update the cached copy of the news in the adapter.
            newsAdapter.setNews(news)
        }
    }

    /**
     * Set the recyclerView from binding
     */
    override fun setRecyclerView() {
        mainRecycler = binding.searchRecycler
    }

    /**
     * Load the news by searching query
     */
    override fun loadNews() {
        searchNews(query)
    }

    /**
     * Search the news from query
     * @param query - The query to search the news for
     */
    private fun searchNews(query: String?) {
        // Return if the query is null
        if (query == null)
            return

        val service = retrofitService() ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Response from GET request
                val response = service.searchNewsList(APIkey.key(requireContext()), query) ?: return@launch
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

                    // Update the news into the searchViewModel
                    newsList?.let { searchViewModel.setNews(it) }
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