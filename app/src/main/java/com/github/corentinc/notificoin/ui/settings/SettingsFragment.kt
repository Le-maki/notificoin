package com.github.corentinc.notificoin.ui.settings

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.preference.PreferenceFragmentCompat
import com.github.corentinc.core.SettingsInteractor
import com.github.corentinc.notificoin.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment(
    private val settingsInteractor: SettingsInteractor
): PreferenceFragmentCompat(), SettingsDisplay {
    private val settingsViewModel: SettingsViewModel by viewModel()

    init {
        (settingsInteractor.settingsPresenter as SettingsPresenterImpl).settingsDisplay = this
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey)
    }

    override fun onStart() {
        bindViewModel()
        settingsInteractor.onStart()
        super.onStart()
    }

    private fun bindViewModel() {
        settingsViewModel.text.observe(
            viewLifecycleOwner,
            Observer {
                // settingsFragmentAboutText.text = it
            })
    }
}