package com.example.newsapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.FavoriteAdapter
import com.example.newsapp.databinding.ActivityFavoriteNewsBinding
import com.example.newsapp.model.FavoriteArticle
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.NewsViewModelFactory

class FavoriteNewsActivity : AppCompatActivity() {
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var newsViewModel: NewsViewModel
    lateinit var binding: ActivityFavoriteNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //layout inflate
        binding = ActivityFavoriteNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ViewModel Provider
        newsViewModel =
            ViewModelProvider(this, NewsViewModelFactory(application))[NewsViewModel::class.java]

        newsViewModel.getAllFavoriteArticles().observe(this, Observer {
            favoriteAdapter.setFavArticles(it)
            favoriteAdapter.notifyDataSetChanged()
        })
        favoriteAdapter = FavoriteAdapter() { favoriteArticle -> clickedItem(favoriteArticle) }
        binding.favoritesRv.apply {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = favoriteAdapter
        }

    }

    private fun clickedItem(favoriteArticle: FavoriteArticle) {
        val intent = Intent(baseContext, NewsContentActivity::class.java)
        intent.putExtra("title", favoriteArticle.title)
        intent.putExtra("content", favoriteArticle.content)
        intent.putExtra("imageUrl", favoriteArticle.urlToImage)
        startActivity(intent)
    }
}