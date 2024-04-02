package xyz.wingio.hellish.rest.dto.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val permissions: Long,
    val displayName: String? = null,
    val youtubeChannel: String? = null
)
