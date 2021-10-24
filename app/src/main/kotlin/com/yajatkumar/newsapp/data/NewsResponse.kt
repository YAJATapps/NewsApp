package com.yajatkumar.newsapp.data

data class NewsResponse(
    val status: String,
    val articles: List<News>?,
    val code: String?,
)
