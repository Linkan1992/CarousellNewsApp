package com.linkan.carousellnewsapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkan.carousellnewsapp.data.NewsRepository
import com.linkan.carousellnewsapp.data.model.NewsArticle
import com.linkan.carousellnewsapp.ui.model.UiStateArticle
import com.linkan.carousellnewsapp.util.FilterType
import com.linkan.carousellnewsapp.util.ResultEvent
import com.linkan.carousellnewsapp.util.UtilHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val mNewsFeedState =
        MutableStateFlow<ResultEvent<List<NewsArticle>>>(ResultEvent.Loading)
    val newsFeedState = mNewsFeedState.asStateFlow()

    private val mNewsArticles =
        MutableStateFlow<List<UiStateArticle>>(emptyList())
    val newsArticles = mNewsArticles.asStateFlow()

    private val mSelectedFilterType = MutableStateFlow(FilterType.RECENT)
    val selectedFilterType = mSelectedFilterType.asStateFlow()

    init {
        searchCorousellNews()
    }

    val filterdNews: StateFlow<List<UiStateArticle>> =
        combine(newsArticles, selectedFilterType) { newsPost, filter ->
            when (filter) {
                FilterType.RECENT -> newsPost.sortedByDescending { it.newsArticle.timeCreated }
                FilterType.POPULAR -> newsPost.sortedWith(compareByDescending<UiStateArticle> { it.newsArticle.rank }
                    .thenByDescending { it.newsArticle.timeCreated })
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    private fun searchCorousellNews() {
        viewModelScope.launch(Dispatchers.IO) {
            mNewsFeedState.value = ResultEvent.Loading
            newsRepository.getNews()
                .collectLatest { result ->
                    when (result) {
                        is ResultEvent.Success -> {
                            val articles = result.data.toList()
                            val uiStateArticleList = mutableListOf<UiStateArticle>().run {
                                articles.forEach { article ->
                                    add(
                                        UiStateArticle(
                                            article,
                                            UtilHelper.createdTimeAgoString(article.timeCreated ?: 0)
                                        )
                                    )
                                }
                                this
                            }
                            mNewsArticles.value = uiStateArticleList
                            mNewsFeedState.value = result
                        }

                        else -> mNewsFeedState.value = result
                    }
                }
        }

    }

    fun retry() {
        searchCorousellNews()
    }

    fun setFilterType(filterType: FilterType) {
        mSelectedFilterType.value = filterType
    }

}