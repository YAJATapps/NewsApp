package com.yajatkumar.newsapp.util

import android.content.Context
import android.content.Intent
import com.yajatkumar.newsapp.NewsActivity
import com.yajatkumar.newsapp.ReaderActivity
import com.yajatkumar.newsapp.SettingsActivity
import com.yajatkumar.newsapp.SetupActivity
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.data.Source

/**
 * A class to launch other activities
 */
class ActivityUtil {
    companion object {

        /**
         * Open the news fragment activity with the clicked item
         * @param context - The context
         * @param source - The source object to get name and id
         * @param category - The boolean to indicate whether the activity is launched from CategoriesFragment
         */
        fun launchNews(context: Context, source: Source, category: Boolean) {
            val i = Intent()
            i.setClass(context, NewsActivity::class.java)
            i.putExtra("name", source.name)
            i.putExtra("id", source.id)
            i.putExtra("category", category)

            context.startActivity(i)
        }

        /**
         * Open the news reader activity with the clicked news item
         * @param context - The context
         * @param news - The news object to get title and url
         */
        fun launchReader(context: Context, news: News) {
            val i = Intent()
            i.setClass(context, ReaderActivity::class.java)
            i.putExtra("title", news.title)
            i.putExtra("url", news.url)
            context.startActivity(i)
        }

        /**
         * Launch the setup activity to set API key
         * @param context - The context
         */
        fun launchSetup(context: Context) {
            val i = Intent()
            i.setClass(context, SetupActivity::class.java)
            context.startActivity(i)
        }

        /**
         * Launch the settings activity to set preferences
         * @param context - The context
         */
        fun launchSettings(context: Context) {
            val i = Intent()
            i.setClass(context, SettingsActivity::class.java)
            context.startActivity(i)
        }

    }
}