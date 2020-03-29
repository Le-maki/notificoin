package com.github.corentinc.notificoin.ui.home

import androidx.lifecycle.ViewModel
import com.github.corentinc.core.search.Search
import com.github.corentinc.notificoin.ui.SingleLiveEvent

data class HomeViewModel(
    val shouldShowBatteryWhiteListAlertDialog: SingleLiveEvent<Boolean>,
    val searchAdsPositionList: SingleLiveEvent<MutableList<Search>>
): ViewModel()