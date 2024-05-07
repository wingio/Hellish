package xyz.wingio.hellish.rest.dto.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Record(
    val id: Int,
    val progress: Int,
    val status: Status,
    val player: Player? = null,
    val demon: Demon? = null,
    val submitter: Submitter? = null,
    val nationality: Nationality? = null
) {

    @Serializable
    enum class Status {
        @SerialName("approved")              APPROVED,
        @SerialName("rejected")              REJECTED,
        @SerialName("submitted")             SUBMITTED,
        @SerialName("under consideration")   UNDER_CONSIDERATION
    }

}
