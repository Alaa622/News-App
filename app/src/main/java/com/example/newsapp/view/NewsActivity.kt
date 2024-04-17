package com.example.newsapp.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.ArticleAdapter
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.NewsViewModelFactory

class NewsActivity : AppCompatActivity() {
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var newsViewModel: NewsViewModel
    lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //layout inflate
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ViewModel Provider
        newsViewModel =
            ViewModelProvider(this, NewsViewModelFactory(application))[NewsViewModel::class.java]

        setUpRecyclerView()
        newsViewModel.getArticles()

    }

    private fun setUpRecyclerView() {
        newsViewModel.articlesLiveData.observe(this, Observer {
            articleAdapter.setArticles(it)
            articleAdapter.notifyDataSetChanged()

        })
        articleAdapter = ArticleAdapter() { article -> clickedItem(article) }
        binding.articlesRv.apply {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = articleAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        if (!newsViewModel.isConnected) {
            showConnectionAlertDialog(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        menu?.findItem(R.id.favorite)?.setVisible(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.showFavorites) {
            showFavorites()
        }
        return true
    }

    private fun showFavorites() {
        val intent = Intent(baseContext, FavoriteNewsActivity::class.java)
        startActivity(intent)
    }

    private fun showConnectionAlertDialog(context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle("No Internet")
            setMessage("Please, Check your internet connection Or Go to your favorites news")
            setPositiveButton("Favorites") { _, _ -> showFavorites() }
            setNegativeButton("Reload") { _, _ -> retryLoading() }
            setNeutralButton("Exit") { _, _ -> finish() }
            setCancelable(false)
        }.create().show()
    }

    private fun retryLoading() {
        val intent = getIntent()
        finish()
        startActivity(intent)
    }

    private fun clickedItem(article: Article) {
        val intent = Intent(baseContext, NewsContentActivity::class.java)
        intent.putExtra("title", article.title)
        intent.putExtra("content", article.content)
        intent.putExtra("imageUrl", article.urlToImage)
        startActivity(intent)
    }


}


