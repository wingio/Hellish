package xyz.wingio.hellish.rest.service

import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import xyz.wingio.hellish.rest.Routes
import xyz.wingio.hellish.rest.dto.entity.Demon
import xyz.wingio.hellish.rest.dto.response.Login
import xyz.wingio.hellish.rest.response.PageInfo
import xyz.wingio.hellish.rest.response.page
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class PointercrateService(
    private val apiService: ApiService
) {

    // ==================================
    // ++             Auth             ++
    // ==================================

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun login(
        username: String,
        password: String
    ) = apiService.post<Login>(Routes.V1.Auth) {
        header(HttpHeaders.Authorization, "Basic ${Base64.encode("$username:$password".toByteArray())}")
    }


    // ==================================
    // ++            Demons            ++
    // ==================================

    suspend fun getRankedDemons(
        pageInfo: PageInfo? = null,
        limit: Int = 30,
        query: String? = null
    ) = apiService.paged<Demon>(Routes.V2.Demons.Listed) {
        page(pageInfo, limit)
        query?.let { parameter("name_contains", query) }
    }

    suspend fun getDemons(
        pageInfo: PageInfo? = null,
        limit: Int = 30,
        query: String? = null
    ) = apiService.paged<Demon>(Routes.V2.Demons) {
        page(pageInfo, limit)
        query?.let { parameter("name_contains", query) }
    }

}