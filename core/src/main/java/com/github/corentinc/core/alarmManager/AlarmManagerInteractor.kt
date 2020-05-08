package com.github.corentinc.core.alarmManager

import com.github.corentinc.core.ui.alarmManager.AlarmManagerPresenter

class AlarmManagerInteractor(
    private val alarmManagerPresenter: AlarmManagerPresenter
) {
    fun updateAlarm(minutes: Int) {
        alarmManagerPresenter.updateAlarm(minutes)
    }

    fun cancelAlarm() {
        alarmManagerPresenter.cancelAlarm()
    }
}