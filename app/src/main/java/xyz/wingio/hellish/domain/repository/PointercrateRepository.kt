package xyz.wingio.hellish.domain.repository

import xyz.wingio.hellish.domain.model.ModelDemon
import xyz.wingio.hellish.domain.model.user.ModelUser
import xyz.wingio.hellish.rest.response.PageInfo
import xyz.wingio.hellish.rest.response.transform
import xyz.wingio.hellish.rest.service.PointercrateService

class PointercrateRepository(
    private val service: PointercrateService
) {

    // ==================================
    // ++             Auth             ++
    // ==================================

    suspend fun login(
        username: String,
        password: String
    ) = service.login(username, password).transform {
        it.token to ModelUser.fromApiUser(it.data)
    }


    // ==================================
    // ++            Demons            ++
    // ==================================

    suspend fun getRankedDemons(
        pageInfo: PageInfo? = null
    ) = service.getRankedDemons(pageInfo).transform {
        it.map { apiDemon -> ModelDemon.fromApiDemon(apiDemon) }
    }

}