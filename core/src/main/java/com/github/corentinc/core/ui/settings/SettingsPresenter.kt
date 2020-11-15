package com.github.corentinc.core.ui.settings

interface SettingsPresenter {
    fun presentAboutFragment()
    fun presentBatteryWarningFragment()
    fun presentNotificationIntervalPreference(entry: CharSequence)
}