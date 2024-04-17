package com.example.newsapp.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.newsapp.repository.Repository
import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.localdatabse.ArticlesRoomDatabase
import com.example.newsapp.model.Article
import com.example.newsapp.model.FavoriteArticle
import com.example.newsapp.model.News
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.nio.channels.NoConnectionPendingException
import kotlin.jvm.Throws

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val articlesMutableLiveData: MutableLiveData<List<Article>> = MutableLiveData()
    val articlesLiveData: LiveData<List<Article>> get() = articlesMutableLiveData
    var news: News? = null
    private val repository: Repository
    var isConnected=false

    init { repository = Repository(ArticlesRoomDatabase.getDatabase(application))}

    fun getArticles() {
        viewModelScope.launch {
            safeGetArticles()
        }
    }

    fun addToFavorite(favoriteArticle: FavoriteArticle) {
        viewModelScope.launch {
            repository.addToFavorite(favoriteArticle)
        }
    }

    fun getAllFavoriteArticles() = repository.getAllFavoriteArticles()

    fun deleteFromFavorites(favoriteArticle: FavoriteArticle) {
        viewModelScope.launch {
            repository.deleteFromFavorites(favoriteArticle)
        }
    }

    private suspend fun safeGetArticles() {
        try {
            if (internetConnection(this.getApplication())) {
                isConnected=true
                val response = repository.getNews()
                if (response.isSuccessful) {
                    if (news == null) {
                        news = response.body()
                        articlesMutableLiveData.postValue( news?.articles)
                    } else {
                        articlesMutableLiveData.postValue(news?.articles)
                        val newArticles = response.body()!!.articles
                        articlesMutableLiveData.postValue(newArticles)
                    }
                }
            }else
                isConnected=false

        } catch (e: Throwable) {
            if (e is IOException)
                throw IOException("No Internet Connection")
        }

    }
}


private fun internetConnection(context: Context): Boolean {
    (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
        return getNetworkCapabilities(activeNetwork)?.run {
            when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } ?: false
    }
}





