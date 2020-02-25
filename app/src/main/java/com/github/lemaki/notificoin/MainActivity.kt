package com.github.lemaki.notificoin

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.github.lemaki.notificoin.data.sharedPreferences.SharedPreferencesRepository
import com.github.lemaki.notificoin.injection.*
import com.github.lemaki.notificoin.logger.NotifiCoinLogger
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
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
                        detectNewAdsModule
                    )
                )
            }
        } catch (exception: KoinAppAlreadyStartedException) {
            NotifiCoinLogger.i(this.applicationContext.resources.getString(R.string.koin_already_started))
        }

        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
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
            client.addPlugin(SharedPreferencesFlipperPlugin(this, SharedPreferencesRepository.PREFERENCE_FILE))
            val networkFlipperPlugin = NetworkFlipperPlugin()
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.addPlugin(networkFlipperPlugin)
            client.start()
        }
        requestBatteryWhiteListPermission(this@MainActivity)
    }

    private fun requestBatteryWhiteListPermission(context: Context) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        val sharedPreferencesRepository = SharedPreferencesRepository(this)
        if (sharedPreferencesRepository.shouldShowBatteryWhiteListDialog && powerManager != null && !powerManager.isIgnoringBatteryOptimizations(packageName)) {
            val alertMessage = getString(R.string.battery_white_list_explanation)
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val view: View = View.inflate(context, R.layout.battery_whitelist_alertdialog, null)
            Glide.with(context)
                .load(R.raw.battery_whitelist)
                .into(view.findViewById(R.id.batteryWhiteListGif))
            builder.setView(view)
            builder.setMessage(alertMessage)
            builder.setPositiveButton("OK") { _, _ ->
                try {
                    goToBatteryWhiteListOfTheApp(context)
                } catch (exception: ActivityNotFoundException) {
                    goToBatteryWhiteList()
                }
            }
            builder.setNeutralButton("Maybe later") { _, _ -> }
            builder.setNegativeButton("Never") { _, _ ->
                sharedPreferencesRepository.shouldShowBatteryWhiteListDialog = false
            }
            builder.create().show()
        }

    }

    private fun goToBatteryWhiteListOfTheApp(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
        val packageName: String = context.packageName
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun goToBatteryWhiteList() {
        val intent = Intent()
        intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
        startActivity(intent)
    }
}
