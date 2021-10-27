package com.yajatkumar.newsapp.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.R


class SourceHolder(v: View) : RecyclerView.ViewHolder(v) {
    var sourceText: TextView? = null // The name of this source

    init {
        sourceText = v.findViewById<View>(R.id.source_text) as TextView
    }
}