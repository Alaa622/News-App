package com.example.newsapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
            ViewModelProvider(
                this,
                NewsViewModelFactory(application)
            )[NewsViewModel::class.java]

        //Display RecyclerView
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        //Get all Favorites Articles
        newsViewModel.getAllFavoriteArticles().observe(this, Observer {
            favoriteAdapter.setFavArticles(it)
            favoriteAdapter.notifyDataSetChanged()
            updateUI(it)
        })
        //Custom Favorites News Adapter
        //Click to show favorites
        //Long Click to delete item
        favoriteAdapter = FavoriteAdapter({ favoriteArticle -> clickedItem(favoriteArticle) },
            { favoriteArticle -> longClickedItem(favoriteArticle) })

        //set recyclerView adapter with favorites news
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

    private fun longClickedItem(favoriteArticle: FavoriteArticle) {
        AlertDialog.Builder(this).apply {
            setTitle("Delete From Favorites")
            setMessage("Do you want to delete this news?")
            setPositiveButton("Delete") { _, _ -> deleteLongClickedItem(favoriteArticle) }
            setNegativeButton("Cancel", null)
        }.create().show()

    }

    private fun deleteLongClickedItem(favoriteArticle: FavoriteArticle) {
        newsViewModel.deleteFromFavorites(favoriteArticle)
        Toast.makeText(this, "News deleted from favorites", Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(favoriteArticles: List<FavoriteArticle>) {
        if (favoriteArticles.isNullOrEmpty()) {
            binding.favoritesRv.visibility = GONE
            binding.emptyFavoritesTv.visibility = VISIBLE
        } else {
            binding.favoritesRv.visibility = VISIBLE
            binding.emptyFavoritesTv.visibility = GONE
        }

    }
}