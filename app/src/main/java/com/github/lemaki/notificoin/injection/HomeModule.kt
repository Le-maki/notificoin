package com.github.lemaki.notificoin.injection

import androidx.lifecycle.MutableLiveData
import com.github.lemaki.core.home.HomeInteractor
import com.github.lemaki.core.ui.home.HomePresenter
import com.github.lemaki.notificoin.ui.home.HomePresenterImpl
import com.github.lemaki.notificoin.ui.home.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    single { HomeInteractor(get(), get(), get(), get()) }
    single<HomePresenter> { HomePresenterImpl(get(), get()) }
    single { HomeViewModel(MutableLiveData(), MutableLiveData(), MutableLiveData()) }
}