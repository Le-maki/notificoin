package com.github.lemaki.notificoin.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.lemaki.core.home.HomeErrorType
import com.github.lemaki.core.search.Search
import com.github.lemaki.notificoin.ui.ad.AdListViewModel

data class HomeViewModel(
    val adListViewModel: MutableLiveData<AdListViewModel>,
    val errorType: MutableLiveData<HomeErrorType>,
    val shouldShowBatteryWhiteListAlertDialog: MutableLiveData<Boolean>,
    val searchList: MutableLiveData<List<Search>>
): ViewModel()