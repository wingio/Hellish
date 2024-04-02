package xyz.wingio.hellish.domain.model

import xyz.wingio.hellish.rest.dto.entity.Player

data class ModelPlayer(
    val id: Int,
    val name: String,
    val banned: Boolean,
    val nationality: ModelNationality? = null,
    val created: List<ModelDemon>? = null,
    val records: List<ModelRecord>? = null,
    val published: List<ModelDemon>? = null,
    val verified: List<ModelDemon>? = null
) {

    companion object {

        fun fromApiPlayer(player: Player): ModelPlayer = with(player) {
            ModelPlayer(
                id = id,
                name = name,
                banned = banned,
                nationality = nationality?.let { ModelNationality.fromApiNationality(it) },
                created = created?.map { ModelDemon.fromApiDemon(it) },
                records = records?.map { ModelRecord.fromApiRecord(it) },
                published = published?.map { ModelDemon.fromApiDemon(it) },
                verified = verified?.map { ModelDemon.fromApiDemon(it) }
            )
        }

    }

}
