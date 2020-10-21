package com.github.corentinc.notificoin.ui.editSearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.corentinc.core.editSearch.UrlError

data class EditSearchViewModel(
    val savedTitle: MutableLiveData<String>,
    val title: MutableLiveData<String>,
    val url: MutableLiveData<String>,
    val isSaveButtonEnabled: MutableLiveData<Boolean>,
    val urlError: MutableLiveData<UrlError?>,
    val isUrlInfoTextVisible: MutableLiveData<Boolean>,
    val UrlButtonDisplayedChild: MutableLiveData<Int>
): ViewModel()