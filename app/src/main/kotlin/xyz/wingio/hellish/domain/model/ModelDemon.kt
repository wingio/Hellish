package xyz.wingio.hellish.domain.model

import xyz.wingio.hellish.rest.dto.entity.Demon
import xyz.wingio.hellish.util.exp
import kotlin.math.pow

data class ModelDemon(
    val id: Int,
    val name: String,
    val position: Int?,
    val publisher: ModelPlayer? = null,
    val verifier: ModelPlayer? = null,
    val video: String? = null,
    val thumbnail: String? = null,
    val levelId: Int? = null,
    val requirement: Int? = null,
    val creators: List<ModelPlayer>? = null,
    val records: List<ModelRecord>? = null
) {

    companion object {

        fun fromApiDemon(demon: Demon): ModelDemon = with(demon) {
            ModelDemon(
                id = id,
                name = name,
                position = position,
                publisher = publisher?.let { ModelPlayer.fromApiPlayer(it) },
                verifier = verifier?.let { ModelPlayer.fromApiPlayer(it) },
                video = video,
                thumbnail = thumbnail,
                levelId = levelId,
                requirement = requirement,
                creators = creators?.map { ModelPlayer.fromApiPlayer(it) },
                records = records?.map { ModelRecord.fromApiRecord(it) }
            )
        }

    }

    // Translated from https://github.com/stadust/pointercrate/blob/4a20ea3ad1d4e417b1da4eab784efb0b8a361e37/pointercrate-demonlist/src/demon/mod.rs#L224-L241
    fun score(progress: Int): Double {
        val position = position?.toDouble() ?: return 0.0
        if (requirement == null) return 0.0

        val beatenScore = when(position) {
            in 56.0..150.0 -> 1.039035131 * ((185.7 * (-0.2715 * position).exp()) + 14.84)
            in 36.0..55.0 -> 1.0371139743 * ((212.61 * 1.036.pow(1.0 - position)) + 25.071)
            in 21.0..35.0 -> ((250 - 83.389) * (1.0099685.pow(2.0 - position)) - 31.152) * 1.0371139743
            in 4.0..20.0 -> ((326.1 * (-0.0871 * position).exp()) + 51.09) * 1.037117142
            in 1.0..3.0 -> (-18.2899079915 * position) + 368.2899079915
            else -> 0.0
        }

        return if (progress != 100) {
            (beatenScore * (5.0.pow((progress - requirement).toDouble() / (100 - requirement).toDouble()))) / 10.0
        } else {
            beatenScore
        }
    }

}