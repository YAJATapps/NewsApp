package com.yajatkumar.newsapp.util

import android.util.Base64

class APIkey {
    companion object {

        /**
         * The API key for https://newsapi.org
         */
        fun key(): String {
            val key = "NWYzODlhYmEzYzcwNDhhZjk2ZDVhZDM0MDhkNWJlOGI="
            return String(Base64.decode(key.toByteArray(), Base64.DEFAULT))
        }

    }
}