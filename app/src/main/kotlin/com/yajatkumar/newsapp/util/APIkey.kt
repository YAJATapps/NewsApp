package com.yajatkumar.newsapp.util

import android.content.Context
import android.util.Base64
import com.yajatkumar.newsapp.setting.SettingsApp

class APIkey {
    companion object {

        /**
         * The API key for https://newsapi.org
         */
        fun key(context: Context): String {
            val prefKey = SettingsApp.getAPIkey(context)

            if (prefKey.isEmpty()) {
                val key = "NWYzODlhYmEzYzcwNDhhZjk2ZDVhZDM0MDhkNWJlOGI="
                return String(Base64.decode(key.toByteArray(), Base64.DEFAULT))
            }

            return prefKey
        }


    }
}