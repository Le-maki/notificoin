package com.github.lemaki.notificoin.injection

import androidx.lifecycle.MutableLiveData
import com.github.lemaki.core.adList.AdListInteractor
import com.github.lemaki.core.ui.adList.AdListPresenter
import com.github.lemaki.notificoin.ui.adList.AdListPresenterImpl
import com.github.lemaki.notificoin.ui.adList.AdListViewModel
import org.koin.dsl.module

val adListModule = module {
    single { AdListInteractor(get(), get(), get()) }
    single<AdListPresenter> { AdListPresenterImpl(get(), get()) }
    single {
        AdListViewModel(
            MutableLiveData(),
            MutableLiveData()
        )
    }
}