package com.linkan.carousellnewsapp.data

import com.linkan.carousellnewsapp.data.model.NewsArticle
import com.linkan.carousellnewsapp.util.ResultEvent
import com.linkan.carousellnewsapp.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(private val newsService: NewsService) : NewsRepository {

    override suspend fun getNews(): Flow<ResultEvent<List<NewsArticle>>> {
        return flow {
            val response = safeApiCall {
                newsService.getNews()
            }
            emit(response)
        }
    }
}