package app.bibbi.bff.config.filter

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/13/24
 * Time: 2:27 PM
 */
@Configuration
class WebFluxRequestFilter: WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
        chain.filter(exchange).contextWrite {
            it.put(REQUEST_CONTEXT_KEY, exchange.request)
        }

    companion object {
        private const val REQUEST_CONTEXT_KEY = "request"
        suspend fun currentContextRequest() = Mono.deferContextual {
            Mono.justOrEmpty(it.getOrEmpty<ServerHttpRequest>(REQUEST_CONTEXT_KEY))
        }.awaitSingle()
    }
}
