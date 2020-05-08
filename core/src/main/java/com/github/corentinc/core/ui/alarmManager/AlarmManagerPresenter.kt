package com.github.corentinc.core.ui.alarmManager

interface AlarmManagerPresenter {
    fun updateAlarm(minutes: Int)
    fun cancelAlarm()
}