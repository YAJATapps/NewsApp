package com.yajatkumar.newsapp.data

/**
 * The News Response object from a sources query to News API
 */
data class SourceResponse(
    val status: String,
    val sources: List<Source>?
)
