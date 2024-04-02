package xyz.wingio.hellish.domain.manager

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.wingio.hellish.domain.manager.base.BasePreferenceManager
import xyz.wingio.hellish.domain.manager.base.booleanPreference
import xyz.wingio.hellish.domain.manager.base.stringPreference
import xyz.wingio.hellish.rest.dto.User

/**
 * Manages authentication state throughout the app
 */
class AuthManager(
    context: Context,
    private val json: Json
): BasePreferenceManager(context.getSharedPreferences("auth", Context.MODE_PRIVATE)) {

    /**
     * The logged in user's authentication token
     */
    var authToken by stringPreference("token", "")
        private set

    /**
     * Whether the user went through the auth flow, you cannot assume this means
     * the user is authed because they could be using the app as a guest
     */
    var onboarded by booleanPreference("onboarded", false)
        private set

    /**
     * Serialized form of the logged in user's profile
     */
    private var userPref by stringPreference("user", "")

    /**
     * Whether or not the user is signed in and able to make authenticated requests
     */
    val isAuthed: Boolean
        get() = authToken.isNotBlank()

    /**
     * The current user's profile information
     */
    val currentUser: User?
        get() = if (userPref.isNotBlank()) json.decodeFromString<User>(userPref) else null

    /**
     * Update the persisted [authentication token][authToken]
     */
    fun setToken(newToken: String) {
        authToken = newToken
    }

    /**
     * Update the stored user information
     *
     * @param newUser The new user entity returned from the login or update account endpoints
     *
     * @see currentUser
     */
    fun setUser(newUser: User?) {
        userPref = if (newUser != null) json.encodeToString<User>(newUser) else ""
    }

    /**
     * Update whether or not the user has passed the onboarding/auth flow
     *
     * @see onboarded
     */
    fun setPassedOnboarding(hasOnboarded: Boolean) {
        onboarded = hasOnboarded
    }

}