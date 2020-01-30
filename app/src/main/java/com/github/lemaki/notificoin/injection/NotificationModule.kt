package com.github.lemaki.notificoin.injection

import android.content.Context
import com.github.lemaki.notificoin.ui.notificationPush.NotificationManager
import org.koin.dsl.module

val notificationModule = module {
	single { (context: Context) -> NotificationManager(context) }
}