package com.github.corentinc.notificoin.ui.batteryWarning

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
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.corentinc.core.BatteryWarningInteractor
import com.github.corentinc.logger.NotifiCoinLogger
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.ChildFragment


class BatteryWarningFragment(
    val batteryWarningInteractor: BatteryWarningInteractor
): ChildFragment() {
    private var wasDefaultDialogAlreadyShown = false
    private var shouldDisplaySpecialConstructorDialog = false
    private var specialIntent: Intent? = null
    private lateinit var alertDialog: AlertDialog

    private fun presentBatteryWhitelistRequestAlertDialog(): View {
        val context = requireContext()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_alertdialog, null)
        Glide.with(context)
            .load(R.raw.battery_whitelist)
            .into(view.findViewById(R.id.batteryWhiteListGif))
        alertDialog = builder.setView(view)
            .setOnCancelListener {
            }
            .create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        view.findViewById<Button>(R.id.batteryWhiteListOKButton).setOnClickListener {
            wasDefaultDialogAlreadyShown = true
            try {
                goToBatteryWhiteListOfTheApp(context)
            } catch (exception: ActivityNotFoundException) {
                goToBatteryWhiteList()
            }
            if (!shouldDisplaySpecialConstructorDialog) {
                requireActivity().onBackPressed()
            }
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
        return view
    }

    private fun presentSpecialConstructorDialog(view: View) {
        view.findViewById<ImageView>(R.id.batteryWhiteListGif).isVisible = false
        view.findViewById<TextView>(R.id.batteryWhiteListTitle).text =
            getString(R.string.BatteryWarningDetectedSpecialConstructor)
        view.findViewById<TextView>(R.id.batteryWhiteListOKButton).setOnClickListener {
            specialIntent?.let {
                try {
                    startActivity(it)
                } catch (exception: ActivityNotFoundException) {
                    NotifiCoinLogger.e(
                        "Tried to open special power management app, found Intent but app not found",
                        exception
                    )
                }
            } ?: NotifiCoinLogger.e("Tried to open special power management app, not intent found")
            requireActivity().onBackPressed()
        }
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
        PowerManagementPackages.findCallableIntent(requireContext())?.let {
            specialIntent = it
            shouldDisplaySpecialConstructorDialog = true
            NotifiCoinLogger.i("BatteryWarningFragment detected special intent : ${it.component}")
        }
        val batteryWarningFragmentArgs: BatteryWarningFragmentArgs by navArgs()
        val view = presentBatteryWhitelistRequestAlertDialog()
        if (shouldDisplaySpecialConstructorDialog &&
            (!batteryWarningFragmentArgs.shouldDisplayDefaultDialog || (batteryWarningFragmentArgs.shouldDisplayDefaultDialog && wasDefaultDialogAlreadyShown))
        ) {
            presentSpecialConstructorDialog(view)
        }
        super.onStart()
    }
}