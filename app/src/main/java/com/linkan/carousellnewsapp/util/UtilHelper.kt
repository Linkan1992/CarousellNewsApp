package com.linkan.carousellnewsapp.util

import com.linkan.carousellnewsapp.data.model.NewsArticle

object UtilHelper {

    fun sort(list: List<NewsArticle>): List<NewsArticle> {
        return list.sortedByDescending {
            it.timeCreated
        }
    }
}