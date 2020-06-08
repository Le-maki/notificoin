package com.github.corentinc.notificoin.ui.batteryWarning

interface BatteryWarningDisplay {
    fun displayBatteryWhitelistRequestAlertDialog(shouldDisplaySpecialConstructorDialog: Boolean)
    fun displaySpecialConstructorDialog()
}
