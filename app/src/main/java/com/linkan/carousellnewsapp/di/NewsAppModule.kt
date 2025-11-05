package com.linkan.newsfeedapp.di

import com.linkan.carousellnewsapp.data.NewsRepoImpl
import com.linkan.carousellnewsapp.data.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsAppModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(newsFeedRepositoryImpl: NewsRepoImpl) : NewsRepository
}