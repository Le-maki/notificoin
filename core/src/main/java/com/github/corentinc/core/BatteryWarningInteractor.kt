package com.github.corentinc.core

import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.core.ui.BatteryWarningPresenter

class BatteryWarningInteractor(
    val batteryWarningPresenter: BatteryWarningPresenter,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    fun onBatteryWhiteListAlertDialogNeutralButtonPressed() {
        sharedPreferencesRepository.shouldShowBatteryWhiteListDialog = false
    }

    fun onStart(
        shouldDisplaySpecialConstructorDialog: Boolean,
        shouldDisplayDefaultDialog: Boolean,
        wasDefaultDialogAlreadyShown: Boolean
    ) {
        if (shouldDisplaySpecialConstructorDialog &&
            (!shouldDisplayDefaultDialog || (shouldDisplayDefaultDialog && wasDefaultDialogAlreadyShown))
        ) {
            batteryWarningPresenter.presentSpecialConstructorDialog()
        } else {
            batteryWarningPresenter.presentBatteryWhitelistRequestAlertDialog(
                shouldDisplaySpecialConstructorDialog
            )
        }
    }
}