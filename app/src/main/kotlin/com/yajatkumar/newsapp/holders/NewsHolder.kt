package com.yajatkumar.newsapp.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.R

class NewsHolder(v: View) : RecyclerView.ViewHolder(v) {
    var newsIcon: ImageView? = null
    var newsText: TextView? = null

    init {
        newsIcon = v.findViewById<View>(R.id.news_icon) as ImageView
        newsText = v.findViewById<View>(R.id.news_text) as TextView
    }
}