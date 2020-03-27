package com.github.lemaki.notificoin.ui.adList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.lemaki.core.adList.AdListErrorType
import com.github.lemaki.notificoin.ui.ad.AdListTextViewModel

data class AdListViewModel(
    val adListTextViewModel: MutableLiveData<AdListTextViewModel>,
    val errorType: MutableLiveData<AdListErrorType>
): ViewModel()