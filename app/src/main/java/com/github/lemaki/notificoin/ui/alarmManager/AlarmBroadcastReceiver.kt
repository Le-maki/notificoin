package com.github.lemaki.notificoin.ui.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.logger.NotifiCoinLogger
import com.github.lemaki.notificoin.ui.detectNewAds.DetectNewAdsService
import org.koin.core.KoinComponent

class AlarmBroadcastReceiver: BroadcastReceiver(), KoinComponent {

    override fun onReceive(context: Context, intent: Intent) {
        NotifiCoinLogger.i(context.getString(R.string.enteringOnReceive))
        DetectNewAdsService.enqueueWork(context, Intent())
    }
}