package xyz.wingio.hellish.domain.repository

import xyz.wingio.hellish.domain.model.user.ModelUser
import xyz.wingio.hellish.rest.response.transform
import xyz.wingio.hellish.rest.service.PointercrateService

class PointercrateRepository(
    private val service: PointercrateService
) {

    suspend fun login(
        username: String,
        password: String
    ) = service.login(username, password).transform {
        it.token to ModelUser.fromApiUser(it.data)
    }

}