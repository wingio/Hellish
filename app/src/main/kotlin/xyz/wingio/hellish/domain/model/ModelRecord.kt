package xyz.wingio.hellish.domain.model

import xyz.wingio.hellish.rest.dto.entity.Record

data class ModelRecord(
    val id: Int,
    val progress: Int,
    val status: Status,
    val player: ModelPlayer? = null,
    val demon: ModelDemon? = null,
    val submitter: ModelSubmitter? = null,
    val nationality: ModelNationality? = null,
    val video: String? = null
) {

    enum class Status {
        APPROVED,
        REJECTED,
        SUBMITTED,
        UNDER_CONSIDERATION;

        companion object {

            fun fromApiRecordStatus(recordStatus: Record.Status) = when(recordStatus) {
                Record.Status.APPROVED -> APPROVED
                Record.Status.REJECTED -> REJECTED
                Record.Status.SUBMITTED -> SUBMITTED
                Record.Status.UNDER_CONSIDERATION -> UNDER_CONSIDERATION
            }

        }

    }

    companion object {

        fun fromApiRecord(record: Record): ModelRecord = with(record) {
            ModelRecord(
                id = id,
                progress = progress,
                status = Status.fromApiRecordStatus(status),
                player = player?.let { ModelPlayer.fromApiPlayer(it) },
                demon = demon?.let { ModelDemon.fromApiDemon(it) },
                submitter = submitter?.let { ModelSubmitter.fromApiSubmitter(it) },
                nationality = nationality?.let { ModelNationality.fromApiNationality(it) },
                video = video
            )
        }

    }

}
