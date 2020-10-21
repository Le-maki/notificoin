package com.github.corentinc.notificoin.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.corentinc.core.search.Search

data class HomeViewModel(
    val shouldShowBatteryWhiteListAlertDialog: MutableLiveData<Boolean>,
    val searchAdsPositionList: MutableLiveData<MutableList<Search>>
): ViewModel()