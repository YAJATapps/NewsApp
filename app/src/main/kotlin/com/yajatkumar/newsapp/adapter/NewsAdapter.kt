package com.yajatkumar.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.holder.NewsHolder
import com.yajatkumar.newsapp.setting.SettingsApp

class NewsAdapter(private var context: Context) : RecyclerView.Adapter<NewsHolder>() {

    private var newsList: List<News>? = ArrayList()
    private var grid: Boolean = SettingsApp.isGridNews(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewsHolder {
        val layout: Int = if (grid)
            R.layout.news_grid_item
        else
            R.layout.news_list_item

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layout, viewGroup, false)

        val holder = NewsHolder(view)
        view.setOnClickListener {
            openNewsActivity()
        }

        return holder
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsItem = newsList?.get(position)

        if (newsItem != null) {
            holder.newsText?.text = newsItem.title

            val image = newsItem.urlToImage

            holder.newsIcon?.let {
                Glide.with(context)
                    .load(image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_error)
                    .signature(ObjectKey(newsItem.id))
                    .transition(DrawableTransitionOptions.withCrossFade()).into(it)
            }
        }
    }

    override fun getItemCount(): Int {
        if (newsList == null)
            return 0
        return newsList!!.size
    }

    fun setNews(l: List<News>?) {
        newsList = l
        notifyDataSetChanged()
    }

    fun setGrid(grid: Boolean) {
        this.grid = grid
    }

    private fun openNewsActivity() {
        // TODO
    }

}