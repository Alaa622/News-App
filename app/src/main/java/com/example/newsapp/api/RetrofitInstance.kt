package com.example.newsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL="https://newsapi.org/v2/"

object RetrofitInstance {
     private val retrofit by lazy {
          Retrofit.Builder().baseUrl(BASE_URL).
          addConverterFactory(GsonConverterFactory.create()).build()
     }

      val newsAPI by lazy {
          retrofit.create(NewsAPI::class.java)
     }

}