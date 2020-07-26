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
        private const val CONNEXION_ERROR_DURING_LAST_AD_CHECK =
            "CONNEXION_ERROR_DURING_LAST_AD_CHECK"
    }

    override var shouldShowBatteryWhiteListDialog: Boolean
        get() {
            return sharedPreferences.getBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, true)
        }
        set(value) {
            sharedPreferencesEditor.putBoolean(SHOULD_SHOW_BATTERY_WHITE_LIST_DIALOG, value).apply()
        }
    override var connexionErrorDuringLastAdCheck: Boolean
        get() {
            return sharedPreferences.getBoolean(CONNEXION_ERROR_DURING_LAST_AD_CHECK, false)
        }
        set(value) {
            sharedPreferencesEditor.putBoolean(CONNEXION_ERROR_DURING_LAST_AD_CHECK, value).apply()
        }
}