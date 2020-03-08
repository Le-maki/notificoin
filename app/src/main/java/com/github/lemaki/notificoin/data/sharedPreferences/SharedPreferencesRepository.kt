package com.github.lemaki.notificoin.data.sharedPreferences

import android.content.SharedPreferences

class SharedPreferencesRepository(
    private val sharedPreferencesEditor: SharedPreferences.Editor,
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG =
            "SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG"
        const val PREFERENCE_FILE = "NOTIFICOIN"
    }

    var shouldShowBatteryWhiteListDialog: Boolean
        get() {
            return sharedPreferences.getBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, true)
        }
        set(value) {
            sharedPreferencesEditor.putBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, value).apply()
        }


}