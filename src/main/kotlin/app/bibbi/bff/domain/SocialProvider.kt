package app.bibbi.bff.domain

import java.util.*

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 2:09 PM
 */
enum class SocialProvider{
    APPLE,
    GOOGLE,
    KAKAO;

    companion object {
        fun fromString(provider: String): SocialProvider {
            return valueOf(provider.uppercase(Locale.getDefault()))
        }
    }
}
