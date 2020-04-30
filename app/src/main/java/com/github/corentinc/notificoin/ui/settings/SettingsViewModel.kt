package com.github.corentinc.notificoin.ui.settings

import androidx.lifecycle.ViewModel
import com.github.corentinc.notificoin.ui.SingleLiveEvent

class SettingsViewModel: ViewModel() {
    val text = SingleLiveEvent<String>()
}