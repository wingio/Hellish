package xyz.wingio.hellish.rest.dto.entity

import kotlinx.serialization.Serializable

@Serializable
data class Demon(
    val id: Int,
    val name: String,
    val position: Int?,
    val publisher: Player? = null,
    val verifier: Player? = null,
    val video: String? = null,
    val thumbnail: String? = null,
    val levelId: Int? = null,
    val requirement: Int? = null,
    val creators: List<Player>? = null,
    val records: List<Record>? = null
)