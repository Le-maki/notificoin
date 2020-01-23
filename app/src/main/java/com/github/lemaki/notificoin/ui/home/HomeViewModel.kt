package com.github.lemaki.notificoin.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.lemaki.notificoin.domain.home.HomeErrorType
import com.github.lemaki.notificoin.ui.ad.AdListViewModel

data class HomeViewModel(
	val adListViewModel: MutableLiveData<AdListViewModel> = MutableLiveData(),
	val errorType: MutableLiveData<HomeErrorType> = MutableLiveData()
): ViewModel()