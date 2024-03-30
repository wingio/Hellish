package xyz.wingio.hellish.rest.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ApiError(
    val code: Int,
    val message: String,
    val data: JsonObject
)
