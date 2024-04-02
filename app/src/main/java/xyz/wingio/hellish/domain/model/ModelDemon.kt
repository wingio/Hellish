package xyz.wingio.hellish.domain.model

import xyz.wingio.hellish.rest.dto.entity.Demon

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
    val creators: List<ModelPlayer>? = null
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
                requirement = requirement
            )
        }

    }

}