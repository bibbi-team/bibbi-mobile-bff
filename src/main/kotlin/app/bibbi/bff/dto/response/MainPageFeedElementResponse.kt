package app.bibbi.bff.dto.response

import java.time.ZonedDateTime

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/15/24
 * Time: 10:06 AM
 */
data class MainPageFeedElementResponse(
    val imageUrl: String,
    val content: String,
    val authorName: String,
    val createdAt: ZonedDateTime,
)
