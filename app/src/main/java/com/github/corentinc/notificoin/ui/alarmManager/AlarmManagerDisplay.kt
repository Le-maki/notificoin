package com.github.corentinc.notificoin.ui.alarmManager

interface AlarmManagerDisplay {
    fun updateAlarm(minutes: Int)
    fun cancelAlarm()
}