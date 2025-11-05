package com.linkan.newsfeedapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.linkan.carousellnewsapp.BuildConfig
import com.linkan.carousellnewsapp.R
import com.linkan.carousellnewsapp.data.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun injectGson() : Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun injectGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun injectRetrofitApi(gsonConverterFactory: GsonConverterFactory) : NewsService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context: Context) : RequestManager = Glide.with(context).
    setDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.placeholder)
        .error(R.drawable.placeholder))

}