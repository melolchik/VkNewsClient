package ru.melolchik.vknewsclient.domain

import ru.melolchik.vknewsclient.R

data class PostComment(
    val id: Int,
    val authorName : String = "Author Name",
    val authorAvatarId : Int = R.drawable.comment_author_avatar,
    val commentText : String = "Long comment text",
    val publicationDate: String = "14:00"

)
