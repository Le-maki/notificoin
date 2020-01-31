package com.github.lemaki.notificoin.ui.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock

class NotifiCoinAlarmManager(private val context: Context) {

	companion object {
		private const val INTERVAL_FIVE_MINUTES = 1000 * 60 * 5.toLong()
	}

	fun setAlarmManager() {
		(context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).setRepeating(
			AlarmManager.ELAPSED_REALTIME_WAKEUP,
			SystemClock.elapsedRealtime() + INTERVAL_FIVE_MINUTES,
			INTERVAL_FIVE_MINUTES,
			Intent(context, AlarmBroadcastReceiver::class.java).let { intent ->
				PendingIntent.getBroadcast(context, 0, intent, 0)
			}
		)
	}

	fun cancelAlarm(context: Context) {
		(context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(
			Intent(context, AlarmBroadcastReceiver::class.java).let { intent ->
				PendingIntent.getBroadcast(context, 0, intent, 0)
			})
	}
}