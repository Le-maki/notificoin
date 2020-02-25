package com.github.lemaki.notificoin.data.sharedPreferences

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreferencesRepository(private val context: Context) {
    companion object {
        private const val SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG =
            "SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG"
        const val PREFERENCE_FILE = "NOTIFICOIN"
    }

    var shouldShowBatteryWhiteListDialog: Boolean
        get() {
            return context.getSharedPreferences(PREFERENCE_FILE, MODE_PRIVATE)
                .getBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, true)
        }
        set(value) {
            context.getSharedPreferences(PREFERENCE_FILE, MODE_PRIVATE).edit()
                .putBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, value).apply()
        }


}