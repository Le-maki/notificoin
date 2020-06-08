package com.github.corentinc.notificoin.ui.batteryWarning

import androidx.lifecycle.ViewModel

data class BatteryWarningFragmentViewModel(
    var wasDefaultDialogAlreadyShown: Boolean
): ViewModel()
