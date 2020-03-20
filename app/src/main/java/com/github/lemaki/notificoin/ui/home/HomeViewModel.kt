package com.github.lemaki.notificoin.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.lemaki.core.search.Search

data class HomeViewModel(
    val shouldShowBatteryWhiteListAlertDialog: MutableLiveData<Boolean>,
    val searchList: MutableLiveData<List<Search>>
): ViewModel()