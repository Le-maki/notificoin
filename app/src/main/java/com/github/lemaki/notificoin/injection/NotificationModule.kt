package com.github.lemaki.notificoin.injection

import com.github.lemaki.notificoin.ui.notificationPush.NotificationManager
import org.koin.dsl.module

val notificationModule = module {
	single { NotificationManager(get()) }
}