package com.github.corentinc.notificoin.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.corentinc.notificoin.R
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment: Fragment() {
    private val settingsViewModel: SettingsViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindViewModel()
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onStart() {
        settingsViewModel.text.value = settingsViewModel.text.value
        super.onStart()
    }

    private fun bindViewModel() {
        settingsViewModel.text.observe(
            viewLifecycleOwner,
            Observer {
                settingsFragmentText.text = it
            })
    }
}