package com.github.corentinc.repository.sharedPreferences

import android.content.SharedPreferences
import com.github.corentinc.core.repository.GlobalSharedPreferencesRepository

class GlobalSharedPreferencesRepositoryImpl(
    private val sharedPreferencesEditor: SharedPreferences.Editor,
    private val sharedPreferences: SharedPreferences
): GlobalSharedPreferencesRepository {
    companion object {
        private const val ALARM_INTERVAL_PREFERENCE_KEY =
            "notificationInterval"
    }

    override var alarmIntervalPreference: Int
        get() {
            return sharedPreferences.getString(ALARM_INTERVAL_PREFERENCE_KEY, "5")?.toInt() ?: 5
        }
        set(value) {
            sharedPreferencesEditor.putString(ALARM_INTERVAL_PREFERENCE_KEY, value.toString())
                .apply()
        }
}