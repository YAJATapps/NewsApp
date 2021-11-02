package com.yajatkumar.newsapp.util

import com.yajatkumar.newsapp.data.NewsResponse
import com.yajatkumar.newsapp.data.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsAPI {

    /**
     * Top headlines by country
     * @param apiKey: The API key for news API
     * @param country: The country to get news for
     */
    @GET("top-headlines")
    suspend fun newsList(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String
    ): Response<NewsResponse>?

    /**
     * Search everything with query
     * @param apiKey: The API key for news API
     * @param q: The query to search news for
     */
    @GET("everything")
    suspend fun searchNewsList(
        @Query("apiKey") apiKey: String,
        @Query("q") q: String
    ): Response<NewsResponse>?

    /**
     * Return the sources available from the API
     * @param apiKey: The API key for news API
     * @param language: The language to get news for
     */
    @GET("top-headlines/sources")
    suspend fun newsSources(
        @Query("apiKey") apiKey: String,
        @Query("language") language: String
    ): Response<SourceResponse>?

    /**
     * Search headlines from this source
     * @param apiKey: The API key for news API
     * @param sources: The name of the source to get news from
     */
    @GET("top-headlines")
    suspend fun newsFromSource(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources: String
    ): Response<NewsResponse>?

    /**
     * Search headlines from this category
     * @param apiKey: The API key for news API
     * @param country: The country to get news for
     * @param category: The name of the category to get news from
     */
    @GET("top-headlines")
    suspend fun newsFromCategory(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String
    ): Response<NewsResponse>?

}