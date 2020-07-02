package com.github.corentinc.notificoin.ui.settings

import com.github.corentinc.core.ui.settings.SettingsPresenter

class SettingsPresenterImpl(
    private val settingsViewModel: SettingsViewModel
): SettingsPresenter {
    lateinit var settingsDisplay: SettingsDisplay
    override fun presentAboutFragment() {
        settingsDisplay.displayAboutFragment()
    }

    override fun presentAccurateNotificationClicked() {
        settingsDisplay.displayBatteryWarningFragment()
    }

    override fun presentNotificationIntervalPreference(entry: CharSequence) {
        settingsViewModel.notificationIntervalEntry.value = entry.toString()
    }
}