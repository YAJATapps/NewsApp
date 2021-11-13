package com.yajatkumar.newsapp.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.R


/**
 * The viewHolder for news_item
 */
class NewsHolder(v: View) : RecyclerView.ViewHolder(v) {
    var newsIcon: ImageView? = null // The icon of this news item
    var newsText: TextView? = null // The name of this news item

    init {
        newsIcon = v.findViewById<View>(R.id.news_icon) as ImageView
        newsText = v.findViewById<View>(R.id.news_text) as TextView
    }
}