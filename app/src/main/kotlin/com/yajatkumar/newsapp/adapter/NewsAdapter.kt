package com.yajatkumar.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.holder.NewsHolder
import com.yajatkumar.newsapp.setting.SettingsApp
import com.yajatkumar.newsapp.util.ActivityUtil.Companion.launchReader


/**
 * The news adapter used in BaseNewsFragment
 */
class NewsAdapter(private var context: Context) : RecyclerView.Adapter<NewsHolder>() {

    // List of items in this adapter
    private var newsList: List<News>? = ArrayList()

    // If the grid layout is enabled
    private var grid: Boolean = SettingsApp.isGridNews(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewsHolder {
        // Load layout based on grid layout setting
        val layout: Int = if (grid)
            R.layout.news_grid_item
        else
            R.layout.news_list_item

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layout, viewGroup, false)

        val holder = NewsHolder(view)
        view.setOnClickListener {
            val news = newsList?.get(holder.adapterPosition)
            if (news != null) {
                // Launch the reader activity when clicking on news item
                launchReader(context, news, holder.itemView)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsItem = newsList?.get(position)

        if (newsItem != null) {
            holder.newsText?.text = newsItem.title

            val image = newsItem.urlToImage

            val id: Long = newsItem.id ?: (0..99999).random().toLong()

            try {
                holder.newsIcon?.let {
                    Glide.with(context)
                        .load(image)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_photo)
                        .error(R.drawable.ic_error)
                        .signature(ObjectKey(id))
                        .transition(DrawableTransitionOptions.withCrossFade()).into(it)
                }
            } catch (e: Exception) {
            }
        }
    }

    // Items count
    override fun getItemCount(): Int {
        if (newsList == null)
            return 0
        return newsList!!.size
    }

    // Set the items
    fun setNews(l: List<News>?) {
        newsList = l
        notifyDataSetChanged()
    }

}