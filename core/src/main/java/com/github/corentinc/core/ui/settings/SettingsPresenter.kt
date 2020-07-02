package com.github.corentinc.core.ui.settings

interface SettingsPresenter {
    fun presentAboutFragment()
    fun presentAccurateNotificationClicked()
    fun presentNotificationIntervalPreference(entry: CharSequence)
}