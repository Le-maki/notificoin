package com.github.corentinc.core

import com.github.corentinc.core.repository.SharedPreferencesRepository

class BatteryWarningInteractor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    fun onBatteryWhiteListAlertDialogNeutralButtonPressed() {
        sharedPreferencesRepository.shouldShowBatteryWhiteListDialog = false
    }
}