package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.home.HomeInteractor
import com.github.corentinc.core.ui.home.HomePresenter
import com.github.corentinc.notificoin.ui.SingleLiveEvent
import com.github.corentinc.notificoin.ui.home.HomePresenterImpl
import com.github.corentinc.notificoin.ui.home.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    single { HomeInteractor(get(), get(), get(), get(), get()) }
    single<HomePresenter> { HomePresenterImpl(get()) }
    single {
        HomeViewModel(
            SingleLiveEvent(),
            SingleLiveEvent()
        )
    }
}