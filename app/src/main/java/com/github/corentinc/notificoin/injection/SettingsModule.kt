package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.SettingsInteractor
import com.github.corentinc.core.ui.settings.SettingsPresenter
import com.github.corentinc.notificoin.ui.settings.SettingsPresenterImpl
import com.github.corentinc.notificoin.ui.settings.SettingsViewModel
import org.koin.dsl.module

val settingsModule = module {
    single { SettingsViewModel() }
    single { SettingsInteractor(settingsPresenter = get()) }
    single<SettingsPresenter> { SettingsPresenterImpl() }
}