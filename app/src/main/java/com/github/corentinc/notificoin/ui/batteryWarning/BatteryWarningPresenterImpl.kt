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
}