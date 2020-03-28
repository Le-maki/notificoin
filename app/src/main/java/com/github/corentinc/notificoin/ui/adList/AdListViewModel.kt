package com.github.corentinc.notificoin.ui.adList

import androidx.lifecycle.ViewModel
import com.github.corentinc.core.adList.AdListErrorType
import com.github.corentinc.notificoin.ui.SingleLiveEvent
import com.github.corentinc.notificoin.ui.ad.AdListTextViewModel

data class AdListViewModel(
    val adListTextViewModel: SingleLiveEvent<AdListTextViewModel>,
    val errorType: SingleLiveEvent<AdListErrorType>
): ViewModel()