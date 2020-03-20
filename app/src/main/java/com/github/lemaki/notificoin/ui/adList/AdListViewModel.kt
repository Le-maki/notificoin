package com.github.lemaki.notificoin.ui.adList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.lemaki.core.adList.AdListErrorType
import com.github.lemaki.notificoin.ui.ad.AdListViewModel

data class AdListViewModel(
    val adListViewModel: MutableLiveData<AdListViewModel>,
    val errorType: MutableLiveData<AdListErrorType>
): ViewModel()