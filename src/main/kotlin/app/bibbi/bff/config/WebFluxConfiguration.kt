package app.bibbi.bff.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 11:59 AM
 */
@EnableWebFlux
@Configuration
class WebFluxConfiguration: WebFluxConfigurer {
    @Bean
    fun objectMapper(): ObjectMapper = JsonMapper.builder()
        .addModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()
        )
        .addModule(JavaTimeModule())
        .build()

    override fun configureContentTypeResolver(builder: RequestedContentTypeResolverBuilder) {
        builder.fixedResolver(MediaType.APPLICATION_JSON)
    }

    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs().maxInMemorySize(512 * 1024)
    }
}
