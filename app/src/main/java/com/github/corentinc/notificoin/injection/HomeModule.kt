package com.github.corentinc.notificoin.injection

import androidx.lifecycle.MutableLiveData
import com.github.corentinc.core.home.HomeInteractor
import com.github.corentinc.core.ui.home.HomePresenter
import com.github.corentinc.notificoin.ui.home.HomePresenterImpl
import com.github.corentinc.notificoin.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single {
        HomeInteractor(
            homePresenter = get(),
            searchRepository = get(),
            searchPositionRepository = get(),
            searchAdsPositionRepository = get(),
            sharedPreferencesRepository = get(),
            searchAdsPostionDefaultSorter = get(),
            alarmManagerInteractor = get(),
            globalSharedPreferencesRepository = get()
        )
    }
    single<HomePresenter> { HomePresenterImpl() }
    viewModel {
        HomeViewModel(
            shouldShowBatteryWhiteListAlertDialog = MutableLiveData(),
            searchAdsPositionList = MutableLiveData()
        )
    }
}