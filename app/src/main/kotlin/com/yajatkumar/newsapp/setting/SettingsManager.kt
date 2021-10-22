package com.yajatkumar.newsapp.setting

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


/**
 * Helper class for SharedPreferences
 */

class SettingsManager {
    companion object {

        /**
         * Returns a SharedPreferences object
         * @param context - The context
         */
        private fun get(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        /**
         * Return the string value for the key
         * @param context - The context
         * @param key - The key to get the value for
         * @param defValue - The default value if value does not exists
         */
        fun getString(context: Context, key: String, defValue: String): String {
            return get(context).getString(key, defValue) ?: return defValue
        }

        /**
         * Return the boolean value for the key
         * @param context - The context
         * @param key - The key to get the value for
         * @param defValue - The default value if value does not exists
         */
        fun getBoolean(context: Context, key: String, defValue: Boolean): Boolean {
            return get(context).getBoolean(key, defValue)
        }
    }
}