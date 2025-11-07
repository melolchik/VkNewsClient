package ru.melolchik.vknewsclient.data.model.comments

import com.google.gson.annotations.SerializedName
import com.sumin.vknewsclient.data.model.ProfileDto

data class CommentsContentDto(
    @SerializedName("items") val comments: List<CommentDto>,
    @SerializedName("profiles") val profiles: List<ProfileDto>
)
