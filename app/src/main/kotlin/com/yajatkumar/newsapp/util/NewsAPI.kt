package com.yajatkumar.newsapp.util

import com.yajatkumar.newsapp.data.NewsResponse
import com.yajatkumar.newsapp.data.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsAPI {
    @GET("top-headlines")
    suspend fun newsList(@Query("apiKey") apiKey: String, @Query("country") country: String): Response<NewsResponse>

    @GET("everything")
    suspend fun searchNewsList(@Query("apiKey") apiKey: String, @Query("q") q: String): Response<NewsResponse>

    @GET("top-headlines/sources")
    suspend fun newsSources(@Query("apiKey") apiKey: String): Response<SourceResponse>
}