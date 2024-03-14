package app.bibbi.bff.config

import io.netty.channel.ChannelOption
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration
import java.util.function.Consumer

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/13/24
 * Time: 2:16 PM
 */
@Configuration
class WebClientConfig {
    private val consumer = Consumer<ClientCodecConfigurer> { configurer ->
        val codecs: ClientCodecConfigurer.ClientDefaultCodecs = configurer.defaultCodecs()
        codecs.maxInMemorySize(-1) // 10MB
    }

    @Bean
    fun connectionProvider() = ConnectionProvider
        .builder("bibbi-webclient")
        .maxConnections(100)
        .maxIdleTime(Duration.ofSeconds(58))
        .maxLifeTime(Duration.ofSeconds(58))
        .pendingAcquireTimeout(Duration.ofMillis(5000))
        .pendingAcquireMaxCount(-1)
        .evictInBackground(Duration.ofSeconds(30))
        .lifo()
        .build()

    @Bean
    fun httpClient() = HttpClient
        .create(connectionProvider())
        .headers { it.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON) }
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .option(ChannelOption.TCP_NODELAY, true)
        .responseTimeout(Duration.ofSeconds(10))
        .followRedirect(true)
        .compress(true)
        .keepAlive(true)

    @Bean
    fun reactorClientConnector() = ReactorClientHttpConnector(httpClient())

    @Bean
    fun devServiceWebClient(reactorConnector: ReactorClientHttpConnector): WebClient {
        return WebClient
            .builder()
            .codecs(consumer)
            .baseUrl("https://dev.api.no5ing.kr/")
            .clientConnector(reactorConnector)
            .build()
    }
}
