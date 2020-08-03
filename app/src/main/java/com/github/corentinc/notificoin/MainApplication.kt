package com.github.corentinc.notificoin

import android.app.Application
import com.github.corentinc.logger.NotifiCoinLogger
import com.github.corentinc.notificoin.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            startKoin {
                androidLogger()
                fragmentFactory()
                androidContext(this@MainApplication)
                modules(
                    listOf(
                        fragmentModule,
                        homeModule,
                        databaseModule,
                        adModule,
                        searchModule,
                        webPageModule,
                        notificationModule,
                        alarmManagerModule,
                        searchAdsPositionModule,
                        detectNewAdsModule,
                        sharedPreferencesModule,
                        adListModule,
                        editSearchModule,
                        searchesRecyclerViewModule,
                        searchPositionModule,
                        settingsModule,
                        batteryWarningModule
                    )
                )
            }
        } catch (exception: IllegalStateException) {
            NotifiCoinLogger.i(this.applicationContext.resources.getString(R.string.koinAlreadyStarted))
        }
    }
}