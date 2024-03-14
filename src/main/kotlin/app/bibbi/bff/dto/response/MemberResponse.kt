package app.bibbi.bff.dto.response

import java.time.LocalDate

/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/14/24
 * Time: 11:30 AM
 */
data class MemberResponse(
    val memberId: String,
    val name: String,
    val imageUrl: String?,
    val familyJoinAt: LocalDate?,
    val dayOfBirth: LocalDate,
)
