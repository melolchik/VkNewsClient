package ru.melolchik.vknewsclient.domain

import ru.melolchik.vknewsclient.R

data class PostComment(
    val id: Long,
    val authorName : String,
    val authorAvatarId : String,
    val commentText : String,
    val publicationDate: String

)
