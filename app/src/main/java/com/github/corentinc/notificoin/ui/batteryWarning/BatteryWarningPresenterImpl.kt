package com.github.corentinc.notificoin.ui.batteryWarning

import com.github.corentinc.core.ui.BatteryWarningPresenter

class BatteryWarningPresenterImpl(
): BatteryWarningPresenter {
    lateinit var batteryWarningDisplay: BatteryWarningDisplay

    override fun presentBatteryWhitelistRequestAlertDialog(shouldDisplaySpecialConstructorDialog: Boolean) {
        batteryWarningDisplay.displayBatteryWhitelistRequestAlertDialog(
            shouldDisplaySpecialConstructorDialog
        )
    }

    override fun presentSpecialConstructorDialog() {
        batteryWarningDisplay.displaySpecialConstructorDialog()
    }

    override fun presentHuaweiDialog() {
        batteryWarningDisplay.displayHuaweiDialog()
    }

    override fun presentBack() {
        batteryWarningDisplay.displayBack()
    }

    override fun presentBatteryWhiteList() {
        batteryWarningDisplay.displayBatteryWhiteList()
    }

    override fun presentSpecialIntent() {
        batteryWarningDisplay.displaySpecialIntent()
    }
}