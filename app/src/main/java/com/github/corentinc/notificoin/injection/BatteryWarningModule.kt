package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.BatteryWarningInteractor
import org.koin.dsl.module

val batteryWarningModule = module {
    single {
        BatteryWarningInteractor(
            sharedPreferencesRepository = get()
        )
    }
}