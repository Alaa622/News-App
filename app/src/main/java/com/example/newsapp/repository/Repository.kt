package com.example.newsapp.repository

import android.app.Application
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import com.example.newsapp.api.NewsAPI
import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.localdatabse.ArticlesRoomDatabase
import com.example.newsapp.localdatabse.FavArticleDAO
import com.example.newsapp.model.FavoriteArticle
import com.example.newsapp.model.News
import retrofit2.Response

class Repository(private val db:ArticlesRoomDatabase) {

    suspend fun getNews()= RetrofitInstance.newsAPI.getNews()

    suspend fun addToFavorite(favoriteArticle: FavoriteArticle)= db.favArticleDao().insert(favoriteArticle)

    fun getAllFavoriteArticles()=db.favArticleDao().getAllFavoriteArticles()

    suspend fun deleteFromFavorites(article: FavoriteArticle)=db.favArticleDao().deleteFromFavorites(article)



}
