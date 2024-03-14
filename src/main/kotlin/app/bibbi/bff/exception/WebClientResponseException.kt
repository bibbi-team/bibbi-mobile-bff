package app.bibbi.bff.exception

import app.bibbi.bff.dto.response.ErrorResponse

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/13/24
 * Time: 3:40 PM
 */
class WebClientResponseException(
    val errorResponse: ErrorResponse,
): RuntimeException()
