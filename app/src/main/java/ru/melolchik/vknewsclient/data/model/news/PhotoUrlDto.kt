package ru.melolchik.vknewsclient.data.model.news

import com.google.gson.annotations.SerializedName

data class PhotoUrlDto(
    @SerializedName("url") val url: String
)
