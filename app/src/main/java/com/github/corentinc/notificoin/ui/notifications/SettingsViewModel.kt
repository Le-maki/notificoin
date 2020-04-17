package com.github.corentinc.notificoin.ui.notifications

import androidx.lifecycle.ViewModel
import com.github.corentinc.notificoin.ui.SingleLiveEvent

class SettingsViewModel: ViewModel() {
    val text = SingleLiveEvent<String>().apply {
        value = "This is notifications Fragment"
    }
}