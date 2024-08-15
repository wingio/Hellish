package xyz.wingio.hellish.rest.dto.response

import kotlinx.serialization.Serializable
import xyz.wingio.hellish.rest.dto.entity.User

@Serializable
data class Login(
    val data: User,
    val token: String
)
