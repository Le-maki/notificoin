package com.github.corentinc.notificoin.injection

import com.github.corentinc.notificoin.ui.notifications.SettingsViewModel
import org.koin.dsl.module

val settingsModule = module {
    single { SettingsViewModel() }
}