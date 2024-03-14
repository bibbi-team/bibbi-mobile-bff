package app.bibbi.bff.component

import app.bibbi.bff.config.filter.WebFluxRequestFilter.Companion.currentContextRequest
import app.bibbi.bff.dto.response.ClientErrorResponse
import app.bibbi.bff.dto.response.ErrorResponse
import app.bibbi.bff.dto.response.PaginationResponse
import app.bibbi.bff.exception.WebClientResponseException
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import java.lang.RuntimeException

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/13/24
 * Time: 2:53 PM
 */
@Component
final class BackendClient(
    private val _webClient: WebClient,
) {
    @PostConstruct
    fun onConstruct() {
        webClient = _webClient
    }

    companion object {
        lateinit var webClient: WebClient
    }

    suspend inline fun <reified T> get(url: String): Mono<T> {
        val requestContext = currentContextRequest()
        println(T::class.java)
        return webClient.get()
            .uri(url)
            .headers {
                requestContext.headers.forEach { key, value ->
                    println("key: $key, value: $value")
                    if(key.startsWith("X-")){
                        it.addAll(key, value)
                    }
                }
                //it.addAll(requestContext.headers)
            }
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { clientResponse ->
                clientResponse
                    .bodyToMono(ClientErrorResponse::class.java)
                    .map { WebClientResponseException(ErrorResponse(
                        code = it.code,
                        message = it.message,
                        ref  = "BE $url"
                    )) }
            }
            .bodyToMono(T::class.java)
    }

    suspend inline fun <reified X> getPaged(url: String): Mono<PaginationResponse<X>> {
        val requestContext = currentContextRequest()
        return webClient.get()
            .uri(url)
            .headers {
                requestContext.headers.forEach { key, value ->
                    println("key: $key, value: $value")
                    if(key.startsWith("X-")){
                        it.addAll(key, value)
                    }
                }
                //it.addAll(requestContext.headers)
            }
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { clientResponse ->
                clientResponse
                    .bodyToMono(ClientErrorResponse::class.java)
                    .map { WebClientResponseException(ErrorResponse(
                        code = it.code,
                        message = it.message,
                        ref  = "BE $url"
                    )) }
            }
            .bodyToMono<PaginationResponse<X>>()
    }
}
