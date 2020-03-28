package com.github.lemaki.notificoin.ui.adList

import androidx.lifecycle.ViewModel
import com.github.lemaki.core.adList.AdListErrorType
import com.github.lemaki.notificoin.ui.SingleLiveEvent
import com.github.lemaki.notificoin.ui.ad.AdListTextViewModel

data class AdListViewModel(
    val adListTextViewModel: SingleLiveEvent<AdListTextViewModel>,
    val errorType: SingleLiveEvent<AdListErrorType>
): ViewModel()