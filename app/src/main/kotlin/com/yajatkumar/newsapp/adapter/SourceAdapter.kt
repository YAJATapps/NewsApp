package com.yajatkumar.newsapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.data.Source
import com.yajatkumar.newsapp.holder.SourceHolder
import com.yajatkumar.newsapp.util.ActivityUtil.Companion.launchNews

class SourceAdapter(private var context: Context) : RecyclerView.Adapter<SourceHolder>() {

    private var sourceList: List<Source> = ArrayList()

    // Whether this adapter is inside CategoriesFragment
    private var category: Boolean = false

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SourceHolder {
        val layout: Int = R.layout.source_list_view

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layout, viewGroup, false)

        val holder = SourceHolder(view)
        view.setOnClickListener {
            launchNews(context, sourceList[holder.adapterPosition], category)
        }

        return holder
    }

    override fun onBindViewHolder(holder: SourceHolder, position: Int) {
        val sourceItem = sourceList[position]
        holder.sourceText?.text = sourceItem.name

        val image = if (category)
            Uri.parse(sourceItem.url)
        else
            "https://www.google.com/s2/favicons?sz=48&domain_url=" + sourceItem.url

        // Load the source icon in source image view
        holder.sourceIcon?.let {
            Glide.with(context)
                .load(image)
                .error(R.drawable.ic_error)
                .signature(ObjectKey(sourceItem.id))
                .transition(DrawableTransitionOptions.withCrossFade()).into(it)
        }
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    fun setCategories(l: List<Source>) {
        sourceList = l

        if (sourceList.isNotEmpty())
            category = sourceList[0].id == "business"

        notifyDataSetChanged()
    }

}