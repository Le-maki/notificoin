package com.github.corentinc.notificoin.ui.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.github.corentinc.core.ui.alarmManager.AlarmManagerDisplay

class NotifiCoinAlarmManager(private val context: Context): AlarmManagerDisplay {
    override fun updateAlarm(minutes: Int) {
        cancelAlarm()
        val milliSeconds = minutes * 60 * 1000
        (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + milliSeconds,
            milliSeconds.toLong(),
            Intent(context, AlarmBroadcastReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        )
    }

    override fun cancelAlarm() {
        (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(
            Intent(context, AlarmBroadcastReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            })
    }
}