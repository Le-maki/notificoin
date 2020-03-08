package com.github.lemaki.notificoin.ui.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.domain.home.HomeErrorType.*
import com.github.lemaki.notificoin.domain.home.HomeInteractor
import com.github.lemaki.notificoin.ui.alarmManager.NotifiCoinAlarmManager
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val homeInteractor: HomeInteractor by inject()
    private val homeViewModel: HomeViewModel by viewModel()
    private val alarmManager: NotifiCoinAlarmManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindViewModel()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = requireContext()
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        homeInteractor.onStart(
            powerManager != null && powerManager.isIgnoringBatteryOptimizations(
                context.packageName
            )
        )
        alarmManager.setAlarmManager()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bindViewModel() {
        homeViewModel.adListViewModel.observe(this.viewLifecycleOwner, Observer {
            textHome?.text = it.text
            hideProgressBar()
        })
        homeViewModel.errorType.observe(this.viewLifecycleOwner, Observer { homeErrorType ->
            homeErrorType?.let {
                val text = when (it) {
                    CONNECTION -> getString(R.string.homeConnectionErrorMessage)
                    PARSING -> getString(R.string.homeParsingErrorMessage)
                    UNKNOWN -> getString(R.string.homeUnknownErrorMessage)
                }
                textHome?.text = text
                hideProgressBar()
            }
        })
        homeViewModel.shouldShowBatteryWhiteListAlertDialog.observe(
            this.viewLifecycleOwner,
            Observer {
                if (it) {
                    presentBatteryWhitelistRequestAlertDialog()
                    homeViewModel.shouldShowBatteryWhiteListAlertDialog.value = false
                }
            })
    }

    private fun hideProgressBar() {
        progressBarHome?.isVisible = false
    }

    private fun presentBatteryWhitelistRequestAlertDialog() {
        val context = requireContext()
        val alertMessage = getString(R.string.batteryWhiteListExplanation)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_alertdialog, null)
        Glide.with(context)
            .load(R.raw.battery_whitelist)
            .into(view.findViewById(R.id.batteryWhiteListGif))
        builder.setView(view)
        builder.setMessage(alertMessage)
        builder.setPositiveButton(getString(R.string.OK)) { _, _ ->
            try {
                goToBatteryWhiteListOfTheApp(context)
            } catch (exception: ActivityNotFoundException) {
                goToBatteryWhiteList()
            }
        }
        builder.setNeutralButton(getString(R.string.alertDialogStopAsking)) { _, _ ->
            homeInteractor.onBatteryWhiteListAlertDialogNeutralButtonPressed()
        }
        builder.setNegativeButton(getString(R.string.alertDialogMaybeLater)) { _, _ ->
        }
        builder.create().show()
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