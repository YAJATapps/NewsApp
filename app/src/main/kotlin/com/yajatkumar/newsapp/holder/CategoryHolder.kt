package com.yajatkumar.newsapp.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.R


class CategoryHolder(v: View) : RecyclerView.ViewHolder(v) {
    var categoryText: TextView? = null // The name of this category

    init {
        categoryText = v.findViewById<View>(R.id.category_text) as TextView
    }
}