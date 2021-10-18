package com.yajatkumar.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.adapters.NewsAdapter
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainRecycler = binding.mainRecycler

        val newsAdapter = NewsAdapter(this)
        mainRecycler.adapter = newsAdapter
        mainRecycler.layoutManager = LinearLayoutManager(this)

        val newsList = listOf(News(1, "https://cbsnews1.cbsistatic.com/hub/i/r/2021/06/01/1b30335b-7026-40a5-a60c-fb0f07ce040c/thumbnail/1200x630/8d37e32fc0650c8704926275bc43275b/cbsnews-prime-day-header.jpg", "News item 1"))
        newsAdapter.setNews(newsList)

    }


}