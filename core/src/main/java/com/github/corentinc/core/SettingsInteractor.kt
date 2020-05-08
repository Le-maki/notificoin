package com.github.corentinc.core

import com.github.corentinc.core.alarmManager.AlarmManagerInteractor
import com.github.corentinc.core.ui.settings.SettingsPresenter

class SettingsInteractor(
    val settingsPresenter: SettingsPresenter,
    private val alarmManagerInteractor: AlarmManagerInteractor
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
        if (minutes == 0) {
            alarmManagerInteractor.cancelAlarm()
        } else {
            alarmManagerInteractor.updateAlarm(minutes)
        }
        settingsPresenter.presentNotificationIntervalPreference(entry)
    }
}