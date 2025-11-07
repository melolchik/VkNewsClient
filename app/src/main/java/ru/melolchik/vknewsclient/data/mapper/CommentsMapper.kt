package ru.melolchik.vknewsclient.data.mapper

import ru.melolchik.vknewsclient.data.model.comments.CommentsContentDto
import ru.melolchik.vknewsclient.domain.PostComment

class CommentsMapper {

    fun mapResponseToComments(response : CommentsContentDto) : List<PostComment>{
        val result = mutableListOf<PostComment>()
        if(response == null){
            return result
        }

        val comments = response.comments
        val profiles = response.profiles

        for(comment in comments){
            val author = profiles.find { it.id == comment.authorId } ?: continue

            val postComment = PostComment(
                id = comment.id,
                authorName = " ${author.firstName} ${author.lastName}" ,
                authorAvatarId = author.avatarUrl,
                commentText = comment.text,
                publicationDate = comment.date
            )
            result.add(postComment)
        }
        return result
    }
}