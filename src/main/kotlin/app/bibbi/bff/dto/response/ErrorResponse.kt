package app.bibbi.bff.dto.response

import app.bibbi.bff.exception.ErrorCode

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 12:09 PM
 */
data class ErrorResponse(
    val code: String,
    val message: String,
    val ref: String = "BFF",
) {
    companion object {
        fun fromErrorCode(errorCode: ErrorCode) = ErrorResponse(
            errorCode.code,
            errorCode.message
        )
    }
}
