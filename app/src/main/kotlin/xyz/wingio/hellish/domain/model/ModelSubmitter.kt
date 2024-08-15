package xyz.wingio.hellish.domain.model

import kotlinx.serialization.Serializable
import xyz.wingio.hellish.rest.dto.entity.Submitter

@Serializable
data class ModelSubmitter(
    val id: Int,
    val banned: Boolean
) {

    companion object {

        fun fromApiSubmitter(submitter: Submitter) = with(submitter) {
            ModelSubmitter(id, banned)
        }

    }

}
