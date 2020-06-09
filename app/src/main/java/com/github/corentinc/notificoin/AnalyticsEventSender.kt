package com.github.corentinc.notificoin

import android.os.Bundle
import com.github.corentinc.logger.analytics.NotificoinEvent
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsEventSender {
    lateinit var firebaseAnalytics: FirebaseAnalytics

    fun sendEvent(event: NotificoinEvent) {
        val params = Bundle()
        event.parameters.forEach {
            params.putString(it.name, it.value)
        }
        firebaseAnalytics.logEvent(event.name, params)
    }
}