package com.github.corentinc.notificoin.ui.adList

import androidx.lifecycle.ViewModel
import com.github.corentinc.core.adList.AdListErrorType
import com.github.corentinc.notificoin.ui.SingleLiveEvent

data class AdListViewModel(
    val adViewModelList: SingleLiveEvent<MutableList<AdViewModel>>,
    val errorType: SingleLiveEvent<AdListErrorType>
): ViewModel()