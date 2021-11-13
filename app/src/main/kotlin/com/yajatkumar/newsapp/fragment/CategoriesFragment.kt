package com.yajatkumar.newsapp.fragment

import com.yajatkumar.newsapp.R
import com.yajatkumar.newsapp.data.Source


/**
 * The categories fragment that shows different news categories such as business, entertainment etc.
 */
class CategoriesFragment : BaseCategoryFragment() {

    /**
     * Load the items for news categories by getting the titles, values and their icons from resources
     * The drawables are passed as a resource url
     */
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
        val sourceUrl = listOf(
            R.drawable.ic_business,
            R.drawable.ic_entertainment,
            R.drawable.ic_general,
            R.drawable.ic_health,
            R.drawable.ic_science,
            R.drawable.ic_sports,
            R.drawable.ic_technology
        )

        for (i in sourceName.indices) {
            categories.add(
                Source(
                    resources.getString(sourceName[i]),
                    sourceValue[i],
                    "android.resource://com.yajatkumar.newsapp/" + sourceUrl[i]
                )
            )
        }

        sourceViewModel.setCategories(categories)
    }

}