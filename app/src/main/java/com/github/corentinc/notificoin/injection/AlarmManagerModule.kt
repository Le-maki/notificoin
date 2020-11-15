package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.alarmManager.AlarmManagerInteractor
import com.github.corentinc.core.ui.alarmManager.AlarmManagerDisplay
import com.github.corentinc.core.ui.alarmManager.AlarmManagerPresenter
import com.github.corentinc.notificoin.ui.alarmManager.AlarmManagerPresenterImpl
import com.github.corentinc.notificoin.ui.alarmManager.NotifiCoinAlarmManager
import org.koin.dsl.module

val alarmManagerModule = module {
    single<AlarmManagerDisplay> { NotifiCoinAlarmManager(context = get()) }
    single<AlarmManagerPresenter> { AlarmManagerPresenterImpl(alarmManagerDisplay = get()) }
    single { AlarmManagerInteractor(alarmManagerPresenter = get()) }
}