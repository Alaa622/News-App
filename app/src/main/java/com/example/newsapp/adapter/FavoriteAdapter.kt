package com.example.newsapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.NewsItemBinding
import com.example.newsapp.model.FavoriteArticle

class FavoriteAdapter(private val itemClickListener: (FavoriteArticle)->Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private var favoriteArticles: List<FavoriteArticle> = listOf()

    inner class FavoriteViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteArticle: FavoriteArticle,itemClickListener: (FavoriteArticle) -> Unit) {
            binding.apply {
                Glide.with(root.context).load(favoriteArticle.urlToImage).into(articleImage)
                articleTitleTv.text = favoriteArticle.title
                articleDescriptionTv.text = favoriteArticle.content
                root.setOnClickListener(View.OnClickListener {
                    itemClickListener(favoriteArticle)
                })
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {

        return FavoriteViewHolder(
            NewsItemBinding.inflate(
                (LayoutInflater.from(parent.context)),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = favoriteArticles.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteArticles[position],itemClickListener)
    }
    fun setFavArticles(favoriteArticles:List<FavoriteArticle>){
        this.favoriteArticles=favoriteArticles
    }

}