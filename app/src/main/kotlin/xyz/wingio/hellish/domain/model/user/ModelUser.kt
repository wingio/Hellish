package xyz.wingio.hellish.domain.model.user

import xyz.wingio.hellish.rest.dto.entity.User

data class ModelUser(
    val id: Int,
    val username: String,
    val displayName: String?,
    val permissions: Permissions,
    val youtubeChannel: String?
) {

    fun toApiUser(): User = User(
        id = id,
        name = username,
        permissions = permissions.bitmask,
        displayName = displayName,
        youtubeChannel = youtubeChannel
    )

    companion object {

        fun fromApiUser(user: User) = with(user) {
            ModelUser(
                id = id,
                username = name,
                displayName = displayName,
                permissions = Permissions(permissions),
                youtubeChannel = youtubeChannel
            )
        }

    }

}