package app.bibbi.bff.config.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 9:45 AM
 */
@Component
class JWTAuthenticationFilter: GatewayFilter {
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        exchange.request.headers.forEach{ (key, value) ->
            println("key: $key, value: $value")
        }
        return chain.filter(exchange)
    }


}
