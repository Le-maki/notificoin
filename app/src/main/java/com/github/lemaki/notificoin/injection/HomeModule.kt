package com.github.lemaki.notificoin.injection

import com.github.lemaki.core.home.HomeInteractor
import com.github.lemaki.core.ui.home.HomePresenter
import com.github.lemaki.notificoin.ui.SingleLiveEvent
import com.github.lemaki.notificoin.ui.home.HomePresenterImpl
import com.github.lemaki.notificoin.ui.home.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    single { HomeInteractor(get(), get(), get(), get()) }
    single<HomePresenter> { HomePresenterImpl(get()) }
    single {
        HomeViewModel(
            SingleLiveEvent(),
            SingleLiveEvent()
        )
    }
}