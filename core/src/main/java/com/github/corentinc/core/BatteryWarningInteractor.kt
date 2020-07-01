package com.github.corentinc.core

import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.core.ui.BatteryWarningPresenter
import com.github.corentinc.core.ui.SpecialConstructor
import com.github.corentinc.core.ui.SpecialConstructor.HUAWEI

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
        wasDefaultDialogAlreadyShown: Boolean,
        specialConstructor: SpecialConstructor?
    ) {
        if (shouldDisplaySpecialConstructorDialog &&
            (!shouldDisplayDefaultDialog || (shouldDisplayDefaultDialog && wasDefaultDialogAlreadyShown))
        ) {
            when (specialConstructor) {
                HUAWEI -> {
                    batteryWarningPresenter.presentHuaweiDialog()
                }
                else -> batteryWarningPresenter.presentSpecialConstructorDialog()
            }
        } else {
            batteryWarningPresenter.presentBatteryWhitelistRequestAlertDialog(
                shouldDisplaySpecialConstructorDialog
            )
        }
    }
}