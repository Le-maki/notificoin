package com.github.corentinc.notificoin.ui.batteryWarning

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.github.corentinc.core.BatteryWarningInteractor
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.ChildFragment

class BatteryWarningFragment(
    val batteryWarningInteractor: BatteryWarningInteractor
): ChildFragment() {

    private fun presentBatteryWhitelistRequestAlertDialog() {
        val context = requireContext()
        val alertMessage = getString(R.string.batteryWhiteListExplanation)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_alertdialog, null)
        Glide.with(context)
            .load(R.raw.battery_whitelist)
            .into(view.findViewById(R.id.batteryWhiteListGif))
        builder.setView(view)
            .setMessage(alertMessage)
            .setPositiveButton(getString(R.string.OK)) { dialog, _ ->
                try {
                    goToBatteryWhiteListOfTheApp(context)
                } catch (exception: ActivityNotFoundException) {
                    goToBatteryWhiteList()
                }
                dialog.dismiss()
                requireActivity().onBackPressed()
            }
            .setNeutralButton(getString(R.string.alertDialogStopAsking)) { dialog, _ ->
                batteryWarningInteractor.onBatteryWhiteListAlertDialogNeutralButtonPressed()
                dialog.dismiss()
                requireActivity().onBackPressed()
            }
            .setNegativeButton(getString(R.string.alertDialogMaybeLater)) { dialog, _ ->
                dialog.dismiss()
                requireActivity().onBackPressed()
            }
            .setOnCancelListener {
                it.dismiss()
                requireActivity().onBackPressed()
            }.create().show()
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

    override fun onStart() {
        presentBatteryWhitelistRequestAlertDialog()
        super.onStart()
    }
}