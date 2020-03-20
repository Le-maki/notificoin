package com.github.lemaki.notificoin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.github.lemaki.core.repository.SharedPreferencesRepository
import com.github.lemaki.logger.NotifiCoinLogger
import com.github.lemaki.notificoin.injection.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.error.KoinAppAlreadyStartedException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            stopKoin()
            startKoin {
                androidLogger()
                fragmentFactory()
                androidContext(this@MainActivity)
                modules(
                    listOf(
                        homeModule,
                        databaseModule,
                        adModule,
                        searchModule,
                        webPageModule,
                        notificationModule,
                        alarmManagerModule,
                        searchWithAdsModule,
                        detectNewAdsModule,
                        sharedPreferencesModule,
                        adListModule
                    )
                )
            }
        } catch (exception: KoinAppAlreadyStartedException) {
            NotifiCoinLogger.i(this.applicationContext.resources.getString(R.string.koinAlreadyStarted))
        }

        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_ad_list, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(
                InspectorFlipperPlugin(
                    this.applicationContext,
                    DescriptorMapping.withDefaults()
                )
            )
            client.addPlugin(
                SharedPreferencesFlipperPlugin(
                    this,
                    SharedPreferencesRepository.PREFERENCE_FILE
                )
            )
            val networkFlipperPlugin = NetworkFlipperPlugin()
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.addPlugin(networkFlipperPlugin)
            client.start()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.navHostFragment).navigateUp()
                || super.onSupportNavigateUp()
    }
}
