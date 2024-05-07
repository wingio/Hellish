package xyz.wingio.hellish.domain.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
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

    fun getFlag(context: Context): ImageBitmap {
        val flag = context.resources.assets.open("flags/countries/$countryCode.png")
        val flagBmp: Bitmap
        flag.use {
            val bytes = it.readBytes()
            flagBmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
        return flagBmp.asImageBitmap()
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