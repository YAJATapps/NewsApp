package com.yajatkumar.newsapp

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebSettings


class ReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("title")
        var url = intent.getStringExtra("url")

        if (title == null || url == null || title.isEmpty() || url.isEmpty()) {
            // Exit activity if title or url was not set
            finish()
        }

        if (url != null)
            url = url.replace("http://", "https://")

        val newsWebView = WebView(this)
        newsWebView.webViewClient = WebViewClient()

        if (url != null && allowedJS(url)) {
            // Enable javascript for the webView
            val webSettings: WebSettings = newsWebView.settings
            webSettings.javaScriptEnabled = true
        }

        setContentView(newsWebView)

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

    // Websites that will not be allowed to use javascript in web view
    private val noJSlist =
        listOf(
            "https://www.nytimes.com",
            "https://www.washingtonpost.com"
        )

    // Determine if websites can use javascript
    private fun allowedJS(url: String): Boolean {
        for (i in noJSlist) {
            if (url.contains(i))
                return false
        }
        return true
    }

}