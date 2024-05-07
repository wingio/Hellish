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

    val flagCodeMap = mapOf(
        'A' to "\uD83C\uDDE6",
        'B' to "\uD83C\uDDE7",
        'C' to "\uD83C\uDDE8",
        'D' to "\uD83C\uDDE9",
        'E' to "\uD83C\uDDEA",
        'F' to "\uD83C\uDDEB",
        'G' to "\uD83C\uDDEC",
        'H' to "\uD83C\uDDED",
        'I' to "\uD83C\uDDEE",
        'J' to "\uD83C\uDDEF",
        'K' to "\uD83C\uDDF0",
        'L' to "\uD83C\uDDF1",
        'M' to "\uD83C\uDDF2",
        'N' to "\uD83C\uDDF3",
        'O' to "\uD83C\uDDF4",
        'P' to "\uD83C\uDDF5",
        'Q' to "\uD83C\uDDF6",
        'R' to "\uD83C\uDDF7",
        'S' to "\uD83C\uDDF8",
        'T' to "\uD83C\uDDF9",
        'U' to "\uD83C\uDDFA",
        'V' to "\uD83C\uDDFB",
        'W' to "\uD83C\uDDFC",
        'X' to "\uD83C\uDDFD",
        'Y' to "\uD83C\uDDFE",
        'Z' to "\uD83C\uDDFF",
    )

    fun getFlag(): String {
        var s = ""
        for (char in countryCode) {
            s += flagCodeMap[char]
        }
        return s
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