package xyz.wingio.hellish.util

import android.util.Log

class AppLogger(
    private val tag: String
) {

    fun verbose(message: String, throwable: Throwable? = null) {
        Log.v(tag, message, throwable)
    }

    fun info(message: String, throwable: Throwable? = null) {
        Log.i(tag, message, throwable)
    }

    fun debug(message: String, throwable: Throwable? = null) {
        Log.d(tag, message, throwable)
    }

    fun warn(message: String, throwable: Throwable? = null) {
        Log.w(tag, message, throwable)
    }

    fun error(message: String, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
    }

}