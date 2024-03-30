package xyz.wingio.hellish.rest.dto

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val data: User,
    val token: String
)
