package com.yajatkumar.newsapp.data


/**
 * The News Response object from a top-headlines or everything query to News API
 */
data class NewsResponse(
    val status: String,
    val articles: List<News>?,
    val code: String?,
)
