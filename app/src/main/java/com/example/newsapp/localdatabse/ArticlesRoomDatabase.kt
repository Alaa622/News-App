package com.example.newsapp.localdatabse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.model.FavoriteArticle

@Database(entities = [FavoriteArticle::class], exportSchema = false,version = 1)
abstract class ArticlesRoomDatabase:RoomDatabase() {

    abstract fun favArticleDao():FavArticleDAO

    companion object {
        @Volatile
        private var INSTANCE:ArticlesRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): ArticlesRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticlesRoomDatabase::class.java,
                    "FavoriteArticle_db"
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}