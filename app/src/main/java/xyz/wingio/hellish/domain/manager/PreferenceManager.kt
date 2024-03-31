package xyz.wingio.hellish.domain.manager

import android.content.Context
import xyz.wingio.hellish.domain.manager.base.BasePreferenceManager

class PreferenceManager(
    context: Context
): BasePreferenceManager(context.getSharedPreferences("prefs", Context.MODE_PRIVATE)) {

}