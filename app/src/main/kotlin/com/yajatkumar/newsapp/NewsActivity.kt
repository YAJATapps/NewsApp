package com.yajatkumar.newsapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.yajatkumar.newsapp.databinding.ActivitySearchBinding
import com.yajatkumar.newsapp.fragment.NewsFragment


/**
 * The news activity that shows the news from the channels or from some category (business etc.) depending on bundles
 */
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

        // Display the back button in actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the title for news activity
        supportActionBar?.title = name

        // Replace the fragment_container with NewsFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NewsFragment.newInstance(category, id!!))
            .commitAllowingStateLoss()
    }

    // Finish the activity when back button is clicked
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