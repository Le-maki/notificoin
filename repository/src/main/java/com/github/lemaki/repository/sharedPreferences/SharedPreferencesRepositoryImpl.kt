package com.github.lemaki.repository.sharedPreferences

import android.content.SharedPreferences
import com.github.lemaki.core.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(
    private val sharedPreferencesEditor: SharedPreferences.Editor,
    private val sharedPreferences: SharedPreferences
): SharedPreferencesRepository {
    companion object {
        private const val SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG =
            "SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG"
    }

    override var shouldShowBatteryWhiteListDialog: Boolean
        get() {
            return sharedPreferences.getBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, true)
        }
        set(value) {
            sharedPreferencesEditor.putBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, value).apply()
        }
}