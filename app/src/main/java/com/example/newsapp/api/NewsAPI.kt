package com.example.newsapp.api

import com.example.newsapp.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val  API_KEY="069840494d46481da1da4e738c588eb8"
interface NewsAPI {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String="us",
        @Query("apiKey") apiKey: String=API_KEY
    ): Response<News>
}