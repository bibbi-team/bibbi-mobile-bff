package app.bibbi.bff.dto.response

import java.time.ZonedDateTime

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/14/24
 * Time: 11:33 AM
 */
data class PostResponse(
    val postId: String,
    val authorId: String,
    val commentCount: Int,
    val emojiCount: Int,
    val imageUrl: String,
    val content: String,
    val createdAt: ZonedDateTime,
)
