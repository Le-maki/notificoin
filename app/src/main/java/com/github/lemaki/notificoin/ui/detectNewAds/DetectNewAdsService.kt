package com.github.lemaki.notificoin.ui.detectNewAds

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.domain.DetectNewAdsInteractor
import com.github.lemaki.notificoin.injection.*
import com.github.lemaki.notificoin.logger.NotifiCoinLogger
import com.github.lemaki.notificoin.ui.notificationPush.NotificationManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.parameter.parametersOf

class DetectNewAdsService: JobIntentService(), DetectNewAdsPresenter {
    private val detectNewAdsInteractor: DetectNewAdsInteractor by inject { parametersOf(this) }
    private val notificationManager: NotificationManager by inject()

    override fun onHandleWork(intent: Intent) {
        try {
            stopKoin()
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
                        detectNewAdsModule,
                        sharedPreferencesModule
                    )
                )
            }
        } catch (exception: KoinAppAlreadyStartedException) {
            NotifiCoinLogger.i(getString(R.string.koinAlreadyStarted))
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