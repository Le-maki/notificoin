package com.github.corentinc.notificoin.ui.settings

interface SettingsDisplay {
    fun displayAboutFragment()
    fun displayBatteryWarningFragment()
    fun displayNotificationIntervalPreference(entry: String)
}