package ru.melolchik.vknewsclient.data.model.news

import com.google.gson.annotations.SerializedName

data class ViewsDto(
    @SerializedName("count") val count: Int
)
