package app.bibbi.bff.config

import app.bibbi.bff.dto.response.ErrorResponse
import app.bibbi.bff.exception.WebClientResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/13/24
 * Time: 5:24 PM
 */
@ControllerAdvice
class WebExceptionHandler {
    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex: WebClientResponseException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(ex.errorResponse)
    }

}
