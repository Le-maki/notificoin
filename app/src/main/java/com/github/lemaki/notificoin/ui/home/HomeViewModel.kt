package com.github.lemaki.notificoin.ui.home

import androidx.lifecycle.ViewModel
import com.github.lemaki.core.search.Search
import com.github.lemaki.notificoin.ui.SingleLiveEvent

data class HomeViewModel(
    val shouldShowBatteryWhiteListAlertDialog: SingleLiveEvent<Boolean>,
    val searchList: SingleLiveEvent<MutableList<Search>>
): ViewModel()