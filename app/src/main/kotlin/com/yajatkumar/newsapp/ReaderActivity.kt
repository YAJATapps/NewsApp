package com.yajatkumar.newsapp

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class ReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newsWebView = WebView(this)
        setContentView(newsWebView)

        val id = intent.getLongExtra("id", -1)
        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")

        if (id == (-1).toLong()) {
            // Exit activity if id was not set
            finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title

        if (url != null) {
            newsWebView.loadUrl(url)
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