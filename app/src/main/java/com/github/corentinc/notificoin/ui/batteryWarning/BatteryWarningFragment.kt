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
import com.github.corentinc.logger.analytics.NotifiCoinEvent.*
import com.github.corentinc.logger.analytics.NotifiCoinEventButtonName.*
import com.github.corentinc.logger.analytics.NotifiCoinEventException.NO_APP_FOR_INTENT
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.*
import com.github.corentinc.logger.analytics.NotifiCoinEventPopUp.DEFAULT
import com.github.corentinc.logger.analytics.NotifiCoinEventPopUp.SPECIAL_CONSTRUCTOR
import com.github.corentinc.logger.analytics.NotifiCoinEventScreen.BATTERY_WARNING
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
        AnalyticsEventSender.sendEvent(
            ScreenStarted(Screen(BATTERY_WARNING))
        )
        val batteryWarningFragmentArgs: BatteryWarningFragmentArgs by navArgs()
        batteryWarningInteractor.onStart(
            PowerManagementPackages.isAnyIntentCallable(requireContext()),
            batteryWarningFragmentArgs.shouldDisplayDefaultDialog,
            batteryWarningFragmentViewModel.wasDefaultDialogAlreadyShown,
            PowerManagementPackages.findCallableConstructor(requireContext())
        )
        super.onStart()
    }

    override fun displayBatteryWhitelistRequestAlertDialog(shouldDisplaySpecialConstructorDialog: Boolean) {
        val context = requireContext()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_alertdialog, null)
        Glide.with(context)
            .load(R.raw.battery_whitelist)
            .into(view.findViewById(R.id.batteryWhiteListGif))
        defaultAlertDialog = builder.setView(view)
            .setOnCancelListener {
                requireActivity().onBackPressed()
            }
            .create()
        defaultAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        defaultAlertDialog?.show()
        AnalyticsEventSender.sendEvent(
            PopUpShown(
                PopUp(DEFAULT),
                Screen(BATTERY_WARNING)
            )
        )
        view.findViewById<Button>(R.id.batteryWhiteListOKButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(OK), Screen(BATTERY_WARNING), PopUp(DEFAULT)
                )
            )
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
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(STOP_ASKING),
                    Screen(BATTERY_WARNING),
                    PopUp(DEFAULT)
                )
            )
            batteryWarningInteractor.onBatteryWhiteListAlertDialogNeutralButtonPressed()
            requireActivity().onBackPressed()
        }
        view.findViewById<Button>(R.id.batteryWhiteListMaybeButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(MAYBE_LATER),
                    Screen(BATTERY_WARNING),
                    PopUp(DEFAULT)
                )
            )
            requireActivity().onBackPressed()
        }
        view.findViewById<ImageView>(R.id.batteryWhiteListGif).isVisible =
            activity?.resources?.configuration?.orientation != Configuration.ORIENTATION_LANDSCAPE
    }

    override fun displaySpecialConstructorDialog(): View {
        val context = requireContext()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_special_alertdialog, null)
        specialAlertDialog = builder.setView(view)
            .setOnCancelListener {
                requireActivity().onBackPressed()
            }
            .create()
        specialAlertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        specialAlertDialog?.show()
        AnalyticsEventSender.sendEvent(
            PopUpShown(
                PopUp(SPECIAL_CONSTRUCTOR),
                Screen(BATTERY_WARNING)
            )
        )
        view.findViewById<Button>(R.id.batteryWhiteListSpecialStopAskingButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(STOP_ASKING),
                    Screen(BATTERY_WARNING),
                    PopUp(SPECIAL_CONSTRUCTOR)
                )
            )
            batteryWarningInteractor.onBatteryWhiteListAlertDialogNeutralButtonPressed()
            requireActivity().onBackPressed()
        }
        view.findViewById<Button>(R.id.batteryWhiteListSpecialMaybeButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(MAYBE_LATER),
                    Screen(BATTERY_WARNING),
                    PopUp(SPECIAL_CONSTRUCTOR)
                )
            )
            requireActivity().onBackPressed()
        }
        view.findViewById<ImageView>(R.id.batteryWhiteListGif).isVisible = false
        view.findViewById<TextView>(R.id.batteryWhiteListSpecialOKButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(OK),
                    Screen(BATTERY_WARNING),
                    PopUp(SPECIAL_CONSTRUCTOR)
                )
            )
            PowerManagementPackages.findCallableIntent(requireContext())?.let {
                NotifiCoinLogger.i("BatteryWarningFragment detected special intent : ${it.intent}")
                try {
                    startActivity(it.intent)
                } catch (exception: ActivityNotFoundException) {
                    NotifiCoinLogger.e(
                        "Tried to open special power management app, found Intent but app not found",
                        exception
                    )
                    AnalyticsEventSender.sendEvent(
                        ExceptionThrown(
                            EventException(NO_APP_FOR_INTENT),
                            Screen(BATTERY_WARNING),
                            PopUp(SPECIAL_CONSTRUCTOR)
                        )
                    )
                }
            } ?: NotifiCoinLogger.e("Tried to open special power management app, not intent found")
            requireActivity().onBackPressed()
        }
        return view
    }

    override fun displayHuaweiDialog() {
        val view = displaySpecialConstructorDialog()
        view.findViewById<ImageView>(R.id.batteryWhiteListGif).isVisible = true
        view.findViewById<TextView>(R.id.batteryWhiteListSpecialTitle).text =
            resources.getString(R.string.batteryWarningDetectedHuawei)
        Glide.with(requireContext())
            .load(R.raw.huawei_battery)
            .into(view.findViewById(R.id.batteryWhiteListGif))
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