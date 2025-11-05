package com.linkan.carousellnewsapp.data

import com.linkan.carousellnewsapp.data.model.NewsArticle
import com.linkan.carousellnewsapp.util.ResultEvent
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<ResultEvent<List<NewsArticle>>>
}


