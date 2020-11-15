package com.github.corentinc.core.ui.batteryWarning

interface BatteryWarningDisplay {
    fun displayBatteryWhitelistRequestAlertDialog(shouldDisplaySpecialConstructorDialog: Boolean)
    fun displaySpecialConstructorDialog()
    fun displayHuaweiDialog()
    fun displayBack()
    fun displayBatteryWhiteList()
    fun displaySpecialIntent()
}
