package app.bibbi.bff.dto.response

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/14/24
 * Time: 11:38 AM
 */
data class MainPageTopBarElementResponse(
    val imageUrl: String?,
    val noImageLetter: String,
    val displayName: String,
    val isBirthday: Boolean,
    val displayRank: Int,
)
