package com.github.corentinc.notificoin

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
import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.logger.NotifiCoinLogger
import com.github.corentinc.notificoin.injection.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin


class MainActivity: AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics
        super.onCreate(savedInstanceState)
        try {
            startKoin {
                androidLogger()
                fragmentFactory()
                androidContext(this@MainActivity)
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
        setupKoinFragmentFactory()

        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigationHome, R.id.navigationAdList, R.id.navigationSettings
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
