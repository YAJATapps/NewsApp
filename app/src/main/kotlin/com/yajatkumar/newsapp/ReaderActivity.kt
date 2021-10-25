package com.yajatkumar.newsapp

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.yajatkumar.newsapp.databinding.ActivityReaderBinding


class ReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReaderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id = intent.getLongExtra("id", -1)
        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")

        if (id == (-1).toLong()) {
            // Exit activity if id was not set
            finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title

        val myWebView: WebView = binding.webView
        if (url != null) {
            myWebView.loadUrl(url)
        }
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