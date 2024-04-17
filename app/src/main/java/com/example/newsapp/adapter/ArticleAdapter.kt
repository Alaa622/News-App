package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.NewsItemBinding
import com.example.newsapp.model.Article

class ArticleAdapter(private val itemClickListener: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private var articles: List<Article> = listOf()

    inner class ArticleViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, itemClickListener: (Article) -> Unit) {
            binding.apply {
                Glide.with(root.context).load(article.urlToImage).into(articleImage)
                articleTitleTv.text = article.title
                articleDescriptionTv.text = article.description
                root.setOnClickListener(View.OnClickListener {
                    itemClickListener(article)
                })

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int = articles.size


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        holder.bind(articles[position], itemClickListener)

    }

    fun setArticles(articles:List<Article>){
        this.articles=articles
    }


}