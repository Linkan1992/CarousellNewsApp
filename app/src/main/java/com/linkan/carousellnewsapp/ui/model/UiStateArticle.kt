package com.linkan.carousellnewsapp.ui.model

import com.linkan.carousellnewsapp.data.model.NewsArticle

data class UiStateArticle (
    val newsArticle: NewsArticle,
    var createdTimeAgo : String = ""
)