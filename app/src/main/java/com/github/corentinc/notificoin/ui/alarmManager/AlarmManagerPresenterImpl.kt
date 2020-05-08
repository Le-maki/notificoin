package com.github.corentinc.notificoin.ui.alarmManager

import com.github.corentinc.core.ui.alarmManager.AlarmManagerPresenter

class AlarmManagerPresenterImpl(
    private val alarmManagerDisplay: AlarmManagerDisplay
): AlarmManagerPresenter {
    override fun updateAlarm(minutes: Int) {
        alarmManagerDisplay.updateAlarm(minutes)
    }

    override fun cancelAlarm() {
        alarmManagerDisplay.cancelAlarm()
    }
}