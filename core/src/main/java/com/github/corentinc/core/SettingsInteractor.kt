package com.github.corentinc.core

import com.github.corentinc.core.ui.settings.SettingsPresenter

class SettingsInteractor(
    val settingsPresenter: SettingsPresenter
) {
    fun onStart(isBatteryWhiteListAlreadyGranted: Boolean) {
        settingsPresenter.presentAccurateNotificationPreferenceValue(
            isBatteryWhiteListAlreadyGranted
        )
    }

    fun onAboutPreferenceClicked() {
        settingsPresenter.presentAboutFragment()
    }

    fun onAccurateNotificationsSwicthed(isChecked: Boolean) {
        settingsPresenter.presentAccurateNotificationChanged(isChecked)
    }

    fun onNotificationIntervalPreferenceChanged(minutes: Int, entry: CharSequence) {
        settingsPresenter.presentNotificationIntervalPreference(entry)
    }
}