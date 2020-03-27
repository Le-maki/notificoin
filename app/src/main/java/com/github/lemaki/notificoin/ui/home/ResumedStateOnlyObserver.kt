package com.github.lemaki.notificoin.ui.home

import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class ResumedStateOnlyObserver<T>(
    private val lifecycleOwner: LifecycleOwner,
    val function: (t: T) -> Unit
): Observer<T> {

    override fun onChanged(t: T) {
        if (lifecycleOwner.lifecycle.currentState == RESUMED) {
            function(t)
        }
    }
}