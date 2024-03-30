package xyz.wingio.hellish.rest

import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import xyz.wingio.hellish.rest.dto.Login
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class PointercrateClient(
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