package com.github.corentinc.notificoin.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.corentinc.core.SettingsInteractor
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.ChildFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment(
    private val settingsInteractor: SettingsInteractor
): ChildFragment(), SettingsDisplay {
    private val settingsViewModel: SettingsViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindViewModel()
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    init {
        (settingsInteractor.settingsPresenter as SettingsPresenterImpl).settingsDisplay = this
    }

    override fun onStart() {
        settingsFragmentAboutText.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_settings_to_navigation_about)
        }
        settingsInteractor.onStart()
        super.onStart()
    }

    private fun bindViewModel() {
        settingsViewModel.text.observe(
            viewLifecycleOwner,
            Observer {
                settingsFragmentAboutText.text = it
            })
    }
}