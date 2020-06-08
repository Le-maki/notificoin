package com.github.corentinc.notificoin.ui.settings

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.DropDownPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.github.corentinc.core.SettingsInteractor
import com.github.corentinc.notificoin.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment(
    private val settingsInteractor: SettingsInteractor
): PreferenceFragmentCompat(), SettingsDisplay {
    private val settingsViewModel: SettingsViewModel by viewModel()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    init {
        (settingsInteractor.settingsPresenter as SettingsPresenterImpl).settingsDisplay = this
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey)
    }

    override fun onStart() {
        addOnBackPressedCallBack()
        findPreference<Preference>(resources.getString(R.string.aboutKey))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                settingsInteractor.onAboutPreferenceClicked()
                true
            }
        findPreference<SwitchPreferenceCompat>(resources.getString(R.string.accurateNotificationsKey))?.setOnPreferenceChangeListener { _, newValue ->
            settingsInteractor.onAccurateNotificationsSwicthed(newValue as Boolean)
            true
        }
        val dropDown =
            findPreference<DropDownPreference>(resources.getString(R.string.notificationIntervalKey))
        dropDown?.title =
            resources.getString(R.string.settingsNotificationIntervalTitle) + dropDown?.entry
        dropDown?.setOnPreferenceChangeListener { _, newValue ->
            val index = dropDown.findIndexOfValue(newValue.toString())
            val entry = dropDown.entries[index]
            settingsInteractor.onNotificationIntervalPreferenceChanged(
                newValue.toString().toInt(),
                entry
            )
            true
        }
        bindViewModel()
        val context = requireContext()
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        settingsInteractor.onStart(
            powerManager != null && powerManager.isIgnoringBatteryOptimizations(
                context.packageName
            )
        )
        super.onStart()
    }

    override fun onPause() {
        onBackPressedCallback.remove()
        super.onPause()
    }

    private fun bindViewModel() {
        settingsViewModel.notificationIntervalEntry.observe(
            viewLifecycleOwner,
            Observer {
                findPreference<DropDownPreference>(resources.getString(R.string.notificationIntervalKey))?.title =
                    resources.getString(R.string.settingsNotificationIntervalTitle) + it
            })
        settingsViewModel.isAccurateNotificationChecked.observe(
            viewLifecycleOwner,
            Observer {
                displayBatteryWarningFragment()
            })
    }

    override fun displayAboutFragment() {
        findNavController().navigate(R.id.settingsToAboutAction)
    }

    override fun displayAccurateNotificationPreferenceValue(batteryWhiteListAlreadyGranted: Boolean) {
        findPreference<SwitchPreferenceCompat>(resources.getString(R.string.accurateNotificationsKey))?.isChecked =
            batteryWhiteListAlreadyGranted
    }

    private fun displayBatteryWarningFragment() {
        findNavController().navigate(R.id.settingsToBatteryWarningAction)
    }

    private fun addOnBackPressedCallBack() {
        onBackPressedCallback =
            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}