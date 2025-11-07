package ru.melolchik.vknewsclient.data.model.news

import com.google.gson.annotations.SerializedName

data class LikesCountDto (
    @SerializedName("likes") val count : Int
)