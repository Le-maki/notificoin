package com.github.lemaki.notificoin.ui.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.lemaki.notificoin.ui.notificationPush.NotificationManager
import org.koin.core.KoinComponent

class AlarmBroadcastReceiver: BroadcastReceiver(), KoinComponent {
	override fun onReceive(context: Context, intent: Intent) {
		NotificationManager(context).sendNotification("RECEIVED", "YAY")
	}
}