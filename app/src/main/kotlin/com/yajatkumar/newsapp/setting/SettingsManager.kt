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
        fun get(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
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
         * Returns a SharedPreferences.Editor object
         * @param context - The context
         */
        private fun put(context: Context): SharedPreferences.Editor {
            return get(context).edit()
        }

        /**
         * Puts the boolean value into the key
         * @param context - The context
         * @param key - The key to put the value in
         * @param value - The new boolean value for the key
         */
        fun putBoolean(context: Context, key: String, value: Boolean) {
            put(context).putBoolean(key, value).commit()
        }

        /**
         * Puts the string value into the key
         * @param context - The context
         * @param key - The key to put the value in
         * @param value - The new string value for the key
         */
        fun putString(context: Context, key: String, value: String) {
            put(context).putString(key, value).commit()
        }

    }
}