package com.yajatkumar.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.data.Source
import com.yajatkumar.newsapp.holder.SourceHolder

class SourceAdapter(private var context: Context) : RecyclerView.Adapter<SourceHolder>() {

    private var sourceList: List<Source> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SourceHolder {
        val layout: Int = R.layout.source_list_view

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layout, viewGroup, false)

        val holder = SourceHolder(view)
        view.setOnClickListener {
            sourceClicked(holder.adapterPosition)
        }

        return holder
    }

    override fun onBindViewHolder(holder: SourceHolder, position: Int) {
        val sourceItem = sourceList[position]
        holder.sourceText?.text = sourceItem.name
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    fun setCategories(l: List<Source>) {
        sourceList = l
        notifyDataSetChanged()
    }

    // Open the news fragment activity with the clicked item
    private fun sourceClicked(position: Int) {
        val news = sourceList[position]
        /*val i = Intent()
        i.setClass(context, NewsActivity::class.java)
        i.putExtra("name", news.name)
        i.putExtra("value", news.value)
        context.startActivity(i)*/
    }

}