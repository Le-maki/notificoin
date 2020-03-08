package com.github.lemaki.notificoin.injection

import com.github.lemaki.notificoin.ui.alarmManager.NotifiCoinAlarmManager
import org.koin.dsl.module

val alarmManagerModule = module {
    single { NotifiCoinAlarmManager(get()) }
}