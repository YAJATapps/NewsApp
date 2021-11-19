package com.yajatkumar.newsapp.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.adapter.NewsAdapter
import com.yajatkumar.newsapp.setting.SettingsApp
import com.yajatkumar.newsapp.util.NewsAPI
import com.yajatkumar.newsapp.util.ShakeDetector
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * The base fragment for Home, News and Search fragment.
 *
 * Loads a recyclerview into the view that supports linear or grid layout manager.
 *
 * The recyclerview uses NewsAdapter.
 *
 * Also adds a shake listener to switch between list and grid views for items.
 */
abstract class BaseNewsFragment : Fragment() {

    /**
     * The main recyclerView to show news
     */
    protected lateinit var mainRecycler: RecyclerView

    /**
     * The adapter that shows news
     */
    protected lateinit var newsAdapter: NewsAdapter

    /**
     * Shake to change layout
     */
    private val shakeToSwap by lazy { SettingsApp.isShakeSwap(requireContext()) }
    private val sensorManager by lazy { context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    private val accelerometer by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    private val shakeDetector by lazy { ShakeDetector() }

    /**
     * Set The view model to observe the news and set it in adapter
     */
    abstract fun setViewModel()

    /**
     * Set The recycler view
     */
    abstract fun setRecyclerView()

    /**
     * Load news into viewModel
     */
    abstract fun loadNews()

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        setRecyclerView()
        loadAdapter()
        loadLayoutManager()
        setViewModel()

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
     * Reverse the news layout from list to grid or from grid to list
     */
    private fun swapNewsLayout() {
        val grid = SettingsApp.isGridNews(requireContext())
        SettingsApp.setGridNews(requireContext(), !grid)

        // Reload the adapter after swapping layout
        loadLayoutManager()
        loadAdapter()
        setViewModel()
    }

    override fun onResume() {
        super.onResume()

        // Register shake listener if shakeToSwap is enabled
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

        // Unregister shake listener if shakeToSwap is enabled
        if (shakeToSwap) {
            sensorManager.unregisterListener(shakeDetector);
        }
    }

    /**
     * Retrofit Service
     */
    protected fun retrofitService(): NewsAPI? {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NewsAPI::class.java)
    }

    /**
     * Toast about failing to load news
     */
    protected fun newsFailedToast() {
        if (context != null)
            Toast.makeText(context, resources.getString(R.string.failed_news), Toast.LENGTH_LONG)
                .show()
    }
}