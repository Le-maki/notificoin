package com.github.corentinc.repository.sharedPreferences

import android.content.SharedPreferences
import com.github.corentinc.core.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(
    private val sharedPreferencesEditor: SharedPreferences.Editor,
    private val sharedPreferences: SharedPreferences
): SharedPreferencesRepository {
    companion object {
        private const val SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG =
            "SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG"
        private const val ALARM_INTERVAL_PREFERENCE_KEY =
            "notificationInterval"
    }

    override var shouldShowBatteryWhiteListDialog: Boolean
        get() {
            return sharedPreferences.getBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, true)
        }
        set(value) {
            sharedPreferencesEditor.putBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, value).apply()
        }

    override var alarmIntervalPreference: Int
        get() {
            return sharedPreferences.getInt(ALARM_INTERVAL_PREFERENCE_KEY, 5)
        }
        set(value) {
            sharedPreferencesEditor.putInt(ALARM_INTERVAL_PREFERENCE_KEY, value).apply()
        }
}