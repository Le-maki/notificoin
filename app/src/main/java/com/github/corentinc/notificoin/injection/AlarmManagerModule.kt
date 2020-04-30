package com.github.corentinc.notificoin.injection

import com.github.corentinc.notificoin.ui.alarmManager.NotifiCoinAlarmManager
import org.koin.dsl.module

val alarmManagerModule = module {
    single { NotifiCoinAlarmManager(context = get()) }
}