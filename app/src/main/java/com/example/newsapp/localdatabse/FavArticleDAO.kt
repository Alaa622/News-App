package com.example.newsapp.localdatabse

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.newsapp.model.FavoriteArticle

@Dao
interface FavArticleDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(favoriteArticle: FavoriteArticle)

    @Query("select * from FavoriteArticle")
    fun getAllFavoriteArticles():LiveData<List<FavoriteArticle>>

    @Delete
    suspend fun deleteFromFavorites(article: FavoriteArticle)
}