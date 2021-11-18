package com.yajatkumar.newsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebSettings
import com.yajatkumar.newsapp.util.ActivityUtil.Companion.launchLink


/**
 * The reader activity that open the news url in a webView
 */
class ReaderActivity : AppCompatActivity() {

    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("title")
        val urlInt = intent.getStringExtra("url")

        url = urlInt ?: ""

        if (title == null || title.isEmpty() || url.isEmpty()) {
            // Exit activity if title or url was not set
            finish()
        }

        url = url.replace("http://", "https://")

        val newsWebView = WebView(this)
        newsWebView.webViewClient = WebViewClient()

        if (allowedJS(url)) {
            // Enable javascript for the webView
            val webSettings: WebSettings = newsWebView.settings
            webSettings.javaScriptEnabled = true
        }

        setContentView(newsWebView)

        // Display the back button in actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the title for reader
        supportActionBar?.title = title

        // Load th url in the webView
        newsWebView.loadUrl(url)
    }


    /**
     * Inflate the settings menu into actionBar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate menu
        menuInflater.inflate(R.menu.reader_menu, menu)
        return true
    }

    /**
     * Set the option selected action for actionBar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_link -> {
                launchLink(this, url, findViewById(R.id.action_link))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Websites that will not be allowed to use javascript in web view
     */
    private val noJSlist =
        listOf(
            "https://www.nytimes.com",
            "https://www.washingtonpost.com"
        )

    /**
     * Determine if websites can use javascript
     * @param url - The url of the website
     */
    private fun allowedJS(url: String): Boolean {
        for (i in noJSlist) {
            if (url.contains(i))
                return false
        }
        return true
    }

}