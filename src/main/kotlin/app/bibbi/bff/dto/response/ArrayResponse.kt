package app.bibbi.bff.dto.response

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/14/24
 * Time: 11:28 AM
 */
data class ArrayResponse<T>(
    val results: Collection<T>,
)
