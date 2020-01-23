package com.github.lemaki.notificoin.ui.home

import androidx.lifecycle.ViewModel
import com.github.lemaki.notificoin.domain.home.HomeErrorType
import com.github.lemaki.notificoin.ui.ad.AdListViewModel

data class HomeViewModel(
	val adListViewModel: AdListViewModel? = null,
	val errorType: HomeErrorType? = null
) : ViewModel()