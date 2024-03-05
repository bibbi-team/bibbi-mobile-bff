package app.bibbi.bff.controller

import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import kotlin.time.Duration


/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 9:58 AM
 */
@RestController("/v1/bff/main")
class MainPageController {
    @GetMapping("/v1/ee")
    suspend fun hi() = coroutineScope {
        "hi"
    }

    @GetMapping("/v1/xx")
    fun x(): Mono<String> {
        Thread.sleep(1000)
        return Mono.just("s")
    }
}
