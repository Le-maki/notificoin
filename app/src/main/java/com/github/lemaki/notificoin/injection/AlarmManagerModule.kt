package com.github.lemaki.notificoin.injection

import android.content.Context
import com.github.lemaki.notificoin.ui.alarmManager.NotifiCoinAlarmManager
import org.koin.dsl.module

val alarmManagerModule = module {
	single {(context: Context) -> NotifiCoinAlarmManager(context) }
}