package com.github.corentinc.core.ui.settings

interface SettingsDisplay {
    fun displayAboutFragment()
    fun displayBatteryWarningFragment()
    fun displayNotificationIntervalPreference(entry: String)
}