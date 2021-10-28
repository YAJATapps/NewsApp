package com.yajatkumar.newsapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.yajatkumar.newsapp.databinding.ActivitySearchBinding
import com.yajatkumar.newsapp.fragment.NewsFragment

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        val category = intent.getBooleanExtra("category", false)

        if (name == null || id == null || name.isEmpty() || id.isEmpty()) {
            // Exit activity if name or id was not set
            finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = name

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NewsFragment.newInstance(category, id!!))
            .commitAllowingStateLoss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}