package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.BatteryWarningInteractor
import com.github.corentinc.core.ui.batteryWarning.BatteryWarningPresenter
import com.github.corentinc.notificoin.ui.batteryWarning.BatteryWarningFragmentViewModel
import com.github.corentinc.notificoin.ui.batteryWarning.BatteryWarningPresenterImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val batteryWarningModule = module {
    single {
        BatteryWarningInteractor(
            sharedPreferencesRepository = get(),
            batteryWarningPresenter = get()
        )
    }
    single<BatteryWarningPresenter> {
        BatteryWarningPresenterImpl()
    }
    viewModel {
        BatteryWarningFragmentViewModel(wasDefaultDialogAlreadyShown = false)
    }
}