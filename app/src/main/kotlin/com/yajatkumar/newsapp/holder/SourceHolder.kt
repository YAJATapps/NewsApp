package com.yajatkumar.newsapp.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.R


/**
 * The viewHolder for source_list_item
 */
class SourceHolder(v: View) : RecyclerView.ViewHolder(v) {
    var sourceIcon: ImageView? = null // The icon of this source
    var sourceText: TextView? = null // The name of this source

    init {
        sourceIcon = v.findViewById(R.id.source_icon) as ImageView
        sourceText = v.findViewById(R.id.source_text) as TextView
    }
}