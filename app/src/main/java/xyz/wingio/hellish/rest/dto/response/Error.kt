package xyz.wingio.hellish.rest.dto.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ApiError(
    val code: Int,
    override val message: String,
    val data: JsonObject
): Error()
