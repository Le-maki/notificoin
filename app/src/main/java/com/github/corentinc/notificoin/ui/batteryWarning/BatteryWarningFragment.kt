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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.corentinc.core.BatteryWarningInteractor
import com.github.corentinc.core.ui.batteryWarning.BatteryWarningDisplay
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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BatteryWarningFragment(
    val batteryWarningInteractor: BatteryWarningInteractor
) : Fragment(), BatteryWarningDisplay {
    private val batteryWarningFragmentViewModel: BatteryWarningFragmentViewModel by sharedViewModel()
    private var defaultAlertDialog: AlertDialog? = null
    private var specialAlertDialog: AlertDialog? = null
    private lateinit var dialog: View

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
        loadDefaultGif(view)
        defaultAlertDialog = builder.setView(view)
            .setOnCancelListener {
                batteryWarningInteractor.onDefaultDialogCanceled()
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
        setDefaultOKButtonListener(view, shouldDisplaySpecialConstructorDialog)
        setDefaultStopAskingButtonListener(view)
        setDefaultMaybeButtonListener(view)
    }

    private fun loadDefaultGif(dialog: View) {
        Glide.with(requireContext())
            .load(R.raw.battery_whitelist)
            .into(dialog.findViewById(R.id.batteryWhiteListGif))
        dialog.findViewById<ImageView>(R.id.batteryWhiteListGif).isVisible =
            activity?.resources?.configuration?.orientation != Configuration.ORIENTATION_LANDSCAPE
    }

    private fun setDefaultMaybeButtonListener(dialog: View) {
        dialog.findViewById<Button>(R.id.batteryWhiteListMaybeButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(MAYBE_LATER),
                    Screen(BATTERY_WARNING),
                    PopUp(DEFAULT)
                )
            )
            batteryWarningInteractor.onDefaultMaybeButtonClicked()
        }
    }

    private fun setDefaultStopAskingButtonListener(dialog: View) {
        dialog.findViewById<Button>(R.id.batteryWhiteListStopAskingButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(STOP_ASKING),
                    Screen(BATTERY_WARNING),
                    PopUp(DEFAULT)
                )
            )
            batteryWarningInteractor.onStopAskingButtonPressed()
        }
    }

    private fun setDefaultOKButtonListener(
        dialogue: View,
        shouldDisplaySpecialConstructorDialog: Boolean
    ) {
        dialogue.findViewById<Button>(R.id.batteryWhiteListOKButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(OK), Screen(BATTERY_WARNING), PopUp(DEFAULT)
                )
            )
            batteryWarningInteractor.onDefaultOKButtonClicked(shouldDisplaySpecialConstructorDialog)
        }
    }

    override fun displaySpecialConstructorDialog() {
        val context = requireContext()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_special_alertdialog, null)
        specialAlertDialog = builder.setView(view)
            .setOnCancelListener {
                batteryWarningInteractor.onSpecialCanceled()
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
        view.findViewById<ImageView>(R.id.batteryWhiteListGif).isVisible = false
        setSpecialStopAskingButtonListener(view)
        setSpecialMaybeButtonListener(view)
        setSpecialOKButtonListener(view)
        dialog = view
    }

    private fun setSpecialOKButtonListener(dialog: View) {
        dialog.findViewById<TextView>(R.id.batteryWhiteListSpecialOKButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(OK),
                    Screen(BATTERY_WARNING),
                    PopUp(SPECIAL_CONSTRUCTOR)
                )
            )
            batteryWarningInteractor.onSpecialOkButtonClicked()
        }
    }

    private fun setSpecialMaybeButtonListener(dialog: View) {
        dialog.findViewById<Button>(R.id.batteryWhiteListSpecialMaybeButton).setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(MAYBE_LATER),
                    Screen(BATTERY_WARNING),
                    PopUp(SPECIAL_CONSTRUCTOR)
                )
            )
            batteryWarningInteractor.onSpecialMaybeButtonClicked()
        }
    }

    private fun setSpecialStopAskingButtonListener(dialog: View) {
        dialog.findViewById<Button>(R.id.batteryWhiteListSpecialStopAskingButton)
            .setOnClickListener {
                AnalyticsEventSender.sendEvent(
                    ButtonClicked(
                        ButtonName(STOP_ASKING),
                        Screen(BATTERY_WARNING),
                        PopUp(SPECIAL_CONSTRUCTOR)
                    )
                )
                batteryWarningInteractor.onStopAskingButtonPressed()
            }
    }

    override fun displayHuaweiDialog() {
        dialog.findViewById<ImageView>(R.id.batteryWhiteListGif).isVisible = true
        dialog.findViewById<TextView>(R.id.batteryWhiteListSpecialTitle).text =
            resources.getString(R.string.batteryWarningDetectedHuawei)
        Glide.with(requireContext())
            .load(R.raw.huawei_battery)
            .into(dialog.findViewById(R.id.batteryWhiteListGif))
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

    override fun displayBack() {
        requireActivity().onBackPressed()
    }

    override fun displayBatteryWhiteList() {
        batteryWarningFragmentViewModel.wasDefaultDialogAlreadyShown = true
        try {
            goToBatteryWhiteListOfTheApp(requireContext())
        } catch (exception: ActivityNotFoundException) {
            goToBatteryWhiteList()
        }
    }

    override fun displaySpecialIntent() {
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
    }
}