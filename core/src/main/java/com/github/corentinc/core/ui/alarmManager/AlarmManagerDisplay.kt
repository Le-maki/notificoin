package com.github.corentinc.core.ui.alarmManager

interface AlarmManagerDisplay {
    fun updateAlarm(minutes: Int)
    fun cancelAlarm()
}