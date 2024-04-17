package com.example.newsapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class FavoriteArticle(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var title: String,
    var content: String,
    var urlToImage: String
) : Parcelable
