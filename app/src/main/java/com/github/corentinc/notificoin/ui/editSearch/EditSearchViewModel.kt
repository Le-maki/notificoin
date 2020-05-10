package com.github.corentinc.notificoin.ui.editSearch

import androidx.lifecycle.ViewModel
import com.github.corentinc.core.editSearch.UrlError
import com.github.corentinc.notificoin.ui.SingleLiveEvent

data class EditSearchViewModel(
    val isTitleEmpty: SingleLiveEvent<Boolean>,
    val urlError: SingleLiveEvent<UrlError?>
): ViewModel()