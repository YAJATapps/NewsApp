package com.yajatkumar.newsapp.setting

import android.content.Context

/**
 * Gets the value of the settings
 */
class SettingsApp {

    companion object {

        // If the news uses grid layout
        private const val GRID_NEWS = "grid_news"

        // The custom set API key
        private const val API_KEY = "api_key"

        // Whether shaking device will switch news list between grid or list view
        private const val SHAKE_SWAP = "shake_to_swap"

        // If the grid layout is enabled for news
        fun isGridNews(context: Context): Boolean {
            return SettingsManager.getBoolean(context, GRID_NEWS, false)
        }

        // Set if the grid layout is enabled for news
        fun setGridNews(context: Context, value: Boolean) {
            SettingsManager.putBoolean(context, GRID_NEWS, value)
        }

        // The value of API key
        fun getAPIkey(context: Context): String {
            return SettingsManager.getString(context, API_KEY, "")
        }

        // Set the value of API key
        fun setAPIkey(context: Context, value: String) {
            SettingsManager.putString(context, API_KEY, value)
        }

        // If the shake to swap is enabled
        fun isShakeSwap(context: Context): Boolean {
            return SettingsManager.getBoolean(context, SHAKE_SWAP, false)
        }

    }

}