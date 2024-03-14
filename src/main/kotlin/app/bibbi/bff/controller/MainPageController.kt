package app.bibbi.bff.controller

import app.bibbi.bff.component.BackendClient
import app.bibbi.bff.dto.response.ArrayResponse
import app.bibbi.bff.dto.response.MainPageTopBarElementResponse
import app.bibbi.bff.dto.response.MemberResponse
import app.bibbi.bff.dto.response.PostResponse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


/**
 * bibbi-mobile-bff
 * User: CChuYong
 * Date: 3/5/24
 * Time: 9:58 AM
 */
@RestController
@RequestMapping("/bff/main")
class MainPageController(
    private val client: BackendClient,
) {
    @GetMapping("/top-bar")
    suspend fun topBar() = coroutineScope {
        val today = LocalDate.now()
        val meInfoDeferred = client.get<MemberResponse>("/v1/me/member-info")
        val membersDeferred = client.getPaged<MemberResponse>("/v1/members?type=FAMILY&page=1&size=1000")
        val postsDeferred = client.getPaged<PostResponse>("/v1/posts?date=${today}&page=1&size=1000")
        val meInfo = meInfoDeferred.awaitSingle()
        val postResults = postsDeferred.awaitSingle().results
        val postUploaderRankMap = postResults
            .mapIndexed { index, postResponse ->
                postResponse.authorId to index
            }.associate { it }

        ArrayResponse(
            results = membersDeferred
                .awaitSingle()
                .results
                .sortedWith(compareBy({ it.memberId != meInfo.memberId }, {postUploaderRankMap[it.memberId] ?: Int.MAX_VALUE}))
                .map { member ->
                    MainPageTopBarElementResponse(
                        imageUrl = member.imageUrl,
                        noImageLetter = member.name.first().toString(),
                        displayName = member.name,
                        isBirthday = today.isEqual(member.dayOfBirth),
                        displayRank = postUploaderRankMap[member.memberId] ?: -1,
                    )
                },
        )
    }
}
