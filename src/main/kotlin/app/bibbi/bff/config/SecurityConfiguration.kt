package app.bibbi.bff.config

import app.bibbi.bff.component.JwtParser
import app.bibbi.bff.config.security.JWTAuthenticationToken
import app.bibbi.bff.dto.response.ErrorResponse
import app.bibbi.bff.exception.ErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 11:07 AM
 */
@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration(
    private val jwtParser: JwtParser,
) {
    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        objectMapper: ObjectMapper,
    ): SecurityWebFilterChain = http
        .csrf { it.disable() }
        .cors { it.disable() }
        .formLogin { it.disable() }
        .httpBasic { it.disable() }
        .anonymous { it.disable() }
        .logout { it.disable() }
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) //session STATELESS
        .exceptionHandling { handler ->
            handler.authenticationEntryPoint { exchange, _ ->
                exchange.response.let { response ->
                    response.headers.add("Content-Type", "application/json")
                    val buffer = response.bufferFactory().wrap(
                        objectMapper.writeValueAsBytes(ErrorResponse.fromErrorCode(ErrorCode.AUTHENTICATION_FAILED))
                    )
                    response.writeWith(Mono.just(buffer))
                }
            }.accessDeniedHandler { exchange, _ ->
                exchange.response.let { response ->
                    response.headers.add("Content-Type", "application/json")
                    val buffer = response.bufferFactory().wrap(
                        objectMapper.writeValueAsBytes(ErrorResponse.fromErrorCode(ErrorCode.AUTHORIZATION_FAILED))
                    )
                    response.writeWith(Mono.just(buffer))
                }
            }
        }
        .authenticationManager { manager ->
            Mono.just(manager)
        }
        .authorizeExchange { exchange ->
            exchange
                .pathMatchers("/oo").permitAll()
                .anyExchange().permitAll()
        }
        .headers { headerSpec ->
            headerSpec.frameOptions {
                it.mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN)
            }
        }
        .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        .build()

    private fun authenticationWebFilter() = AuthenticationWebFilter(
        ReactiveAuthenticationManager { authentication -> Mono.just(authentication) }
    ).apply {
        setServerAuthenticationConverter(serverAuthenticationConverter())
    }

    private fun serverAuthenticationConverter(): ServerAuthenticationConverter {
        return ServerAuthenticationConverter { exchange: ServerWebExchange ->
            exchange.request.headers["X-AUTH-TOKEN"]?.firstOrNull()?.let { jwtToken ->
                try {
                    val tokenDetails = jwtParser.parseToken(jwtToken)
                    return@ServerAuthenticationConverter Mono.justOrEmpty(
                        JWTAuthenticationToken(
                            tokenDetails,
                            jwtToken
                        )
                    )
                } catch (e: Exception) {

                }
            }
            return@ServerAuthenticationConverter Mono.empty<Authentication>()
        }
    }

}
