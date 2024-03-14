package app.bibbi.bff.dto.response

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/14/24
 * Time: 11:28 AM
 */
data class PaginationResponse<T>(
    val currentPage: Int,
    val totalPage: Int,
    val itemPerPage: Int,
    val hasNext: Boolean,
    val results: List<T>,
)
