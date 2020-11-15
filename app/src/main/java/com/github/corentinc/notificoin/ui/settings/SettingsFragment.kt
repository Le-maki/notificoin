package com.github.corentinc.notificoin.ui.settings

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.preference.DropDownPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.corentinc.core.SettingsInteractor
import com.github.corentinc.core.ui.settings.SettingsDisplay
import com.github.corentinc.logger.analytics.NotifiCoinEvent.ScreenStarted
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.Screen
import com.github.corentinc.logger.analytics.NotifiCoinEventScreen.SETTINGS
import com.github.corentinc.notificoin.AnalyticsEventSender
import com.github.corentinc.notificoin.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment(
    private val settingsInteractor: SettingsInteractor
): PreferenceFragmentCompat(), SettingsDisplay {
    private val settingsViewModel: SettingsViewModel by sharedViewModel()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    init {
        (settingsInteractor.settingsPresenter as SettingsPresenterImpl).settingsDisplay = this
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey)
    }

    override fun onStart() {
        AnalyticsEventSender.sendEvent(
            ScreenStarted(
                Screen(SETTINGS)
            )
        )
        addOnBackPressedCallBack()
        findPreference<Preference>(resources.getString(R.string.aboutKey))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                settingsInteractor.onAboutPreferenceClicked()
                true
            }
        findPreference<Preference>(resources.getString(R.string.accurateNotificationsKey))?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                settingsInteractor.onAccurateNotificationsClicked()
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
        super.onStart()
    }

    override fun onPause() {
        onBackPressedCallback.remove()
        super.onPause()
    }

    private fun bindViewModel() {
        settingsViewModel.notificationIntervalEntry.observe(
            viewLifecycleOwner,
            {
                findPreference<DropDownPreference>(resources.getString(R.string.notificationIntervalKey))?.title =
                    resources.getString(R.string.settingsNotificationIntervalTitle) + it
            })
    }

    override fun displayAboutFragment() {
        findNavController().navigate(R.id.settingsToAboutAction)
    }

    override fun displayBatteryWarningFragment() {
        findNavController().navigate(R.id.settingsToBatteryWarningAction)
    }

    private fun addOnBackPressedCallBack() {
        onBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }


    override fun displayNotificationIntervalPreference(entry: String) {
        settingsViewModel.notificationIntervalEntry.value = entry
    }
}