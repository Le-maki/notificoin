package com.github.lemaki.core.repository

interface SharedPreferencesRepository {
    companion object {
        const val PREFERENCE_FILE = "NOTIFICOIN"
    }

    var shouldShowBatteryWhiteListDialog: Boolean
}
