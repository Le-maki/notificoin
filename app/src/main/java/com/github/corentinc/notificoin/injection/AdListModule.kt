package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.adList.AdListInteractor
import com.github.corentinc.core.ui.adList.AdListPresenter
import com.github.corentinc.notificoin.ui.SingleLiveEvent
import com.github.corentinc.notificoin.ui.adList.AdListPresenterImpl
import com.github.corentinc.notificoin.ui.adList.AdListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adListModule = module {
    single { AdListInteractor(adListPresenter = get(), searchAdsPositionRepository = get()) }
    single<AdListPresenter> {
        AdListPresenterImpl(
            adsListToAdViewModelListTransformer = get()
        )
    }
    viewModel {
        AdListViewModel(
            adViewModelList = SingleLiveEvent(),
            errorType = SingleLiveEvent()
        )
    }
}