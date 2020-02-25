package com.github.lemaki.notificoin.ui.detectNewAds

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.domain.DetectNewAdsInteractor
import com.github.lemaki.notificoin.injection.adModule
import com.github.lemaki.notificoin.injection.alarmManagerModule
import com.github.lemaki.notificoin.injection.databaseModule
import com.github.lemaki.notificoin.injection.detectNewAdsModule
import com.github.lemaki.notificoin.injection.homeModule
import com.github.lemaki.notificoin.injection.notificationModule
import com.github.lemaki.notificoin.injection.searchModule
import com.github.lemaki.notificoin.injection.searchWithAdsModule
import com.github.lemaki.notificoin.injection.webPageModule
import com.github.lemaki.notificoin.logger.NotifiCoinLogger
import com.github.lemaki.notificoin.ui.notificationPush.NotificationManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.parameter.parametersOf

class DetectNewAdsService: JobIntentService(), DetectNewAdsPresenter {
    private val detectNewAdsInteractor: DetectNewAdsInteractor by inject { parametersOf(this) }
    private val notificationManager: NotificationManager by inject { parametersOf(this) }

    override fun onHandleWork(intent: Intent) {
        try {
            startKoin {
                androidLogger()
                fragmentFactory()
                androidContext(this@DetectNewAdsService)
                modules(
                    listOf(
                        homeModule,
                        databaseModule,
                        adModule,
                        searchModule,
                        webPageModule,
                        searchWithAdsModule,
                        notificationModule,
                        alarmManagerModule,
                        detectNewAdsModule
                    )
                )
            }
        } catch (exception: KoinAppAlreadyStartedException) {
            NotifiCoinLogger.i(getString(R.string.koin_already_started))
        }
        detectNewAdsInteractor.onServiceStarted()
    }

    companion object {
        private const val JOB_ID = 1
        @JvmStatic
        fun enqueueWork(context: Context?, work: Intent?) {
            context?.let {
                work?.let {
                    enqueueWork(
                        context, DetectNewAdsService::class.java,
                        JOB_ID, work
                    )
                }
            }
        }
    }

    override fun presentBigtextNotification(title: String, text: String, bigText: String) {
        notificationManager.sendBigtextNotification(title, text, bigText)
    }

    override fun presentNewAdNotifications(size: Int, titlesString: String, title: String) {
        notificationManager.sendNewAdNotifications(size, titlesString, title)
    }
}