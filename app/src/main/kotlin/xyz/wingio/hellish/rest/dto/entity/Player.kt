package xyz.wingio.hellish.rest.dto.entity

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val name: String,
    val banned: Boolean,
    val nationality: Nationality? = null,
    val created: List<Demon>? = null,
    val records: List<Record>? = null,
    val published: List<Demon>? = null,
    val verified: List<Demon>? = null
)
