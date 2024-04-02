package xyz.wingio.hellish.domain.model.user

/**
 * The permissions assigned to a given [user][ModelUser]
 *
 * @param bitmask The bitmask representing the raw permissions
 */
@Suppress("MemberVisibilityCanBePrivate")
class Permissions(
    val bitmask: Long
) {

    /**
     * Users that can manage other users, including granting them permissions
     *
     * Can assign: [listAdministrator], [listHelper]
     */
    val administrator: Boolean = hasPermission(Permission.ADMINISTRATOR)

    /**
     * Users that have access to the pointercrate user list
     */
    val moderator: Boolean = hasPermission(Permission.MODERATOR) || administrator


    /**
     * Users that administrate the demonlist
     *
     * Can assign: [listHelper], [listModerator]
     */
    val listAdministrator: Boolean = hasPermission(Permission.LIST_ADMINISTRATOR)

    /**
     * 	Users that moderate the demonlist and manage the demon placements
     */
    val listModerator: Boolean = hasPermission(Permission.LIST_MODERATOR) || listAdministrator

    /**
     * 	Users that help out in managing the demonlist by reviewing records
     */
    val listHelper: Boolean = hasPermission(Permission.LIST_HELPER) || listModerator

    /**
     * Checks if the [permission] is included in this set
     */
    private fun hasPermission(permission: Permission): Boolean {
        return (bitmask and permission.bit) != 0L
    }

    /**
     * Converts this set of permissions into an easily workable list
     */
    fun toList(): List<Permission> {
        if (bitmask == 0L) return emptyList() // Don't waste time looping through permissions if none are present
        val perms = mutableListOf<Permission>()
        Permission.entries.forEach { permission ->
            if (hasPermission(permission)) perms.add(permission)
        }
        return perms
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Permissions) return false
        return bitmask == other.bitmask
    }

    override fun hashCode(): Int {
        return bitmask.toInt()
    }

    companion object {

        /**
         * Creates a [Permissions] object using the provided [permissions]
         */
        fun fromList(permissions: List<Permission>): Permissions {
            var bits = 0L
            permissions.forEach { permission ->
                bits = bits or permission.bit
            }
            return Permissions(bits)
        }

        /**
         * Creates a [Permissions] object from the provided [permissions][permission]
         */
        fun from(vararg permission: Permission): Permissions = fromList(permission.toList())

    }

}