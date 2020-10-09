package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.SettingsInteractor
import com.github.corentinc.core.ui.settings.SettingsPresenter
import com.github.corentinc.notificoin.ui.settings.SettingsPresenterImpl
import com.github.corentinc.notificoin.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel() }
    single { SettingsInteractor(settingsPresenter = get(), alarmManagerInteractor = get()) }
    single<SettingsPresenter> { SettingsPresenterImpl() }
}