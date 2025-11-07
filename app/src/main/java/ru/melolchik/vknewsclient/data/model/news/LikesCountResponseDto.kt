package ru.melolchik.vknewsclient.data.model.news

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto (
    @SerializedName("response") val likes : LikesCountDto
)