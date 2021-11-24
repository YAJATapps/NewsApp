package com.yajatkumar.newsapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.data.NewsResponse
import com.yajatkumar.newsapp.databinding.RecyclerViewBinding
import com.yajatkumar.newsapp.model.NewsViewModel
import com.yajatkumar.newsapp.model.NewsViewModelFactory
import com.yajatkumar.newsapp.util.APIkey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


/**
 * The news fragment that is launched when clicking on some item in channel or category fragment
 * This shows a list of news depending on the source id and launch fragment
 */
class NewsFragment : BaseNewsFragment() {

    private lateinit var binding: RecyclerViewBinding

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(null)
    }

    // Source id
    private lateinit var source: String

    // Whether this fragment is launched from category fragment (or channel fragment if false)
    private var category: Boolean = false

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
     * Load the news from the API
     */
    override fun loadNews() {
        val service = retrofitService() ?: return

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Response from GET request
                val response: Response<NewsResponse> = if (category)
                    service.newsFromCategory(APIkey.key(requireContext()), "us", source) ?: return@launch
                else
                    service.newsFromSource(APIkey.key(requireContext()), source) ?: return@launch

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

                    // Set the news into the newsViewModel
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

    companion object {
        /**
         * New instance for this fragment
         * @param c: Whether this fragment shows news for category
         * @param s: The id of the source
         */
        fun newInstance(c: Boolean, s: String): NewsFragment {
            val fragment = NewsFragment()
            fragment.source = s
            fragment.category = c
            return fragment
        }
    }

}