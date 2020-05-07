package com.github.corentinc.notificoin.ui.settings

interface SettingsDisplay {
    fun displayAboutFragment()
    fun displayAccurateNotificationPreferenceValue(batteryWhiteListAlreadyGranted: Boolean)
}