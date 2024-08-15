package xyz.wingio.hellish.rest.dto.entity

import kotlinx.serialization.Serializable

@Serializable
data class Submitter(
    val id: Int,
    val banned: Boolean
)
