package xyz.wingio.hellish.rest.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<D>(
    val data: D
)