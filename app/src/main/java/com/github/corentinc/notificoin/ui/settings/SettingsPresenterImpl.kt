package com.github.corentinc.notificoin.ui.settings

import com.github.corentinc.core.ui.settings.SettingsDisplay
import com.github.corentinc.core.ui.settings.SettingsPresenter

class SettingsPresenterImpl : SettingsPresenter {
    lateinit var settingsDisplay: SettingsDisplay
    override fun presentAboutFragment() {
        settingsDisplay.displayAboutFragment()
    }

    override fun presentBatteryWarningFragment() {
        settingsDisplay.displayBatteryWarningFragment()
    }

    override fun presentNotificationIntervalPreference(entry: CharSequence) {
        settingsDisplay.displayNotificationIntervalPreference(entry.toString())
    }
}