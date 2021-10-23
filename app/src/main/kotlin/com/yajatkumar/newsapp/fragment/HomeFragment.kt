package com.yajatkumar.newsapp.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.NewsAPI
import com.yajatkumar.newsapp.adapter.NewsAdapter
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.databinding.HomeFragmentBinding
import com.yajatkumar.newsapp.db.NewsRepository
import com.yajatkumar.newsapp.db.NewsRoomDatabase
import com.yajatkumar.newsapp.model.NewsViewModel
import com.yajatkumar.newsapp.model.NewsViewModelFactory
import com.yajatkumar.newsapp.setting.SettingsApp
import com.yajatkumar.newsapp.setting.SettingsManager
import com.yajatkumar.newsapp.util.ShakeDetector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    private lateinit var mainRecycler: RecyclerView

    private lateinit var newsAdapter: NewsAdapter

    private val newsViewModel: NewsViewModel by viewModels {
        val database = NewsRoomDatabase.getDatabase(requireContext())
        val repository by lazy { NewsRepository(database.newsDao()) }
        NewsViewModelFactory(repository)
    }

    /**
     * Shake to change layout
     */
    private val shakeToSwap = true
    private val sensorManager by lazy { context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    private val accelerometer by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    private val shakeDetector by lazy { ShakeDetector() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        mainRecycler = binding.mainRecycler

        loadAdapter()
        loadLayoutManager()
        setViewModel()

        loadNews()

        // Set shake listener if it is enabled
        if (shakeToSwap && accelerometer != null) {
            shakeDetector.setOnShakeListener(object : ShakeDetector.OnShakeListener {
                override fun onShake(count: Int) {
                    swapNewsLayout()
                }
            })
        }
    }

    /**
     * Load the news adapter into recyclerview at startup or when layout is updated
     */
    private fun loadAdapter() {
        newsAdapter = NewsAdapter(requireContext())
        mainRecycler.adapter = newsAdapter
    }

    /**
     * Load the layout manager for adapter depending on grid settings
     */
    private fun loadLayoutManager() {
        val grid = SettingsApp.isGridNews(requireContext())
        if (grid)
            mainRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        else
            mainRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    /**
     * Set The view model to observe the news and set it in adapter
     */
    private fun setViewModel() {
        // Add an observer on the LiveData returned by getNews.
        // The onChanged() method fires when the observed data changes and the activity is in the foreground.
        newsViewModel.allNews.observe(viewLifecycleOwner) { news ->
            // Update the cached copy of the news in the adapter.
            newsAdapter.setNews(news)
        }
    }

    /**
     * Update the news from the API
     */
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

    /**
     * Reverse the news layout from list to grid or from grid to list
     */
    private fun swapNewsLayout() {
        val grid = SettingsApp.isGridNews(requireContext())
        SettingsManager.putBoolean(requireContext(), SettingsApp.GRID_NEWS, !grid)
        loadLayoutManager()
        loadAdapter()
        setViewModel()
    }

    override fun onResume() {
        super.onResume()

        if (shakeToSwap) {
            sensorManager.registerListener(
                shakeDetector,
                accelerometer,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause() {
        super.onPause()

        if (shakeToSwap) {
            sensorManager.unregisterListener(shakeDetector);
        }
    }

    /**
     * The API key for https://newsapi.org
     */
    private fun key(): String {
        val key = "NWYzODlhYmEzYzcwNDhhZjk2ZDVhZDM0MDhkNWJlOGI="
        return String(Base64.decode(key.toByteArray(), Base64.DEFAULT))
    }

}