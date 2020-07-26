package com.github.corentinc.core.repository

interface SharedPreferencesRepository {
    companion object {
        const val PREFERENCE_FILE = "NOTIFICOIN"
    }
    var shouldShowBatteryWhiteListDialog: Boolean
    var connexionErrorDuringLastAdCheck: Boolean
}
