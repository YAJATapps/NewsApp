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


class SearchFragment : BaseFragment() {

    private lateinit var binding: SearchFragmentBinding

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory()
    }

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
                if (s.isNotEmpty())
                    searchNews(s.toString())
                else
                    searchViewModel.allNews.value = null
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

    override fun setRecyclerView() {
        mainRecycler = binding.searchRecycler
    }

    private fun searchNews(query: String?) {
        if (query == null)
            return

        val service = retrofitService()

        CoroutineScope(Dispatchers.IO).launch {

            // Response from GET request
            val response = service.searchNewsList(APIkey.key(), query)
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

                // Update the news into the searchViewModel
                newsList?.let { searchViewModel.setNews(it) }
            }
        }
    }

}