package com.github.lemaki.notificoin.injection

import androidx.lifecycle.MutableLiveData
import com.github.lemaki.notificoin.domain.home.HomeInteractor
import com.github.lemaki.notificoin.ui.home.HomePresenter
import com.github.lemaki.notificoin.ui.home.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    single { HomeInteractor(get(), get(), get(), get()) }
    single { HomePresenter(get(), get()) }
    single { HomeViewModel(MutableLiveData(), MutableLiveData(), MutableLiveData()) }
}