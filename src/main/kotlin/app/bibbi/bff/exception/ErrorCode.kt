package app.bibbi.bff.exception

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 12:12 PM
 */
enum class ErrorCode(
    val code: String,
    val message: String,
) {
    AUTHENTICATION_FAILED("AU0001", "Authentication failed"),
    TOKEN_AUTHENTICATION_FAILED("AU0002", "Token Authentication failed"),
    AUTHORIZATION_FAILED("AU0003", "No Permission"),
    REFRESH_TOKEN_INVALID("AU0004", "Refresh Token is invalid"),
    TOKEN_EXPIRED("AU0005", "Token is expired"),
}
