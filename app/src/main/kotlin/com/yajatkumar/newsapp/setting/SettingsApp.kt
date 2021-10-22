package com.yajatkumar.newsapp.setting

import android.content.Context

/**
 * Gets the value of the settings
 */
class SettingsApp {

    companion object {

        // If the news uses grid layout
        private const val GRID_NEWS = "grid_news"

        fun isGridNews(context: Context): Boolean {
            return SettingsManager.getBoolean(context, GRID_NEWS, false)
        }

    }

}