package com.example.newsapp.view

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityNewsContentBinding
import com.example.newsapp.model.Article
import com.example.newsapp.model.FavoriteArticle
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.NewsViewModelFactory

class NewsContentActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewsContentBinding
    lateinit var newsViewModel: NewsViewModel
    lateinit var favoriteArticle: FavoriteArticle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //layout inflate
        binding = ActivityNewsContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewModel Provider
        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(application)
        )[NewsViewModel::class.java]


        favoriteArticle = FavoriteArticle(
            0,
            intent.getStringExtra("title").toString(),
            intent.getStringExtra("content").toString(),
            intent.getStringExtra("imageUrl").toString()
        )

        binding.apply {
            contentTitleTv.text = favoriteArticle.title
            contentTv.text = favoriteArticle.content
            Glide.with(baseContext).load(favoriteArticle.urlToImage)
                .into(contentArticleImage)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        menu?.findItem(R.id.showFavorites)?.setVisible(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            newsViewModel.addToFavorite(favoriteArticle)
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()

        }
        return true
    }
}