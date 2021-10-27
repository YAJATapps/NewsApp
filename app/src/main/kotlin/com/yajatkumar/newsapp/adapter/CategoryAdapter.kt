package com.yajatkumar.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.data.Category
import com.yajatkumar.newsapp.holder.CategoryHolder

class CategoryAdapter(private var context: Context) : RecyclerView.Adapter<CategoryHolder>() {

    private var categoryList: List<Category> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CategoryHolder {
        val layout: Int = R.layout.category_list_view

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layout, viewGroup, false)

        val holder = CategoryHolder(view)
        view.setOnClickListener {
            openCategory(holder.adapterPosition)
        }

        return holder
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val categoryItem = categoryList[position]
        holder.categoryText?.text = categoryItem.name
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setCategories(l: List<Category>) {
        categoryList = l
        notifyDataSetChanged()
    }

    // Open the news fragment activity with the clicked news item
    private fun openCategory(position: Int) {
        val news = categoryList[position]
        /*val i = Intent()
        i.setClass(context, NewsActivity::class.java)
        i.putExtra("name", news.name)
        i.putExtra("value", news.value)
        context.startActivity(i)*/
    }

}