package com.github.corentinc.notificoin.ui.batteryWarning
import android.view.View

interface BatteryWarningDisplay {
    fun displayBatteryWhitelistRequestAlertDialog(shouldDisplaySpecialConstructorDialog: Boolean)
    fun displaySpecialConstructorDialog(): View
    fun displayHuaweiDialog()
    fun displayBack()
    fun displayBatteryWhiteList()
    fun displaySpecialIntent()
}
