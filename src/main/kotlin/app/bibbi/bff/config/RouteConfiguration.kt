package app.bibbi.bff.config

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 9:28 AM
 */
@Configuration
class RouteConfiguration(
   // private val jwtAuthenticationFilter: JWTAuthenticationFilter,
) {
    @Bean
    fun bibbiRouteLocator(builder: RouteLocatorBuilder) = builder.routes {
        route(id = "bibbi-backend") {
            method(GET) and path("/v1/**")
            filters {
//                stripPrefix(PREFIX_SIZE)
//                addRequestHeader(AUTHORIZATION, (myApiUsername to myApiPassword).toBasicAuthHeaderValue())
              //  filter(jwtAuthenticationFilter)
                rewritePath("/v1/(?<segment>.*)", "/v1/${'$'}{segment}")
               // addRequestHeader()
            }
            uri("https://api.no5ing.kr/")
        }
    }
}
