package com.github.corentinc.notificoin.ui.adList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.corentinc.core.adList.AdListErrorType

data class AdListViewModel(
    val adViewDataList: MutableLiveData<MutableList<AdViewData>>,
    val errorType: MutableLiveData<AdListErrorType>
): ViewModel()