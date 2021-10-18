package com.yajatkumar.newsapp

import com.yajatkumar.newsapp.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsAPI {
    @GET("top-headlines")
    suspend fun newsList(@Query("apiKey") apiKey: String, @Query("country") country: String): Response<NewsResponse>
}