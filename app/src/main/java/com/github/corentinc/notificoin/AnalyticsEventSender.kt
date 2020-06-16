package com.github.corentinc.notificoin

import android.os.Bundle
import com.github.corentinc.logger.analytics.NotifiCoinEvent
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsEventSender {
    lateinit var firebaseAnalytics: FirebaseAnalytics

    fun sendEvent(event: NotifiCoinEvent) {
        val bundle = Bundle()
        event.parameters.forEach { parameter ->
            parameter?.let {
                bundle.putString(it.name, it.value)
            }
        }
        firebaseAnalytics.logEvent(event.key, bundle)
    }
}