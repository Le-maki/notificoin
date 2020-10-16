package com.github.corentinc.core

import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.core.ui.BatteryWarningPresenter
import com.github.corentinc.core.ui.SpecialConstructor
import com.github.corentinc.core.ui.SpecialConstructor.HUAWEI

class BatteryWarningInteractor(
    val batteryWarningPresenter: BatteryWarningPresenter,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    fun onStopAskingButtonPressed() {
        sharedPreferencesRepository.shouldShowBatteryWhiteListDialog = false
        batteryWarningPresenter.presentBack()
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

    fun onDefaultDialogCanceled() {
        batteryWarningPresenter.presentBack()
    }

    fun onDefaultMaybeButtonClicked() {
        batteryWarningPresenter.presentBack()
    }

    fun onDefaultOKButtonClicked(shouldDisplaySpecialConstructorDialog: Boolean) {
        batteryWarningPresenter.presentBatteryWhiteList()
        if (!shouldDisplaySpecialConstructorDialog) {
            batteryWarningPresenter.presentBack()
        }
    }

    fun onSpecialOkButtonClicked() {
        batteryWarningPresenter.presentSpecialIntent()
        batteryWarningPresenter.presentBack()
    }

    fun onSpecialMaybeButtonClicked() {
        batteryWarningPresenter.presentBack()
    }

    fun onSpecialCanceled() {
        batteryWarningPresenter.presentBack()
    }
}