package com.yajatkumar.newsapp

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class ReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newsWebView = WebView(this)
        newsWebView.webViewClient = WebViewClient()
        setContentView(newsWebView)

        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")

        if (title == null || url == null || title.isEmpty() || url.isEmpty()) {
            // Exit activity if title or url was not set
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