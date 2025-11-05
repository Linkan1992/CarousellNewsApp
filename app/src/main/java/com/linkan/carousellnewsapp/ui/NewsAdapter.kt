package com.linkan.carousellnewsapp.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.linkan.carousellnewsapp.data.model.NewsArticle
import com.linkan.carousellnewsapp.databinding.ItemNewsRowBinding

import javax.inject.Inject

class NewsAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var onItemClickListener: ((NewsArticle) -> Unit)? = null

    fun deleteOnItemClickListener(listener: (NewsArticle) -> Unit) {
        onItemClickListener = listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerDiffList = AsyncListDiffer(this, diffCallback)

    var newArticleList: List<NewsArticle>
        get() = recyclerDiffList.currentList
        set(value) = recyclerDiffList.submitList(value)

    class NewsViewHolder(val mBinding: ItemNewsRowBinding) :
        RecyclerView.ViewHolder(mBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        newArticleList[position].let { newsArticle ->
            glide.load(newsArticle.bannerUrl)
                .into(holder.mBinding.imvArticleImage)
            holder.mBinding.mtvArticleTitle.text = newsArticle.title
            holder.mBinding.mtvArticleDescription.text = newsArticle.description
            holder.mBinding.mtvTimeCreated.text = newsArticle.timeCreated?.toString()
            holder.itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { listener ->
                        listener(newsArticle)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return newArticleList.size
    }
}