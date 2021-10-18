package com.yajatkumar.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.data.News
import com.yajatkumar.newsapp.holders.NewsHolder

class NewsAdapter(private var context: Context) : RecyclerView.Adapter<NewsHolder>() {

    private var newsList: List<News>? = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.news_list_item, viewGroup, false)

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
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
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

    private fun openNewsActivity() {
        // TODO
    }

}