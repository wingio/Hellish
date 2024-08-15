package xyz.wingio.hellish.rest.dto.entity

import kotlinx.serialization.Serializable

@Serializable
data class Nationality(
    val nation: String,
    val countryCode: String,
    val subdivision: Subdivision? = null
)

@Serializable
data class Subdivision(
    val name: String,
    val isoCode: String
)