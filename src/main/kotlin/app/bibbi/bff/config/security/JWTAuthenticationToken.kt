package app.bibbi.bff.config.security

import app.bibbi.bff.domain.Token
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 2:13 PM
 */
class JWTAuthenticationToken(
    val tokenDetails: Token,
    private val accessToken: String,
    authorities: List<GrantedAuthority> = emptyList(),
): AbstractAuthenticationToken(authorities) {
    override fun getCredentials() = accessToken

    override fun getPrincipal(): Any = tokenDetails.identifier
}
