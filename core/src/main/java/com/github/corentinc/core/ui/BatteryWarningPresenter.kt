package com.github.corentinc.core.ui

interface BatteryWarningPresenter {
    fun presentBatteryWhitelistRequestAlertDialog(shouldDisplaySpecialConstructorDialog: Boolean)
    fun presentSpecialConstructorDialog()
    fun presentHuaweiDialog()
}