package com.yajatkumar.newsapp.util

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
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
         * @param view - The view to start animation from
         */
        fun launchNews(context: Context, source: Source, category: Boolean, view: View) {
            val i = Intent()
            i.setClass(context, NewsActivity::class.java)
            i.putExtra("name", source.name)
            i.putExtra("id", source.id)
            i.putExtra("category", category)

            startActivityWithAnimation(context, i, view)
        }

        /**
         * Open the news reader activity with the clicked news item
         * @param context - The context
         * @param news - The news object to get title and url
         * @param view - The view to start animation from
         */
        fun launchReader(context: Context, news: News, view: View) {
            val i = Intent()
            i.setClass(context, ReaderActivity::class.java)
            i.putExtra("title", news.title)
            i.putExtra("url", news.url)
            startActivityWithAnimation(context, i, view)
        }

        /**
         * Launch the setup activity to set API key
         * @param context - The context
         * @param view - The view to start animation from
         */
        fun launchSetup(context: Context, view: View) {
            val i = Intent()
            i.setClass(context, SetupActivity::class.java)
            startActivityWithAnimation(context, i, view)
        }

        /**
         * Launch the settings activity to set preferences
         * @param context - The context
         * @param view - The view to start animation from
         */
        fun launchSettings(context: Context, view: View) {
            val i = Intent()
            i.setClass(context, SettingsActivity::class.java)
            startActivityWithAnimation(context, i, view)
        }

        /**
         * Launch the link in a browser
         * @param context - The context
         * @param url - The url of the website
         * @param view - The view to start animation from
         */
        fun launchLink(context: Context, url: String, view: View) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivityWithAnimation(context, browserIntent, view)
        }

        /**
         * Launch an activity with clip reveal or scale animation depending on API
         * @param context - The context
         * @param intent - The intent of the activity to start
         * @param view - The view to start animation from
         */
        private fun startActivityWithAnimation(context: Context, intent: Intent, view: View) {
            context.startActivity(intent, animationBundle(view))
        }

        /**
         * Get the bundle for the launch animation
         * @param view - The view
         */
        private fun animationBundle(view: View): Bundle {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                return ActivityOptions.makeClipRevealAnimation(
                    view,
                    0,
                    0,
                    view.measuredWidth,
                    view.measuredHeight
                ).toBundle()

            return ActivityOptions.makeScaleUpAnimation(
                view,
                0,
                0,
                view.measuredWidth,
                view.measuredHeight
            ).toBundle()
        }
    }
}