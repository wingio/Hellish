package xyz.wingio.hellish.domain.model.user

enum class Permission(
    val bit: Long
) {
    LIST_HELPER(0x2),
    LIST_MODERATOR(0x4),
    LIST_ADMINISTRATOR(0x8),
    MODERATOR(0x2000),
    ADMINISTRATOR(0x4000)
}