package app.bibbi.bff.domain

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 2:08 PM
 */
class TemporaryToken(
    val provider: SocialProvider,
    identifier: String,
): Token(
    identifier = identifier
)
