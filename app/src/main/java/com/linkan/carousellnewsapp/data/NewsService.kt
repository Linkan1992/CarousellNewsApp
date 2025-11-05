package com.linkan.carousellnewsapp.data

import com.linkan.carousellnewsapp.data.model.NewsArticle
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {

    @GET("carousell-interview-assets/android/carousell_news.json")
    suspend fun getNews(): Response<List<NewsArticle>>

}