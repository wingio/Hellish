package xyz.wingio.hellish.rest.service

import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import xyz.wingio.hellish.rest.Routes
import xyz.wingio.hellish.rest.dto.response.Login
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class PointercrateService(
    private val apiService: ApiService
) {

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun login(
        username: String,
        password: String
    ) = apiService.post<Login>(Routes.V1.Auth) {
        header(HttpHeaders.Authorization, "Basic ${Base64.encode("$username:$password".toByteArray())}")
    }

}