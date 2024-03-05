package app.bibbi.bff.component

import app.bibbi.bff.domain.AccessToken
import app.bibbi.bff.domain.SocialProvider
import app.bibbi.bff.domain.TemporaryToken
import app.bibbi.bff.domain.Token
import io.jsonwebtoken.*
import io.jsonwebtoken.JwtParser
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 2:04 PM
 */
@Component
class JwtParser {
    lateinit var signKey: SecretKey
    lateinit var jwtParser: JwtParser

    init {
        signKey = SecretKeySpec("asdkgjsjdgsdg92gn2gi3ig23igi23g2g".toByteArray(), Jwts.SIG.HS256.id)
        jwtParser = Jwts
            .parser()
            .verifyWith(signKey
            )
            .build()
    }

    fun parseToken(token: String): Token {
        val tokenClaim: Jws<Claims> = jwtParser.parseSignedClaims(token)
        val tokenTypeStr = tokenClaim.header["type"] as String
        val userId = tokenClaim.payload["userId"] as String
        return when(tokenTypeStr.lowercase(Locale.getDefault())) {
            "temporary" -> {
                val provider = tokenClaim.payload["provider"] as String
                TemporaryToken(
                    provider = SocialProvider.fromString(provider),
                    identifier = userId
                )
            }
            "access" -> AccessToken(userId)
            "refresh" -> AccessToken(userId)
            else -> throw RuntimeException()
        }
    }
}
