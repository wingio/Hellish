package xyz.wingio.hellish.domain.manager.base

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import kotlin.reflect.KProperty

abstract class BasePreferenceManager(
    protected val prefs: SharedPreferences,
    private val commit: Boolean = false
) {

    fun getString(key: String, defaultValue: String) =
        prefs.getString(key, defaultValue)!!

    fun getBoolean(key: String, defaultValue: Boolean) =
        prefs.getBoolean(key, defaultValue)

    fun getInt(key: String, defaultValue: Int) = prefs.getInt(key, defaultValue)
    fun getFloat(key: String, defaultValue: Float) =
        prefs.getFloat(key, defaultValue)

    inline fun <reified E : Enum<E>> getEnum(key: String, defaultValue: E) =
        enumValueOf<E>(getString(key, defaultValue.name))

    fun putString(key: String, value: String) = prefs.edit(commit) { putString(key, value) }
    fun putBoolean(key: String, value: Boolean) = prefs.edit(commit) { putBoolean(key, value) }
    fun putInt(key: String, value: Int) = prefs.edit(commit) { putInt(key, value) }
    fun putFloat(key: String, value: Float) = prefs.edit(commit) { putFloat(key, value) }

    inline fun <reified E : Enum<E>> putEnum(key: String, value: E) =
        putString(key, value.name)

}

class Preference<T>(
    private val key: String,
    defaultValue: T,
    getter: (key: String, defaultValue: T) -> T,
    private val setter: (key: String, newValue: T) -> Unit
) {
    private var value by mutableStateOf(getter(key, defaultValue))
        private set

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = value
    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T) {
        value = newValue
        setter(key, newValue)
    }
}