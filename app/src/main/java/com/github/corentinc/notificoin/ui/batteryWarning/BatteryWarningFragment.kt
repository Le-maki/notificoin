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
import com.github.corentinc.logger.analytics.NotifiCoinEvent
import com.github.corentinc.notificoin.AnalyticsEventSender
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.ChildFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BatteryWarningFragment(
    val batteryWarningInteractor: BatteryWarningInteractor
): ChildFragment(), BatteryWarningDisplay {
    private val batteryWarningFragmentViewModel: BatteryWarningFragmentViewModel by viewModel()
    private var defaultAlertDialog: AlertDialog? = null
    private var specialAlertDialog: AlertDialog? = null

    init {
        (batteryWarningInteractor.batteryWarningPresenter as BatteryWarningPresenterImpl).batteryWarningDisplay =
            this
    }

    override fun onPause() {
        defaultAlertDialog?.dismiss()
        specialAlertDialog?.dismiss()
        super.onPause()
    }

    override fun onStart() {
        AnalyticsEventSender.sendEvent(NotifiCoinEvent.BATTERY_WARNING_START)
        val batteryWarningFragmentArgs: BatteryWarningFragmentArgs by navArgs()
        batteryWarningInteractor.onStart(
            PowerManagementPackages.isAnyIntentCallable(requireContext()),
            batteryWarningFragmentArgs.shouldDisplayDefaultDialog,
            batteryWarningFragmentViewModel.wasDefaultDialogAlreadyShown
        )
        super.onStart()
    }

    override fun displayBatteryWhitelistRequestAlertDialog(shouldDisplaySpecialConstructorDialog: Boolean) {
        AnalyticsEventSender.sendEvent(NotifiCoinEvent.BATTERY_WARNING_DEFAULT_POPUP_SHOW)
        val context = requireContext()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_alertdialog, null)
        Glide.with(context)
            .load(R.raw.battery_whitelist)
            .into(view.findViewById(R.id.batteryWhiteListGif))
        defaultAlertDialog = builder.setView(view)
            .setOnCancelListener {
                // empty
            }
            .create()
        defaultAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        defaultAlertDialog?.show()
        view.findViewById<Button>(R.id.batteryWhiteListOKButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(NotifiCoinEvent.DEFAULT_OK_CLICKED)
            batteryWarningFragmentViewModel.wasDefaultDialogAlreadyShown = true
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
            AnalyticsEventSender.sendEvent(NotifiCoinEvent.DEFAULT_STOP_ASKING_CLICKED)
            batteryWarningInteractor.onBatteryWhiteListAlertDialogNeutralButtonPressed()
            requireActivity().onBackPressed()
        }
        view.findViewById<Button>(R.id.batteryWhiteListMaybeButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(NotifiCoinEvent.DEFAULT_MAYBE_LATER_CLICKED)
            requireActivity().onBackPressed()
        }
        view.findViewById<ImageView>(R.id.batteryWhiteListGif).isVisible =
            activity?.resources?.configuration?.orientation != Configuration.ORIENTATION_LANDSCAPE
    }

    override fun displaySpecialConstructorDialog() {
        AnalyticsEventSender.sendEvent(NotifiCoinEvent.BATTERY_WARNING_SPECIAL_POPUP_SHOW)
        val context = requireContext()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_special_alertdialog, null)
        specialAlertDialog = builder.setView(view)
            .setOnCancelListener {
                // empty
            }
            .create()
        specialAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        specialAlertDialog?.show()
        view.findViewById<Button>(R.id.batteryWhiteListSpecialStopAskingButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(NotifiCoinEvent.SPECIAL_STOP_ASKING_CLICKED)
            batteryWarningInteractor.onBatteryWhiteListAlertDialogNeutralButtonPressed()
            requireActivity().onBackPressed()
        }
        view.findViewById<Button>(R.id.batteryWhiteListSpecialMaybeButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(NotifiCoinEvent.SPECIAL_MAYBE_LATER_CLICKED)
            requireActivity().onBackPressed()
        }
        view.findViewById<TextView>(R.id.batteryWhiteListSpecialOKButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(NotifiCoinEvent.SPECIAL_OK_CLICKED)
            PowerManagementPackages.findCallableIntent(requireContext())?.let {
                NotifiCoinLogger.i("BatteryWarningFragment detected special intent : ${it.component}")
                try {
                    startActivity(it)
                } catch (exception: ActivityNotFoundException) {
                    NotifiCoinLogger.e(
                        "Tried to open special power management app, found Intent but app not found",
                        exception
                    )
                    AnalyticsEventSender.sendEvent(NotifiCoinEvent.BATTERY_WARNING_NO_APP_FOR_INTENT)
                }
            } ?: NotifiCoinLogger.e("Tried to open special power management app, not intent found")
            requireActivity().onBackPressed()
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