package com.linkan.carousellnewsapp.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.linkan.carousellnewsapp.databinding.ItemNewsRowBinding
import com.linkan.carousellnewsapp.ui.model.UiStateArticle
import javax.inject.Inject

class NewsAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var onItemClickListener: ((UiStateArticle) -> Unit)? = null

    fun deleteOnItemClickListener(listener: (UiStateArticle) -> Unit) {
        onItemClickListener = listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<UiStateArticle>() {
        override fun areItemsTheSame(oldItem: UiStateArticle, newItem: UiStateArticle): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UiStateArticle, newItem: UiStateArticle): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerDiffList = AsyncListDiffer(this, diffCallback)

    var newArticleList: List<UiStateArticle>
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

        newArticleList[position].let { UiStateArticle ->
            glide.load(UiStateArticle.newsArticle.bannerUrl)
                .into(holder.mBinding.imvArticleImage)
            holder.mBinding.mtvArticleTitle.text = UiStateArticle.newsArticle.title
            holder.mBinding.mtvArticleDescription.text = UiStateArticle.newsArticle.description
            holder.mBinding.mtvTimeCreated.text = UiStateArticle.createdTimeAgo.toString()
            holder.itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { listener ->
                        listener(UiStateArticle)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return newArticleList.size
    }
}