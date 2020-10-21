package com.github.corentinc.notificoin.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {
    val notificationIntervalEntry = MutableLiveData<String>()
}