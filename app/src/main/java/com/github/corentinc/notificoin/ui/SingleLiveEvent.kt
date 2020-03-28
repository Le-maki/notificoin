package com.github.corentinc.notificoin.ui

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.corentinc.logger.NotifiCoinLogger
import java.util.concurrent.atomic.AtomicBoolean


/**
 * https://github.com/android/architecture-samples/tree/dev-todo-mvvm-live
 */
class SingleLiveEvent<T>: MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            NotifiCoinLogger.i("Multiple observers registered but only one will be notified of changes.")
        }
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }
}