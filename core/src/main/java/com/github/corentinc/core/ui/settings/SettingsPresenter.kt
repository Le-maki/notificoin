package com.github.corentinc.core.ui.settings

interface SettingsPresenter {
    fun presentAboutFragment()
    fun presentAccurateNotificationPreferenceValue(batteryWhiteListAlreadyGranted: Boolean)
    fun presentAccurateNotificationChanged(isChecked: Boolean)
    fun presentNotificationIntervalPreference(entry: CharSequence)
}