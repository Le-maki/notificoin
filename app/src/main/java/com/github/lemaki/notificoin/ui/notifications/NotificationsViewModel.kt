package com.github.lemaki.notificoin.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.lemaki.notificoin.ui.SingleLiveEvent

class NotificationsViewModel: ViewModel() {
    private val _text = SingleLiveEvent<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}