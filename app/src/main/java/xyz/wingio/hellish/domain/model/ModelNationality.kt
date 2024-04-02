package xyz.wingio.hellish.domain.model

import xyz.wingio.hellish.rest.dto.entity.Nationality
import xyz.wingio.hellish.rest.dto.entity.Subdivision

data class ModelNationality(
    val nation: String,
    val countryCode: String,
    val subdivision: ModelSubdivision? = null
) {

    companion object {

        fun fromApiNationality(nationality: Nationality): ModelNationality = with(nationality) {
            ModelNationality(
                nation = nation,
                countryCode = countryCode,
                subdivision = subdivision?.let { ModelSubdivision.fromApiSubdivision(it) }
            )
        }

    }

}

data class ModelSubdivision(
    val name: String,
    val isoCode: String
) {

    companion object {

        fun fromApiSubdivision(subdivision: Subdivision) = with(subdivision) {
            ModelSubdivision(name, isoCode)
        }

    }

}