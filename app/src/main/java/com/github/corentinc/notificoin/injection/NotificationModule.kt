package com.github.corentinc.notificoin.injection

import com.github.corentinc.notificoin.ui.notificationPush.NotificationManager
import org.koin.dsl.module

val notificationModule = module {
	single { NotificationManager(get()) }
}