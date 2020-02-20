package com.github.lemaki.notificoin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import com.facebook.soloader.SoLoader
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
import kotlinx.android.synthetic.main.activity_main.navView
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
			client.addPlugin(InspectorFlipperPlugin(this.applicationContext, DescriptorMapping.withDefaults()))
			val networkFlipperPlugin = NetworkFlipperPlugin()
			client.addPlugin(DatabasesFlipperPlugin(this))
			client.addPlugin(networkFlipperPlugin)
			client.start()
		}
	}
}
