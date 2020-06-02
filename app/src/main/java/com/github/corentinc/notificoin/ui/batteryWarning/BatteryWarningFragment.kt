package com.github.corentinc.notificoin.ui.batteryWarning

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.github.corentinc.core.BatteryWarningInteractor
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.ChildFragment
import kotlinx.android.synthetic.main.battery_whitelist_alertdialog.*


class BatteryWarningFragment(
    val batteryWarningInteractor: BatteryWarningInteractor
): ChildFragment() {
    lateinit var alertDialog: AlertDialog

    private fun presentBatteryWhitelistRequestAlertDialog() {
        val context = requireContext()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_alertdialog, null)
        Glide.with(context)
            .load(R.raw.battery_whitelist)
            .into(view.findViewById(R.id.batteryWhiteListGif))
        alertDialog = builder.setView(view)
            .setOnCancelListener {
                requireActivity().onBackPressed()
            }
            .create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        view.findViewById<Button>(R.id.batteryWhiteListOKButton).setOnClickListener {
            try {
                goToBatteryWhiteListOfTheApp(context)
            } catch (exception: ActivityNotFoundException) {
                goToBatteryWhiteList()
            }
            requireActivity().onBackPressed()
        }
        view.findViewById<Button>(R.id.batteryWhiteListStopAskingButton).setOnClickListener {
            batteryWarningInteractor.onBatteryWhiteListAlertDialogNeutralButtonPressed()
            requireActivity().onBackPressed()
        }
        view.findViewById<Button>(R.id.batteryWhiteListMaybeButton).setOnClickListener {
            requireActivity().onBackPressed()
        }
        view.findViewById<ImageView>(R.id.batteryWhiteListGif).isVisible =
            activity?.resources?.configuration?.orientation != Configuration.ORIENTATION_LANDSCAPE
    }

    override fun onPause() {
        alertDialog.dismiss()
        super.onPause()
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