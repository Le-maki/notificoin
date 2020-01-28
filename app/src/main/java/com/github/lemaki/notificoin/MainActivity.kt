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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
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
