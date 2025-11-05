package com.linkan.carousellnewsapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticle(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("banner_url") var bannerUrl: String? = null,
    @SerializedName("time_created") var timeCreated: Long? = null,
    @SerializedName("rank") var rank: Int? = null
) : Parcelable