package com.yajatkumar.newsapp.fragment

import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.data.Source


class CategoriesFragment : BaseCategoryFragment() {

    override fun loadItems() {
        val categories = mutableListOf<Source>()

        val sourceName = listOf(
            R.string.business,
            R.string.entertainment,
            R.string.general,
            R.string.health,
            R.string.science,
            R.string.sports,
            R.string.technology
        )
        val sourceValue = listOf(
            "business",
            "entertainment",
            "general",
            "health",
            "science",
            "sports",
            "technology"
        )

        for (i in sourceName.indices) {
            categories.add(Source(resources.getString(sourceName[i]), sourceValue[i]))
        }

        sourceViewModel.setCategories(categories)
    }

}